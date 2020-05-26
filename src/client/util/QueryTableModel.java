package client.util;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class QueryTableModel extends AbstractTableModel {

    private ArrayList<ArrayList<Object>> queryResults;

    public QueryTableModel(ArrayList<ArrayList<Object>> queryResults) { setQueryResults(queryResults); }

    @Override
    public int getRowCount() { return queryResults.get(0).size(); }

    @Override
    public int getColumnCount() { return queryResults.size(); }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) { return queryResults.get(columnIndex).get(rowIndex); }

    public void setQueryResults(ArrayList<ArrayList<Object>> queryResults) {

        queryResults = new ArrayList<>();

        for(int i = 0; i < queryResults.size(); i++) {
            ArrayList<Object> column = queryResults.get(i);
            ArrayList<Object> columnCopy = new ArrayList<>();
            for(int j = 0; j < column.size(); j++) {
                columnCopy.add(column.get(j));
            }
            queryResults.add(columnCopy);
        }

    }

}
