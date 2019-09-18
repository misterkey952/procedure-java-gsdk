package century.gsdk.ts.net;

import century.gsdk.net.netty.NettyServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeServer extends NettyServer {
    private static final Logger logger= LoggerFactory.getLogger("TimeEncoder");
    private TimeEncoder timeEncoder;
    private TimeDecoder timeDecoder;
    public TimeServer(String name, String ip, int port) {
        super(name, ip, port);
        timeEncoder=new TimeEncoder();
        timeDecoder=new TimeDecoder();
    }

    @Override
    public void onUserClose() {
        logger.info("timeserver user close");
    }

    @Override
    public ChannelInitializer<SocketChannel> channelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast("encoder",timeEncoder);
                socketChannel.pipeline().addLast("decoder",timeDecoder);
            }
        };
    }
}
