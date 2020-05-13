package UI.OLAPQueries;

import Pojo.Enums.OLAPOperation;
import Pojo.Schema.Attribute;
import Pojo.Schema.StarSchema;
import Processing.ReadWriteXmlFile;
import Services.OLAPQueriesService;
import UI.SchemaCreation.FirstPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueryPage extends JFrame implements ActionListener {
    JPanel tablePanel = new ScrollableTable();
    JPanel textPanel = new JPanel();
    JPanel container = new JPanel();
    JPanel buttonPanel = new JPanel();
    JComboBox cb = new JComboBox();
    JButton b = new JButton();
    StarSchema globalSchema = new StarSchema();
    OLAPQueriesService olapQueriesService;
    static JLabel l;
    JTextField condition;
    JCheckBox check;
    String schemaName;
    ArrayList<Pojo.Schema.Dimension> dim;
    List<JCheckBox> cbarr = new ArrayList<JCheckBox>();
    java.util.List<Attribute> attributeList;
    JButton clear;
    JButton execute;
    JButton exit;
    JButton home;
    HashMap<Attribute,String> queryMap;
    List<String[]> res;

    public QueryPage(String name){

        schemaName = name;
        ReadWriteXmlFile readWriteXmlFile= new ReadWriteXmlFile();
        globalSchema = readWriteXmlFile.readStarSchema(name);

        dim = new ArrayList<Pojo.Schema.Dimension>();
        dim = globalSchema.getDimension();

        addToTextPanel();
        addToButtonPanel();
        showFrame();

        //olapQueriesService = new OLAPQueriesService("schemaname")


    }

    public void showFrame() {
        textPanel.setLayout(null);
        textPanel.setPreferredSize(new Dimension(900, 350));
        buttonPanel.setPreferredSize(new Dimension(900, 50));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(textPanel, BorderLayout.NORTH);
        container.add(tablePanel);
        container.add(buttonPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("OLAP QUERIES");
        setContentPane(container);
        pack();
        setVisible(true);
    }

    // add stuff to text panel
    public void addToTextPanel() {
        JLabel t = new JLabel("Select the OLAP Operation");
        t.setSize(400, 20);
        t.setLocation(10, 10);
        textPanel.add(t);
        String[] queryNames = new String[]{"Roll Up", "Slice" , "Dice"};
        cb = new JComboBox(queryNames);
        cb.setBounds(10, 35, 100, 30);
        textPanel.add(cb);

        b = new JButton("Select");
        b.setBounds(140, 35, 100, 20);
        textPanel.add(b);
        b.addActionListener(this);


        l = new JLabel("Sample Query Here");
        l.setBounds(10,80,800,25);
        textPanel.add(l);

        int v = 130;
        int p = 130;
        for (Pojo.Schema.Dimension dimension : dim){
            JLabel l1 = new JLabel(dimension.getName());
            l1.setBounds(10,v,100,20);
            textPanel.add(l1);
            System.out.println(dimension.getName());
            v = v+40;
            java.util.List<Attribute> attributeList = dimension.getAttributes();
            int x = 200;
            for (Attribute attribute : attributeList) {
                System.out.println(attribute.getName());
                check = new JCheckBox(attribute.getName());

                check.setFont(new Font("Arial", Font.PLAIN, 15));
                check.setBounds(x, p, 120, 20);
                textPanel.add(check);
                x = x + 120;
                cbarr.add(check);
                check.addActionListener(this);
            }
            p = p+40;

        }
        JLabel z = new JLabel("Write the condition for SLICE or DICE Query below: ");
        z.setSize(400, 20);
        z.setLocation(10, 240);
        textPanel.add(z);

        System.out.println("ppppp");
        System.out.println(cbarr);
        condition = new JTextField();
        condition.setSize(600,30);
        condition.setLocation(10,280);
        textPanel.add(condition);



//        for (Map.Entry<Attribute, String> entry : map.entrySet()){
//            JLabel l1 = new JLabel(entry.getValue());
//            l1.setBounds(10,v,100,20);
//            textPanel.add(l1);
//            v = v + 40;
//        }
//        Set<String> keys = new HashSet<String>();
//        for (Map.Entry<Attribute, String> entry : map.entrySet()) {
//            if (Objects.equals("dim1", entry.getValue())) {
//                keys.add((entry.getKey()).getName());
//            }
//        }
//        System.out.println("aaaaaaaaa");
//        System.out.println(keys);
//
//        Iterator<String> i = keys.iterator();
//        int x = 200;
//        while (i.hasNext()) {
//
//            // System.out.println(i.next());
//            System.out.println("yes");
//            check = new JCheckBox(i.next());
//            check.setFont(new Font("Arial", Font.PLAIN, 15));
//            check.setBounds(x, 100, 100, 20);
//            textPanel.add(check);
//            x = x + 120;
//            check.addActionListener(this);
//        }
//
//        Set<String> keys1 = new HashSet<String>();
//        for (Map.Entry<Attribute, String> entry : map.entrySet()) {
//            if (Objects.equals("dim2", entry.getValue())) {
//                keys1.add((entry.getKey()).getName());
//            }
//        }
//        System.out.println("aaaaaaaaa");
//        System.out.println(keys1);
//
//        Iterator<String> i1 = keys1.iterator();
//        int x1 = 200;
//        while (i1.hasNext()) {
//
//            // System.out.println(i1.next());
//            System.out.println("yes");
//            check = new JCheckBox(i1.next());
//            check.setFont(new Font("Arial", Font.PLAIN, 15));
//            check.setBounds(x1, 140, 100, 20);
//            textPanel.add(check);
//            x1 = x1 + 120;
//            check.addActionListener(this);
//        }
    }

    // add stuff to button panel
    public void addToButtonPanel() {
        clear = new JButton("New Query");
        clear.setSize(70, 20);
        buttonPanel.add(clear);
        clear.addActionListener(this);

        execute = new JButton("Execute Query");
        execute.setSize(70, 20);
        buttonPanel.add(execute);
        execute.addActionListener(this);

        JLabel k = new JLabel("              ");
        buttonPanel.add(k);

        exit = new JButton("Exit");
        exit.setSize(50, 20);
        buttonPanel.add(exit);
        exit.addActionListener(this);

        home = new JButton("Go to Homepage");
        home.setSize(100, 20);
        buttonPanel.add(home);
        home.addActionListener(this);

    }

//    public HashMap<Attribute,String> getMap(ArrayList attr){
//
//        HashMap<Attribute,String> map = new HashMap<>();
//        for (int counter = 0; counter < attr.size(); counter++) {
//            System.out.println(attr.get(counter));
//
//        }
//        return  map;
//    }
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == b){
            //todo for every dimension (label) create checkboxes of its attributes
            if(cb.getItemAt(cb.getSelectedIndex()) == "Roll Up"){
                l.setText("hello roll up");
                System.out.println("inside roll up option");
                String sampleQuery = olapQueriesService.getSampleQueryService(OLAPOperation.ROLL_UP);
                l.setText("Sample Query :" + sampleQuery);
            }
            if(cb.getItemAt(cb.getSelectedIndex()) == "Slice"){
                l.setText("helloo slice ");
                String sampleQuery = olapQueriesService.getSampleQueryService(OLAPOperation.SLICE);
                l.setText("Sample Query :" + sampleQuery);
            }
            if(cb.getItemAt(cb.getSelectedIndex()) == "Dice"){
                l.setText("helloo dice ");
                String sampleQuery = olapQueriesService.getSampleQueryService(OLAPOperation.DICE);
                l.setText("Sample Query :" + sampleQuery);
            }

        }

        if(e.getSource() == check){
            String data = check.getText();
            System.out.println(data);
        }

        if(e.getSource() == execute){

            System.out.println("--------");
            ArrayList<String> attrname = new ArrayList<>();
            for (JCheckBox jCheckBox : cbarr)
            {
                if (jCheckBox.isSelected()) {
                    attrname.add(jCheckBox.getText());
                }

            }
            System.out.println(attrname);

            OLAPOperation o  = (OLAPOperation) cb.getItemAt(cb.getSelectedIndex());

            if (o == OLAPOperation.ROLL_UP){
                res = olapQueriesService.rollupService(queryMap);
            }

            if (o == OLAPOperation.DICE || o == OLAPOperation.SLICE){
                res = olapQueriesService.sliceOrDiceService(queryMap,condition.getText());
            }

            ScrollableTable s = new ScrollableTable();
            try {
                s.populateTable((ResultSet) res);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if(e.getSource() == clear){
            this.setVisible(false);
            QueryPage q = new QueryPage(schemaName);
            q.setVisible(true);
        }

        if(e.getSource() == home){

            this.setVisible(false);
            FirstPage f = new FirstPage();
            f.setVisible(true);

        }

        if(e.getSource() == exit){
            System.exit(0);
        }
    }

}


