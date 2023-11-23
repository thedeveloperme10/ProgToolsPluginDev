package panel;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.ui.components.panels.NonOpaquePanel;
import com.intellij.ui.table.TableView;
import model.AffectedItem;
import model.BugImpact;
import org.jetbrains.annotations.NotNull;
import service.AffectedFunctionService;
import service.BugImpactAnalysis;
import table.BugImpactTableModel;
import table.FunctionAffectedTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AffectedControlPanel extends NonOpaquePanel
{

    private static final Logger LOG = Logger.getInstance(AffectedControlPanel.class);
    private TableView<AffectedItem> resultsTable;
    private FunctionAffectedTableModel functionAffectedTableModel;
//    private static List<AffectedItem> affectedItemList = new ArrayList<>();

    private static final AffectedFunctionService affectedFunctionService = new AffectedFunctionService();

    public AffectedControlPanel(TableView<AffectedItem> resultsTable, FunctionAffectedTableModel functionAffectedTableModel)
    {
        this.resultsTable = resultsTable;
        this.functionAffectedTableModel = functionAffectedTableModel;
        this.init();
    }

    private void init()
    {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(BorderFactory.createEmptyBorder());

        ActionToolbar toolbar = this.createToolbar();
        toolbar.setTargetComponent(this);
        this.add(toolbar.getComponent());

//        BugImpact bugImpact = new BugImpact();
//
//        getFunctionAffected(bugImpact);
    }

    @NotNull
    private ActionToolbar createToolbar()
    {
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(new ClearTableAction());
        return ActionManager.getInstance().createActionToolbar(ActionPlaces.TOOLBAR, actionGroup, true);
    }

    private void clearResults()
    {
        affectedFunctionService.setAffectedItemList(new ArrayList<>());
        this.functionAffectedTableModel.setItems(new ArrayList<>());
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
            AffectedControlPanel.this.clearResults();
        }
    }

    public void getFunctionAffected(BugImpact bugImpact)
    {
        affectedFunctionService.setAffectedItemList(bugImpact.getFunctionAffected());
//        AffectedItem affectedItem = new AffectedItem();
//        affectedItem.setAffected("AffectedFunction1");
//        affectedItemList.add(affectedItem);

//        bugImpact.setFunctionAffected(affectedItemList);
//
//        System.out.println(affectedItemList);
//        System.out.println("HARDCODED AFFECTED_FUNCTION!! in AffectedControlPanel.java");

        if (affectedFunctionService.getAffectedItemList().isEmpty())
        {
            this.resultsTable.getEmptyText().setText("No Functions Affected by the selected Bug");
        }

        this.functionAffectedTableModel.setItems(affectedFunctionService.getAffectedItemList());
        this.resultsTable.updateColumnSizes();
    }

}