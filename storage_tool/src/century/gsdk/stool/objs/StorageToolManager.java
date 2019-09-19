package century.gsdk.stool.objs;

import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *     Copyright (C) <2019>  <Century>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class StorageToolManager {
    private static final StorageToolManager instance=new StorageToolManager();
    private StorageToolManager(){
        dataBaseEntityMap=new HashMap<>();
    }
    private Map<String,DataBaseEntity> dataBaseEntityMap;
    public static StorageToolManager getInstance(){
        return instance;
    }

    public void loadDefineXML(String dir){
        File fileDir=new File(dir);
        File[] defineFileList=fileDir.listFiles();
        DataBaseEntity dataBaseEntity;
        for(File file:defineFileList){
            Element element= XMLTool.getRootElement(file);
            List<Element> dblist=element.elements("database");
            for(Element edb:dblist){
                dataBaseEntity=new DataBaseEntity(edb);
                dataBaseEntityMap.put(dataBaseEntity.getDbName(),dataBaseEntity);
            }

        }
    }
}
