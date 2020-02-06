package century.gsdk.robot;

import century.gsdk.net.core.Identifier;

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
public abstract class Component {
    private Robot robot;
    private Identifier identifier;
    private List<Behavior> allBehaviorOrder=new ArrayList<>();
    private Map<Class,Integer> behaviorMap=new HashMap<>();
    private int lifeCycle=Robot.UNINIT_LIFECYCLE;
    private int curBehPointer;

    public Component(Identifier identifier) {
        this.identifier = identifier;
    }

    public Component(Identifier identifier, int lifeCycle) {
        this.identifier = identifier;
        this.lifeCycle = lifeCycle;
    }


    boolean isComplete(){
        return curBehPointer>=allBehaviorOrder.size();
    }

    void resetPointer(){
        if(lifeCycle>0){
            lifeCycle--;
        }
        curBehPointer=0;
    }

    protected void next(){
        robot.nextComponent();
    }

    void robot(Robot robot){
        this.robot=robot;
    }

    public <T extends Robot>T robot(){
        return (T) robot;
    }

    protected abstract void consist();

    protected void addBehavior(Behavior behavior){
        behavior.robot(robot);
        behavior.component(this);
        behaviorMap.put(behavior.getClass(),allBehaviorOrder.size());
        allBehaviorOrder.add(behavior);
    }

    Result execute(Class clazz){
        curBehPointer=behaviorMap.get(clazz);
        return execute();
    }


    Result waitForResult(RobotEvent robotEvent){
        return allBehaviorOrder.get(curBehPointer).waitResult(robotEvent);
    }

    void nextBehavior(){
        curBehPointer++;
        NextBehaviorEvent behaviorEvent=new NextBehaviorEvent();
        behaviorEvent.robot(robot);
        robot.next().addEvent(behaviorEvent);
    }


    void jumpBehavior(Class clazz){
        curBehPointer=behaviorMap.get(clazz);
        NextBehaviorEvent behaviorEvent=new NextBehaviorEvent();
        behaviorEvent.robot(robot);
        robot.next().addEvent(behaviorEvent);
    }

    Result execute(){
        Behavior behavior=allBehaviorOrder.get(curBehPointer);
        return behavior.execute();
    }

    int getLifeCycle() {
        return lifeCycle;
    }

    void setLifeCycle(int lifeCycle) {
        this.lifeCycle = lifeCycle;
    }
}
