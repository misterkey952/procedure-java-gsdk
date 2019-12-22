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
public class NestManager {
    private static final NestManager instance=new NestManager();
    private List<Nest> nestList;
    private NestManager(){
        nestList=new ArrayList<>();
    }
    public static NestManager getInstance(){
        return instance;
    }

    public void init(String cfg){
        Element rootElement= XMLTool.getRootElement(cfg);
        List<Element> nestEleList=XMLTool.getElementsByTag("nest",rootElement);
        for(Element element:nestEleList){
            try{
                int nestCount=XMLTool.getIntAttrValue(element,"count");
                for(int i=0;i<nestCount;i++){
                    Nest nest= (Nest) Class.forName(XMLTool.getStrAttrValue(element,"class")).getDeclaredConstructor().newInstance();
                    nest.init(element);
                    nestList.add(nest);
                }
            }catch(Exception e){
                RobotLogger.SYSLOG.error("NestManager.init nest err",e);
            }
        }
    }

}
