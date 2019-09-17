package century.gsdk.net.netty;

import century.gsdk.net.core.NetSession;
import io.netty.channel.socket.SocketChannel;

public class NettySession extends NetSession {
    private SocketChannel socketChannel;

    public NettySession(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
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

    public SocketChannel socketChannel(){
        return socketChannel;
    }
}
