package com.egg.ih.biz.api.service;

import com.egg.ih.biz.api.vo.IhClassVO;

/**
 * @author Administrator
 */
public interface ApiService {
    /**
     * 保存接口类
     * @param ihClassVO
     */
    void createClass(IhClassVO ihClassVO);
}
