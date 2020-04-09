package UIOLD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class sample1 extends JFrame implements ActionListener {
    private Container c;
    private JLabel title;
    private JLabel name;
    private JTextField tname;
    private JLabel mno;
    private JTextField tmno;
    private JLabel gender;
    private JRadioButton male;
    private JRadioButton female;
    private ButtonGroup gengp;
    private JLabel choice;
    private JComboBox option;
    private JComboBox month;
    private JComboBox year;
    private JLabel add;
    private JTextArea tadd;
    private JCheckBox term;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;
    private JLabel res;
    private String choiceOption[]= {"a", "b", "c"};
    private JTextArea resadd;

    public sample1(){
        setTitle("Registration Form");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);
        title = new JLabel("Registration Form");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(100, 100);
        c.add(name);

        tname = new JTextField();
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(190, 20);
        tname.setLocation(200, 100);
        c.add(tname);

        gender = new JLabel("Gender");
        gender.setFont(new Font("Arial", Font.PLAIN, 20));
        gender.setSize(100, 20);
        gender.setLocation(100, 200);
        c.add(gender);

        male = new JRadioButton("Male");
        male.setFont(new Font("Arial", Font.PLAIN, 15));
        male.setSelected(true);
        male.setSize(75, 20);
        male.setLocation(200, 200);
        c.add(male);

        female = new JRadioButton("Female");
        female.setFont(new Font("Arial", Font.PLAIN, 15));
        female.setSelected(false);
        female.setSize(80, 20);
        female.setLocation(275, 200);
        c.add(female);

        gengp = new ButtonGroup();
        gengp.add(male);
        gengp.add(female);

        choice = new JLabel("choices");
        choice.setFont(new Font("Arial", Font.PLAIN, 20));
        choice.setSize(100, 20);
        choice.setLocation(100, 250);
        c.add(choice);

        option = new JComboBox(choiceOption);
        option.setFont(new Font("Arial", Font.PLAIN, 15));
        option.setSize(50, 20);
        option.setLocation(200, 250);
        c.add(option);

        term = new JCheckBox("Accept sample 1");
        term.setFont(new Font("Arial", Font.PLAIN, 15));
        term.setSize(250, 20);
        term.setLocation(150, 400);
        c.add(term);

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 450);
        sub.addActionListener(this);
        c.add(sub);

        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 500);
        c.add(res);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        Service s= new Service();
        if (e.getSource() == sub) {
            s.sampleFunc(tname.getText(),
                    male.isSelected()?"M":"F",
                    (String)option.getSelectedItem(),
                    term.isSelected()?"termSelected": "termNotSelected");
        }
        // do whatever you want to do with the date here.
    }
}

// Driver Code
class Registration {

    public static void main(String[] args) throws Exception
    {
        sample1 s1 = new sample1();
    }
}

class Service{
    public void sampleFunc(String name, String gender, String choice,String accept){
        System.out.println(name);
        System.out.println(gender);
        System.out.println(choice);
        System.out.println(accept);
    }
}

