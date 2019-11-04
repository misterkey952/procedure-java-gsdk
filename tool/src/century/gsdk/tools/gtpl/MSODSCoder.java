package century.gsdk.tools.gtpl;

import century.gsdk.tools.ods.MSODSFile;
import century.gsdk.tools.ods.ODSFile;
import century.gsdk.tools.ods.ODSRecord;
import century.gsdk.tools.ods.ODSSheet;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class MSODSCoder extends ODSCoder{

    public MSODSCoder(String name, String directory) {
        super(name, directory);
        odsFile=new MSODSFile(this.name,this.directory);
    }
    @Override
    protected void decode(Object object) throws Exception{
        genSheetItera(object.getClass());
        Field[] fields=object.getClass().getDeclaredFields();
        for(Field field:fields){
           output(field,object);
        }

        odsFile.out();
    }

    @Override
    protected void encode(Object object) throws Exception{
        odsFile.in();
        Field[] fields=object.getClass().getDeclaredFields();
        for(Field field:fields){
            input(field,object);
        }
    }
}
