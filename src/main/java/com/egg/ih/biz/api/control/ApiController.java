package com.egg.ih.biz.api.control;

import com.egg.ih.biz.api.service.ApiService;
import com.egg.ih.biz.api.vo.ClassVO;
import com.egg.ih.biz.api.vo.InterfaceVO;
import com.egg.ih.biz.api.vo.params.BodyVO;
import com.egg.ih.biz.api.vo.params.HeaderVO;
import com.egg.ih.biz.api.vo.params.QueryVO;
import com.egg.ih.biz.api.vo.params.ResponseVO;
import com.egg.ih.log.annotation.LogOperation;
import com.egg.ih.util.errorcode.DefaultErrorCode;
import com.egg.ih.util.response.BaseResponse;
import com.egg.ih.util.response.ResponseBuilder;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value = "/rest/apiService/v1")
@Api(value = "/rest/apiService/v1", tags = {"接口管理类"})
public class ApiController {
    @Autowired
    private ApiService apiService;
    private static final Gson gson = new Gson();

    /**
     * 创建接口函数时入参接口对象
     */
    private static final String INTERFACE_VO = "interfaceVO";
    private static final String PARAMS_VO = "queryVO";
    private static final String HEADERS_VO = "headerVO";
    private static final String BODY_VO = "bodyVO";
    private static final String RESPONSE_VO = "responseVO";


    @ApiOperation(notes = "/class", value = "创建接口类")
    @ApiImplicitParam(name = "ihClassVO", value = "接口类", dataType = "com.egg.ih.biz.api.vo.IhClassVO", paramType = "body", required = true)
    @PostMapping(value = "/class")
    public BaseResponse<Boolean> createClass(@RequestBody ClassVO classVO) {

        return ResponseBuilder.build(DefaultErrorCode.SUCCESS, apiService.saveClass(classVO));
    }

    @ApiOperation(notes = "/interface", value = "创建接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "封装map对象, 包含params、headers、body、response", dataType = "Map", paramType = "body")
    })
    @PostMapping(value = "/interface")
    public BaseResponse<Boolean> createInterface(@RequestBody Map<String, Object> map) {

        ParamClass paramClass = this.assembleMap(map);

        return ResponseBuilder.build(DefaultErrorCode.SUCCESS, apiService.saveInterface(paramClass.getInterfaceVO(),
                paramClass.getQuery(),
                paramClass.getHeader(),
                paramClass.getBody(),
                paramClass.getResponse()));
    }

    @ApiOperation(notes = "/classes", value = "查询接口类列表")
    @GetMapping(value = "/classes")
    public BaseResponse<List<ClassVO>> findClasses() {

        return ResponseBuilder.build(DefaultErrorCode.SUCCESS, apiService.findClasses());
    }

    /*@ApiOperation(notes = "/interface", value = "根据接口名查询接口类列表, 以接口类包裹形式展示")
    @ApiImplicitParam(name = "interfaceName", value = "根据类名查询接口类", dataType = "String", paramType = "query", required = true)
    @GetMapping(value = "/interface")
    public BaseResponse<List<IhClassVO>> findInterfaceByName(@RequestParam String interfaceName) {
        List<IhClassVO> list = new ArrayList<>();

        return ResponseBuilder.build(DefaultErrorCode.SUCCESS, list);
    }*/

    @ApiOperation(notes = "/interfaces", value = "根据类主键查询接口列表")
    @ApiImplicitParam(name = "classId", value = "接口类主键", dataType = "String", paramType = "query", required = true)
    @GetMapping(value = "/interfaces")
    public BaseResponse<List<InterfaceVO>> findInterfaceByClassId(@RequestParam String classId) {
        return ResponseBuilder.build(DefaultErrorCode.SUCCESS, apiService.findInterfacesByClassId(classId));
    }

    @LogOperation(service = "apiService", module = "api", description = "根据接口主键查询接口内容")
    @ApiOperation(notes = "/interface/{interfaceId}", value = "根据接口主键查询接口内容")
    @ApiImplicitParam(name = "interfaceId", value = "接口主键", dataType = "String", paramType = "path", required = true)
    @GetMapping(value = "/interface/{interfaceId}")
    public BaseResponse<InterfaceVO> findInterfaceById(@PathVariable String interfaceId) {
        return ResponseBuilder.build(DefaultErrorCode.SUCCESS, apiService.findInterfaceById(interfaceId));
    }

    @ApiOperation(notes = "/class/{classId}", value = "根据类主键删除接口类")
    @ApiImplicitParam(name = "classId", value = "接口主键", dataType = "String", paramType = "path", required = true)
    @DeleteMapping(value= "/class/{classId}")
    public BaseResponse deleteClassById(@PathVariable String classId) {
        apiService.deleteClassById(classId);
        return ResponseBuilder.build(DefaultErrorCode.SUCCESS);
    }

    @ApiOperation(notes = "/interface/{interfaceId}", value = "根据类主键删除接口")
    @ApiImplicitParam(name = "interface", value = "接口主键", dataType = "String", paramType = "path", required = true)
    @DeleteMapping(value= "/interface/{interfaceId}")
    public BaseResponse deleteInterfaceById(@PathVariable String interfaceId) {
        apiService.deleteInterfaceById(interfaceId);
        return ResponseBuilder.build(DefaultErrorCode.SUCCESS);
    }

    @ApiOperation(notes = "/class/{classId}", value = "修改接口类")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "classId", value = "接口类主键", dataType = "String", paramType = "path", required = true),
        @ApiImplicitParam(name = "className", value = "接口类名称", dataType = "String", paramType = "query", required = true)
    })
    @PutMapping(value = "/class/{classId}")
    public BaseResponse<Boolean> updateClass(@PathVariable String classId, @RequestParam String className) {
        return ResponseBuilder.build(DefaultErrorCode.SUCCESS, apiService.updateClass(classId, className));
    }

    @ApiOperation(notes = "/interface/{interfaceId}", value = "修改接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "interfaceId", value = "接口主键", dataType = "String", paramType = "path", required = true),
            @ApiImplicitParam(name = "interfaceVO", value = "接口类", dataType = "IhInterfaceVO", paramType = "query", required = true),
            @ApiImplicitParam(name = "paramsVO", value = "params参数", dataType = "IhParamsVO", paramType = "query"),
            @ApiImplicitParam(name = "headerVO", value = "headers参数", dataType = "IhHeaderVO", paramType = "query"),
            @ApiImplicitParam(name = "bodyVO", value = "body参数", dataType = "IhBodyVO", paramType = "query"),
            @ApiImplicitParam(name = "responseVO", value = "返回值类", dataType = "IhResponseVO", paramType = "query")
    })
    @PutMapping(value = "/interface/{interfaceId}")
    public BaseResponse updateInterfaceById(@PathVariable String interfaceId, @RequestBody Map<String, Object> map) {
        ParamClass paramClass = this.assembleMap(map);

        paramClass.getInterfaceVO().setInterfaceId(interfaceId);
        return ResponseBuilder.build(DefaultErrorCode.SUCCESS, apiService.updateInterface(paramClass.getInterfaceVO(),
                paramClass.getQuery(),
                paramClass.getHeader(),
                paramClass.getBody(),
                paramClass.getResponse()));
    }


    public ParamClass assembleMap(Map<String, Object> map) {
        InterfaceVO interfaceVO = null;
        QueryVO queryVO = null;
        HeaderVO headerVO = null;
        BodyVO bodyVO = null;
        ResponseVO responseVO = null;

        if(map.get(INTERFACE_VO) != null) {
            interfaceVO = gson.fromJson(gson.toJson(map.get(INTERFACE_VO)), InterfaceVO.class);
        }
        if(map.get(PARAMS_VO) != null) {
            queryVO = gson.fromJson(gson.toJson(map.get(PARAMS_VO)), QueryVO.class);

        }
        if(map.get(HEADERS_VO) != null) {
            headerVO = gson.fromJson(gson.toJson(map.get(HEADERS_VO)), HeaderVO.class);

        }
        if(map.get(BODY_VO) != null) {
            bodyVO = gson.fromJson(gson.toJson(map.get(BODY_VO)), BodyVO.class);
        }

        if(map.get(RESPONSE_VO) != null) {
            responseVO = gson.fromJson(gson.toJson(map.get(RESPONSE_VO)), ResponseVO.class);
        }
        ParamClass paramClass = new ParamClass();
        paramClass.setInterfaceVO(interfaceVO);
        paramClass.setQuery(queryVO);
        paramClass.setHeader(headerVO);
        paramClass.setBody(bodyVO);
        paramClass.setResponse(responseVO);

        return paramClass;
    }

    @ApiOperation(notes = "/class/{classId}", value = "根据主键查询接口类")
    @ApiImplicitParam(name = "classId", value = "接口类主键", dataType = "String", paramType = "path")
    @GetMapping(value = "/class/{classId}")
    public BaseResponse<ClassVO> findClassById(@PathVariable String classId) {
        return ResponseBuilder.build(DefaultErrorCode.SUCCESS, apiService.findClassById(classId));
    }

    @Data
    class ParamClass {
        private InterfaceVO interfaceVO;
        private QueryVO query;
        private HeaderVO header;
        private BodyVO body;
        private ResponseVO response;
    }

}
