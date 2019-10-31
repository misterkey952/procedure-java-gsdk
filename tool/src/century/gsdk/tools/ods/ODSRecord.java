package century.gsdk.tools.ods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ODSRecord {
    private List<ODSKeyValue> kvList;
    private Map<String, ODSKeyValue> kvmap;

    public ODSRecord() {
        kvList=new ArrayList<>();
        kvmap=new HashMap<>();
    }

    public ODSKeyValue getKeyValue(String key){
        return kvmap.get(key);
    }

    public void addKV(String key,Object obj){
        ODSKeyValue keyValue=new ODSKeyValue(key,String.valueOf(obj));
        kvList.add(keyValue);
        kvmap.put(keyValue.getKey(),keyValue);
    }

    public List<ODSKeyValue> getKvList() {
        return kvList;
    }
}
