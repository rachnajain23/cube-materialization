package UI.OLAPQueries;

import javax.swing.*;
import java.awt.*;

public class ScrollableTable extends JPanel {
    public ScrollableTable() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(900, 200));

        JTable table = new JTable(30, 50);

        // Turn off JTable's auto resize so that JScrollPane will show a
        // horizontal scroll bar.
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane pane = new JScrollPane(table);
        add(pane);
    }
}