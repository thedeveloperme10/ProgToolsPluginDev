import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.Splitter;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.SideBorder;
import panel.AffectedControlPanel;
import panel.AffectedResultsPanel;
import panel.TableControlPanel;
import panel.TableResultsPanel;
import table.AffectedResultsTable;
import table.BugImpactTableModel;
import table.FunctionAffectedTableModel;
import table.ResultsTable;

import javax.swing.*;
import java.util.ArrayList;

public class AffectedToolWindow
{
    private final JPanel contentToolWindow;

    public JComponent getContent()
    {
        return this.contentToolWindow;
    }

    public AffectedToolWindow()
    {
        this.contentToolWindow = new SimpleToolWindowPanel(true, true);
        FunctionAffectedTableModel functionAffectedTableModel = new FunctionAffectedTableModel(FunctionAffectedTableModel.generateColumnInfo(), new ArrayList<>());
        AffectedResultsTable affectedResultsTable = new AffectedResultsTable(functionAffectedTableModel);

        AffectedResultsPanel affectedResultsPanel = new AffectedResultsPanel(affectedResultsTable, functionAffectedTableModel);
        affectedResultsPanel.setBorder(IdeBorderFactory.createBorder(SideBorder.TOP | SideBorder.RIGHT ));

        AffectedControlPanel affectedControlPanel = new AffectedControlPanel(affectedResultsTable, functionAffectedTableModel);
        affectedControlPanel.setBorder(IdeBorderFactory.createBorder(SideBorder.TOP | SideBorder.RIGHT | SideBorder.BOTTOM));

        OnePixelSplitter horizontalSplitter = new OnePixelSplitter(true, 0.0f);
        horizontalSplitter.setBorder(BorderFactory.createEmptyBorder());
        horizontalSplitter.setDividerPositionStrategy(Splitter.DividerPositionStrategy.KEEP_FIRST_SIZE);
        horizontalSplitter.setResizeEnabled(false);
        horizontalSplitter.setFirstComponent(affectedControlPanel);
        horizontalSplitter.setSecondComponent(affectedResultsPanel);

        this.contentToolWindow.add(horizontalSplitter);
    }
}
