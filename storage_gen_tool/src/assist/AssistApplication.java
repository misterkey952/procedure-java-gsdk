package assist;

import assist.buildtable.DataBaseEntity;
import assist.gencode.AccessEntity;
import century.gsdk.docker.GameApplication;
import century.gsdk.storage.core.StorageInfo;
import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
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
public class AssistApplication extends GameApplication {

    public static final Logger logger= LoggerFactory.getLogger("AssistApplication");
    private static final AssistApplication instance=new AssistApplication();
    private String project="F:\\workspace\\github\\procedure-java-gsdk\\storage\\src";
    private AssistApplication(){
        dataBaseEntityMap=new HashMap<>();
        storageInfoMap=new HashMap<>();
        accessEntities=new ArrayList<>();
    }

    private List<AccessEntity> accessEntities;
    private Map<String, DataBaseEntity> dataBaseEntityMap;
    private Map<String, StorageInfo> storageInfoMap;
    public static AssistApplication getInstance(){
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
        String connectionsConfig=getCfgRootPath()+File.separator+"connections.xml";
        Element connCfgElement=XMLTool.getRootElement(connectionsConfig);
        List<Element> connList=connCfgElement.elements();
        for(Element element:connList){
            StorageInfo storageInfo=new StorageInfo(element);
            storageInfoMap.put(storageInfo.getName(),storageInfo);
        }

        String storagesDir=getCfgRootPath()+File.separator+"storages";
        File storagesDirFile=new File(storagesDir);
        File[] subDir=storagesDirFile.listFiles();
        for(File subdd:subDir){
            loadDefine(subdd);
        }

    }


    private void loadDefine(File dir){
        File[] files=dir.listFiles();
        List<File> accessFileList=new ArrayList<>();
        File storeFile=null;
        for(File f:files){
            if(f.getName().equals("storage.xml")){
                storeFile=f;
            }else{
                accessFileList.add(f);
            }
        }

        Element rootElement=XMLTool.getRootElement(storeFile);
        DataBaseEntity dataBaseEntity=new DataBaseEntity(rootElement);
        dataBaseEntityMap.put(dataBaseEntity.getDbName(),dataBaseEntity);
        for(File ff:accessFileList){
            rootElement=XMLTool.getRootElement(ff);
            AccessEntity accessEntity=new AccessEntity(rootElement);
            accessEntities.add(accessEntity);
        }


    }

    public StorageInfo getStorageInfo(String dbName){
        return storageInfoMap.get(dbName);
    }
    public DataBaseEntity getDataBaseEntity(String dbName){
        return dataBaseEntityMap.get(dbName);
    }

    public String getProject() {
        return project;
    }

    public void genAccess(){
        for(AccessEntity accessEntity:accessEntities){
            accessEntity.autoGen();
        }
    }
}
