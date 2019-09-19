package century.gsdk.storage;

import century.gsdk.docker.GameApplication;
import century.gsdk.storage.core.IStorage;
import century.gsdk.storage.core.Storage;
import century.gsdk.storage.core.StorageConnect;
import century.gsdk.storage.core.StorageInfo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    private TestApplication(){}
    public static TestApplication getInstance(){
        return instance;
    }

    @Override
    public void initialize() {
        IStorage storage=new Storage();
        StorageInfo storageInfo=new StorageInfo(
                "storate_test",
                "127.0.0.1",
                "x5online",
                "111111",
                "com.mysql.jdbc.Driver",
                1
        );
        storage.init(storageInfo);

        StorageConnect connect=storage.achiveConnection();
        try {
            PreparedStatement pst=connect.preparedStatement("INSERT INTO cc_zz VALUES(?,?,?,?,?,?,?)");
            pst.setInt(1,1);
            pst.setInt(2,1);
            pst.setInt(3,1);
            pst.setInt(4,1);
            pst.setInt(5,1);
            pst.setString(6,"999999");
            pst.setString(7,"9699999");
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
