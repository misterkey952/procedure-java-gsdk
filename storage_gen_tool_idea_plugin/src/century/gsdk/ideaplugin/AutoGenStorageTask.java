package century.gsdk.ideaplugin;

import century.gsdk.storage.core.AssistExeInfo;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
public class AutoGenStorageTask extends Task.Backgroundable{
    private AnActionEvent anActionEvent;
    private Project mProject;
    public AutoGenStorageTask(@Nullable Project project, AnActionEvent event, @Nls(capitalization = Nls.Capitalization.Title) @NotNull String title) {
        super(project, title);
        this.mProject=project;
        this.anActionEvent=event;
    }

    @Override
    public void run(@NotNull ProgressIndicator progressIndicator) {
        AssistExeInfo exeInfo=new AssistExeInfo();
        int errCount=0;
        int success=0;
        int hope=0;
        try{
            Object[] selectObjects=anActionEvent.getDataContext().getData(DataKeys.SELECTED_ITEMS);
            if(selectObjects==null){
                exeInfo.appendString("the select objects is null");
            }else{
                double selectCount=selectObjects.length;
                double progress=0;
                hope=selectObjects.length;
                XmlFileImpl xmlFile=null;
                VirtualFile file=null;
                String fileName=null;
                for(int i=0;i<selectObjects.length;i++){
                    if(selectObjects[i] instanceof XmlFileImpl){
                        xmlFile= (XmlFileImpl) selectObjects[i];
                    }else{
                        errCount++;
                        exeInfo.appendString("there is a wrong file that you selected "+selectObjects[i].toString());
                        continue;
                    }
                    file=xmlFile.getVirtualFile();
                    fileName=file.getName();
                    exeInfo.appendString("");


                }
            }
        }catch(Exception e){
            exeInfo.appendException(e);
        }

        ApplicationManager.getApplication().invokeLater(new ShowInfoRunnable(exeInfo.toString(),mProject,"Hope="+hope+",SUCCESS="+success+",Err="+errCount));

    }
}
