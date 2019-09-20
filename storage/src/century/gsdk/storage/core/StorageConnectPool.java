package century.gsdk.storage.core;
import com.jolbox.bonecp.BoneCP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

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
 * Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class StorageConnectPool {
    private static final Logger logger= LoggerFactory.getLogger("StorageConnectPool");
    private BoneCP boneCP;

    public StorageConnectPool(StorageInfo storageInfo) throws Exception{
        try {
            this.boneCP =new BoneCP(storageInfo.BoneCPConfig());
        } catch (Exception e) {
            logger.error("init pool err",e);
            throw e;
        }
    }
    protected StorageConnect getConnection(){
        try {
            return new StorageConnect(boneCP.getConnection());
        } catch (SQLException e) {
            logger.error("getConnection err",e);
            return null;
        }
    }
}
