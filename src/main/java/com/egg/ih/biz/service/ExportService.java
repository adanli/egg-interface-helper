package com.egg.ih.biz.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * @author Administrator
 */
public interface ExportService {
    /**
     * 导出Word
     */
    XWPFDocument exportWord() throws Exception;
}
