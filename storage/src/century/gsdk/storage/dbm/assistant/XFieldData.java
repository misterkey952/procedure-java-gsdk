package century.gsdk.storage.dbm.assistant;

public class XFieldData {
	/** 类名称*/
	private String classField;
	/** 数据库字段名*/
	private String dbField;
	/** 字段大小*/
	private int length;
	/** 是否为自增ID*/
	private boolean auto_increment;
	
	public String getClassField() {
		return classField;
	}
	public void setClassField(String classField) {
		this.classField = classField;
	}
	public String getDbField() {
		return dbField;
	}
	public void setDbField(String dbField) {
		this.dbField = dbField;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public boolean isAuto_increment() {
		return auto_increment;
	}
	public void setAuto_increment(boolean auto_increment) {
		this.auto_increment = auto_increment;
	}
	
	
	
	
}
