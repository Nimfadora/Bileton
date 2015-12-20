package dto;

import java.util.List;

public class QueryResDto {
    List rows;
    List columnNames;

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public List getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List columnNames) {
        this.columnNames = columnNames;
    }
}
