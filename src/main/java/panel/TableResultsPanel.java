package panel;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.panels.NonOpaquePanel;
import com.intellij.ui.table.TableView;
import model.BugImpact;
import org.jetbrains.annotations.NotNull;
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

    }


}