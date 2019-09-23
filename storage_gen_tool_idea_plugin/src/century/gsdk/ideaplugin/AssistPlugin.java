package century.gsdk.ideaplugin;

import century.gsdk.storage.core.AssistExeInfo;
import century.gsdk.storage.core.DataBaseEntity;
import assist.gencode.AccessEntity;
import century.gsdk.storage.core.StorageInfo;
import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;

import java.io.File;

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
public class AssistPlugin {

    public static void genAccess(String path, AssistExeInfo exeInfo){
        File file=new File(path);
        Element root= XMLTool.getRootElement(file);
        if(root==null||!root.getName().equals("access")){
            exeInfo.appendString("you selected a wrong file "+file.getName());
            return;
        }
        AccessEntity accessEntity=new AccessEntity(root);
        String ref=XMLTool.getStrAttrValue(root,"ref");
        StorageInfo storageInfo=new StorageInfo(root);
        file=new File(ref);
        if(!file.exists()){
            exeInfo.appendString("there is no ref file "+ref);
            return;
        }
        root=XMLTool.getRootElement(file);
        if(root==null||!root.getName().equals("storage")){
            exeInfo.appendString("you ref a wrong file "+file.getName());
            return;
        }


        DataBaseEntity dataBaseEntity=new DataBaseEntity(root);
        dataBaseEntity.setStorageInfo(storageInfo);
        dataBaseEntity.autoGen(exeInfo);
        exeInfo.appendString("auto gen db "+dataBaseEntity.getDbName());

    }


    public static void builddb(String path, AssistExeInfo exeInfo){
        File file=new File(path);
        Element root= XMLTool.getRootElement(file);
        if(root==null||!root.getName().equals("connect")){
            exeInfo.appendString("you selected a wrong file "+file.getName());
            return;
        }
        String ref=XMLTool.getStrAttrValue(root,"ref");
        StorageInfo storageInfo=new StorageInfo(root);
        file=new File(ref);
        if(!file.exists()){
            exeInfo.appendString("there is no ref file "+ref);
            return;
        }
        root=XMLTool.getRootElement(file);
        if(root==null||!root.getName().equals("storage")){
            exeInfo.appendString("you ref a wrong file "+file.getName());
            return;
        }


        DataBaseEntity dataBaseEntity=new DataBaseEntity(root);
        dataBaseEntity.setStorageInfo(storageInfo);
        dataBaseEntity.autoGen(exeInfo);
        exeInfo.appendString("auto gen db "+dataBaseEntity.getDbName());

    }
}
