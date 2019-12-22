package century.gsdk.robot;

import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;

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
 * Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public abstract class Robot {
    private Identifier identifier;
    private List<BehaviorUnit> behaviorUnits=new ArrayList<>();
    private BehaviorUnit startUnit;

    void init(Element element){
        identifier=new Identifier(
                XMLTool.getStrAttrValue(element,"name"),
                XMLTool.getStrAttrValue(element,"category")
        );
        consist();
    }

    protected void addUnit(BehaviorUnit unit){
        behaviorUnits.add(unit);
    }

    protected void setStartUnit(BehaviorUnit behaviorUnit){
        this.startUnit=behaviorUnit;
    }

    protected abstract void consist();

}