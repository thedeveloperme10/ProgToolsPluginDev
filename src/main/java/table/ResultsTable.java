package table;

import com.intellij.ide.BrowserUtil;
import com.intellij.ui.table.TableView;
import com.intellij.util.ui.ListTableModel;
import model.BugImpact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ResultsTable extends TableView<BugImpact>
{
    public ResultsTable(ListTableModel<BugImpact> model)
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
        this.getEmptyText().setText("No Bugs identified!");
    }
}
