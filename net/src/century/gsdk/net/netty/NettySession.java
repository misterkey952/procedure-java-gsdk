package century.gsdk.net.netty;

import century.gsdk.net.core.NetSession;
import century.gsdk.net.core.SessionCloseHook;
import io.netty.channel.socket.SocketChannel;

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

    public SocketChannel socketChannel(){
        return socketChannel;
    }
}
