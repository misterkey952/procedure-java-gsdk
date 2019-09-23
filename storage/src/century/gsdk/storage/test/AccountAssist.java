package century.gsdk.storage.test;

import century.gsdk.storage.core.SqlTable;
import century.gsdk.storage.core.StorageConnect;
import century.gsdk.storage.core.StorageSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AccountAssist {
    private static final Logger logger = LoggerFactory.getLogger("AccountAssist");

    private static final Map<String, StorageSql> sqlMap = new HashMap<>();

    static {
        sqlMap.put("selectAccount",new StorageSql(
        new String[]{
            "select * from "
        },
        new SqlTable[]{
            new SqlTable("account",32)
        }
        ));
        sqlMap.put("updateAccount",new StorageSql(
        new String[]{
            "UPDATE ",
            " SET login_name=? WHERE id=?"
        },
        new SqlTable[]{
            new SqlTable("account",32)
        }
        ));
        sqlMap.put("insertAccount",new StorageSql(
        new String[]{
            "INSERT INTO ",
            " VALUES(?,?,?)"
        },
        new SqlTable[]{
            new SqlTable("account",32)
        }
        ));
        sqlMap.put("deleteAccount",new StorageSql(
        new String[]{
            "DELETE FROM ",
            " WHERE id=?"
        },
        new SqlTable[]{
            new SqlTable("account",32)
        }
        ));
    }

    public static AccountStruct selectAccountOne(StorageConnect connect, int __split__) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("selectAccount").splitSql(__split__));
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return new AccountStruct(
                resultSet.getString("id"),
                resultSet.getString("opertor_id"),
                resultSet.getString("login_name")
                );
            }
            return null;
        } catch (SQLException e) {
            logger.error("selectAccountOne",e);
            throw e;
        }
    }

    public static List<AccountStruct> selectAccountList(StorageConnect connect, int __split__) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("selectAccount").splitSql(__split__));
            ResultSet resultSet=preparedStatement.executeQuery();
            List<AccountStruct> list=new ArrayList<>();
            while(resultSet.next()){
                AccountStruct struct=new AccountStruct(
                resultSet.getString("id"),
                resultSet.getString("opertor_id"),
                resultSet.getString("login_name")
                );
                list.add(struct);
            }
            return list;
        } catch (SQLException e) {
            logger.error("selectAccountList",e);
            throw e;
        }
    }

    public static Map<String, AccountStruct> selectAccountMap(StorageConnect connect, int __split__) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("selectAccount").splitSql(__split__));
            ResultSet resultSet=preparedStatement.executeQuery();
            Map<String,AccountStruct> map=new HashMap<>();
            while(resultSet.next()){
                AccountStruct struct=new AccountStruct(
                resultSet.getString("id"),
                resultSet.getString("opertor_id"),
                resultSet.getString("login_name")
                );
                map.put(struct.getId(),struct);
            }
            return map;
        } catch (SQLException e) {
            logger.error("selectAccountMap",e);
            throw e;
        }
    }

    public static void updateAccount(StorageConnect connect, int __split__, AccountStruct acc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("updateAccount").splitSql(__split__));
            preparedStatement.setString(1,acc.getLogin_name());
            preparedStatement.setString(2,acc.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("updateAccount",e);
            throw e;
        }
    }

    public static void updateAccountBatch(StorageConnect connect, int __split__, AccountStruct acc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatementBatch(sqlMap.get("updateAccount").splitSql(__split__));
            preparedStatement.setString(1,acc.getLogin_name());
            preparedStatement.setString(2,acc.getId());
            preparedStatement.addBatch();
        } catch (SQLException e) {
            logger.error("updateAccountBatch",e);
            throw e;
        }
    }

    public static void insertAccount(StorageConnect connect, int __split__, AccountStruct acc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("insertAccount").splitSql(__split__));
            preparedStatement.setString(1,acc.getId());
            preparedStatement.setString(2,acc.getOpertor_id());
            preparedStatement.setString(3,acc.getLogin_name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("insertAccount",e);
            throw e;
        }
    }

    public static void insertAccountBatch(StorageConnect connect, int __split__, AccountStruct acc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatementBatch(sqlMap.get("insertAccount").splitSql(__split__));
            preparedStatement.setString(1,acc.getId());
            preparedStatement.setString(2,acc.getOpertor_id());
            preparedStatement.setString(3,acc.getLogin_name());
            preparedStatement.addBatch();
        } catch (SQLException e) {
            logger.error("insertAccountBatch",e);
            throw e;
        }
    }

    public static void deleteAccount(StorageConnect connect, int __split__, AccountStruct acc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("deleteAccount").splitSql(__split__));
            preparedStatement.setString(1,acc.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("deleteAccount",e);
            throw e;
        }
    }

    public static void deleteAccountBatch(StorageConnect connect, int __split__, AccountStruct acc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatementBatch(sqlMap.get("deleteAccount").splitSql(__split__));
            preparedStatement.setString(1,acc.getId());
            preparedStatement.addBatch();
        } catch (SQLException e) {
            logger.error("deleteAccountBatch",e);
            throw e;
        }
    }
}