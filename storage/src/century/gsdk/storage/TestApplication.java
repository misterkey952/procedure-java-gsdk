package century.gsdk.storage;

import century.gsdk.docker.GameApplication;
import century.gsdk.storage.core.*;

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

        StorageTransaction storageTransaction=new StorageTransaction();
        StorageConnect connect=storageTransaction.getConnection(storage);
        try {
            PreparedStatement pst=connect.preparedStatement("INSERT INTO cc_zz2 VALUES(?,?)");
            pst.setInt(1,46);
            pst.setInt(2,2);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        storageTransaction.commit();
        storageTransaction.release();
    }
}
