package panel;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.panels.NonOpaquePanel;
import com.intellij.ui.table.TableView;
import model.AffectedItem;
import model.BugImpact;
import table.BugImpactTableModel;
import table.FunctionAffectedTableModel;

import javax.swing.*;
import java.awt.*;

public class AffectedResultsPanel extends NonOpaquePanel
{

    private static final Logger LOG = Logger.getInstance(AffectedResultsPanel.class);
    private final TableView<AffectedItem> resultsTable;
    private final FunctionAffectedTableModel functionAffectedTableModel;

    public AffectedResultsPanel(TableView<AffectedItem> resultsTable, FunctionAffectedTableModel functionAffectedTableModel)
    {
        this.resultsTable = resultsTable;
        this.functionAffectedTableModel = functionAffectedTableModel;
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