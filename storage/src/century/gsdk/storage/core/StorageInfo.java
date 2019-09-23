package century.gsdk.storage.core;

import century.gsdk.tools.str.StringTool;
import century.gsdk.tools.xml.XMLTool;
import com.jolbox.bonecp.BoneCPConfig;
import org.dom4j.Element;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

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
 * Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class StorageInfo {
    private String name;
    private StorageURL url;
    private DataBaseEntity dataBaseEntity;
    public StorageInfo(Element element) {
        url=new StorageURL(element);
        String ref=XMLTool.getStrAttrValue(element,"ref");
        Element dataRoot=XMLTool.getRootElement(ref);
        dataBaseEntity=new DataBaseEntity(dataRoot,url);
        name=dataBaseEntity.getName();
    }


    public String getName() {
        return name;
    }

    public int getSplit() {
        return dataBaseEntity.getSplit();
    }

    public void setName(String name) {
        this.name = name;
    }

    public StorageURL getUrl() {
        return url;
    }
}
