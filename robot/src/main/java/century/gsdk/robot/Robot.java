package century.gsdk.robot;

import century.gsdk.net.core.Identifier;
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
    static final int INFINITE_LIFECYCLE=-1;
    static final int UNINIT_LIFECYCLE=-2;

    private Identifier identifier;
    private List<Component> components=new ArrayList<>();
    private List<Component> completeList=new ArrayList<>();
    private Map<Integer, Reaction> reactionMap=new HashMap<>();
    private Map<String,Object> attribute=new HashMap<>();
    private Nest nest;
    private int lifeCycle=INFINITE_LIFECYCLE;
    private int curComponentPointer;
    void nest(Nest nest){
        this.nest=nest;
    }
    protected Nest next(){
        return nest;
    }

    void init(Element element,int auto_num){
        identifier=new Identifier(
                XMLTool.getStrAttrValue(element,"name")+"_"+auto_num,
                XMLTool.getStrAttrValue(element,"category")
        );
        int lc=XMLTool.getIntAttrValue(element,"lifecycle");
        if(lc>0){
            lifeCycle=lc;
        }
        initialize(element);
        consist();
    }

    void go(){
        onStart();
        curComponentPointer=0;
        executeComponent();
    }

    void executeComponent(){
        if(components.size()<=0){
            RobotLogger.SYSLOG.info("[{}] [{}] has finished all tasks",nest.getIdentifier().toString(),identifier.toString());
            stop();
            return;
        }
        Component component=components.get(curComponentPointer);
        if(component.isComplete()){
            nextComponent();
            return;
        }
        component.execute();
    }

    void nextComponent(){
        if(!components.get(curComponentPointer).isComplete()){
            return;
        }

        components.get(curComponentPointer).resetPointer();
        if(components.get(curComponentPointer).getLifeCycle()==0){
            completeList.add(components.remove(curComponentPointer));
        }else{
            curComponentPointer++;
        }


        if(curComponentPointer>=components.size()){
            curComponentPointer=0;
        }
        NextComponentEvent event=new NextComponentEvent();
        event.robot(this);
        nest.addEvent(event);
    }


    public void stop(){
        onStop();
    }

    protected abstract void onStop();
    protected abstract void onStart();

    protected abstract void initialize(Element element);

    protected void addReaction(Reaction reaction){
        reactionMap.put(reaction.eventType(),reaction);
    }

    protected void addComponent(Component component){
        component.robot(this);
        if(component.getLifeCycle()==UNINIT_LIFECYCLE){
            component.setLifeCycle(lifeCycle);
        }
        component.consist();
        components.add(component);
    }


    public void attribute(String key,Object object){
        attribute.put(key,object);
    }

    public <T>T attribute(String key){
        return (T) attribute.get(key);
    }

    public <T>T removeAttribute(String key){
        return (T) attribute.remove(key);
    }

    protected abstract void consist();

    public void handleEvent(RobotEvent robotEvent){
        robotEvent.robot(this);
        nest.addEvent(robotEvent);
    }

    void reactionEvent(RobotEvent robotEvent){

        components.get(curComponentPointer).waitForResult(robotEvent);

        Reaction reaction=reactionMap.get(robotEvent.eventType());
        if(reaction!=null){
            reaction.action(robotEvent);
        }
    }

    public Identifier getIdentifier() {
        return identifier;
    }
}
