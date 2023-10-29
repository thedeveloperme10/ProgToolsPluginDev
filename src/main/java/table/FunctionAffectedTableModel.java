package table;

import com.intellij.ui.components.labels.BoldLabel;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.ListTableModel;
import model.AffectedItem;
import model.BugImpact;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.table.TableCellRenderer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FunctionAffectedTableModel extends ListTableModel<AffectedItem>
{
    public FunctionAffectedTableModel(ColumnInfo @NotNull [] columnNames, @NotNull List<AffectedItem> functionAffected)
    {
        super(columnNames, functionAffected);
    }

    static final String[] COLUMNS = {"AffectedFunctionName"};
    public static ColumnInfo<AffectedItem, String>[] generateColumnInfo()
    {
        ColumnInfo<AffectedItem, String>[] columnInfos = new ColumnInfo[COLUMNS.length];
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(COLUMNS).forEach(eachColumn -> {
                    columnInfos[i.get()] = new ColumnInfo<>(eachColumn)
                    {
                        @Nullable
                        @Override
                        public String valueOf(AffectedItem functionAffected)
                        {
                            return switch (eachColumn) {
                                case "AffectedFunctionName" -> functionAffected.getAffected();
                                default -> "Not Available";
                            };
                        }

                    };
                    i.getAndIncrement();
                }
        );
        return columnInfos;
    }
}
