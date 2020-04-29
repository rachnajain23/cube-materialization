package  UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GenLattice {
    JFrame f;
    GenLattice(){
        f=new JFrame("Data Cube Management");
        final JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setSize(350,100);
        JButton b=new JButton("Select");
        b.setBounds(250,100,100,20);

                ArrayList<String> a1 = new ArrayList<String>();
        //a1 = getListofschema from backend
        a1.add("Market");
        a1.add("Store");
        a1.add("School");

        String str[] = new String[a1.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < a1.size(); j++) {
            str[j] = a1.get(j);
        }
        final JComboBox cb=new JComboBox(str);

        cb.setBounds(50, 100,150,20);
        String head = "Select the Schema from below!";
        label.setText(head);
        f.add(cb);
        f.add(label);
        f.add(b);
        f.setLayout(null);
        f.setBounds(300,90,900,600);
        f.setVisible(true);

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = "Schema Selected is : "
                        + cb.getItemAt(cb.getSelectedIndex());
                label.setText(data);
                //call function to pass the schema selected
                JTextArea t = new JTextArea();

                f.add(t);
            }
        });
    }
    public static void main(String[] args) {
        new GenLattice();
    }
} 