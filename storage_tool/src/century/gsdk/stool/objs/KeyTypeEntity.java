package century.gsdk.stool.objs;

import java.util.ArrayList;
import java.util.List;

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
 * Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class KeyTypeEntity {
    private String tableName;
    private String keyType;
    private List<String> fields;

    public KeyTypeEntity(String keyType,String tableName) {
        this.tableName=tableName;
        this.keyType=keyType;
        fields=new ArrayList<>();
    }


    public void addField(String field){
        fields.add(field);
    }

    public String genSql(){
        StringBuilder builder=new StringBuilder();
        if(keyType.equals("KEY")){
            builder.append(keyType).append(" ");
        }else{
            builder.append(keyType).append(" KEY ");
        }

        builder.append(tableName).append("_").append(keyType);
        StringBuilder sstf=new StringBuilder();
        for(int i=0;i<fields.size();i++){
            builder.append(fields.get(i));
            sstf.append(fields.get(i));
            if(i<(fields.size()-1)){
                builder.append("_");
                sstf.append(",");
            }
        }
        builder.append(" (").append(sstf.toString()).append(")");

        return builder.toString();
    }

}
