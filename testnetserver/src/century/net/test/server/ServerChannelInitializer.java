package century.net.test.server;

import century.gsdk.net.netty.Int32HeadDecoder;
import century.gsdk.net.netty.Int32HeadEncoder;
import century.gsdk.net.netty.protobuf.ProtobufMessageManager;
import century.gsdk.net.netty.protobuf.SimpleProtobufDecoder;
import century.gsdk.net.netty.protobuf.SimpleProtobufEncoder;
import century.net.test.msg.TestSignature;
import century.net.test.msg.TestString;
import io.netty.channel.ChannelInitializer;
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
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private ProtobufMessageManager protobufMessageManager;

    public ServerChannelInitializer(){
        protobufMessageManager=new ProtobufMessageManager();
        protobufMessageManager.registerParse(2001, TestString.parser());
        protobufMessageManager.registerParse(2002, TestSignature.parser());

    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast("headde",new Int32HeadDecoder());
        socketChannel.pipeline().addLast("headen",new Int32HeadEncoder());
        socketChannel.pipeline().addLast("encoder",new SimpleProtobufEncoder(protobufMessageManager));
        socketChannel.pipeline().addLast("decoder",new SimpleProtobufDecoder(protobufMessageManager));
        socketChannel.pipeline().addLast("handler",new ServerLogicHandler());
    }
}
