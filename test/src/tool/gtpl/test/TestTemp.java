package tool.gtpl.test;

import century.gsdk.tools.gtpl.AbstractTemplate;
import century.gsdk.tools.gtpl.Template;
import century.gsdk.tools.str.StringTool;

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
public class TestTemp extends AbstractTemplate {
    @Template(key = "field1")
    private Map<String,AComponent> aComponentMap;
    public TestTemp() {
        super("test1","mmo");

//        aComponentMap=new HashMap<>();
//
//        Map<String,BComponent> bcomList=new HashMap<>();
//        for(int i=0;i<10;i++){
//            BComponent bco=new BComponent();
//            bco.setField1(StringTool.valueOf(i));
//            bco.setField2(StringTool.valueOf(i%3));
//            bco.setField3(i%4);
//            bcomList.put(bco.getField1(),bco);
//        }
//
//        for(int i=0;i<10;i++){
//            AComponent aComponent=new AComponent();
//            aComponent.setField1(StringTool.valueOf(i));
//            aComponent.setField2(i*5);
//            aComponent.setComponent(bcomList.get(StringTool.valueOf(i)));
//            aComponent.getIntlist().addAll(bcomList.values());
//            aComponent.getMap().putAll(bcomList);
//            aComponentMap.put(aComponent.getField1(),aComponent);
//        }
    }
}
