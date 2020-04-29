package UI;
import UI.NewCube;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

class MyFirst
    extends JFrame 
    implements ActionListener { 
  
    // Components of the Form 
    private Container c; 
    private JLabel title; 
    private JButton newcreate; 
    private JButton manipulate;
    private JButton exit;

     public MyFirst() 
    {   
        setTitle("Data-Cube Management"); 
        setBounds(300, 90, 900, 600); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setResizable(true); 

        c = getContentPane(); 
        c.setLayout(null);

        title = new JLabel("Welcome Select Your Choice!"); 
        title.setFont(new Font("Arial", Font.PLAIN, 25)); 
        title.setSize(400, 30); 
        title.setLocation(265, 80); 
        c.add(title);
        
        newcreate = new JButton("New Cube Creation");
        newcreate.setFont(new Font("Arial", Font.PLAIN, 20));
        newcreate.setSize(400, 40);
        newcreate.setLocation(250, 200);
        newcreate.addActionListener(this);
        c.add(newcreate);
        
        manipulate = new JButton("Manipulate Existing One");
        manipulate.setFont(new Font("Arial", Font.PLAIN, 20));
        manipulate.setSize(400, 40);
        manipulate.setLocation(250, 260);
        manipulate.addActionListener(this);
        c.add(manipulate);
        
        exit = new JButton("Exit");
        exit.setFont(new Font("Arial", Font.PLAIN, 20));
        exit.setSize(100, 30);
        exit.setLocation(750, 510);
        exit.addActionListener(this);
        c.add(exit);

        this.setVisible(true);
    }
     
     public void actionPerformed(ActionEvent e) 
    { 
        if (e.getSource() == newcreate) { 
             
               System.out.println("inside new create");
               this.setVisible(false);
               NewCube obj = new NewCube();
               obj.setVisible(true);
//               obj.pack();
//               repaint();
        }
        if(e.getSource() == manipulate){
                System.out.println("inside manipulate");
                
        }
        if(e.getSource() == exit){
            System.exit(0);
        }
    }
}

class FirstPage { 
  
    public static void main(String[] args) throws Exception 
    { 
        MyFirst my = new MyFirst(); 
    } 
}
