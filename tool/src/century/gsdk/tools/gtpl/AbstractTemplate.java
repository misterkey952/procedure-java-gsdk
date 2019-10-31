package century.gsdk.tools.gtpl;

import century.gsdk.tools.ods.MSODSFile;
import century.gsdk.tools.ods.ODSFile;

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
public abstract class AbstractTemplate {
    private String directory;
    private String name_ods;
    private String name_xml;

    public AbstractTemplate(String directory, String name_ods, String name_xml) {
        this.directory = directory;
        this.name_ods = name_ods;
        this.name_xml = name_xml;
    }

    public void writeODS(){
        ODSFile odsFile=new MSODSFile(name_ods,directory);
        onWriteODS(odsFile);
        odsFile.out();
    }

    protected abstract void onWriteODS(ODSFile odsFile);
}
