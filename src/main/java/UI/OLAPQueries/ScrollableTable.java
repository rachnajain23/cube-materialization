package UI.OLAPQueries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class ScrollableTable extends JPanel {

    JTable table;
    JScrollPane pane;
    Vector<String> columnNames;


    public ScrollableTable() {
        initializeUI();
    }

    public void initializeUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1100, 300));

        table = new JTable(30, 50);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        pane = new JScrollPane(table);
        add(pane);
    }

    public void populateTable(ResultSet rs) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) table.getModel();


        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }


        model.setDataVector(data, columnNames);
        for (int column = 0; column < table.getColumnCount(); column++){
            table.getColumnModel().getColumn(column).setPreferredWidth(150);
        }

    }

}