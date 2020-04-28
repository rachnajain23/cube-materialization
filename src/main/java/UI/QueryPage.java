package UI;

import javax.swing.*;
import java.awt.*;

public class QueryPage extends JFrame {
    JPanel tablePanel = new ScrollableTable();
    JPanel textPanel = new JPanel();
    JPanel container = new JPanel();
    JPanel buttonPanel = new JPanel();

    public void showFrame() {
        textPanel.setLayout(null);
        textPanel.setPreferredSize(new Dimension(500, 300));
        buttonPanel.setPreferredSize(new Dimension(500, 40));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(textPanel, BorderLayout.NORTH);
        container.add(tablePanel);
        container.add(buttonPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Scrollable JTable");
        setContentPane(container);
        pack();
        setVisible(true);
    }

    // add stuff to text panel
    public void addToTextPanel() {
        JLabel t = new JLabel("Do whatever here");
        t.setSize(400, 30);
        t.setLocation(100, 100);
        textPanel.add(t);
    }


    // add stuff to button panel
    public void addToButtonPanel() {
        JButton clear = new JButton("clear");
        JButton execute = new JButton("execute");
        clear.setSize(50, 20);
        execute.setSize(50, 20);
        buttonPanel.add(clear);
        buttonPanel.add(execute);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                QueryPage q = new QueryPage();
                q.addToTextPanel();
                q.addToButtonPanel();
                q.showFrame();
            }
        });
    }

}


