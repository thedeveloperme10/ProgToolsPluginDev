package table;

import com.intellij.icons.AllIcons;
import com.intellij.ui.components.labels.BoldLabel;
import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.ListTableModel;
import model.BugImpact;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import model.BugImpact;

import javax.swing.table.TableCellRenderer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BugImpactTableModel extends ListTableModel<BugImpact>
{
    public BugImpactTableModel(ColumnInfo @NotNull [] columnNames, @NotNull List<BugImpact> bugImpacts)
    {
        super(columnNames, bugImpacts);
    }

    static final String[] COLUMNS = {"MethodName", "FunctionImpactPercentage"};
    public static ColumnInfo<BugImpact, String>[] generateColumnInfo()
    {
        ColumnInfo<BugImpact, String>[] columnInfos = new ColumnInfo[COLUMNS.length];
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(COLUMNS).forEach(eachColumn -> {
                    columnInfos[i.get()] = new ColumnInfo<>(eachColumn)
                    {
                        @Nullable
                        @Override
                        public String valueOf(BugImpact o)
                        {
                            return switch (eachColumn) {
                                case "MethodName" -> o.getBugMethodName();
                                case "FunctionImpactPercentage" -> o.getFunctionImpactPercentage().toString();
                                default -> "Not Available";
                            };
                        }

                        @Override
                        public TableCellRenderer getCustomizedRenderer(BugImpact o, TableCellRenderer renderer)
                        {
                            switch (eachColumn)
                            {
                                case "MethodName":
                                    return (table, value, isSelected, hasFocus, row, column) -> new BoldLabel(value.toString());
                                default:
                                    return super.getCustomizedRenderer(o, renderer);
                            }
                        }

                    };
                    i.getAndIncrement();
                }
        );
        return columnInfos;
    }
}
