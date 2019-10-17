package century.net.test.client;

import century.gsdk.docker.GameApplication;
import century.gsdk.net.core.Identifier;
import century.gsdk.net.netty.NettyConnect;
import century.net.test.msg.TestString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

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
public class TestClientApplication extends GameApplication {
    private static final TestClientApplication instance=new TestClientApplication();
    private NettyConnect nettyConnect;
    public static TestClientApplication getInstance(){
        return instance;
    }
    private TestClientApplication() {
        super("TestClientApplication");
    }

    @Override
    public void initialize() {
        Identifier identifier=new Identifier("test","OM");
        nettyConnect=new NettyConnect(
                identifier,
                "127.0.0.1",6000,8,
                new ClientChannelInitializer()
        );
        nettyConnect.connect();

        for(int i=0;i<100000000;i++){
            TestString testString=TestString.newBuilder().setLineKey("LJALKDJFLKJDKSFKKASDF_"+i).build();
            nettyConnect.sendMsg(testString);
        }


    }
}
