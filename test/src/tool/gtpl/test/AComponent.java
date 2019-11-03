package tool.gtpl.test;

import century.gsdk.tools.gtpl.Template;

import java.util.List;

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
@Template(name="acomp",des = "this is a test component")
public class AComponent{
    @Template(name = "fieldName1",des = "laljdkljfljalksjdlkfj")
    private String field1;
    @Template(name = "fieldName2",des = "laljdkljfljalksjdlkfj")
    private int field2;
    @Template(name = "fieldName3",des = "laljdkljfljalksjdlkfj")
    private BComponent component;
    @Template(name = "fieldName4",des = "laljdkljfljalksjdlkfj")
    private List<BComponent> intlist;
    @Template(name = "fieldName5",des = "laljdkljfljalksjdlkfj")
    private List<BComponent> map;
}
