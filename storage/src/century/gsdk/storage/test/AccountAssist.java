package century.gsdk.storage.test;

import century.gsdk.storage.core.StorageConnect;
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

    private static final String[] selectAccount = new String[]{
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?",
		"select * from account_0 where id=? and opera=?"
	};

    public static List<AccountStruct> selectAccountList(StorageConnect connect, SubAccount sa, int splitID) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(selectAccount[splitID%selectAccount.length]);
            List<AccountStruct> list=new ArrayList<>();
            AccountStruct struct;
            preparedStatement.setString(1,sa.getId());
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                struct=new AccountStruct(
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

    public static Map<String, AccountStruct> selectAccountMap(StorageConnect connect, SubAccount sa, int splitID) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(selectAccount[splitID%selectAccount.length]);
            Map<String,AccountStruct> map=new HashMap<>();
            AccountStruct struct;
            preparedStatement.setString(1,sa.getId());
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                struct=new AccountStruct(
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

    public static AccountStruct selectAccount(StorageConnect connect, SubAccount sa, int splitID) throws SQLException {
        try {
            PreparedStatement preparedStatement=connect.preparedStatement(selectAccount[splitID%selectAccount.length]);
            preparedStatement.setString(1,sa.getId());
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
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
                return struct;
            }
        } catch (SQLException e) {
            logger.error("selectAccount",e);
            throw e;
        }
        return null;
    }
}