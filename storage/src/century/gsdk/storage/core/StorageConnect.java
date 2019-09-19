package century.gsdk.storage.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
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
public class StorageConnect {
    private static final Logger logger= LoggerFactory.getLogger("StorageConnect");
    private Connection connection;
    public StorageConnect(Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement preparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public Statement statement() throws SQLException {
        return connection.createStatement();
    }
    public void commit() throws SQLException{
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("commit err",e);
            throw e;
        }
    }
    public void rollback() throws SQLException{
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("rollback err",e);
            throw e;
        }
    }
    public void begin() throws SQLException{
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("setAutoCommit err",e);
            throw e;
        }

        try {
           connection.setSavepoint();
        } catch (SQLException e) {
            logger.error("setSavepoint err",e);
            throw e;
        }
    }

    public void release(){
        try {
            connection.close();
        } catch (Exception e) {
            logger.error("release connection err",e);
        }
    }
}
