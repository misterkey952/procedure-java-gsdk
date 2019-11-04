package century.gsdk.tools.ods;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

    private static final Logger logger= LoggerFactory.getLogger(MSODSFile.class);
    public MSODSFile(String name, String directory) {
        super(name, directory);
    }


    private void fillRecord(Row row,ODSSheet odsSheet){
        ODSRecord record=new ODSRecord();
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
    public void in() {
        Workbook workbook=null;
        try {
            workbook=new XSSFWorkbook(new FileInputStream(new File(getPathWithNameZH())));
            int sheetCount=workbook.getNumberOfSheets();
            for(int i=0;i<sheetCount;i++){
                Sheet sheet=workbook.getSheetAt(i);
                ODSSheet odsSheet=new ODSSheet();
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
            logger.error("in err",e);
        }finally {
            if(workbook!=null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    logger.error("in workbook.close() err",e);
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
                    nameCell.getCellComment().getString().getString());
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
            Comment comment = drawing.createCellComment(new XSSFClientAnchor(0, 0, 0,0, (short) 3, 3, (short) 5, 6));
            comment.setString(new XSSFRichTextString(odsHead.getDes()));
            nameCell.setCellComment(comment);
        }
        Cell classCell=nameRow.createCell(odsSheet.getOdsHeads().size());
        classCell.setCellValue(odsSheet.getName_en());


        return 2;
    }

    @Override
    public void out() {
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
            workbook.write(new FileOutputStream(new File(getPathWithNameZH())));
        }catch (Exception e){
            logger.error("out err",e);
        }finally {
            if(workbook!=null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    logger.error("out workbook.close() err",e);
                }
            }
        }

    }


    public String getPathWithNameZH(){
        return directory+File.separator+name+".xlsx";
    }

}
