package century.gsdk.tools.gtpl;

import century.gsdk.tools.ToolLogger;

import java.io.File;


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
    private String name_edit;
    private TemplateWrapper templateWrapper;

    public AbstractTemplate(String name_edit) {
        this.name_edit = name_edit;

    }

    void wrapper(TemplateWrapper wrapper){
        this.templateWrapper=wrapper;
    }

    protected abstract void onReadComplete();

    public String getNameProd(){
        return this.getClass().getName();
    }

    public void outputXML()throws Exception{
        ODSCoder odsCoder=new MSODSCoder(getNameProd(),templateWrapper.getDirectory_prod());
        odsCoder.decode(this);
        odsCoder.getOdsFile().outXML();
        ToolLogger.GTPL.info("{} output a XML file ,name={}",this.getClass().getSimpleName(),getNameProd());
    }

    public void inputXML()throws Exception{
        ODSCoder odsCoder=new MSODSCoder(this.getClass().getName(),templateWrapper.getDirectory_prod());
        odsCoder.getOdsFile().inXML();
        odsCoder.encode(this);
        onReadComplete();
        ToolLogger.GTPL.info("{} input a XML file ,name={}",this.getClass().getSimpleName(),getNameProd());
    }

    public void outputODS() throws Exception {
        ODSCoder odsCoder=new MSODSCoder(name_edit,templateWrapper.getDirectory_edit());
        odsCoder.decode(this);
        odsCoder.getOdsFile().outXLSX();
        ToolLogger.GTPL.info("{} output a ODS file ,name={}",this.getClass().getSimpleName(),name_edit);
    }

    public void inputODS() throws Exception {
        ODSCoder odsCoder=new MSODSCoder(name_edit,templateWrapper.getDirectory_edit());
        odsCoder.getOdsFile().inXLSX();
        odsCoder.encode(this);
        ToolLogger.GTPL.info("{} input a ODS file ,name={}",this.getClass().getSimpleName(),name_edit);
    }

}
