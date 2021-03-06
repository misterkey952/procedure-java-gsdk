package century.gsdk.tools.ods;

import century.gsdk.tools.ToolLogger;
import century.gsdk.tools.str.StringTool;
import century.gsdk.tools.xml.XMLTool;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C) <2019>  <Century>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * <p>
 * Author's Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class MSODSFile extends ODSFile{

    
    public MSODSFile(String name, String directory) {
        super(name, directory);
    }


    private void fillRecord(Row row,ODSSheet odsSheet){
        ODSRecord record=new ODSRecord(odsSheet);
        for(ODSHead head:odsSheet.getOdsHeads()){
            Cell cell=row.getCell(head.getIndex());
            if(cell==null){
                record.addKV(head.getKey(),"");
            }else{
                record.addKV(head.getKey(),cell.toString());
            }

        }
        odsSheet.addRecord(record);
    }

    @Override
    public void inXLSX() {
        Workbook workbook=null;
        try {
            workbook=new XSSFWorkbook(new FileInputStream(new File(getPathWithNameXLSX())));
            int sheetCount=workbook.getNumberOfSheets();
            for(int i=0;i<sheetCount;i++){
                Sheet sheet=workbook.getSheetAt(i);
                ODSSheet odsSheet=new ODSSheet(this);
                odsSheet.setName_zh(sheet.getSheetName());
                int rowIndex=inHead(sheet,odsSheet);
                int rowCount=sheet.getLastRowNum();
                for(;rowIndex<=rowCount;rowIndex++){
                    Row row=sheet.getRow(rowIndex);
                    fillRecord(row,odsSheet);
                }
                addSheet(odsSheet);
            }
        } catch (Exception e) {
            ToolLogger.GTPL.error("inXLSX err",e);
        }finally {
            if(workbook!=null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    ToolLogger.GTPL.error("inXLSX workbook.close() err",e);
                }
            }
        }
    }

    private int inHead(Sheet sheet,ODSSheet odsSheet){
        Row keyRow=sheet.getRow(0);
        Row nameRow=sheet.getRow(1);
        Comment msg=keyRow.getCell(0).getCellComment();
        int cellCount=Integer.parseInt(msg.getString().getString());
        for(int i=0;i<cellCount;i++){
            Cell keyCell=keyRow.getCell(i);
            Cell nameCell=nameRow.getCell(i);
            odsSheet.addHead(keyCell.getStringCellValue(),
                    nameCell.getStringCellValue(),
                    nameCell.getCellComment()==null?"":nameCell.getCellComment().getString().getString());
        }

        Cell classCell=nameRow.getCell(cellCount);
        odsSheet.setName_en(classCell.getStringCellValue());
        return 2;
    }


    private int outHead(Sheet sheet,Drawing drawing,ODSSheet odsSheet){
        Row keyRow=sheet.createRow(0);
        Row nameRow=sheet.createRow(1);
        Comment msg = drawing.createCellComment(new XSSFClientAnchor(0, 0, 0,0, (short) 3, 3, (short) 5, 6));
        msg.setString(new XSSFRichTextString(String.valueOf(odsSheet.getOdsHeads().size())));
        for(ODSHead odsHead:odsSheet.getOdsHeads()){
            Cell keyCell=keyRow.createCell(odsHead.getIndex());
            Cell nameCell=nameRow.createCell(odsHead.getIndex());
            if(odsHead.getIndex()==0){
                keyCell.setCellComment(msg);
            }
            keyCell.setCellValue(odsHead.getKey());
            nameCell.setCellValue(odsHead.getName());
            if(!StringTool.SPACE.equals(odsHead.getDes())){
                Comment comment = drawing.createCellComment(new XSSFClientAnchor(0, 0, 0,0, (short) 3, 3, (short) 5, 6));
                comment.setString(new XSSFRichTextString(odsHead.getDes()));
                nameCell.setCellComment(comment);
            }
        }
        Cell classCell=nameRow.createCell(odsSheet.getOdsHeads().size());
        classCell.setCellValue(odsSheet.getName_en());


        return 2;
    }

    @Override
    public void outXLSX() {
        Workbook workbook=null;
        try{
            workbook=new XSSFWorkbook();
            for(ODSSheet odsSheet:sheetList){
                Sheet sheet=workbook.createSheet(odsSheet.getName_zh());
                Drawing drawing=sheet.createDrawingPatriarch();
                int rowIndex=outHead(sheet,drawing,odsSheet);
                for(ODSRecord record:odsSheet.getRecordList()){
                    Row row=sheet.createRow(rowIndex);
                    for(ODSKeyValue kv:record.getKvList()){
                        ODSHead odsHead=odsSheet.getHead(kv.getKey());
                        Cell cell=row.createCell(odsHead.getIndex());
                        cell.setCellValue(kv.getValue());
                    }
                    rowIndex++;
                }
            }

            File dir=new File(directory);
            if(!dir.exists()){
                dir.mkdirs();
            }



            File file=new File(getPathWithNameXLSX());
            if(file.exists()){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                if(!file.renameTo(new File(getPathWithNameXLSX()+"."+sdf.format(new Date())))){
                    ToolLogger.GTPL.error("outXLSX err,there is a same file and can't rename the old file {}",file.getName());
                    return;
                }
            }
            workbook.write(new FileOutputStream(file));
        }catch (Exception e){
            ToolLogger.GTPL.error("outXLSX err",e);
        }finally {
            if(workbook!=null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    ToolLogger.GTPL.error("outXLSX workbook.close() err",e);
                }
            }
        }

    }

    @Override
    public void outXML() {
        Document document= DocumentHelper.createDocument();
        OutputFormat format = OutputFormat.createPrettyPrint();

        XMLWriter writer=null;
        try{

            Element root=document.addElement("root");
            for(ODSSheet sheet:sheetList){
                sheet.encodeElement(root);
            }


            File dir=new File(directory);
            if(!dir.exists()){
                dir.mkdirs();
            }


            File file=new File(getPathWithNameXML());
            if(file.exists()){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                if(!file.renameTo(new File(getPathWithNameXML()+"."+sdf.format(new Date())))){
                    ToolLogger.GTPL.error("outXLSX err,there is a same file and can't rename the old file {}",file.getName());
                    return;
                }
            }


            writer = new XMLWriter( new FileOutputStream(file), format);
            writer.write(document);
            writer.flush();
        }catch (Exception e){
            ToolLogger.GTPL.error("outXML err",e);
        }finally{
            try {
                if(writer!=null){
                    writer.close();
                }

            } catch (IOException e) {
                ToolLogger.GTPL.error("outXML close err",e);
            }
        }

    }
    @Override
    public void inXML() {
        Element root= XMLTool.getRootElement(getPathWithNameXML());
        List<Element> elementList=root.elements("sheet");
        for(Element sheetElement:elementList){
            ODSSheet odsSheet=new ODSSheet(this);
            odsSheet.decodeElement(sheetElement);
            addSheet(odsSheet);
        }
    }

    public String getPathWithNameXLSX(){
        return directory+File.separator+name+".xlsx";
    }
    public String getPathWithNameXML(){
        return directory+File.separator+name+".xml";
    }
}
