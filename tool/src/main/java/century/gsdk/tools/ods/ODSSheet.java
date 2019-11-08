package century.gsdk.tools.ods;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ODSSheet {
    private String name_en;
    private String name_zh;

    private List<ODSHead> odsHeads;
    private Map<String,ODSHead> headMap;
    private List<ODSRecord> recordList;
    private ODSFile odsFile;




    public void encodeElement(Element superElement){
        Element element=superElement.addElement("sheet");
        element.addAttribute("name_en",name_en);
        element.addAttribute("name_zh",name_zh);
        for(ODSHead odsHead:odsHeads){
            odsHead.encodeElement(element);
        }

        for(ODSRecord odsRecord:recordList){
            odsRecord.encodeElement(element);
        }
    }

    public void decodeElement(Element element){
        this.name_zh=element.attributeValue("name_zh");
        this.name_en=element.attributeValue("name_en");
        List<Element> headElementList=element.elements("odsHead");

        for(Element odsHeadEle:headElementList){
            ODSHead head=new ODSHead();
            head.decodeElement(odsHeadEle);
            odsHeads.add(head);
            headMap.put(head.getKey(),head);
        }

        List<Element> recElements=element.elements("record");
        for(Element ele:recElements){
            ODSRecord odsRecord=new ODSRecord(this);
            odsRecord.decodeElement(ele);
            recordList.add(odsRecord);
        }


    }

    public ODSSheet(ODSFile odsFile) {
        this.odsFile=odsFile;
        odsHeads=new ArrayList<>();
        headMap=new HashMap<>();
        recordList=new ArrayList<>();
    }

    public ODSFile getOdsFile() {
        return odsFile;
    }

    void setName_en(String name_en) {
        this.name_en = name_en;
    }

    void setName_zh(String name_zh) {
        this.name_zh = name_zh;
    }

    public ODSSheet(String name_en, String name_zh,ODSFile odsFile) {
        this.odsFile=odsFile;
        this.name_en = name_en;
        this.name_zh = name_zh;
        odsHeads=new ArrayList<>();
        headMap=new HashMap<>();
        recordList=new ArrayList<>();
    }

    public void addHead(String key, String name, String des){
        ODSHead head=new ODSHead(key,name,des);
        head.index(odsHeads.size());
        odsHeads.add(head);
        headMap.put(head.getKey(),head);
    }

    public ODSHead getHead(String key){
        return headMap.get(key);
    }


    public String getName_en() {
        return name_en;
    }
    public void addRecord(ODSRecord odsRecord){
        recordList.add(odsRecord);
    }

    public String getName_zh() {
        return name_zh;
    }

    public List<ODSRecord> getRecordList() {
        return recordList;
    }

    public List<ODSHead> getOdsHeads() {
        return odsHeads;
    }
}
