package panel;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.panels.NonOpaquePanel;
import com.intellij.ui.table.TableView;
import model.BugImpact;
import service.BugImpactAnalysis;
import table.BugImpactTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TableResultsPanel extends NonOpaquePanel
{

    private static final Logger LOG = Logger.getInstance(TableResultsPanel.class);
    private final TableView<BugImpact> resultsTable;
    private final BugImpactTableModel bugImpactTableModel;

    public TableResultsPanel(TableView<BugImpact> resultsTable, BugImpactTableModel bugImpactTableModel)
    {
        this.resultsTable = resultsTable;
        this.bugImpactTableModel = bugImpactTableModel;
        this.init();
    }

    private void init()
    {
        this.setBorder(BorderFactory.createEmptyBorder());
        JPanel scrollPanel = new JPanel();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setLayout(new BorderLayout());
        scrollPanel.add(ScrollPaneFactory.createScrollPane(this.resultsTable), BorderLayout.CENTER);
        this.setLayout(new BorderLayout());
        this.add(scrollPanel, BorderLayout.CENTER);
        getBugImpact();
    }

    private void getBugImpact()
    {
        List<BugImpact> bugList = new ArrayList<>();
        BugImpact bug = new BugImpact();
        try
        {
            bug = BugImpactAnalysis.updateBugImpact(bug);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage());
        }
        if (bug.getBugId() == null || bug.getBugId() < 1)
        {
            this.resultsTable.getEmptyText().setText("No impact found for the given bug!");
        }
        bugList.add(bug);

        this.bugImpactTableModel.setItems(bugList);
        this.resultsTable.updateColumnSizes();

    }

}