package century.gsdk.storage.test;

import century.gsdk.docker.GameApplication;
import century.gsdk.storage.core.*;
import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
 * Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class TestApplication extends GameApplication {

    private static final TestApplication instance=new TestApplication();
    private IStorage storage;
    private TestApplication(){}
    public static TestApplication getInstance(){
        return instance;
    }

    @Override
    public void initialize() {
        storage=new Storage();
        String aa=getCfgRootPath()+ File.separator+"connections.xml";
        Element ee= XMLTool.getRootElement(aa);
        StorageInfo storageInfo=new StorageInfo(ee.element("connection"));
        storage.init(storageInfo);
        AccountStruct accountStruct=new AccountStruct("ome","fgdg","sfgsdfg");
//        try {
//            AccountAssist.insertAccount(storage.achiveConnection(),0,accountStruct);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        try {
            accountStruct=AccountAssist.selectAccountOne(storage.achiveConnection(),0);
            List<AccountStruct> list=AccountAssist.selectAccountList(storage.achiveConnection(),0);
            Map<String,AccountStruct> map=AccountAssist.selectAccountMap(storage.achiveConnection(),0);
            System.out.println("ddd");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public IStorage getStorage() {
        return storage;
    }
}
