package com.egg.ih.log.aspect;

import com.egg.ih.log.annotation.LogOperation;
import com.egg.ih.log.constant.LogConstant;
import com.egg.ih.log.dto.HiOperDTO;
import com.egg.ih.log.service.LogService;
import com.egg.ih.log.utils.LogOperationUtils;
import com.egg.ih.util.ex.BaseRuntimeException;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Aspect
@Component
public class LogAspect {
    private final Gson gson = new Gson();
    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.egg.ih.log.annotation.LogOperation)")
    public void logPointCut(){}

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {

        HiOperDTO log = new HiOperDTO();
        advance(log, point);

        LogOperationUtils.set(log);

        Object result = point.proceed();

        LogOperationUtils.remove();
        logService.insertLog(log);

        return result;
    }

    public void advance(HiOperDTO log, ProceedingJoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogOperation logOperation = method.getAnnotation(LogOperation.class);

        String service = "", module = "", description = "";
        if(logOperation != null){
            service = logOperation.service();
            module = logOperation.module();
            description = logOperation.description();
        }
        //类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        //请求的参数
        Object[] args = joinPoint.getArgs();
        /*List<String> list = new ArrayList<>(args.length);
        if(args.length == 0){
            log.setOperateArgs("{}");
        }else{
            for(Object arg : args){
                list.add(gson.toJson(arg).replaceAll(",", ";"));
            }
            String str = list.toString();
            log.setOperateArgs(str.substring(1, str.length()-1));
        }*/
        if(args.length > 0) {
            log.setOperateArgs((String) args[0]);
        }

        log.setService(service);
        log.setModule(module);
        log.setDescription(description);
        log.setOperateClass(className);
        log.setOperateMethod(methodName);

        log.setLogState(LogConstant.LOG_STATE_SUCCESS);
        //待添加用户、ip
        log.setOperateIp(ip);
    }

    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void afterThrowing(Throwable e){
        HiOperDTO log = LogOperationUtils.remove();
        log.setLogState(LogConstant.LOG_STATE_ERROR);

        if(e instanceof BaseRuntimeException){
            BaseRuntimeException exception = (BaseRuntimeException) e;
            log.setErrorCode(exception.getErrCode());
            log.setErrorMsg(exception.getErrMsg());
        }

        logService.insertLog(log);
    }

}
