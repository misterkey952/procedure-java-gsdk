package century.gsdk.net.core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *     Copyright (C) <2019>  <Century>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class NetSession implements ISession{
    private Identifier identifier;
    private NetConnect connect;
    private SessionInvalid sessionInvalid;
    @Override
    public void addConnect(NetConnect connect, boolean master) {
        this.connect=connect;
    }

    public void sendMsg(Object msg){
        connect.sendMsg(msg);
    }
    public void syncSendMsg(Object msg){
        connect.syncSendMsg(msg);
    }

    public void sendMsg(Object msg,NetSendCallBack callBack){
        connect.sendMsg(msg,callBack);
    }

    @Override
    public Identifier identifier() {
        return this.identifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        this.identifier=identifier;
    }

    @Override
    public void setSessionInvalid(SessionInvalid sessionInvalid) {
        this.sessionInvalid=sessionInvalid;
        sessionInvalid.setSession(this);
    }

}
