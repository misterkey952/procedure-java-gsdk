package tool.gtpl.test;

import century.gsdk.tools.gtpl.Template;

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
@Template(name="acomp",des = "this is a test component")
public class AComponent{
    @Template(name = "fieldName1",des = "laljdkljfljalksjdlkfj")
    private String field1;
    @Template(name = "fieldName2",des = "laljdkljfljalksjdlkfj")
    private int field2;
    @Template(ref = "field1",name = "fieldName3",des = "laljdkljfljalksjdlkfj")
    private BComponent component;
    @Template(ref = "field2",name = "fieldName4",des = "laljdkljfljalksjdlkfj")
    private List<BComponent> intlist;
    @Template(ref = "field2",key="field1",name = "fieldName5",des = "laljdkljfljalksjdlkfj")
    private Map<String,BComponent> map;

    public AComponent(){
        intlist=new ArrayList<>();
        map=new HashMap<>();
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public int getField2() {
        return field2;
    }

    public void setField2(int field2) {
        this.field2 = field2;
    }

    public BComponent getComponent() {
        return component;
    }

    public void setComponent(BComponent component) {
        this.component = component;
    }

    public List<BComponent> getIntlist() {
        return intlist;
    }

    public void setIntlist(List<BComponent> intlist) {
        this.intlist = intlist;
    }

    public Map<String, BComponent> getMap() {
        return map;
    }

    public void setMap(Map<String, BComponent> map) {
        this.map = map;
    }
}
