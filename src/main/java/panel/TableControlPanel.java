package panel;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.panels.NonOpaquePanel;
import com.intellij.ui.table.TableView;
import com.intellij.util.ui.ColumnInfo;
import model.AffectedItem;
import model.BugImpact;
import org.jetbrains.annotations.NotNull;
import service.BugImpactAnalysis;
import table.BugImpactTableModel;
import table.FunctionAffectedTableModel;

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

    private AffectedControlPanel affectedControlPanel;

    public TableControlPanel(TableView<BugImpact> resultsTable, BugImpactTableModel bugImpactTableModel)
    {
        this.resultsTable = resultsTable;
        this.bugImpactTableModel = bugImpactTableModel;
        this.init();
    }

    private void init()
    {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(BorderFactory.createEmptyBorder());

        ActionToolbar toolbar = this.createToolbar();
        toolbar.setTargetComponent(this);
        this.add(toolbar.getComponent());

        TableView<AffectedItem> affectedResultsTable = new TableView<>();
        List<AffectedItem> functionAffected = new ArrayList<>();
        FunctionAffectedTableModel functionAffectedTableModel = new FunctionAffectedTableModel(FunctionAffectedTableModel.generateColumnInfo(), functionAffected);
        this.affectedControlPanel = new AffectedControlPanel(affectedResultsTable, functionAffectedTableModel);

        getBugImpact();

//        BugImpact bug = new BugImpact();
//        bug.setBugMethodName("BUG1");
//        bug.setBugId(1);
//        bug.setFunctionImpactPercentage(21f);
//        List<AffectedItem> affectedItemList = new ArrayList<>();
//        AffectedItem affectedItem = new AffectedItem();
//        affectedItem.setAffected("AffectedMethod12");
//        affectedItemList.add(affectedItem);
//        bug.setFunctionAffected(affectedItemList);
//        addBugForAnalysis(bug);
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
        bugImpact.setBugImpactList(new ArrayList<>());
        this.bugImpactTableModel.setItems(new ArrayList<>());
    }

    public class SearchAction extends DumbAwareAction
    {
        protected SearchAction()
        {
            super("Reanalyze", "Reanalyze marked Bugs", AllIcons.Actions.Refresh);
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

        try
        {
            bugImpact.updateBugImpactAnalysis();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage());
        }
        if (bugImpact.getBugImpactList().isEmpty())
        {
            this.resultsTable.getEmptyText().setText("No bugs selected!");
        }

//        if (bug.getBugId() == null || bug.getBugId() < 1)
//        {
//            this.resultsTable.getEmptyText().setText("No impact found for the given bug!");
//        }

        this.bugImpactTableModel.setItems(bugImpact.getBugImpactList());
        this.resultsTable.updateColumnSizes();
    }

    public void addBugForAnalysis(BugImpact bug) {
        bugImpact.getBugImpactList().add(bug);
        getBugImpact();
        affectedControlPanel.getFunctionAffected(bug);
    }

}