package century.gsdk.robot;

import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;

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
public class Nest implements Runnable{
    private Identifier identifier;
    private List<Robot> robots;

    public Nest() {
        robots=new ArrayList<>();
    }

    void init(Element element){
        identifier=new Identifier(
                XMLTool.getStrAttrValue(element,"name"),
                XMLTool.getStrAttrValue(element,"category")
        );

        List<Element> robotEleList=XMLTool.getElementsByTag("robot",element);
        for(Element robEle:robotEleList){
            try{
                int count=XMLTool.getIntAttrValue(robEle,"count");
                for(int i=0;i<count;i++){
                    Robot robot= (Robot) Class.forName(XMLTool.getStrAttrValue(robEle,"class")).getDeclaredConstructor().newInstance();
                    robot.init(robEle);
                    robots.add(robot);
                }
            }catch(Exception e){
                RobotLogger.SYSLOG.error("Nest init robot err",e);
            }

        }

    }

    @Override
    public void run() {

    }
}
