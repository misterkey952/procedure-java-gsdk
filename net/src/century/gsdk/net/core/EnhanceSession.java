package century.gsdk.net.core;

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
public class EnhanceSession implements ISession{

    @Override
    public void addConnect(NetConnect connect, boolean master) {

    }

    @Override
    public void sendMsg(Object msg) {

    }

    @Override
    public void syncSendMsg(Object msg) {

    }

    @Override
    public void sendMsg(Object msg, NetSendCallBack callBack) {

    }

    @Override
    public Identifier identifier() {
        return null;
    }

    @Override
    public void setIdentifier(Identifier identifier) {

    }

    @Override
    public void addSessionInvalid(SessionInvalid sessionInvalid) {

    }

    @Override
    public void onConnectClose(NetConnect connect) {

    }

    @Override
    public NetConnect masterConnect() {
        return null;
    }

    @Override
    public void attribute(String key, Object attr) {

    }

    @Override
    public <T> T attribute(String key) {
        return null;
    }

    @Override
    public <T> T cleanAttr(String key) {
        return null;
    }

    @Override
    public void cleanAllAttr() {

    }
}
