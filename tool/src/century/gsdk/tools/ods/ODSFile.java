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
public abstract class ODSFile {
    String name;
    List<ODSSheet> sheetList;
    private Map<String, ODSSheet> sheetMap;
    String directory;
    ODSFile(String name,String directory) {
        this.name = name;
        this.directory = directory;
        sheetList=new ArrayList<>();
        sheetMap=new HashMap<>();
    }


    public ODSSheet getSheet(String key){
        return sheetMap.get(key);
    }

    public abstract void inXLSX();
    public abstract void inXML();
    public abstract void outXLSX();
    public abstract void outXML();
    public void addSheet(ODSSheet sheet){
        sheetList.add(sheet);
        sheetMap.put(sheet.getName_en(),sheet);
    }
}
