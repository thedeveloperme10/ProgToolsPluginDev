import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

public class ContextMenuAction extends DumbAwareAction
{
    @Override
    public void actionPerformed(@NotNull AnActionEvent e)
    {
        Editor ediTorRequiredData = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = ediTorRequiredData.getCaretModel();
        String selectedText = caretModel.getCurrentCaret().getSelectedText();

        System.out.println("Marked Bug: ");
        System.out.println(selectedText);
    }

    @Override
    public void update(@NotNull AnActionEvent e)
    {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        String selectedText = caretModel.getCurrentCaret().getSelectedText();
        if (selectedText != null && selectedText.isEmpty()) {
            e.getPresentation().setVisible(false);
        }
    }
}
