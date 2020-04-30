package UI.SchemaCreation;

import Pojo.Schema.StarSchema;
import Services.SchemaCreationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class NewCube extends JFrame implements ActionListener {

    public int number_Dimension;
    // Components of the Form
    StarSchema globalSchema;
    String nameSchema;
    private Container c;
    private JLabel title;
    private JLabel s_name;
    private JTextField tname;
    private JLabel dno;
    private JTextField tdno;
    //    private JLabel fno;
//    private JTextField tfno;
    private JButton sub;
    private JButton reset;
    private JButton next;
    private JTextArea tout;
    private JLabel res;
    //public int number_FactsVar;


    public NewCube() {
//        globalSchema = new StarSchema();

        setTitle("Data-Cube Management");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("New Cube Creation");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        s_name = new JLabel("Schema Name");
        s_name.setFont(new Font("Arial", Font.PLAIN, 15));
        s_name.setSize(200, 20);
        s_name.setLocation(100, 100);
        c.add(s_name);

        tname = new JTextField();
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(190, 20);
        tname.setLocation(320, 100);
        c.add(tname);

        dno = new JLabel("Number of Dimension Table");
        dno.setFont(new Font("Arial", Font.PLAIN, 15));
        dno.setSize(290, 20);
        dno.setLocation(100, 150);
        c.add(dno);

        tdno = new JTextField();
        tdno.setFont(new Font("Arial", Font.PLAIN, 15));
        tdno.setSize(190, 20);
        tdno.setLocation(320, 150);
        c.add(tdno);

//        fno = new JLabel("Number of Fact Variables"); 
//        fno.setFont(new Font("Arial", Font.PLAIN, 15)); 
//        fno.setSize(290, 20); 
//        fno.setLocation(100, 200); 
//        c.add(fno);
//
//        tfno = new JTextField(); 
//        tfno.setFont(new Font("Arial", Font.PLAIN, 15)); 
//        tfno.setSize(190, 20); 
//        tfno.setLocation(320, 200); 
//        c.add(tfno);

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 450);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(270, 450);
        reset.addActionListener(this);
        c.add(reset);

        next = new JButton("Next");
        next.setFont(new Font("Arial", Font.PLAIN, 15));
        next.setSize(100, 20);
        next.setLocation(700, 520);
        next.addActionListener(this);
        c.add(next);

        tout = new JTextArea();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setSize(300, 400);
        tout.setLocation(550, 100);
        tout.setLineWrap(true);
        tout.setEditable(false);
        c.add(tout);

        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 500);
        c.add(res);

        //setVisible(true);
        //pack();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sub) {
            String data
                    = "Schema Name : "
                    + tname.getText() + "\n"
                    + "Number of Dimension Table : "
                    + tdno.getText() + "\n";
            tout.setText(data);
            tout.setEditable(false);
            res.setText("Details Entered! Click on next.");

            System.out.println("inside new cube submit");
            nameSchema = new String();
            nameSchema = tname.getText();
            int dnum = Integer.parseInt(tdno.getText());

            SchemaCreationService service = new SchemaCreationService();
            boolean doesSameFileExist = true;
            try {
                doesSameFileExist = service.doesSchemaExistService(nameSchema);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Unknown occurred in checking for same file name");
            }

            if (!doesSameFileExist) {
                globalSchema = service.newSchemaService(nameSchema);
                number_Dimension = Integer.parseInt(tdno.getText());
            } else {
                JOptionPane.showMessageDialog(this, "Schema already exists with the same file name");
            }
        }

        if (e.getSource() == next) {

            //DimensionTables d = new DimensionTables(globalSchema,number_Dimension,nameSchema,dnum);
            DimensionTables d = new DimensionTables(globalSchema, number_Dimension);
            System.out.println("in new cube" + number_Dimension);

            d.setVisible(true);
            //d.pack();
            this.dispose();


        } else if (e.getSource() == reset) {
            String def = "";
            tname.setText(def);
            tdno.setText(def);
            //tfno.setText(def); 
            res.setText(def);
            tout.setText(def);

        }
    }
}
