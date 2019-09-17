package century.gsdk.net.netty;
import century.gsdk.net.core.NetConnect;
import century.gsdk.tools.xml.XMLTool;
import com.google.protobuf.Parser;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class NettyConnect extends NetConnect {
    public static final AttributeKey<NettyConnect> NETTYCONNECT=AttributeKey.valueOf("NETTYCONNECT");
    protected ChannelFuture channelFuture;
    private Map<Integer, Parser> type2parse;
    private Map<Parser,Integer> parse2type;
    public NettyConnect(String name, String ip, int port) {
        super(name, ip, port);
        type2parse=new HashMap<>();
        parse2type=new HashMap<>();
    }

    public void registerParse(int msgType,Parser parser){
        type2parse.put(msgType,parser);
        parse2type.put(parser,msgType);
    }

    public void loadMessage(Element element){
        List<Element> elements= XMLTool.getElementsByTag("handle",element);
        int type;
        Class msgparse;
        for(Element ele:elements){
            type=XMLTool.getIntAttrValue(ele,"type");
            try {
                msgparse= Class.forName(XMLTool.getStrAttrValue(ele,"msg"));
                Field field=msgparse.getDeclaredField("PARSER");
                boolean ass=field.isAccessible();
                field.setAccessible(true);
                registerParse(type, (Parser) field.get(null));
                field.setAccessible(ass);
            } catch (Exception e) {
                error("loadMessage err",e);
            }
        }
    }


    public int getMsgType(Parser parser){
        return parse2type.get(parser);
    }

    public Parser getParser(int msgType){
        return type2parse.get(msgType);
    }

    public abstract ChannelInitializer<SocketChannel> channelInitializer();
}
