package table;

import com.intellij.ui.table.TableView;
import com.intellij.util.ui.ListTableModel;
import model.AffectedItem;
import model.BugImpact;

import javax.swing.*;
import java.awt.*;


public class AffectedResultsTable extends TableView<AffectedItem>
{
    public AffectedResultsTable(ListTableModel<AffectedItem> model)
    {
        super(model);
        this.init();
    }

    private void init()
    {
        this.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setCellSelectionEnabled(true);
        this.setStriped(true);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setAutoCreateRowSorter(true);
        this.getEmptyText().setText("No Bugs selected!");
    }
}
