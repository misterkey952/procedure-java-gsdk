package tool.gtpl.test;

import century.gsdk.tools.gtpl.TemplateClass;
import century.gsdk.tools.gtpl.TemplateField;

import java.util.List;
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
@TemplateClass(name="测试Sheet")
public class AComponent{
    @TemplateField(name = "字段1",des = "这个字段的描述")
    private String field1;
    @TemplateField(name = "字段2",des = "这个字段的描述2")
    private int field2;
    @TemplateField(ref = "field1",name = "字段3",des = "这个字段的描述3")
    private BComponent component;
    @TemplateField(refClass = BComponent.class,ref = "field2",name = "字段3",des = "这个字段的描述3")
    private List<BComponent> intlist;
    @TemplateField(refClass = BComponent.class,ref = "field1",name="fdf",des="fdslf")
    private Map<String,BComponent> map;
}
