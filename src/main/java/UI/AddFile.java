package UI;
import Pojo.*;
import Pojo.Dimension;
import PreProcessing.DatabaseSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

class AddFile
    extends JFrame 
    implements ActionListener { 
  
    // Components of the Form 
    private Container c;
    private JLabel title; 
    static JLabel l;
    StarSchema globalSchema = new StarSchema();
    Dimension d = new Dimension();
    String sname;
    ArrayList<Dimension> dim ;
    String str;
    String filepath;
    ArrayList<Fact> facts;
    DatabaseSetup dbsetup = new DatabaseSetup();

     public AddFile(StarSchema s)
    {

        globalSchema = s;
        sname = globalSchema.getName();
        dim = new ArrayList<Dimension>();
        dim = globalSchema.getDimension();
        facts = new ArrayList<Fact>();
        facts = globalSchema.getFact();


        setTitle("Data-Cube Management"); 
        setBounds(300, 90, 900, 600); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setResizable(true); 

        c = getContentPane(); 
        c.setLayout(null);

        title = new JLabel("Upload the Excel File with following specifications: "); 
        title.setFont(new Font("Arial", Font.PLAIN, 20)); 
        title.setSize(800, 30); 
        title.setLocation(170, 50);
        c.add(title);
        
        // button to open open dialog 
        JButton button2 = new JButton("Open"); 
        button2.setFont(new Font("Arial", Font.PLAIN, 15)); 
        button2.setSize(100, 20); 
        button2.setLocation(600, 380); 
        button2.addActionListener(this); 
        c.add(button2);
        
        JButton button3 = new JButton("Upload"); 
        button3.setFont(new Font("Arial", Font.PLAIN, 15)); 
        button3.setSize(100, 20); 
        button3.setLocation(720, 380); 
        button3.addActionListener(this); 
        c.add(button3);
        
        l = new JLabel("No files selected"); 
        l.setFont(new Font("Arial", Font.PLAIN, 15)); 
        l.setSize(290, 20); 
        l.setLocation(600, 350); 
        c.add(l);

        String str= "";
        String str1= "";
        String str2= "";
        String fc= "";
        int y = 125;
        str1="File name:"+ s.getName()+"\n";
        System.out.println(str1);
        for(Dimension dimension: dim) {
            str = "Sheet name:" + dimension.getName() + "   " + "Attribute in sequence: ";
            //System.out.println(str + "\n" + "Attribute in sequence: ");
            java.util.List<Attribute> attributeList = dimension.getAttributes();
            JLabel l2 = new JLabel(str );
            l2.setFont(new Font("Arial", Font.PLAIN, 15));
            l2.setSize(890, 20);
            l2.setLocation(20,y);
            c.add(l2);
            int b = y + 20;
            int a = 200;
            for(Attribute attribute: attributeList) {

                str2 = attribute.getName() + ",";
                System.out.print(str2);
                JLabel l3 = new JLabel(str2);
                l3.setFont(new Font("Arial", Font.PLAIN, 15));
                l3.setSize(100, 20);
                l3.setLocation(a, b);
                c.add(l3);
                a = a+100;
            }
            y = y+50;
        }
        int q = y+40;
        for(Fact fact: facts){
            int p = 20;
            fc = "Fact name:" + fact.getName() + " Type:" + fact.getType() + " Aggregate Functions:" + fact.getAggregateFuncs();
            System.out.println("\n" + fc);
            JLabel l4 = new JLabel(fc);
            l4.setFont(new Font("Arial", Font.PLAIN, 15));
            l4.setSize(890, 20);
            l4.setLocation(p,q);
            c.add(l4);
            q = q+25;

        }

        JLabel l1 = new JLabel(str1);
        l1.setFont(new Font("Arial", Font.PLAIN, 15)); 
        l1.setSize(850, 20); 
        l1.setLocation(20, 100);
        c.add(l1);

        
        JButton exit = new JButton("Exit"); 
        exit.setFont(new Font("Arial", Font.PLAIN, 15)); 
        exit.setSize(80, 20); 
        exit.setLocation(810, 500);
        exit.addActionListener(this); 
        c.add(exit);
        
        JButton manipulate = new JButton("Generate Lattice of cuboid?");
        manipulate.setFont(new Font("Arial", Font.PLAIN, 15)); 
        manipulate.setSize(250, 20);
        manipulate.setLocation(285, 500);
        manipulate.addActionListener(this); 
        c.add(manipulate);
        
        JButton create = new JButton("Create other star schema?");
        create.setFont(new Font("Arial", Font.PLAIN, 15)); 
        create.setSize(250, 20);
        create.setLocation(10, 500);
        create.addActionListener(this); 
        c.add(create);

        JButton queries = new JButton("Run OLAP Queries");
        queries.setFont(new Font("Arial", Font.PLAIN, 15));
        queries.setSize(250, 20);
        queries.setLocation(565, 500);
        queries.addActionListener(this);
        c.add(queries);

        //this.setVisible(true);
        
        
    }
     
    public void actionPerformed(ActionEvent evt) 
    { 

        String com = evt.getActionCommand(); 
        if (com.equals("Open")){
            // create an object of JFileChooser class 
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            j.setFileSelectionMode(JFileChooser.FILES_ONLY);
            j.addChoosableFileFilter(new FileNameExtensionFilter("Excel File","xlsx","xls"));
            j.setAcceptAllFileFilterUsed(false);
            // invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null); 
            l.setText("Click on upload.");
            
            if (r == JFileChooser.APPROVE_OPTION)
            {
                filepath = j.getSelectedFile().getAbsolutePath();
                System.out.println(filepath);
            } 
            else
                l.setText("The user cancelled the operation"); 
        } 
        if (com.equals("Exit")){
            System.exit(0);
        }
        if (com.equals("Upload")){
            dbsetup.insertIntoDB(globalSchema,filepath);
            l.setText("Successfully uploaded file.");
        }
        if (com.equals("Create other star schema?")){
            this.setVisible(false);
            //System.out.println("inside button manipulate");
            NewCube obj = new NewCube();
               obj.setVisible(true);
        }
        if (com.equals("Generate Lattice of cuboid?")){
            //this.setVisible(false);
            System.out.println("inside button latticeofcuboid");

        }
        if (com.equals("Run OLAP Queries")){
            //this.setVisible(false);
            System.out.println("inside button olap queries");

        }
    }

}
//class AddFile { 
//  
//    public static void main(String[] args) throws Exception 
//    { 
//        A my = new A(); 
//    } 
//}
   