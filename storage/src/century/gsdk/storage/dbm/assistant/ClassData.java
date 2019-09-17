package century.gsdk.storage.dbm.assistant;

import java.util.List;

public class ClassData {
	private String tableName;
	private String insertSql;
	private String updateSql;
	private String deleteSql;
	private List<XFieldData> fieldDatas;
	private List<XFieldData> keyList;
	private String classStr;
	public String getInsertSql() {
		return insertSql;
	}
	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}
	public String getUpdateSql() {
		return updateSql;
	}
	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<XFieldData> getFieldDatas() {
		return fieldDatas;
	}
	public void setFieldDatas(List<XFieldData> fieldDatas) {
		this.fieldDatas = fieldDatas;
	}
	public List<XFieldData> getKeyList() {
		return keyList;
	}
	public void setKeyList(List<XFieldData> keyList) {
		this.keyList = keyList;
	}
	public String getDeleteSql() {
		return deleteSql;
	}
	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
	}
	public String getClassStr() {
		return classStr;
	}
	public void setClassStr(String classStr) {
		this.classStr = classStr;
	}
	
	
}
