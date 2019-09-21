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

public final class Account {
    private static final Logger logger = LoggerFactory.getLogger("Account");

    private static final Map<String, StorageSql> sqlMap = new HashMap<>();

    static {
        sqlMap.put("selectAccount",new StorageSql(
        new String[]{
            "select * from ",
            " where id=? and opera=? and ooq=?"
        },
        new SqlTable[]{
            new SqlTable("account",32)
        }
        ));
        sqlMap.put("selectSubAccount",new StorageSql(
        new String[]{
            "select * from ",
            " where id=? and opera=?"
        },
        new SqlTable[]{
            new SqlTable("account",32)
        }
        ));
        sqlMap.put("selectSingt",new StorageSql(
        new String[]{
            "select * from ",
            " where id=? and opera=?"
        },
        new SqlTable[]{
            new SqlTable("account",32)
        }
        ));
    }

    public static AccountStruct selectAccountOne(StorageConnect connect, int __split__, SubAccount sa, long ooc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("selectAccount").splitSql(__split__));
            preparedStatement.setString(1,sa.getId());
            preparedStatement.setString(2,sa.getOpertor_id());
            preparedStatement.setLong(3,ooc);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return new AccountStruct(
                resultSet.getString("id"),
                resultSet.getString("opertor_id"),
                resultSet.getString("login_name"),
                resultSet.getTimestamp("create_time"),
                resultSet.getString("create_ip"),
                resultSet.getString("lastlogin_ip"),
                resultSet.getLong("lastlogin_date"),
                resultSet.getTimestamp("lastlogout_date"),
                resultSet.getInt("gold"),
                resultSet.getInt("online"),
                resultSet.getInt("platform"),
                resultSet.getByte("ostype"),
                resultSet.getInt("subplatform"),
                resultSet.getString("last_idfa")
                );
            }
            return null;
        } catch (SQLException e) {
            logger.error("selectAccountOne",e);
            throw e;
        }
    }

    public static List<AccountStruct> selectAccountList(StorageConnect connect, int __split__, SubAccount sa, long ooc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("selectAccount").splitSql(__split__));
            preparedStatement.setString(1,sa.getId());
            preparedStatement.setString(2,sa.getOpertor_id());
            preparedStatement.setLong(3,ooc);
            ResultSet resultSet=preparedStatement.executeQuery();
            List<AccountStruct> list=new ArrayList<>();
            while(resultSet.next()){
                AccountStruct struct=new AccountStruct(
                resultSet.getString("id"),
                resultSet.getString("opertor_id"),
                resultSet.getString("login_name"),
                resultSet.getTimestamp("create_time"),
                resultSet.getString("create_ip"),
                resultSet.getString("lastlogin_ip"),
                resultSet.getLong("lastlogin_date"),
                resultSet.getTimestamp("lastlogout_date"),
                resultSet.getInt("gold"),
                resultSet.getInt("online"),
                resultSet.getInt("platform"),
                resultSet.getByte("ostype"),
                resultSet.getInt("subplatform"),
                resultSet.getString("last_idfa")
                );
                list.add(struct);
            }
            return list;
        } catch (SQLException e) {
            logger.error("selectAccountList",e);
            throw e;
        }
    }

    public static Map<String, AccountStruct> selectAccountMap(StorageConnect connect, int __split__, SubAccount sa, long ooc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("selectAccount").splitSql(__split__));
            preparedStatement.setString(1,sa.getId());
            preparedStatement.setString(2,sa.getOpertor_id());
            preparedStatement.setLong(3,ooc);
            ResultSet resultSet=preparedStatement.executeQuery();
            Map<String,AccountStruct> map=new HashMap<>();
            while(resultSet.next()){
                AccountStruct struct=new AccountStruct(
                resultSet.getString("id"),
                resultSet.getString("opertor_id"),
                resultSet.getString("login_name"),
                resultSet.getTimestamp("create_time"),
                resultSet.getString("create_ip"),
                resultSet.getString("lastlogin_ip"),
                resultSet.getLong("lastlogin_date"),
                resultSet.getTimestamp("lastlogout_date"),
                resultSet.getInt("gold"),
                resultSet.getInt("online"),
                resultSet.getInt("platform"),
                resultSet.getByte("ostype"),
                resultSet.getInt("subplatform"),
                resultSet.getString("last_idfa")
                );
                map.put(struct.getId(),struct);
            }
            return map;
        } catch (SQLException e) {
            logger.error("selectAccountMap",e);
            throw e;
        }
    }

    public static SubAccount selectSubAccountOne(StorageConnect connect, int __split__, SubAccount sa, long ooc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("selectSubAccount").splitSql(__split__));
            preparedStatement.setString(1,sa.getId());
            preparedStatement.setString(2,sa.getOpertor_id());
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return new SubAccount(
                resultSet.getString("id"),
                resultSet.getString("opertor_id"),
                resultSet.getString("login_name")
                );
            }
            return null;
        } catch (SQLException e) {
            logger.error("selectSubAccountOne",e);
            throw e;
        }
    }

    public static List<SubAccount> selectSubAccountList(StorageConnect connect, int __split__, SubAccount sa, long ooc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("selectSubAccount").splitSql(__split__));
            preparedStatement.setString(1,sa.getId());
            preparedStatement.setString(2,sa.getOpertor_id());
            ResultSet resultSet=preparedStatement.executeQuery();
            List<SubAccount> list=new ArrayList<>();
            while(resultSet.next()){
                SubAccount struct=new SubAccount(
                resultSet.getString("id"),
                resultSet.getString("opertor_id"),
                resultSet.getString("login_name")
                );
                list.add(struct);
            }
            return list;
        } catch (SQLException e) {
            logger.error("selectSubAccountList",e);
            throw e;
        }
    }

    public static Map<String, SubAccount> selectSubAccountMap(StorageConnect connect, int __split__, SubAccount sa, long ooc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("selectSubAccount").splitSql(__split__));
            preparedStatement.setString(1,sa.getId());
            preparedStatement.setString(2,sa.getOpertor_id());
            ResultSet resultSet=preparedStatement.executeQuery();
            Map<String,SubAccount> map=new HashMap<>();
            while(resultSet.next()){
                SubAccount struct=new SubAccount(
                resultSet.getString("id"),
                resultSet.getString("opertor_id"),
                resultSet.getString("login_name")
                );
                map.put(struct.getId(),struct);
            }
            return map;
        } catch (SQLException e) {
            logger.error("selectSubAccountMap",e);
            throw e;
        }
    }

    public static String selectSingtOne(StorageConnect connect, int __split__, SubAccount sa, long ooc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("selectSingt").splitSql(__split__));
            preparedStatement.setString(1,sa.getId());
            preparedStatement.setString(2,sa.getOpertor_id());
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString(1);
            }
            return "";
        } catch (SQLException e) {
            logger.error("selectSingtOne",e);
            throw e;
        }
    }

    public static List<String> selectSingtList(StorageConnect connect, int __split__, SubAccount sa, long ooc) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get("selectSingt").splitSql(__split__));
            preparedStatement.setString(1,sa.getId());
            preparedStatement.setString(2,sa.getOpertor_id());
            ResultSet resultSet=preparedStatement.executeQuery();
            List<String> list=new ArrayList<>();
            while(resultSet.next()){
                String value=resultSet.getString(1);
                list.add(value);
            }
            return list;
        } catch (SQLException e) {
            logger.error("selectSingtList",e);
            throw e;
        }
    }
}