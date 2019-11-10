package century.gsdk.tools.gtpl;

import org.apache.commons.collections4.map.HashedMap;

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
public abstract class TemplateWrapper {

    private Map<String,AbstractTemplate> template2NameMap;
    private Map<Class,AbstractTemplate> template2ClassMap;

    private String directory_edit;
    private String directory_prod;

    public TemplateWrapper(String directory_edit, String directory_prod) {
        this.directory_edit = directory_edit;
        this.directory_prod = directory_prod;
        template2NameMap=new HashedMap<>();
        template2ClassMap=new HashedMap<>();
        initizlize();
    }

    protected abstract void initizlize();


    protected void registerTemplate(AbstractTemplate template){
        template2NameMap.put(template.getNameProd(),template);
        template2ClassMap.put(template.getClass(),template);
        template.wrapper(this);
    }

    String getDirectory_edit() {
        return directory_edit;
    }

    String getDirectory_prod() {
        return directory_prod;
    }


    public <T extends AbstractTemplate>T getTemplate(String key){
        return (T) template2NameMap.get(key);
    }
    public <T extends AbstractTemplate>T getTemplate(Class key){
        return (T) template2ClassMap.get(key);
    }


    public void outputODS(String name) throws Exception {
        AbstractTemplate abstractTemplate=getTemplate(name);
        abstractTemplate.outputODS();
    }

    public void convertXML(String name) throws Exception {
        AbstractTemplate abstractTemplate=getTemplate(name);
        abstractTemplate.inputODS();
        abstractTemplate.outputXML();
    }

    public void convertXML() throws Exception {
        for(String key:template2NameMap.keySet()){
            convertXML(key);
        }
    }


    public void inputXML(String name) throws Exception {
        AbstractTemplate abstractTemplate=getTemplate(name);
        abstractTemplate.inputXML();
    }

    public void inputXML() throws Exception {
        for(String key:template2NameMap.keySet()){
            inputXML(key);
        }
    }

    public void outputODS() throws Exception {
        for(String key:template2NameMap.keySet()){
            outputODS(key);
        }
    }

}
