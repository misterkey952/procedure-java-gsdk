package century.gsdk.storage.core;

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
public class Storage implements IStorage{

    private StorageSource[] sources;

    @Override
    public void init(StorageInfo info) {
        sources=new StorageSource[info.getSplit()];
        String name=info.getName();

        if(sources.length>1){
            for(int i=0;i<sources.length;i++){
                try {
                    info.setName(name+"_"+i);
                    sources[i]=new StorageSource(info);
                } catch (Exception e) {
                    StorageLogger.Stroage.error("init source failed",e);
                }
            }
        }else{
            try {
                sources[0]=new StorageSource(info);
            } catch (Exception e) {
                StorageLogger.Stroage.error("init source failed",e);
            }
        }

        info.setName(name);
    }

    @Override
    public StorageSource obtainStorageSource() {
        return sources[0];
    }

    @Override
    public StorageSource obtainStorageSource(int splitID) {
        return sources[splitID%sources.length];
    }

    @Override
    public StorageConnect obtainConnection() {
        return sources[0].getConnection();
    }

    @Override
    public StorageConnect obtainConnection(int splitID) {
        return sources[splitID%sources.length].getConnection();
    }

    @Override
    public StorageSource[] storageSouece() {
        return sources;
    }


}
