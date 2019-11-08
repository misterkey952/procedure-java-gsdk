package century.gsdk.storage.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.*;

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
    private Connection connection;
    private Map<String,PreparedStatement> batchStatement;
    public StorageConnect(Connection connection) {
        this.connection = connection;
        batchStatement=new HashMap<>();
    }

    public PreparedStatement preparedStatementBatch(String sql) throws SQLException {
        PreparedStatement preparedStatement=batchStatement.get(sql);
        if(preparedStatement==null){
            preparedStatement=connection.prepareStatement(sql);
            if(preparedStatement!=null){
                batchStatement.put(sql,preparedStatement);
            }
        }
        return preparedStatement;
    }

    public PreparedStatement preparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
    public void commit() throws SQLException{
        try {
            connection.prepareStatement("");
            connection.commit();
        } catch (SQLException e) {
            StorageLogger.Stroage.error("commit err",e);
            throw e;
        }
    }
    public void rollback() throws SQLException{
        try {
            connection.rollback();
        } catch (SQLException e) {
            StorageLogger.Stroage.error("rollback err",e);
            throw e;
        }
    }
    public void begin() throws SQLException{
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            StorageLogger.Stroage.error("setAutoCommit err",e);
            throw e;
        }

        try {
           connection.setSavepoint();
        } catch (SQLException e) {
            StorageLogger.Stroage.error("setSavepoint err",e);
            throw e;
        }
    }


    public void executeBatch() throws SQLException{
        for(PreparedStatement batch:batchStatement.values()){
            try {
                batch.executeBatch();
            } catch (SQLException e) {
                StorageLogger.Stroage.error("executeBatch err",e);
                throw e;
            }
        }
    }

    public void release(){
        try {
            batchStatement.clear();
            connection.close();
        } catch (Exception e) {
            StorageLogger.Stroage.error("release connection err",e);
        }
    }
}
