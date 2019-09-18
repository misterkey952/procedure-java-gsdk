package century.gsdk.timedemo;

import century.gsdk.net.netty.NettyClient;
import century.gsdk.net.netty.NettySession;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoClient extends NettyClient {
    private static final Logger logger= LoggerFactory.getLogger("DEMO");
    private TimeClientDecoder decoder;
    private TimeClientEncoder encoder;
    public DemoClient(String name, String ip, int port) {
        super(name, ip, port);
        decoder=new TimeClientDecoder();
        encoder=new TimeClientEncoder();
    }

    @Override
    public void onUserClose() {
        logger.info("the user close...................");
    }

    @Override
    public ChannelInitializer<SocketChannel> channelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast("encoder",encoder);
                socketChannel.pipeline().addLast("decoder",decoder);
                registerSession(new NettySession(socketChannel));
            }
        };
    }

    public void register(){
        int timeID=1;
        session().sendMsg(timeID);
    }

}
