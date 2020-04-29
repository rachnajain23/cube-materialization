package UI;

import Pojo.Attribute;
import Pojo.Dimension;
import Pojo.StarSchema;
import PreProcessing.SchemaCreation;
import java.awt.*;
import java.awt.event.*;

//import java.swing.JTextFields;
import javax.swing.*;

class DimensionTables 
    extends JFrame 
    implements ActionListener {

    	StarSchema globalSchema;
        Dimension d;
        java.util.List<String> list = new java.util.ArrayList<String>();
        String name;
        
        public int number_attri, numberDimension;

        JTextField jt[] = new JTextField[100];
    	int x, y;
        private Container c; 
	    private JLabel title; 
	    private JLabel s_name;  
	    private JLabel dname; 
	    private JTextField tdname; 
	    private JLabel fno; 
	    private JTextField tfno;
	    private JButton sub; 
	    private JButton reset; 
	    private JButton next;
	    private JTextArea tout; 
	    private JLabel res;
            String named;
             JLabel enteredValue;



    public DimensionTables(StarSchema s, int numberDimension){


    	globalSchema = s;

        this.numberDimension = numberDimension;
        d = new Dimension();
        x = 100;
        y = 300;

    	setTitle("Data-Cube Management"); 
        setBounds(300, 90, 900, 600); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setResizable(true); 

        c = getContentPane(); 
        c.setLayout(null);

        title = new JLabel("Dimension Tables Details"); 
        title.setFont(new Font("Arial", Font.PLAIN, 30)); 
        title.setSize(600, 30); 
        title.setLocation(300, 30); 
        c.add(title); 

        s_name = new JLabel("Dimension Tables"); 
        s_name.setFont(new Font("Arial", Font.PLAIN, 15)); 
        s_name.setSize(200, 20); 
        s_name.setLocation(100, 100); 
        c.add(s_name);

        dname = new JLabel("Name of Dimension Table"); 
        dname.setFont(new Font("Arial", Font.PLAIN, 15)); 
        dname.setSize(290, 20); 
        dname.setLocation(100, 150); 
        c.add(dname); 

        tdname = new JTextField(); 
        tdname.setFont(new Font("Arial", Font.PLAIN, 15)); 
        tdname.setSize(190, 20); 
        tdname.setLocation(320, 150); 
        c.add(tdname); 

        fno = new JLabel("Number of Attributes"); 
        fno.setFont(new Font("Arial", Font.PLAIN, 15)); 
        fno.setSize(290, 20); 
        fno.setLocation(100, 200); 
        c.add(fno);

        tfno = new JTextField(); 
        tfno.setFont(new Font("Arial", Font.PLAIN, 15)); 
        tfno.setSize(190, 20); 
        tfno.setLocation(320, 200); 
        c.add(tfno);
		
	sub = new JButton("Add Attributes"); 
        sub.setFont(new Font("Arial", Font.PLAIN, 15)); 
        sub.setSize(150, 20); 
        sub.setLocation(700, 200); 
        sub.addActionListener(this); 
        c.add(sub);  

        next = new JButton("Next"); 
        next.setFont(new Font("Arial", Font.PLAIN, 15)); 
        next.setSize(150, 20); 
        next.setLocation(700, 500); 
        next.addActionListener(this); 
        c.add(next);
        
    }
        public void insert(int number) {
        for (int i = 0; i < number; i++) {
            if (this.getHeight() <= y + 50) {
                x = x + 200;
                y = 300 ;
            }
            jt[i] = new JTextField();
            jt[i].setBounds(x, y, 100, 30);
            add(jt[i]);
            repaint();
            y = y + 50;

        }
    }

    public void actionPerformed(ActionEvent e) 
    { 

    	if (e.getSource() == sub){
                
        name = tdname.getText();
        
        enteredValue = new JLabel("Name of Dimension: " + tdname.getText() + "   " + "Number of Attributes: " + tfno.getText() );
        enteredValue.setFont(new Font("Arial", Font.PLAIN, 15)); 
        enteredValue.setSize(700, 20); 
        enteredValue.setLocation(100, 250); 
        c.add(enteredValue);

            number_attri = Integer.parseInt(tfno.getText());
            insert(number_attri);

    	}
       
        if (e.getSource() == next){
              for (int i = 0; i < number_attri; i++) {
            list.add(jt[i].getText());
              }
             SchemaCreation sc= new SchemaCreation();
             sc.insertDimension(globalSchema, name, list);

        this.dispose();
        System.out.println(numberDimension);
        if(numberDimension != 1) {

            DimensionTables obj = new DimensionTables(this.globalSchema, this.numberDimension-1);
            System.out.println("inside dimension table new ...");
            obj.setVisible(true);
            //obj.pack();
        }
        else {
             FactVariables f = new FactVariables(globalSchema);
             f.setVisible(true);
            //f.pack();
            this.dispose();
        }
        
        }
    }
    
}
//class DimensionTables
//{
//            public static void main(String[] args){
//                StarSchema s = new StarSchema();           
//                DT D = new DT(s,2,3);
//            }
//
//}