package century.gsdk.storage.dbm.assistant;
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
