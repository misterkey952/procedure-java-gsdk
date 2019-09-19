package century.gsdk.storage.core;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
public class ConciseStorage implements StorageAPI {
    private static final Logger logger= LoggerFactory.getLogger(ConciseStorage.class);
    private BoneCP boneCP;
    private ThreadLocal<Connection> connectionThreadLocal;
    private ThreadLocal<Statement> querystatement;
    private ThreadLocal<Statement> updatestatement;
    private ThreadLocal<Statement> insertstatement;
    private ThreadLocal<Statement> deletestatement;

    @Override
    public void initialze(String config) throws StorageException {
        try {
            boneCP=new BoneCP(buildConfig(config));
            connectionThreadLocal=new ThreadLocal<>();
            querystatement=new ThreadLocal<>();
            updatestatement=new ThreadLocal<>();
            insertstatement=new ThreadLocal<>();
            deletestatement=new ThreadLocal<>();
        } catch (Exception e) {
            throw new StorageException("concise initialze err",e);
        }
    }

    @Override
    public void commit() throws StorageException {
        Connection connection=connectionThreadLocal.get();
        if(connection!=null){
            Statement statement;
            try {
                statement=insertstatement.get();
                if(statement!=null){
                    statement.executeBatch();
                }

                statement=updatestatement.get();
                if(statement!=null){
                    statement.executeBatch();
                }

                statement=deletestatement.get();
                if(statement!=null){
                    statement.executeBatch();
                }
                connection.commit();
            } catch (SQLException e) {
                throw new StorageException("commit err",e);
            }
        }
    }

    @Override
    public void rollback() throws StorageException {
        Connection connection=connectionThreadLocal.get();
        if(connection!=null){
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new StorageException("rollback err",e);
            }
        }
    }


    private Connection getConnection()throws StorageException{
        Connection connection=connectionThreadLocal.get();
        if(connection==null){
            try {
                connection=boneCP.getConnection();
                connectionThreadLocal.set(connection);
            } catch (SQLException e) {
                throw new StorageException("acquire connection err in begin",e);
            }
        }
        return connection;
    }


    private Statement getDeleteStatement()throws StorageException{
        Statement statement=deletestatement.get();
        if(statement==null){
            try {
                statement=getConnection().createStatement();
                deletestatement.set(statement);
            } catch (SQLException e) {
                throw new StorageException("getDeleteStatement err",e);
            }
        }
        return statement;
    }

    private Statement getQueryStatement()throws StorageException{
        Statement statement=querystatement.get();
        if(statement==null){
            try {
                statement=getConnection().createStatement();
                querystatement.set(statement);
            } catch (SQLException e) {
                throw new StorageException("getQueryStatement err",e);
            }
        }
        return statement;
    }

    private Statement getUpdateStatement()throws StorageException{
        Statement statement=updatestatement.get();
        if(statement==null){
            try {
                statement=getConnection().createStatement();
                updatestatement.set(statement);
            } catch (SQLException e) {
                throw new StorageException("getUpdateStatement err",e);
            }
        }
        return statement;
    }

    private Statement getInsertStatement()throws StorageException{
        Statement statement=insertstatement.get();
        if(statement==null){
            try {
                statement=getConnection().createStatement();
                insertstatement.set(statement);
            } catch (SQLException e) {
                throw new StorageException("getInsertStatement err",e);
            }
        }
        return statement;
    }

    @Override
    public void begin() throws StorageException {
        Connection connection=getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new StorageException("begin set auto commit err",e);
        }

        try {
            connection.setSavepoint();
        } catch (SQLException e) {
            throw new StorageException("begin set savepoint err",e);
        }
    }

    @Override
    public void finish() throws StorageException {
        Connection connection=connectionThreadLocal.get();
        if(connection!=null){
            Statement statement;

            statement=insertstatement.get();
            if(statement!=null){
                try {
                    statement.clearBatch();
                } catch (SQLException e) {
                    logger.error("clearBatch insert statement err",e);
                }finally {
                    insertstatement.remove();
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        logger.error("close insert statement err",e);
                    }
                }
            }


            statement=updatestatement.get();
            if(statement!=null){
                try {
                    statement.clearBatch();
                } catch (SQLException e) {
                    logger.error("clearBatch update statement err",e);
                }finally {
                    updatestatement.remove();
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        logger.error("close update statement err",e);
                    }
                }
            }


            statement=deletestatement.get();
            if(statement!=null){
                try {
                    statement.clearBatch();
                } catch (SQLException e) {
                    logger.error("clearBatch delete statement err",e);
                }finally {
                    deletestatement.remove();
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        logger.error("close delete statement err",e);
                    }
                }
            }

            statement=querystatement.get();
            if(statement!=null){
                try {
                    statement.clearBatch();
                } catch (SQLException e) {
                    logger.error("clearBatch query statement err",e);
                }finally {
                    querystatement.remove();
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        logger.error("close query statement err",e);
                    }
                }
            }


            try {
                connection.close();
            } catch (SQLException e) {
                throw new StorageException("finish connection err",e);
            }finally {
                connectionThreadLocal.remove();
            }
        }
    }

    @Override
    public void insert(String sql) throws StorageException {
        Statement statement=getInsertStatement();
        try {
            statement.addBatch(sql);
        } catch (SQLException e) {
            throw new StorageException("insert err",e);
        }
    }

    @Override
    public StorageSelect select(String sql) throws StorageException {
        Statement statement=getQueryStatement();
        try {
            return new StorageSelect(statement.executeQuery(sql));
        } catch (SQLException e) {
            throw new StorageException("select err",e);
        }
    }

    @Override
    public void update(String sql) throws StorageException {
        Statement statement=getUpdateStatement();
        try {
            statement.addBatch(sql);
        } catch (SQLException e) {
            throw new StorageException("update err",e);
        }
    }

    @Override
    public void delete(String sql) throws StorageException {
        Statement statement=getDeleteStatement();
        try {
            statement.addBatch(sql);
        } catch (SQLException e) {
            throw new StorageException("delete err",e);
        }
    }


    private BoneCPConfig buildConfig(String cfg)throws StorageException{

        FileInputStream fileInputStream=null;
        Element rootElement=null;
        try{
            File file=new File(cfg);
            fileInputStream=new FileInputStream(file);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(fileInputStream);
            rootElement = doc.getRootElement();
        }catch (Exception e){
            throw new StorageException("buildConfig err",e);
        }finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new StorageException("close concise config err "+cfg,e);
                }
            }
        }


        BoneCPConfig config=new BoneCPConfig();
        try {
            Class.forName(rootElement.element("devier").attributeValue("value").trim());
        } catch (ClassNotFoundException e) {
            throw new StorageException("class load driver err "+rootElement.element("devier").attributeValue("value").trim(),e);
        }
        config.setUser(rootElement.element("username").attributeValue("value").trim());
        config.setPassword(rootElement.element("password").attributeValue("value").trim());
        config.setJdbcUrl(rootElement.element("url").attributeValue("value").trim());


        config.setPartitionCount(1);
        config.setMaxConnectionsPerPartition(5);
        config.setMinConnectionsPerPartition(10);
        config.setIdleConnectionTestPeriodInMinutes(240);
        config.setAcquireIncrement(2);
        return config;
    }
}
