package UI.OLAPQueries;

import Pojo.Schema.Attribute;
import Processing.CuboidSpecManipulation;
import Processing.SchemaCreation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

public class QueryFirst extends JFrame implements ActionListener {
    JFrame f;
    JPanel textPanel = new JPanel();
    JPanel container = new JPanel();
    JButton b;
    JComboBox cb = new JComboBox();

    public QueryFirst() throws IOException {
        f = new JFrame("Data Cube Management");
        addToTextPanel();

        showFrame();
    }
    public void showFrame() {
        textPanel.setLayout(null);
        textPanel.setPreferredSize(new Dimension(900, 500));
        container.add(textPanel, BorderLayout.NORTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Cube Management");
        setContentPane(container);
        pack();
        setVisible(true);
    }

    public void addToTextPanel() throws IOException {
        JLabel title = new JLabel("OLAP QUERIES");
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setSize(400,30);
        title.setLocation(350,20);

        JLabel t = new JLabel("Select the Schema Name");
        t.setFont(new Font("Arial", Font.PLAIN, 20));
        t.setSize(400, 30);
        t.setLocation(300, 150);

        textPanel.add(title);
        textPanel.add(t);
        SchemaCreation sc = new SchemaCreation();
        int length = (sc.getSchemaList()).length;
        String[] names = new String[length];
        names = sc.getSchemaList();

        cb = new JComboBox(names);
        cb.setBounds(300, 200, 200, 30);
        textPanel.add(cb);

        b = new JButton("Proceed");
        b.setBounds(700, 400, 100, 20);
        textPanel.add(b);
        b.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){

        if (e.getSource() == b){
            String data = "" + cb.getItemAt(cb.getSelectedIndex());
            System.out.println(data);

            CuboidSpecManipulation cuboidSpecManipulation = new CuboidSpecManipulation(data);
//            HashMap<Attribute, String> attributes = new HashMap<Attribute, String>();
//            attributes = cuboidSpecManipulation.getAttributes();
//            System.out.println(attributes);

            //todo send the schema name and get star schema object
            //pass to QueryPage the object

            this.setVisible(false);
            QueryPage queryPage = new QueryPage();
            queryPage.setVisible(true);
        }

    }

    public static void main(String [] args) throws IOException {
        QueryFirst qf = new QueryFirst();
        qf.setVisible(true);
    }
}
