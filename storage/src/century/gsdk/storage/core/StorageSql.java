package century.gsdk.storage.core;

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
 * Author's Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class StorageSql {
    private String[] sqlStage;
    private SqlTable[] tables;

    public StorageSql(String[] sqlStage, SqlTable[] tables) {
        this.sqlStage = sqlStage;
        this.tables = tables;
    }

    public String splitSql(int split){
        StringBuilder stb=new StringBuilder();
        SqlTable sqlTable;
        for(int i=0;i<sqlStage.length;i++){
            stb.append(sqlStage[i]);
            if(i<tables.length){
                sqlTable=tables[i];
                if(sqlTable.getSplit()>1){
                    stb.append(sqlTable.getName()).append("_").append(split%sqlTable.getSplit());
                }else{
                    stb.append(sqlTable.getName());
                }
            }

        }
        return stb.toString();
    }

    public String sql(){
        StringBuilder stb=new StringBuilder();
        for(int i=0;i<sqlStage.length;i++){
            stb.append(sqlStage[i]);
            if(i<tables.length){
                stb.append(tables[i].getName());
            }

        }
        return stb.toString();
    }

}
