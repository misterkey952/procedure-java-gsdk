package century.gsdk.tools.ods;

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




    public ODSSheet() {
        odsHeads=new ArrayList<>();
        headMap=new HashMap<>();
        recordList=new ArrayList<>();
    }

    void setName_en(String name_en) {
        this.name_en = name_en;
    }

    void setName_zh(String name_zh) {
        this.name_zh = name_zh;
    }

    public ODSSheet(String name_en, String name_zh) {
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