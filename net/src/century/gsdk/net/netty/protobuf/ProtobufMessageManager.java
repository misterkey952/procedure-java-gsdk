package century.gsdk.net.netty.protobuf;

import com.google.protobuf.Parser;

import java.util.HashMap;
import java.util.Map;

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
public class ProtobufMessageManager {
    private Map<Integer, Parser> type2parse;
    private Map<Parser,Integer> parse2type;

    public ProtobufMessageManager() {
        type2parse=new HashMap<>();
        parse2type=new HashMap<>();
    }

    public void registerParse(int msgType, Parser parser){
        type2parse.put(msgType,parser);
        parse2type.put(parser,msgType);
    }

    public int getMsgType(Parser parser){
        return parse2type.get(parser);
    }

    public Parser getParser(int msgType){
        return type2parse.get(msgType);
    }
}
