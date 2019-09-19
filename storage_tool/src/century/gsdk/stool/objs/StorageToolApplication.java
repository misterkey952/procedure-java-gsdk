package century.gsdk.stool.objs;

import century.gsdk.docker.GameApplication;
import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class StorageToolApplication extends GameApplication {

    public static final Logger logger= LoggerFactory.getLogger("StoreTool");

    private static final StorageToolApplication instance=new StorageToolApplication();
    private StorageToolApplication(){
        dataBaseEntityMap=new HashMap<>();
    }
    private Map<String,DataBaseEntity> dataBaseEntityMap;
    public static StorageToolApplication getInstance(){
        return instance;
    }

    public void buildDataBaseAndTable(){
        for(DataBaseEntity dataBaseEntity:dataBaseEntityMap.values()){
            dataBaseEntity.autoGen();
        }
        logger.info("buildDataBaseAndTable complete");
    }

    @Override
    public void initialize() {
        File fileDir=new File(getResRootPath());
        File[] defineFileList=fileDir.listFiles();
        DataBaseEntity dataBaseEntity;
        for(File file:defineFileList){
            Element element= XMLTool.getRootElement(file);
            List<Element> dblist=element.elements("database");
            for(Element edb:dblist){
                dataBaseEntity=new DataBaseEntity(edb);
                dataBaseEntityMap.put(dataBaseEntity.getDbName(),dataBaseEntity);
                logger.info("load database info "+dataBaseEntity.getDbName());
            }
        }
    }
}
