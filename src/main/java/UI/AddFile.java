package UI;
import Pojo.Attribute;
import Pojo.StarSchema;
import Pojo.Dimension;
import Pojo.Fact;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
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
    ArrayList<Fact> facts;

     public AddFile(StarSchema s) 
    {   
        
        globalSchema = s;
        sname = globalSchema.getName();
        dim = new ArrayList<Dimension>();
        dim = globalSchema.getDimension();
        facts = new ArrayList<Fact>();
        facts = globalSchema.getFact();
        //ArrayList<Attribute> attri = new ArrayList<Attribute>();
        //attri = d.getAttributes();
        
        System.out.println(sname);
        System.out.println(dim);
        System.out.println(facts);
        //System.out.println(attri);
        
        setTitle("Data-Cube Management"); 
        setBounds(300, 90, 900, 600); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setResizable(true); 

        c = getContentPane(); 
        c.setLayout(null);

        title = new JLabel("Upload the Excel File with following specifications: "); 
        title.setFont(new Font("Arial", Font.PLAIN, 20)); 
        title.setSize(800, 30); 
        title.setLocation(150, 50); 
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
        
        JLabel l1 = new JLabel("Name : " + sname); 
        l1.setFont(new Font("Arial", Font.PLAIN, 15)); 
        l1.setSize(850, 20); 
        l1.setLocation(10, 100); 
        c.add(l1);
        
        JLabel l2 = new JLabel(" " + dim); 
        l2.setFont(new Font("Arial", Font.PLAIN, 15)); 
        l2.setSize(890, 20); 
        l2.setLocation(10, 150); 
        c.add(l2);

        JLabel l3 = new JLabel(" " + facts); 
        l3.setFont(new Font("Arial", Font.PLAIN, 15)); 
        l3.setSize(890, 20); 
        l3.setLocation(10, 220); 
        c.add(l3);
        
        
        
        JButton exit = new JButton("Exit"); 
        exit.setFont(new Font("Arial", Font.PLAIN, 15)); 
        exit.setSize(80, 20); 
        exit.setLocation(740, 500); 
        exit.addActionListener(this); 
        c.add(exit);
        
        JButton manipulate = new JButton("Do you wish to manipulate cube?"); 
        manipulate.setFont(new Font("Arial", Font.PLAIN, 15)); 
        manipulate.setSize(300, 20); 
        manipulate.setLocation(420, 500); 
        manipulate.addActionListener(this); 
        c.add(manipulate);
        
        JButton create = new JButton("Do you wish to create new cube?"); 
        create.setFont(new Font("Arial", Font.PLAIN, 15)); 
        create.setSize(300, 20); 
        create.setLocation(90, 500); 
        create.addActionListener(this); 
        c.add(create);
        
        //this.setVisible(true);
        
        
    }
     
    public void actionPerformed(ActionEvent evt) 
    { 
        // if the user presses the save button show the save dialog 
        String com = evt.getActionCommand(); 
        // if the user presses the open dialog show the open dialog 
        if (com.equals("Open")){
            // create an object of JFileChooser class 
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
  
            // invoke the showsOpenDialog function to show the save dialog 
            int r = j.showOpenDialog(null); 
            l.setText("Click on upload.");
            // if the user selects a file 
            if (r == JFileChooser.APPROVE_OPTION) 
  
            { 
                // set the label to the path of the selected file 
                String filepath = j.getSelectedFile().getAbsolutePath();
                System.out.println(filepath);
                //give this path to backend
                
                //l.setText(j.getSelectedFile().getAbsolutePath()); 
            } 
            // if the user cancelled the operation 
            else
                l.setText("The user cancelled the operation"); 
        } 
        if (com.equals("Exit")){
            System.exit(0);
        }
        if (com.equals("Upload")){
            //call the datacreation function
            l.setText("Successfully uploaded file.");
        }
        if (com.equals("Do you wish to manipulate cube?")){
            //this.setVisible(false);
            //System.out.println("inside button manipulate");
            
        }
        
        if (com.equals("Do you wish to create new cube?")){
            this.setVisible(false);
            //System.out.println("inside button manipulate");
            NewCube obj = new NewCube();
               obj.setVisible(true);
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
   