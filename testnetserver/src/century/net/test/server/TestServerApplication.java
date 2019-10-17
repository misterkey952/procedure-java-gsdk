package century.net.test.server;

import century.gsdk.docker.GameApplication;
import century.gsdk.net.core.Identifier;
import century.gsdk.net.netty.NettyListener;

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
public class TestServerApplication extends GameApplication {
    private static final TestServerApplication instance=new TestServerApplication();
    private NettyListener nettyListener;
    public static TestServerApplication getInstance(){
        return instance;
    }
    private TestServerApplication() {
        super("TestServerApplication");
    }

    @Override
    public void initialize() {
        Identifier identifier=new Identifier("test","OM");
        nettyListener=new NettyListener(
                identifier,
                "127.0.0.1",6000,8,8,
                new ServerChannelInitializer()
        );


        nettyListener.listen();
    }
}
