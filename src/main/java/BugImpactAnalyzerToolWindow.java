import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.Splitter;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.ui.SideBorder;
import panel.TableResultsPanel;
import table.BugImpactTableModel;
import table.ResultsTable;

import javax.swing.*;
import java.util.ArrayList;

public class BugImpactAnalyzerToolWindow
{
    private final JPanel contentToolWindow;

    public JComponent getContent()
    {
        return this.contentToolWindow;
    }

    public BugImpactAnalyzerToolWindow()
    {
        this.contentToolWindow = new SimpleToolWindowPanel(true, true);
        BugImpactTableModel bugImpactTableModel = new BugImpactTableModel(BugImpactTableModel.generateColumnInfo(), new ArrayList<>());
        ResultsTable resultsTable = new ResultsTable(bugImpactTableModel);

        TableResultsPanel tableResultsPanel = new TableResultsPanel(resultsTable,bugImpactTableModel);
        tableResultsPanel.setBorder(IdeBorderFactory.createBorder(SideBorder.TOP | SideBorder.RIGHT));
        OnePixelSplitter horizontalSplitter = new OnePixelSplitter(true, 0.0f);
        horizontalSplitter.setBorder(BorderFactory.createEmptyBorder());
        horizontalSplitter.setDividerPositionStrategy(Splitter.DividerPositionStrategy.KEEP_FIRST_SIZE);
        horizontalSplitter.setResizeEnabled(false);
        horizontalSplitter.setFirstComponent(tableResultsPanel);

        this.contentToolWindow.add(horizontalSplitter);

    }
}
