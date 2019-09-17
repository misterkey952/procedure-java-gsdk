package century.gsdk.storage.dbm.source;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import century.gsdk.storage.dbm.assistant.*;
import century.gsdk.storage.dbm.config.DBSourceConfig;
import century.gsdk.storage.dbm.streng.StrengConnection;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;



public class DBSource{
	private BoneCP connectionPools;
	private ThreadLocal<StrengConnection> localConnection=new ThreadLocal<StrengConnection>();
	private DBSourceConfig sourceConfig;
	public DBSource(DBSourceConfig c){
		try {
			this.sourceConfig=c;
			System.out.println("begin init xdb pool.............................");
			BoneCPConfig boneCPConfig=new BoneCPConfig();
			boneCPConfig.setJdbcUrl(sourceConfig.getUrl());
			boneCPConfig.setUsername(sourceConfig.getUser());
			boneCPConfig.setPassword(sourceConfig.getPassword());
			boneCPConfig.setMinConnectionsPerPartition(sourceConfig.getMinCon());
			boneCPConfig.setMaxConnectionsPerPartition(sourceConfig.getMaxCon());
			boneCPConfig.setPartitionCount(1);
			boneCPConfig.setConnectionTimeoutInMs(10000);
			Class.forName(sourceConfig.getDriver());
			System.out.println("create boneCP.............................");
			connectionPools=new BoneCP(boneCPConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("init xdb pool ok.................................");
		
		if(sourceConfig.getAuto()!=null){
			System.out.println("auto create table ["+sourceConfig.getAuto()+"] start ........................");
			this.auto();
		}
		
	}
	
	
	private void auto(){
		if(XDBConst.AUTO_CREATE.equals(sourceConfig.getAuto())){
			createTables();
		}else if(XDBConst.AUTO_UPDATE.equals(sourceConfig.getAuto())){
			updateTables();
		}
	
	}
	
	private void createTables(){
		try {
			Connection connection=this.getConnection().getConnection();
			DatabaseMetaData databaseMetaData=connection.getMetaData();
			ResultSet tableSet=databaseMetaData.getTables(null,"%","%",new String[]{"TABLE"});
			List<String> talbeList=new ArrayList<String>();
			while(tableSet.next()){
				talbeList.add(tableSet.getString("TABLE_NAME"));
			}
			
			PreparedStatement pst=connection.prepareStatement("");
			for(String tableName:talbeList){
				pst.executeUpdate("DROP TABLE "+tableName);
			}
			
			Map<String, ClassData>tableMap=new HashMap<String, ClassData>();
			String[] packageList=sourceConfig.getPackages().split(";");
			for(String packages:packageList){
				try{
					XLoadClass.loadClass(tableMap,packages);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			for(ClassData classData:tableMap.values()){
				pst.executeUpdate(XDBSqlUtil.createTable(classData));
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.release();
		}
	}
	
	
	private void updateTables(){

		try {
			Connection connection=this.getConnection().getConnection();
			DatabaseMetaData databaseMetaData=connection.getMetaData();
			ResultSet tableSet=databaseMetaData.getTables(null,"%","%",new String[]{"TABLE"});
			Map<String,String> talbeList=new HashMap<String,String>();
			while(tableSet.next()){
				String tableName=tableSet.getString("TABLE_NAME");
				talbeList.put(tableName.toLowerCase(), tableName);
			}
			
			PreparedStatement pst=connection.prepareStatement("");
			
			Map<String,ClassData>tableMap=new HashMap<String, ClassData>();
			String[] packageList=sourceConfig.getPackages().split(";");
			for(String packages:packageList){
				try{
					XLoadClass.loadClass(tableMap,packages);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			for(ClassData classData:tableMap.values()){
				if(talbeList.get(classData.getTableName().toLowerCase())==null){
					pst.executeUpdate(XDBSqlUtil.createTable(classData));
				}else{
					
					 ResultSet rs =pst.executeQuery("SELECT * FROM "+classData.getTableName());
					 ResultSetMetaData rsmd = rs.getMetaData();
					 Map<String,String> fieldMap=new HashMap<String, String>();
					 for(int i=1;i<=rsmd.getColumnCount();i++){
						 String field = rsmd.getColumnName(i);
						 fieldMap.put(field,field);
					 }
					 
					 Map<String, XFieldData> xfieldMap=new HashMap<String, XFieldData>();
					 for(XFieldData fieldData:classData.getKeyList()){
						 xfieldMap.put(fieldData.getDbField(),fieldData);
					 }
					 
					 for(XFieldData fieldData:classData.getFieldDatas()){
						 xfieldMap.put(fieldData.getDbField(),fieldData);
					 }
					 
					 
					 
					 for(String fstr:fieldMap.values()){
						 if(xfieldMap.get(fstr)==null){
							pst.executeUpdate(XDBConst.ALTER_TABLE+" "+classData.getTableName()+" DROP COLUMN "+fstr);
						 }
					 }
					 
					 try{
						 Class<?> class1=Class.forName(classData.getClassStr());
						 for(XFieldData fieldData:xfieldMap.values()){
							 if(fieldMap.get(fieldData.getDbField())==null){
								 Field field=class1.getDeclaredField(fieldData.getClassField());
								 field.setAccessible(true);
								 pst.executeUpdate(XDBSqlUtil.alterTableAddColumn(classData,field,fieldData));
							 }
						 }
					 }catch(Exception e){
						 e.printStackTrace();
					 }
				}
				
				
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.release();
		}
	}
	
	
	public StrengConnection getConnection(){
		try {
			if(localConnection.get()==null){
				localConnection.set(new StrengConnection(connectionPools.getConnection(), sourceConfig.isAutoCommit()));
			}
			return localConnection.get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	

	public void release(){
		try {
			if(localConnection.get()!=null){
				localConnection.get().close();
				localConnection.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 　

参数

意义

说明

一 BoneCP主要配置参数 

1

jdbcUrl 

设置数据库URL 

　

2

username

设置数据库用户名 

　

3

password

设置数据库密码

　

4

partitionCount

设置分区个数。这个参数默认为1，建议3-4（根据特定应用程序而定）。

为了减少锁竞争和改善性能，从当前线程分区(thread-affinity)中获取一个connection,也就是这个样子：partitions[Thread.currentThread().getId() % partitionCount]。当拥有充足的短期(short-lived)的线程时候，这个参数设置越大，性能越好。当超过一定的阀值时，连接池的维护工作就可能对性能造成一定的负面影响（仅当分区上的connection使用耗尽时）。 

5

maxConnectionsPerPartition

设置每个分区含有connection最大个数。这个参数默认为2。如果小于2，BoneCP将设置为50。

比如：partitionCount设置为3，maxConnectionPerPartition设置为5，你就会拥有总共15个connection。注意：BoneCP不会将这些connection一起创建出来，而是说在需要更多connection的时候从minConnectionsPerPartition参数开始逐步地增长connection数

6

minConnectionsPerPartition

设置每个分区含有connection最小个数。这个参数默认为0。

　

7

acquireIncrement

设置分区中的connection增长数量。这个参数默认为1。

当每个分区中的connection大约快用完时，BoneCP动态批量创建connection，这个属性控制一起创建多少个connection（不会大于maxConnectionsPerPartition）.
注意：这个配置属于每个分区的设置。 

8

poolAvailabilityThreshold

设置连接池阀值。这个参数默认为20%。如果小于0或是大于100，BoneCP将设置为20。

连接池观察线程(PoolWatchThread)试图为每个分区维护一定数量的可用connection。
这个数量趋于maxConnectionPerPartition和minConnectionPerPartition之间。这个参数是以百分比的形式来计算的。例如：设置为20，下面的条件如果成立：Free Connections / MaxConnections< poolAvailabilityThreshold；就会创建出新的connection。
换句话来说连接池为每个分区至少维持20%数量的可用connection。设置为0时，每当需要connection的时候，连接池就要重新创建新connection，这个时候可能导致应用程序可能会为了获得新connection而小等一会。

9

(connectionTimeout )0.8版中替换为：connectionTimeoutInMs

设置获取connection超时的时间。这个参数默认为Long.MAX_VALUE;单位：毫秒。

在调用getConnection获取connection时，获取时间超过了这个参数，就视为超时并报异常。

二 BoneCP线程配置参数 

1

releaseHelperThreads

--0.8版中已不建议使用。

设置connection助手线程个数。这个参数默认为3。如果小于0，BoneCP将设置为3。

设置为0时，应用程序线程被阻塞，直到连接池执行必要地清除和回收connection，并使connection在其它线程可用。
设置大于0时，连接池在每个分区中创建助手线程处理回收关闭后的connection（应用程序会通过助手线程异步地将这个connection放置到一个临时队列中进行处理)。
对于应用程序在每个connection上处理大量工作时非常有用。可能会降低运行速度，不过在高并发的应用中会提高性能。

2

statementReleaseHelperThreads

设置statement助手线程个数。这个参数默认为3。如果小于0，BoneCP将设置为3。

设置为0时，应用程序线程被阻塞，直到连接池或JDBC驱动程序关闭statement。
设置大于0时，连接池会在每个分区中创建助理线程，异步地帮助应用程序关闭statement当应用程序打开了大量的statement是非常有用的。可能会降低运行速度，不过在高并发的应用中会提高性能。

3

maxConnectionAge

设置connection的存活时间。这个参数默认为0，单位：毫秒。设置为0该功能失效。

通过ConnectionMaxAgeThread观察每个分区中的connection，不管connection是否空闲，如果这个connection距离创建的时间大于这个参数就会被清除。当前正在使用的connection不受影响，直到返回到连接池再做处理。

4

idleMaxAge在0.8版中替换为

idleMaxAgeInMinutes 

设置connection的空闲存活时间。这个参数默认为60，单位：分钟。设置为0该功能失效。

通过ConnectionTesterThread观察每个分区中的connection，如果这个connection距离最后使用的时间大于这个参数就会被清除。注意：这个参数仅和idleConnectionTestPeriod搭配使用

5

idleConnectionTestPeriod在0.8版中替换为idleConnectionTestPeriodInMinutes  

设置测试connection的间隔时间。这个参数默认为240，单位：分钟。设置为0该功能失效。

通过ConnectionTesterThread观察每个分区中的connection， 如果这个connection距离最后使用的时间大于这个参数并且距离上一次测试的时间大于这个参数就会向数据库发送一条测试语句，如果执行失败则将这个connection清除。 注意：这个值仅和idleMaxAge搭配使用，参数要适当！

三 BoneCP其他可选配置参数

1

acquireRetryAttempts

设置重新获取连接的次数。这个参数默认为5。

获取某个connection失败之后会多次尝试重新连接，如果在这几次还是失败则放弃。

2

acquireRetryDelay

设置重新获取连接的次数间隔时间。这个参数默认为7000，单位：毫秒。如果小于等于0，BoneCP将设置为1000。

获取connection失败之后再次尝试获取connection的间隔时间。

3

lazyInit

设置连接池初始化功能。这个参数默认为false。

设置为true，连接池将会初始化为空，直到获取第一个connection。

4

statementsCacheSize

设置statement缓存个数。这个参数默认为0。

　

5

disableJMX

设置是否关闭JMX功能。这个参数默认为false。

　

6

poolName

设置连接池名字。用于当作JMX和助手线程名字的后缀。

　

四 BoneCP调试配置参数

1

closeConnectionWatch

设置是开启connection关闭情况监视器功能。这个参数默认为false。

每当调用getConnection()时，都会创建CloseThreadMonitor，监视connection有没有关闭或是关闭了两次。警告：这个参数对连接池性能有很大的负面影响，慎用！仅在调试阶段使用！

2

closeConnectionWatchTimeout

设置关闭connection监视器（CloseThreadMonitor）持续多长时间。这个参数默认为0；单位：毫秒。

仅当closeConnectionWatch参数设置为可用时，设置这个参数才会起作用。
设置为0时，永远不关闭。

3

logStatementsEnabled

设置是否开启记录SQL语句功能。这个参数默认是false。

将执行的SQL记录到日志里面（包括参数值）。调试阶段会很有用.

4

queryExecuteTimeLimit

设置执行SQL的超时时间。这个参数默认为0；单位：毫秒。

当查询语句执行的时间超过这个参数，执行的情况就会被记录到日志中。
设置为0时，该功能失效。

5

disableConnectionTracking

设置是否关闭connection跟踪功能。这个参数默认为false。

设置为true，连接池则不会监控connection是否严格的关闭；设置为false，则启用跟踪功能（仅追踪通过Spring或一些事务管理等机制确保正确释放connection并放回到连接池中）。

6

transactionRecoveryEnabled

设置事务回放功能。这个参数默认为false。

	 * 
	 * 
	 * */
	
	
}
