import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.ui.table.TableView;
import com.intellij.util.ui.ColumnInfo;
import model.BugImpact;
import org.jetbrains.annotations.NotNull;
import panel.TableControlPanel;
import table.BugImpactTableModel;
import table.ResultsTable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ContextMenuAction extends DumbAwareAction
{

    AtomicInteger atomicInteger = new AtomicInteger();

    private TableView<BugImpact> resultsTable = new TableView<>();
    private List<BugImpact> bugImpacts = new ArrayList<>();

    private BugImpactTableModel bugImpactTableModel = new BugImpactTableModel(BugImpactTableModel.generateColumnInfo(), bugImpacts);

    TableControlPanel tableControlPanel = new TableControlPanel(resultsTable, bugImpactTableModel);


    @Override
    public void actionPerformed(@NotNull AnActionEvent e)
    {
        Editor ediTorRequiredData = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = ediTorRequiredData.getCaretModel();
        String selectedText = caretModel.getCurrentCaret().getSelectedText();

        System.out.println("Marked Bug: ");
        System.out.println(selectedText);

        BugImpact bug = new BugImpact();
        Integer id = atomicInteger.incrementAndGet();
        bug.setBugId(id);
        bug.setBugMethodName(selectedText);

        // Add Bug
        tableControlPanel.addBugForAnalysis(bug);

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
