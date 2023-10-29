import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class BugImpactAnalyzerToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        BugImpactAnalyzerToolWindow myToolWindow = new BugImpactAnalyzerToolWindow();
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(myToolWindow.getContent(), "Tab1", false);
        toolWindow.getContentManager().addContent(content);

        AffectedToolWindow affectedToolWindow = new AffectedToolWindow();
        ContentFactory contentFactory1 = ContentFactory.getInstance();
        Content content1 = contentFactory1.createContent(affectedToolWindow.getContent(), "Tab2", false);
        toolWindow.getContentManager().addContent(content1);
    }

}
