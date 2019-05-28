package com.egg.ih.biz.service.impl;

import com.egg.ih.biz.service.ExportService;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.function.Function;

/**
 * @author Administrator
 */
@Service
public class ExportServiceImpl implements ExportService {

    private static final String SONGTI = "";
    private static final int DIRECTORY_FONT_SIZE = 16;
    private static final int CLASS_FONT_SIZE = 14;
    private static final int INTEFFACE_FONT_SIZE = 12;
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    private Function<XWPFDocument, XWPFParagraph> directoryParagraph = document -> {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        return paragraph;
    };

    private Function<XWPFDocument, XWPFParagraph> classParagraph = document -> {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        return paragraph;
    };

    private Function<XWPFDocument, XWPFParagraph> interfaceParagraph = document -> {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        return paragraph;
    };

    private Function<XWPFParagraph, XWPFRun> directoryRun = paragraph -> {
        XWPFRun run = paragraph.createRun();
        run.setFontSize(DIRECTORY_FONT_SIZE);
        run.setFontFamily(SONGTI);
        run.setBold(true);
        return run;
    };

    private Function<XWPFParagraph, XWPFRun> classRun = paragraph -> {
        XWPFRun run = paragraph.createRun();
        run.setFontFamily(SONGTI);
        run.setFontSize(CLASS_FONT_SIZE);
        return run;
    };

    private Function<XWPFParagraph, XWPFRun> interfaceRun = paragraph -> {
        XWPFRun run = paragraph.createRun();
        run.setFontFamily(SONGTI);
        run.setFontSize(INTEFFACE_FONT_SIZE);
        return run;
    };

    private Function<XWPFDocument, XWPFTable> paramTable = document -> {
        XWPFTable table = document.createTable();
        return table;
    };

    private Function<XWPFTable, XWPFTableRow> paramRow = table -> table.createRow();

    private Function<XWPFTableRow, XWPFTableCell> paramCell = row -> row.createCell();

    @Override
    public void exportWord() throws Exception{
        XWPFDocument document= new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(new File("D:\\temp\\接口文档.docx"));

        XWPFParagraph dParagraph = directoryParagraph.apply(document);
        XWPFRun dRun = directoryRun.apply(dParagraph);
        dRun.setText("1. 基础信息维护");

        XWPFParagraph cParagraph = classParagraph.apply(document);
        XWPFRun cRun = classRun.apply(cParagraph);
        cRun.setText("1.1 车型与品牌关系");
        cRun.setBold(true);
        newLine(cRun);
        cRun = classRun.apply(cParagraph);
        cRun.addTab();
        cRun.setText("查询和配置汽车车型与汽车品牌之间的关系。");
        cRun.setBold(false);
        cRun.setFontSize(12);

        XWPFParagraph iParagraph = interfaceParagraph.apply(document);
        XWPFRun iRun = interfaceRun.apply(iParagraph);
        iRun.setBold(true);
        iRun.setText("1.1.1 查询车型与品牌的关系");
        newLine(iRun);
        iRun = interfaceRun.apply(iParagraph);
        setInterfaceInfo(iRun, "接口说明");
        iRun = interfaceRun.apply(iParagraph);
        iRun.addTab();
        setInterfaceInfo(iRun, "根据车型代码和品牌名称模糊查询车型与品牌的关系。");
        iRun = interfaceRun.apply(iParagraph);
        setInterfaceInfo(iRun, "接口版本");
        iRun = interfaceRun.apply(iParagraph);
        iRun.addTab();
        setInterfaceInfo(iRun, "v1");
        iRun = interfaceRun.apply(iParagraph);
        setInterfaceInfo(iRun, "接口地址");
        iRun = interfaceRun.apply(iParagraph);
        iRun.addTab();
        setInterfaceInfo(iRun, "/rest/carBrandService/v1/carBrandRels");
        iRun = interfaceRun.apply(iParagraph);
        setInterfaceInfo(iRun, "数据提交方式");
        iRun = interfaceRun.apply(iParagraph);
        iRun.addTab();
        setInterfaceInfo(iRun, GET);
        iRun = interfaceRun.apply(iParagraph);
        iRun.addTab();
        setInterfaceInfo(iRun, "输入参数");
        XWPFTable table = paramTable.apply(document);

        CTTblWidth infoTableWidth = table.getCTTbl().addNewTblPr().addNewTblW();
        infoTableWidth.setType(STTblWidth.DXA);
        infoTableWidth.setW(BigInteger.valueOf(9072));

        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        cell.setText("参数名称");
        cell = row.createCell();
        cell.setText("数据类型");
        cell = row.createCell();
        cell.setText("属性描述");
        cell = row.createCell();
        cell.setText("存储位置");
        cell = row.createCell();
        cell.setText("是否必填");
        cell = row.createCell();
        cell.setText("最大长度");
        cell = row.createCell();
        cell.setText("备注");
        row = table.insertNewTableRow(1);
        cell = row.createCell();
        cell.setText("carModel");
        cell = row.createCell();
        cell.setText("String");
        cell = row.createCell();
        cell.setText("车型代码");
        cell = row.createCell();
        cell.setText("query");
        cell = row.createCell();
        cell.setText("否");
        cell = row.createCell();
        cell.setText("32");
        cell = row.createCell();
        cell.setText("");

        document.write(out);
        out.close();
        document.close();

    }

    /**
     * 换行
     */
    private void newLine(XWPFRun run) {
        run.addBreak(BreakType.TEXT_WRAPPING);
    }

    private void setInterfaceInfo(XWPFRun run, String content) {
        run.setText(content);
        newLine(run);
    }

}
