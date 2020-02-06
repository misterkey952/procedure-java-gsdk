package century.gsdk.robot;

import century.gsdk.net.core.Identifier;

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
public abstract class Behavior {
    private Identifier identifier;
    private Robot robot;
    private Component component;
    private WaitFor waitFor;
    void robot(Robot robot){
        this.robot=robot;
    }

    void component(Component component){
        this.component=component;
    }

    public <T extends Robot>T robot(){
        return (T) robot;
    }
    public Behavior(String name,String category) {
        identifier=new Identifier(name,category);
    }

    protected abstract Result execute();

    protected Result waitFor(WaitFor wait){
        waitFor=wait;
        wait.waitFor();
        return errResult(Result.BEHAVIOR_WAIT,"BEHAVIOR_WAIT");
    }


    protected void cancelWaitFor(){
        this.waitFor=null;
    }

    Result waitResult(RobotEvent robotEvent){
        if(waitFor!=null){
            return waitFor.result(robotEvent);
        }
        return null;
    }

    protected Result success(){
        return new Result();
    }

    protected Result waitGoOn(){
        return errResult(Result.BEHAVIOR_WAIT,"BEHAVIOR_WAIT");
    }

    protected Result errResult(int code, String msg){
        return new Result(code,msg);
    }


    protected void next(){
        component.nextBehavior();
    }

    protected Result execute(Class clazz){
        return component.execute(clazz);
    }

    protected void jump(Class clazz){
        component.jumpBehavior(clazz);
    }
}
