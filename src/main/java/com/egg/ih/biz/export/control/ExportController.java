package com.egg.ih.biz.export.control;

import com.egg.ih.biz.service.ExportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value = "/rest/exportService/v1")
@Api(value = "/rest/exportService/v1", tags = {"文件导出类"})
public class ExportController {
    @Autowired
    private ExportService exportService;

    @ApiOperation(notes = "/word", value = "导出word")
    @PostMapping(value = "/word")
    public void exportWord() throws Exception{
        exportService.exportWord();
    }
}
