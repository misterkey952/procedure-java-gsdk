package century.gsdk.tools.gtpl;

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
public interface TempleteComponent {
    ODSSheet generateSheet();
    ODSRecord generateRecord();
    void parseRecord(ODSRecord record);
    void encode(Element element);
    void decode(Element element);
}
