package century.gsdk.tools.gtpl.test;

import century.gsdk.tools.gtpl.AbstractTemplate;
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
public class TestTemp extends AbstractTemplate {


    public TestTemp() {
        super("F:\\century","test","testx");

    }

    @Override
    protected void onWriteODS(ODSFile odsFile) {
        AComponent aComponent=new AComponent();
        odsFile.addSheet(aComponent.generateSheet());
    }
}
