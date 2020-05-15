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
    static JTextField condition;
    JCheckBox check;
    String schemaName;
    ArrayList<Pojo.Schema.Dimension> dim;
    List<customObject> customObjectList = new ArrayList<customObject>();
    java.util.List<Attribute> attributeList;
    JButton clear;
    JButton execute;
    JButton exit;
    JButton home;
    ResultSet res;
    static JLabel z;

    public QueryPage(String name){

        schemaName = name;
        ReadWriteXmlFile readWriteXmlFile= new ReadWriteXmlFile();
        globalSchema = readWriteXmlFile.readStarSchema(name);

        dim = new ArrayList<Pojo.Schema.Dimension>();
        dim = globalSchema.getDimension();

        addToTextPanel();
        addToButtonPanel();
        showFrame();

        olapQueriesService = new OLAPQueriesService(schemaName);


    }

    public void showFrame() {
        textPanel.setLayout(null);
        textPanel.setPreferredSize(new Dimension(1100, 350));
        buttonPanel.setPreferredSize(new Dimension(1100, 50));
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
        l.setBounds(10,80,1100,25);
        textPanel.add(l);

        z = new JLabel(" ");
        z.setBounds(10,240,400,20);
        textPanel.add(z);

        condition = new JTextField();
        condition.setBounds(10,280,0,0);
        textPanel.add(condition);

        int v = 130;
        int p = 130;
        customObject o;
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
                o = new customObject(attribute,dimension.getName(),check);
                customObjectList.add(o);
            }
            p = p+40;

        }
        JLabel z = new JLabel("Write the condition for SLICE or DICE Query below: ");
        z.setSize(400, 20);
        z.setLocation(10, 240);
        condition = new JTextField();
        condition.setSize(600,30);
        condition.setLocation(10,280);
        if (cb.getItemAt(cb.getSelectedIndex()) == "Roll Up")
        {
              System.out.println("rollup selected");
        }
        else {
            textPanel.add(z);
            textPanel.add(condition);
        }



        execute = new JButton("Execute Query");
        execute.setSize(150, 30);
        execute.setLocation(830,280);
        textPanel.add(execute);
        execute.addActionListener(this);
    }

    // add stuff to button panel
    public void addToButtonPanel() {
        clear = new JButton("New Query");
        clear.setSize(70, 30);
        buttonPanel.add(clear);
        clear.addActionListener(this);

        JLabel k = new JLabel("              ");
       // buttonPanel.add(k);

        exit = new JButton("Exit");
        exit.setSize(50, 30);
        buttonPanel.add(exit);
        exit.addActionListener(this);

        home = new JButton("Go to Homepage");
        home.setSize(100, 30);
        buttonPanel.add(home);
        home.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource() == b){
            //todo for every dimension (label) create checkboxes of its attributes
            if(cb.getItemAt(cb.getSelectedIndex()) == "Roll Up"){
                System.out.println("inside roll up option");
                String sampleQuery = olapQueriesService.getSampleQueryService(OLAPOperation.ROLL_UP);
                l.setText("Sample Query :" + sampleQuery);
                z.setText("   ");
                condition.setBounds(10,280,0,0);
                textPanel.add(z);
                textPanel.add(condition);
                textPanel.revalidate();
                textPanel.repaint();
            }
            if(cb.getItemAt(cb.getSelectedIndex()) == "Slice"){
                String sampleQuery = olapQueriesService.getSampleQueryService(OLAPOperation.SLICE);
                l.setText("Sample Query :" + sampleQuery);
                z.setText("Write the condition for SLICE or DICE Query below: ");
                condition.setSize(600,30);
                condition.setLocation(10,280);
                textPanel.add(z);
                textPanel.add(condition);
                textPanel.revalidate();
                textPanel.repaint();
            }
            if(cb.getItemAt(cb.getSelectedIndex()) == "Dice"){
                String sampleQuery = olapQueriesService.getSampleQueryService(OLAPOperation.DICE);
                l.setText("Sample Query :" + sampleQuery);
                z.setText("Write the condition for SLICE or DICE Query below: ");
                condition.setSize(600,30);
                condition.setLocation(10,280);
                textPanel.add(z);
                textPanel.add(condition);
                textPanel.revalidate();
                textPanel.repaint();
            }

        }

        if(e.getSource() == check){
            String data = check.getText();
            System.out.println(data);
        }

        if(e.getSource() == execute){

            HashMap<Attribute,String> hmap= new HashMap<>();
            for (customObject object : customObjectList){
                if(object.getCheckbox().isSelected()){
                    hmap.put(object.getAttribute(),object.getName());
                }
            }
            System.out.println("pppppp");
            System.out.println(hmap);

            String oo  = String.valueOf(cb.getItemAt(cb.getSelectedIndex()));

            if (oo == "Roll ip" ){
                res = olapQueriesService.rollupService(hmap);
            }

            if (oo == "Slice" || oo == "Dice"){
               // res = olapQueriesService.sliceOrDiceService(hmap,condition.getText());
            }

            ScrollableTable s = new ScrollableTable();
            try {
                System.out.println(";;;;;;;;");
                System.out.println(res);
                s.populateTable(res);
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
class customObject{
    private Attribute attribute;
    private String name;
    private JCheckBox checkbox;

    customObject(Attribute attribute, String name, JCheckBox checkbox){
        this.attribute = attribute;
        this.name = name;
        this.checkbox = checkbox;
    }
    public Attribute getAttribute() {
        return attribute;
    }

    public String getName(){
        return name;
    }

    public JCheckBox getCheckbox() {return  checkbox;
    }
}


