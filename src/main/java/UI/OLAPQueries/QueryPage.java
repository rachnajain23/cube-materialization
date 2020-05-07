package UI.OLAPQueries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import Pojo.Enums.OLAPOperation;
import Pojo.Schema.Attribute;
import Pojo.Schema.StarSchema;
import Processing.SchemaCreation;
import Services.OLAPQueriesService;

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

    public QueryPage(){

        //todo receive star schema object
        //globalSchema = s;
        addToTextPanel();
        addToButtonPanel();
        showFrame();
        //todo get the schema name from the star schema object and make object of OLAPQSERVICES using the name
        //todo gauri will change the function of service n all accordingly
       // olapQueriesService = new OLAPQueriesService("schemaname")


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
        t.setLocation(20, 10);
        textPanel.add(t);
        String[] queryNames = new String[]{"Roll Up", "Slice" , "Dice"};
        cb = new JComboBox(queryNames);
        cb.setBounds(20, 35, 100, 30);
        textPanel.add(cb);

        b = new JButton("Select");
        b.setBounds(140, 35, 100, 20);
        textPanel.add(b);
        b.addActionListener(this);


        l = new JLabel("");
        l.setBounds(20,70,800,25);
        textPanel.add(l);
//        l = new JLabel("Sample Query!");
//        l.setBounds(20,35,800,25);

        Map<Attribute, String> map = new HashMap<Attribute, String>();
        //Map<String, String> map = new HashMap<String, String>();
        Attribute a = new Attribute();
        a.setCode(1);
        a.setName("Name");
        Attribute b = new Attribute();
        b.setCode(2);
        b.setName("Type");
        map.put(a, "dim1");
        map.put(b,"dim1");
        Attribute c = new Attribute();
        c.setCode(3);
        c.setName("ID");
        Attribute d = new Attribute();
        d.setCode(4);
        d.setName("P_Name");
        map.put(c,"dim2");
        map.put(d,"dim2");
        System.out.println(map);

        Set<String> keys = new HashSet<String>();
        for (Map.Entry<Attribute, String> entry : map.entrySet()) {
            if (Objects.equals("dim1", entry.getValue())) {
                keys.add((entry.getKey()).getName());
            }
        }
        System.out.println("aaaaaaaaa");
        System.out.println(keys);

        Iterator<String> i = keys.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
            
        }
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

    public void actionPerformed(ActionEvent e){

        if(e.getSource() == b){
            //todo for every dimension (label) create checkboxes of its attributes
            if(cb.getItemAt(cb.getSelectedIndex()) == "Roll Up"){
                l.setText("hello roll u p");
                System.out.println("inside roll up option");
                // String sampleQuery = olapQueriesService.getSampleQueryService(OLAPOperation.ROLL_UP);
                //l.setText("Sample Query :" + sampleQuery);
            }
            if(cb.getItemAt(cb.getSelectedIndex()) == "Slice"){
                l.setText("helloo slice ");
             //   String sampleQuery = olapQueriesService.getSampleQueryService(OLAPOperation.SLICE);
                //l.setText("Sample Query :" + sampleQuery);
            }
            if(cb.getItemAt(cb.getSelectedIndex()) == "Dice"){
                l.setText("helloo dice ");
              //  String sampleQuery = olapQueriesService.getSampleQueryService(OLAPOperation.DICE);
                //l.setText("Sample Query :" + sampleQuery);
            }

        }
    }

}


