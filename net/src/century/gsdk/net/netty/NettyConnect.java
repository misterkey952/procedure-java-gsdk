package century.gsdk.net.netty;
import century.gsdk.net.core.NetConnect;
import century.gsdk.tools.xml.XMLTool;
import com.google.protobuf.Parser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import org.dom4j.Element;

import java.lang.reflect.Field;
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
public abstract class NettyConnect extends NetConnect {
    public static final AttributeKey<NettyConnect> NETTYCONNECT=AttributeKey.valueOf("NETTYCONNECT");
    protected Channel channel;
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
