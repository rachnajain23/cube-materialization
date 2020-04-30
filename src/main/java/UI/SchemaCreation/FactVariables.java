package UI.SchemaCreation;

import Pojo.Enums.AggregateFunc;
import Pojo.Schema.StarSchema;
import Services.SchemaCreationService;
import UI.AddFile;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FactVariables extends JFrame implements ActionListener {

    String name;
    Pojo.Enums.Type type;
    JPanel panel;
    StarSchema globalSchema = new StarSchema();
    HashSet<AggregateFunc> fns = new HashSet<AggregateFunc>();
    JRadioButton numeric;
    JRadioButton str;
    JTextField factname;
    ButtonGroup gengp;
    JCheckBox sum;
    JCheckBox count;
    JCheckBox avg;
    JCheckBox mean;
    JCheckBox median;
    JCheckBox mode;
    JButton adding;
    JButton proceed;
    SchemaCreationService sc = new SchemaCreationService();
    ArrayList<AggregateFunc> fnList = new ArrayList<AggregateFunc>();

    public FactVariables(StarSchema s) {

        super("Fact Variable and Aggregate functions Details");
        setLayout(new BorderLayout());
        globalSchema = s;
        System.out.println(s.getFact());
        this.panel = new JPanel();
        this.panel.setLayout(new FlowLayout());
        add(panel, BorderLayout.CENTER);

        JLabel title = new JLabel("Fact Variable Name             Type                       Aggregate Function");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        add(title, BorderLayout.NORTH);


        JPanel subPanel = new JPanel();
        adding = new JButton("Click here to Add more Fact Variables");
        proceed = new JButton("Proceed to Create Schema");
        subPanel.add(adding);
        subPanel.add(proceed);
        adding.addActionListener(this);
        proceed.addActionListener(this);
        //adding.addActionListener(this);

        add(subPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 90, 900, 600);
        setVisible(true);

        factname = new JTextField(17);
        factname.setFont(new Font("Arial", Font.PLAIN, 15));
        this.panel.add(factname);

        numeric = new JRadioButton("Numeric");
        numeric.setFont(new Font("Arial", Font.PLAIN, 17));
        numeric.setSelected(false);
        this.panel.add(numeric);
        numeric.addActionListener(this);

        str = new JRadioButton("String");
        str.setFont(new Font("Arial", Font.PLAIN, 17));
        str.setSelected(false);
        this.panel.add(str);
        str.addActionListener(this);

        gengp = new ButtonGroup();
        gengp.add(numeric);
        gengp.add(str);

        sum = new JCheckBox("Sum");
        sum.setFont(new Font("Arial", Font.PLAIN, 15));
        this.panel.add(sum);
        sum.addActionListener(this);

        count = new JCheckBox("Count");
        count.setFont(new Font("Arial", Font.PLAIN, 15));
        this.panel.add(count);
        count.addActionListener(this);

        avg = new JCheckBox("Average");
        avg.setFont(new Font("Arial", Font.PLAIN, 15));
        this.panel.add(avg);
        avg.addActionListener(this);

        mean = new JCheckBox("Mean");
        mean.setFont(new Font("Arial", Font.PLAIN, 15));
        this.panel.add(mean);
        mean.addActionListener(this);

        median = new JCheckBox("Median");
        median.setFont(new Font("Arial", Font.PLAIN, 15));
        this.panel.add(median);
        median.addActionListener(this);

        mode = new JCheckBox("Mode");
        mode.setFont(new Font("Arial", Font.PLAIN, 15));
        this.panel.add(mode);
        mode.addActionListener(this);

        fnList = new ArrayList<AggregateFunc>(fns);
    }

    public void actionPerformed(ActionEvent evt) {

        //String com = evt.getActionCommand(); 
        // if the user presses the open dialog show the open dialog 
        if (evt.getSource() == adding) {

            fnList = new ArrayList<AggregateFunc>(fns);
            factname = new JTextField(17);
            factname.setFont(new Font("Arial", Font.PLAIN, 15));
            this.panel.add(factname);

            numeric = new JRadioButton("Numeric");
            numeric.setFont(new Font("Arial", Font.PLAIN, 17));
            numeric.setSelected(false);
            this.panel.add(numeric);
            numeric.addActionListener(this);

            str = new JRadioButton("String");
            str.setFont(new Font("Arial", Font.PLAIN, 17));
            str.setSelected(false);
            this.panel.add(str);
            str.addActionListener(this);

            gengp = new ButtonGroup();
            gengp.add(numeric);
            gengp.add(str);

            sum = new JCheckBox("Sum");
            sum.setFont(new Font("Arial", Font.PLAIN, 15));
            this.panel.add(sum);
//        sum.setSize(100, 20); 
//        sum.setLocation(100,460);
            sum.addActionListener(this);
//        c.add(sum);

            count = new JCheckBox("Count");
            count.setFont(new Font("Arial", Font.PLAIN, 15));
            this.panel.add(count);
//        count.setSize(100, 20); 
//        count.setLocation(100,490);
            count.addActionListener(this);
//        c.add(count);

            avg = new JCheckBox("Average");
            avg.setFont(new Font("Arial", Font.PLAIN, 15));
            this.panel.add(avg);
//        avg.setSize(100, 20); 
//        avg.setLocation(100,520);
            avg.addActionListener(this);
//        c.add(avg);

            mean = new JCheckBox("Mean");
            mean.setFont(new Font("Arial", Font.PLAIN, 15));
            this.panel.add(mean);
//        mean.setSize(100, 20); 
//        mean.setLocation(100,550);
            mean.addActionListener(this);
//        c.add(mean);

            median = new JCheckBox("Median");
            median.setFont(new Font("Arial", Font.PLAIN, 15));
            this.panel.add(median);
//        median.setSize(100, 20); 
//        median.setLocation(100,580);
            median.addActionListener(this);
//        c.add(median);

            mode = new JCheckBox("Mode");
            mode.setFont(new Font("Arial", Font.PLAIN, 15));
            this.panel.add(mode);
//        mode.setSize(100, 20); 
//        mode.setLocation(100,610); 
            mode.addActionListener(this);
//        c.add(mode);


            sc.insertFactService(globalSchema, name, type, fnList);

            this.panel.revalidate();
            validate();
        }

        if (evt.getSource() == numeric || evt.getSource() == str) {
            name = factname.getText();
            if (numeric.isSelected())
                type = Pojo.Enums.Type.NUMERIC;
            else
                type = Pojo.Enums.Type.STRING;
        }

        if (evt.getSource() == sum || evt.getSource() == count || evt.getSource() == avg
                || evt.getSource() == mean || evt.getSource() == median || evt.getSource() == mode) {

            if (sum.isSelected()) {
                fns.add(AggregateFunc.SUM);
            }
            if (count.isSelected()) {
                fns.add(AggregateFunc.COUNT);
            }
            if (avg.isSelected()) {
                fns.add(AggregateFunc.AVG);
                System.out.println("avg is selectedd");
            }
            if (mean.isSelected()) {
                fns.add(AggregateFunc.MEAN);
            }
            if (median.isSelected()) {
                fns.add(AggregateFunc.MEDIAN);
            }
            if (mode.isSelected()) {
                fns.add(AggregateFunc.MODE);
            }

        }
        if (evt.getSource() == proceed) {

            sc.insertFactService(globalSchema, name, type, fnList);
            String ans = "";
            try {
                ans = sc.writeSchemaService(globalSchema);
            } catch (JAXBException ex) {
//                ans= false;
                Logger.getLogger(FactVariables.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
//                ans= false;
                Logger.getLogger(FactVariables.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(ans);
//        else
//            System.out.println("Unknown error occured while schema creation");


            this.setVisible(false);
            AddFile file = new AddFile(globalSchema);
            file.setVisible(true);
        }
    }

}

//    public static void main(String[] args) {
//          FactVariables acojfar = new FactVariables();
//    }

