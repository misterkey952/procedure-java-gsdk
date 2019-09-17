package century.gsdk.storage.dbm.assistant;

public interface XDBConst {
	/** 在入库的时候，字段上没有值时，用这个值替代*/
	String DEF_STR="DEFSTR";
	/** 暂停切换文件*/
	byte STOP_T=1;
	/** 在入库时某个字段处理错误时，用这个值代替*/
	String ERR_V="EV";
	String LAST_STR="\n";
	
	/** 在入库时某个数字类型的字段处理错误时，用这个值代替*/
	byte ERR_N_V=-100;
	String ERR_TIME="1989-03-04 02:30:40";
	String AUTO_CREATE="create";
	String AUTO_UPDATE="update";
	
	
	/** 入库常量，更新*/
	byte UPD=1;
	/** 入库常量插入*/
	byte ADD=2;
	/** 入库常量删除*/
	byte DEL=3;
	/** 入库间隔*/


	/** 条件的链接符号*/
	String COND_AND=" AND ";
	String COND_WHERE="WHERE";
	/** 更新SQL语句*/
	String UPD_SQL="UPDATE";
	/** 插入SQL语句*/
	String INSERT_SQL="INSERT INTO";
	String VALUE="VALUES";
	/** 删除SQL语句*/
	String DEL_SQL="DELETE";
	String PARAM="?";
	String FROM_SQL="FROM";
	String SET="SET";
	String CREATE_TABLE="CREATE TABLE";
	String ALTER_TABLE="ALTER TABLE";
	String SQL_ADD="ADD";
	String SQL_CREATE_HOUZHUI="ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
	String SQL_PRIMARY="PRIMARY KEY ";
	String SQL_STRING_CHARSET="COLLATE utf8_bin";
	String SQL_NOTNULL="NOT NULL";
}
