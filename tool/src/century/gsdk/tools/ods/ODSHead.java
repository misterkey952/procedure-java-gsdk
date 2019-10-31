package century.gsdk.tools.ods;

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
public class ODSHead {
    private String key;
    private String name;
    private String des;
    private int index;

    ODSHead(String key, String name, String des) {
        this.key = key;
        this.name = name;
        this.des = des;
    }

    void index(int index){
        this.index=index;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }

    public int getIndex() {
        return index;
    }
}
