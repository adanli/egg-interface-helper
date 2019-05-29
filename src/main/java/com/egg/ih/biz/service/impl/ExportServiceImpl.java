package com.egg.ih.biz.service.impl;

import com.egg.ih.biz.service.ExportService;
import com.egg.ih.constant.BaseConstant;
import com.egg.ih.db.mapper.IhDirectoryMapper;
import com.egg.ih.db.model.IhClass;
import com.egg.ih.db.model.IhDirectory;
import com.egg.ih.db.model.IhInterface;
import com.egg.ih.db.model.IhParams;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.List;
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
    private static final int ROW_SPACING = 15;

    @Autowired
    private IhDirectoryMapper ihDirectoryMapper;

    private Function<XWPFDocument, XWPFParagraph> directoryParagraph = document -> {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationHanging(2);

        return paragraph;
    };

    private Function<XWPFDocument, XWPFParagraph> classParagraph = document -> {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationHanging(2);

        return paragraph;
    };

    private Function<XWPFDocument, XWPFParagraph> interfaceParagraph = document -> {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationHanging(2);

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
        run.setTextPosition(ROW_SPACING);
        return run;
    };

    private Function<XWPFDocument, XWPFTable> paramTable = document -> {
        XWPFTable table = document.createTable();
        return table;
    };

    @Override
    public XWPFDocument exportWord() throws Exception{
        XWPFDocument document= new XWPFDocument();

//        FileOutputStream out = new FileOutputStream(new File("D:\\temp\\接口文档.docx"));

        List<IhDirectory> directories = ihDirectoryMapper.findDirectory();

        int directorySize = directories.size();
        IhDirectory directory = null;
        for(int directoryIndex=1; directoryIndex<=directorySize; directoryIndex++) {
            directory = directories.get(directoryIndex-1);
            XWPFParagraph dParagraph = directoryParagraph.apply(document);
            XWPFRun dRun = directoryRun.apply(dParagraph);
            dRun.setText(directoryIndex + ". " + directory.getName());

            int classSize = directory.getClasses().size();
            IhClass clazz = null;
            for(int classIndex=1; classIndex<=classSize; classIndex++) {
                clazz = directory.getClasses().get(classIndex-1);

                XWPFParagraph cParagraph = classParagraph.apply(document);
                XWPFRun cRun = classRun.apply(cParagraph);
                cRun.setText(directoryIndex + "." + classIndex + " " + clazz.getName());
                cRun.setBold(true);
                newLine(cRun);
                cRun = classRun.apply(cParagraph);
                cRun.addTab();
                cRun.setText(clazz.getDescription());
                cRun.setBold(false);
                cRun.setFontSize(11);

                int interfaceSize = clazz.getInterfaces().size();
                IhInterface inter = null;
                for(int interfaceIndex=1; interfaceIndex<=interfaceSize; interfaceIndex++) {
                    inter = clazz.getInterfaces().get(interfaceIndex-1);
                    XWPFParagraph iParagraph = interfaceParagraph.apply(document);
                    XWPFRun iRun = interfaceRun.apply(iParagraph);
                    iRun.setBold(true);
                    iRun.setText(directoryIndex + "." + classIndex + "." + interfaceIndex + " " + inter.getName());
                    newLine(iRun);
                    iRun = interfaceRun.apply(iParagraph);
                    setInterfaceInfo(iRun, "接口说明");
                    iRun = interfaceRun.apply(iParagraph);
                    iRun.addTab();
                    setInterfaceInfo(iRun, inter.getDescription());
                    iRun = interfaceRun.apply(iParagraph);
                    setInterfaceInfo(iRun, "接口版本");
                    iRun = interfaceRun.apply(iParagraph);
                    iRun.addTab();
                    setInterfaceInfo(iRun, "v1");
                    iRun = interfaceRun.apply(iParagraph);
                    setInterfaceInfo(iRun, "接口地址");
                    iRun = interfaceRun.apply(iParagraph);
                    iRun.addTab();
                    setInterfaceInfo(iRun, inter.getUrl());
                    iRun = interfaceRun.apply(iParagraph);
                    setInterfaceInfo(iRun, "数据提交方式");
                    iRun = interfaceRun.apply(iParagraph);
                    iRun.addTab();
                    setInterfaceInfo(iRun, inter.getType());
                    iRun = interfaceRun.apply(iParagraph);
                    iRun.setText("输入参数");
                    XWPFTable table = paramTable.apply(document);

                    CTTblWidth infoTableWidth = table.getCTTbl().addNewTblPr().addNewTblW();
                    infoTableWidth.setType(STTblWidth.DXA);
                    infoTableWidth.setW(BigInteger.valueOf(9072));

                    XWPFTableRow row = table.getRow(0);
                    XWPFTableCell cell = row.getCell(0);
                    createTitle(row, cell, false);

                    int position = this.assambleTable(inter, document, table, row, cell, iRun,false, 0);

                    XWPFParagraph returnParamParagraph = interfaceParagraph.apply(document);
                    iRun = interfaceRun.apply(returnParamParagraph);
                    iRun.setText("返回值");


                    if(position != -1) {
                        table = paramTable.apply(document);

                        infoTableWidth = table.getCTTbl().addNewTblPr().addNewTblW();
                        infoTableWidth.setType(STTblWidth.DXA);
                        infoTableWidth.setW(BigInteger.valueOf(9072));

                        row = table.getRow(0);
                        cell = row.getCell(0);

                        createTitle(row, cell, true);
                        this.assambleTable(inter, document, table, row, cell, iRun, true, position);

                    }else {
                        iRun.addBreak(BreakType.TEXT_WRAPPING);
                        iRun = interfaceRun.apply(returnParamParagraph);
                        iRun.addTab();
                        iRun.setText("无");
                    }

                    newLine(iRun);

                }

            }

        }


//        document.write(out);
//        out.close();
//        document.close();
        return document;
    }

    /**
     * 拼装table和example
     * @param inter 接口
     * @param document doc
     * @param table 表格
     * @param row 行
     * @param cell 单元格
     * @param iRun 渲染
     * @param isResponse 是否返回值
     * @param start 起始点
     * @return
     * @throws IOException
     */
    private int assambleTable(IhInterface inter, XWPFDocument document, XWPFTable table, XWPFTableRow row, XWPFTableCell cell, XWPFRun iRun, boolean isResponse, int start) throws IOException {

        IhParams param = null;
        String example = null;
        int position = -1;
        for(int i=start; i<inter.getParams().size(); i++) {
            param = inter.getParams().get(i);
            if (param.getFlag().equals(BaseConstant.例子位置.example.getCode())) {
                example = new String(param.getExample(), Charset.defaultCharset());
                continue;
            }
            if (!isResponse && param.getPosition().equals(BaseConstant.参数存储位置.RESPONSE.getCode())) {
                position = i;
                break;
            }

            row = table.insertNewTableRow(table.getNumberOfRows());
            cell = row.createCell();
            this.setCellText(cell, param.getCode());
            cell = row.createCell();
            this.setCellText(cell, param.getType());
            cell = row.createCell();
            this.setCellText(cell, param.getDescription());
            if(!isResponse) {
                cell = row.createCell();
                this.setCellText(cell, BaseConstant.参数存储位置.getNameByCode(param.getPosition()));
            }
            cell = row.createCell();
            this.setCellText(cell, BaseConstant.是否必填.getNameByCode(param.getNecessary()));
            cell = row.createCell();
            if (param.getMaxLength() != null) {
                this.setCellText(cell, String.valueOf(param.getMaxLength()));
            } else {
                cell.setText("");
            }
            cell = row.createCell();
            this.setCellText(cell, param.getRemark());
        }

        if(example != null) {
            XWPFParagraph exampleParagraph = interfaceParagraph.apply(document);
            iRun = interfaceRun.apply(exampleParagraph);
            iRun.setText("例: ");
            iRun = interfaceRun.apply(exampleParagraph);
            iRun.addTab();
            setInterfaceInfo(iRun, example);
        }

        return position;
    }

    private void setCellText(XWPFTableCell cell, String text) {
        XWPFRun run = cell.getParagraphs().get(0).createRun();
        run.setText(text);
        run.setFontSize(INTEFFACE_FONT_SIZE);
        run.setFontFamily(SONGTI);
        run.setTextPosition(ROW_SPACING);
    }

    private void createTitle(XWPFTableRow row, XWPFTableCell cell, boolean isResponse) {
        this.setCellText(cell, "参数名称");
        cell = row.createCell();
        this.setCellText(cell, "数据类型");
        cell = row.createCell();
        this.setCellText(cell, "属性描述");
        if(!isResponse) {
            cell = row.createCell();
            this.setCellText(cell, "存储位置");
        }
        cell = row.createCell();
        this.setCellText(cell, "是否必填");
        cell = row.createCell();
        this.setCellText(cell, "最大长度(Byte)");
        cell = row.createCell();
        this.setCellText(cell, "备注");
    }

    /**
     *
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
