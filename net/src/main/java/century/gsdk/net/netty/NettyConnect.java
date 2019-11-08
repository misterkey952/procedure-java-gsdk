package century.gsdk.net.netty;

import century.gsdk.net.NetLogger;
import century.gsdk.net.core.*;
import io.netty.channel.socket.SocketChannel;

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
public class NettyConnect implements NetConnect {

    private SocketChannel channel;
    private NetAddress remoteAddress;
    private NetAddress localAddress;
    public NettyConnect(SocketChannel channel) {
        this.channel = channel;
        remoteAddress=new NetAddress(channel.remoteAddress().getAddress().getHostAddress(),channel.remoteAddress().getPort());
        localAddress=new NetAddress(channel.localAddress().getAddress().getHostAddress(),channel.localAddress().getPort());
    }

    public SocketChannel channel(){
        return channel;
    }


    @Override
    public NetAddress getRemoteAddress() {
        return this.remoteAddress;
    }

    @Override
    public NetAddress getLocalAddress() {
        return this.localAddress;
    }

    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public void close() {
        if(channel!=null){
            channel.close();
        }
        NetLogger.NetLog.info("Connect:{} connect {} close",remoteAddress.toString());
    }
    @Override
    public void sendMsg(Object msg) {
        channel.writeAndFlush(msg);
    }

    @Override
    public void syncSendMsg(Object msg) {
        try {
            channel.writeAndFlush(msg).sync();
        } catch (InterruptedException e) {
            NetLogger.NetLog.error("syncSendMsg err",e);
        }
    }

    @Override
    public void sendMsg(Object msg, NetSendCallBack sendCallBack) {
        channel.writeAndFlush(msg).addListener(new NettySendCallBack(sendCallBack));
    }

    @Override
    public void addCloseHook(NetConnectCloseHook connectCloseHook) {
        connectCloseHook.setConnect(this);
        channel.closeFuture().addListener(new NettyConnectCloseHook(connectCloseHook));
    }
}
