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

public class TableControlPanel extends NonOpaquePanel
{

    private static final Logger LOG = Logger.getInstance(TableControlPanel.class);
    private TableView<BugImpact> resultsTable;
    private BugImpactTableModel bugImpactTableModel;
    private static final BugImpactAnalysis bugImpact = new BugImpactAnalysis();

    public TableControlPanel(TableView<BugImpact> resultsTable, BugImpactTableModel bugImpactTableModel)
    {
        this.resultsTable = resultsTable;
        this.bugImpactTableModel = bugImpactTableModel;
//        this.bugImpact = new BugImpactAnalysis();
        this.init();
    }


    public TableControlPanel() {

    }

    private void init()
    {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(BorderFactory.createEmptyBorder());

        ActionToolbar toolbar = this.createToolbar();
        toolbar.setTargetComponent(this);
        this.add(toolbar.getComponent());

        getBugImpact();
    }

    @NotNull
    private ActionToolbar createToolbar()
    {
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(new SearchAction());
        actionGroup.add(new ClearTableAction());
        return ActionManager.getInstance().createActionToolbar(ActionPlaces.TOOLBAR, actionGroup, true);
    }

    private void clearResults()
    {

        this.bugImpactTableModel.setItems(new ArrayList<>());
    }

    public class SearchAction extends DumbAwareAction
    {
        protected SearchAction()
        {
            super("Get StackOverflow Issues", "Get StackOverflow issues", AllIcons.Actions.Refresh);
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e)
        {
            TableControlPanel.this.getBugImpact();
        }
    }

    public class ClearTableAction extends DumbAwareAction
    {
        protected ClearTableAction()
        {
            super("Clear Inputs and Results", "Clear inputs and results", AllIcons.Actions.Cancel);
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e)
        {
            TableControlPanel.this.clearResults();
        }
    }

    private void getBugImpact()
    {

        List<BugImpact> bugList = bugImpact.getBugImpactList();
        BugImpact bug1 = new BugImpact();
        BugImpact bug2 = new BugImpact();
        bugList.add(bug1);
        bugList.add(bug2);
        try
        {
            bugImpact.updateBugImpactAnalysis();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage());
        }
        if (bugList.isEmpty())
        {
            this.resultsTable.getEmptyText().setText("No bugs selected!");
        }

//        if (bug.getBugId() == null || bug.getBugId() < 1)
//        {
//            this.resultsTable.getEmptyText().setText("No impact found for the given bug!");
//        }

        this.bugImpactTableModel.setItems(bugList);
        this.resultsTable.updateColumnSizes();
    }

    public static void addBugForAnalysis(BugImpact bug) {
        bugImpact.addBugForAnalysis(bug);

    }

}