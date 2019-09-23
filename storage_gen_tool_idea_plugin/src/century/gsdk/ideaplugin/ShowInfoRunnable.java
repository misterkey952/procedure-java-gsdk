package century.gsdk.ideaplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class ShowInfoRunnable implements Runnable{

    private String info;
    private Project mProject;
    private String title;
    public ShowInfoRunnable(String info, Project mProject, String title) {
        this.title=title;
        this.info = info;
        this.mProject=mProject;
    }

    @Override
    public void run() {
        Messages.showMessageDialog(mProject,info, title, Messages.getInformationIcon());
    }
}
