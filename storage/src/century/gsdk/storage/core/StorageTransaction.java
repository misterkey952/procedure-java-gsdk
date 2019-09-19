package century.gsdk.storage.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
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
 * Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class StorageTransaction {
    private static final Logger logger= LoggerFactory.getLogger("StorageTransaction");
    private Map<IStorage,StorageConnect[]> storageListMap;

    public StorageTransaction() {
        storageListMap=new HashMap<>();
    }

    public StorageConnect getConnection(IStorage storage){
        StorageConnect[] storageConnects=storageListMap.get(storage);
        if(storageConnects==null){
            storageConnects=new StorageConnect[storage.storageSouece().length];
            storageListMap.put(storage,storageConnects);
        }
        StorageConnect connect=storageConnects[0];
        if(connect==null){
            connect=storageConnects[0]=storage.achiveConnection();
            try {
                connect.begin();
            } catch (SQLException e) {
                logger.error("getConnection begin err",e);
            }
        }
        return connect;
    }

    public StorageConnect getConnection(IStorage storage,int splitID){
        StorageConnect[] storageConnects=storageListMap.get(storage);
        if(storageConnects==null){
            storageConnects=new StorageConnect[storage.storageSouece().length];
            storageListMap.put(storage,storageConnects);
        }

        StorageConnect connect=storageConnects[splitID%storageConnects.length];
        if(connect==null){
            connect = storageConnects[splitID%storageConnects.length]=storage.achiveConnection(splitID);
            try {
                connect.begin();
            } catch (SQLException e) {
                logger.error("getConnection split begin err",e);
            }
        }
        return connect;
    }


    public void release(){
        for(StorageConnect[] storageConnects:storageListMap.values()){
            for(StorageConnect sc:storageConnects){
                if(sc!=null){
                    sc.release();
                }
            }
        }
    }

    public boolean commit(){
        for(StorageConnect[] storageConnects:storageListMap.values()){
            for(StorageConnect sc:storageConnects){
                if(sc!=null){
                    try {
                        sc.commit();
                    } catch (SQLException e) {
                        logger.error("commit err",e);
                        rollBack();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void rollBack(){
        for(StorageConnect[] storageConnects:storageListMap.values()){
            for(StorageConnect sc:storageConnects){
                if(sc!=null){
                    try {
                        sc.rollback();
                    } catch (SQLException e) {
                        logger.error("rollBack err",e);
                    }
                }
            }
        }
    }
}
