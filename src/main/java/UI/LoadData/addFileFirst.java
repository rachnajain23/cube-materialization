package UI.LoadData;

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

public class addFileFirst extends JFrame implements ActionListener {
    JFrame f;
    JPanel textPanel = new JPanel();
    JPanel container = new JPanel();
    JButton b;
    JButton c;
    JComboBox cb = new JComboBox();

    public addFileFirst() throws IOException {
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
        JLabel title = new JLabel("LOADING DATA");
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setSize(400,30);
        title.setLocation(350,20);

        JLabel t = new JLabel("Select the Schema Name");
        t.setFont(new Font("Arial", Font.PLAIN, 20));
        t.setSize(400, 30);
        t.setLocation(340, 150);

        textPanel.add(title);
        textPanel.add(t);
        SchemaCreation sc = new SchemaCreation();
        int length = (sc.getSchemaList()).length;
        String[] names = new String[length];
        names = sc.getSchemaList();

        cb = new JComboBox(names);
        cb.setBounds(340, 200, 200, 30);
        textPanel.add(cb);

        b = new JButton("Create New Database");
        b.setBounds(150, 400, 250, 20);
        textPanel.add(b);
        b.addActionListener(this);

        c = new JButton("Update Database");
        c.setBounds(500, 400, 250, 20);
        textPanel.add(c);
        c.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){

        if (e.getSource() == b){
            String data = "" + cb.getItemAt(cb.getSelectedIndex());
            System.out.println(data);
            ReadWriteXmlFile readWriteXmlFile= new ReadWriteXmlFile();
            StarSchema schema = readWriteXmlFile.readStarSchema(data);

            //todo send the schema name and get star schema object
            //pass to QueryPage the object
            int type = 1;
            this.setVisible(false);
            AddFile addFile = new AddFile(schema,type);
            addFile.setVisible(true);
        }

        if (e.getSource() == c){
            String data = "" + cb.getItemAt(cb.getSelectedIndex());
            System.out.println(data);

            ReadWriteXmlFile readWriteXmlFile= new ReadWriteXmlFile();
            StarSchema schema = readWriteXmlFile.readStarSchema(data);

            //todo send the schema name and get star schema object

            //pass to QueryPage the object
            int type = 2;
            this.setVisible(false);
            AddFile addFile = new AddFile(schema,type);
            addFile.setVisible(true);
        }

    }

    public static void main(String [] args) throws IOException {
        addFileFirst ff = new addFileFirst();
        ff.setVisible(true);
    }
}
