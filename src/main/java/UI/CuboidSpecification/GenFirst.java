package UI.CuboidSpecification;

import Pojo.Schema.Attribute;
import Pojo.Schema.StarSchema;
import Processing.CuboidSpecManipulation;
import Processing.ReadWriteXmlFile;
import Processing.SchemaCreation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class GenFirst extends JFrame implements ActionListener {
    JFrame f;
    JPanel textPanel = new JPanel();
    JPanel container = new JPanel();
    JButton b;
    JComboBox cb = new JComboBox();

    public GenFirst() throws IOException {
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
        JLabel title = new JLabel("Generation of Lattice of Cuboid");
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setSize(400,30);
        title.setLocation(240,20);

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

            this.setVisible(false);
            GenSecond genSecond = new GenSecond(data);
            genSecond.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            genSecond.setBounds(300,90,900, 600);
            genSecond.setVisible(true);
        }

    }

    public static void main(String [] args) throws IOException {
         GenFirst genFirstf = new GenFirst();
        genFirstf.setVisible(true);
    }
}
