package century.gsdk.net.netty;

import century.gsdk.net.core.NetConnect;
import century.gsdk.net.core.NetSession;
import century.gsdk.net.core.SessionCloseHook;
import io.netty.channel.socket.SocketChannel;
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
public class NettySession extends NetSession {
    private SocketChannel socketChannel;

    public NettySession(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
        socketChannel.closeFuture().addListener(new NetSessionCloseEvent(this));
    }

    public NettySession(SocketChannel socketChannel,SessionCloseHook closeHook) {
        this.socketChannel = socketChannel;
        initCloseHook(closeHook);
        socketChannel.closeFuture().addListener(new NetSessionCloseEvent(this));
    }

    @Override
    public void sendMsg(Object msg) {
        socketChannel.writeAndFlush(msg);
    }

    @Override
    public void syncSendMsg(Object msg) {
        try {
            socketChannel.writeAndFlush(msg).sync();
        } catch (InterruptedException e) {
            error("syncSendMsg err",e);
        }
    }

    @Override
    public String localIP() {
        return socketChannel.localAddress().getHostName();
    }

    @Override
    public int localPort() {
        return socketChannel.localAddress().getPort();
    }

    @Override
    public String remoteIP() {
        return socketChannel.remoteAddress().getHostName();
    }

    @Override
    public int remotePort() {
        return socketChannel.remoteAddress().getPort();
    }

    @Override
    public void onClose() {
        socketChannel.close();
        info("session is close");
    }

    @Override
    public <T extends NetConnect> T getNetConnect() {
        return (T) socketChannel().attr(NettyConnect.NETTYCONNECT).get();
    }

    public SocketChannel socketChannel(){
        return socketChannel;
    }
}
