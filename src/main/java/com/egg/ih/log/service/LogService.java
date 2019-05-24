package com.egg.ih.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.ih.db.model.IhHiOper;
import com.egg.ih.log.vo.HiOperVO;

/**
 * @author adanl
 */
public interface LogService extends IService<IhHiOper> {
    /**
     * 保存历史记录
     * @param hiOperVO
     */
    void saveLog(HiOperVO hiOperVO);
}
