package com.egg.ih.biz.service.impl;

import com.egg.ih.biz.service.ExportService;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Administrator
 */
@Service
public class ExportServiceImpl implements ExportService {

    @Override
    public void exportWord() throws Exception{
        XWPFDocument document= new XWPFDocument();
        
        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(new File("D:\\temp\\接口文档.docx"));
        // 添加标题
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();
        run.setText("1.基础信息维护");
        run.setFontSize(16);
        run.setBold(true);
        run.setFontFamily("宋体(中文正文)");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("1.1车型与品牌关系");
        run.setFontSize(12);
        run.setBold(true);
        run.setFontFamily("宋体(中文正文)");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setSpacingBefore(4);
        run = paragraph.createRun();
        run.setText("查询和配置汽车车型与汽车品牌之间的关系2。");
        run.setFontSize(10);
        run.setCharacterSpacing(2);
        run.setFontFamily("宋体(中文正文)");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("1.1.1 查询车型与品牌的关系");
        run.setFontSize(10);
        run.setBold(true);
        run.setFontFamily("宋体(中文正文)");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("接口说明");
        run.setFontSize(10);
        run.setFontFamily("宋体(中文正文)");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("根据车型代码和品牌名称模糊查询车型与品牌的关系。");
        run.setFontSize(10);
        run.setFontFamily("宋体(中文正文)");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("接口版本");
        run.setFontSize(10);
        run.setFontFamily("宋体(中文正文)");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("v1");
        run.setFontSize(10);
        run.setFontFamily("宋体(中文正文)");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("接口地址。");
        run.setFontSize(10);
        run.setFontFamily("宋体(中文正文)");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("/rest/carBrandService/v1/carBrandRels。");
        run.setFontSize(10);
        run.setFontFamily("宋体(中文正文)");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("数据提交方式");
        run.setFontSize(10);
        run.setFontFamily("宋体(中文正文)");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("GET");
        run.setFontSize(10);
        run.setFontFamily("宋体(中文正文)");

        paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        run = paragraph.createRun();
        run.setText("输入参数");
        run.setFontSize(10);
        run.setFontFamily("宋体(中文正文)");

        XWPFTable table = document.createTable();
        table.getCTTbl().addNewTblPr().addNewTblW().setType(STTblWidth.DXA);

        XWPFTableRow row = table.createRow();
        XWPFTableCell cell = row.addNewTableCell();
        cell.setText("参数名称");
        

        cell = row.addNewTableCell();
        cell.setText("数据类型");
        

        cell = row.addNewTableCell();
        cell.setText("属性描述");
        

        cell = row.addNewTableCell();
        cell.setText("存储位置");
        

        cell = row.addNewTableCell();
        cell.setText("是否必填");
        

        cell = row.addNewTableCell();
        cell.setText("最大长度");
        

        cell = row.addNewTableCell();
        cell.setText("备注");
        

        document.write(out);
        out.close();

    }

}
