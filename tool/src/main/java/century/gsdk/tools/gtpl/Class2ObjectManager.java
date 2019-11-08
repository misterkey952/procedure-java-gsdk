package century.gsdk.tools.gtpl;

import java.util.HashMap;
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
 * Author' Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class Class2ObjectManager {
    private Map<Class,Class2Object> map=new HashMap<>();

    Class2Object getClass2Object(Class clazz){
        return map.get(clazz);
    }

    Class2Object createClass2Object(Class clazz){
        Class2Object class2Object=new Class2Object(clazz);
        map.put(clazz,class2Object);
        return class2Object;
    }

    boolean isRead(Class clazz){
        return map.containsKey(clazz);
    }



}
