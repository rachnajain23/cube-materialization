package UI.SchemaCreation;

import UI.CuboidSpecification.GenFirst;
import UI.LoadData.addFileFirst;
import UI.OLAPQueries.QueryFirst;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FirstPage extends JFrame implements ActionListener {

    // Components of the Form 
    private Container c;
    private JLabel title;
    private JButton newcreate;
    private JButton addFile;
    private JButton manipulate;
    private JButton queries;
    private JButton exit;

    public FirstPage() {
        setTitle("Data-Cube Management");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Welcome Select Your Choice!");
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setSize(400, 30);
        title.setLocation(245, 80);
        c.add(title);

        newcreate = new JButton("Create Star Schema");
        newcreate.setFont(new Font("Arial", Font.PLAIN, 20));
        newcreate.setSize(350, 30);
        newcreate.setLocation(250, 200);
        newcreate.addActionListener(this);
        c.add(newcreate);

        addFile = new JButton("Upload Data File");
        addFile.setFont(new Font("Arial", Font.PLAIN, 20));
        addFile.setSize(350, 30);
        addFile.setLocation(250, 240);
        addFile.addActionListener(this);
        c.add(addFile);

        manipulate = new JButton("Generate Lattice of Cuboid");
        manipulate.setFont(new Font("Arial", Font.PLAIN, 20));
        manipulate.setSize(350, 30);
        manipulate.setLocation(250, 280);
        manipulate.addActionListener(this);
        c.add(manipulate);

        queries = new JButton("Run OLAP Queries");
        queries.setFont(new Font("Arial", Font.PLAIN, 20));
        queries.setSize(350, 30);
        queries.setLocation(250, 320);
        queries.addActionListener(this);
        c.add(queries);

        exit = new JButton("Exit");
        exit.setFont(new Font("Arial", Font.PLAIN, 20));
        exit.setSize(100, 30);
        exit.setLocation(750, 510);
        exit.addActionListener(this);
        c.add(exit);


        this.setVisible(true);


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newcreate) {

            System.out.println("inside new create");
            this.setVisible(false);
            NewCube obj = new NewCube();
            obj.setVisible(true);

        }
        if (e.getSource() == addFile){
            System.out.println("upload file");
            try {
                this.setVisible(false);
                addFileFirst ff = new addFileFirst();
                ff.setVisible(true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if (e.getSource() == manipulate) {
            System.out.println("inside manipulate");
            this.setVisible(false);
            try {
                GenFirst genFirst = new GenFirst();
                genFirst.setBounds(300,90,900, 500);
                genFirst.setVisible(true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        if (e.getSource() == queries) {
            try {
                this.setVisible(false);
                QueryFirst queryFirst = new QueryFirst();
                queryFirst.setVisible(true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.out.println("inside queries");
        }
        if (e.getSource() == exit) {
            System.exit(0);
        }
    }

    public static void main(String[] args) throws Exception {
        try {

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e) {
            System.out.println("Look and Feel not set");
        }
        FirstPage my = new FirstPage();
    }
}
