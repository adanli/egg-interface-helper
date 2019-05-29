package com.egg.ih.biz.export.control;

import com.egg.ih.biz.service.ExportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value = "/rest/exportService/v1")
@Api(value = "/rest/exportService/v1", tags = {"文件导出类"})
public class ExportController {
    @Autowired
    private ExportService exportService;

    @ApiOperation(notes = "/word", value = "导出word文档")
    @PostMapping(value = "/word")
    public void exportWord(HttpServletResponse response) throws Exception{
        XWPFDocument document = exportService.exportWord();

        response.setContentType("application/msword;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=gvmbpm.docx");
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");

        document.write(response.getOutputStream());
        document.close();

    }

}
