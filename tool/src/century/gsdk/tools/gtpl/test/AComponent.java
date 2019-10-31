package century.gsdk.tools.gtpl.test;

import century.gsdk.tools.gtpl.TempleteComponent;
import century.gsdk.tools.ods.ODSHead;
import century.gsdk.tools.ods.ODSKeyValue;
import century.gsdk.tools.ods.ODSRecord;
import century.gsdk.tools.ods.ODSSheet;
import org.dom4j.Element;

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
public class AComponent implements TempleteComponent {
    private String f1;
    private int f2;
    private String kk;
    @Override
    public ODSSheet generateSheet() {
        ODSSheet sheet=new ODSSheet(AComponent.class.getName(),"AC");
        sheet.addHead("f1",
                "F1",
                "F1DES");

        sheet.addHead("f2",
                "F2",
                "F2des");

        sheet.addHead("kk",
                "KK",
                "KKDES");
        return sheet;
    }

    @Override
    public ODSRecord generateRecord() {
        ODSRecord record=new ODSRecord();
        record.addKV("f1",
                f1);

        record.addKV("f2",
                f2);

        record.addKV("kk",
                kk);
        return record;
    }

    @Override
    public void parseRecord(ODSRecord record) {
        this.f1=record.getKeyValue("f1").getValue();
        this.f2=record.getKeyValue("f2").intValue();
        this.kk=record.getKeyValue("kk").getValue();
    }

    @Override
    public void encode(Element element) {

    }

    @Override
    public void decode(Element element) {

    }
}
