package century.gsdk.tools.gtpl;

import century.gsdk.docker.GameDocker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger= LoggerFactory.getLogger(AbstractTemplate.class);
    private String name;
    private String category;
    private String directory;
    public AbstractTemplate(String name, String category) {
        this.name = name;
        this.category = category;
        this.directory= GameDocker.getInstance().getResPath()+ File.separator+category;
    }

    public void outputODS() throws Exception {
        ODSCoder odsCoder=new MSODSCoder(name,directory);
        odsCoder.decode(this);
    }

    public void inputODS() throws Exception {
        ODSCoder odsCoder=new MSODSCoder(name,directory);
        odsCoder.encode(this);
    }

}
