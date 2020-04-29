package UI;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
class solve11 extends JFrame implements ItemListener {

    // frame
    static JFrame f;

    // label
    static JLabel l, l1;

    // combobox
    static JComboBox c1;

    // main class
    public static void main(String[] args)
    {
        // create a new frame
        f = new JFrame("Data Cube Management");

        // create a object
        solve11 s = new solve11();

        // set layout of frame
        f.setLayout(new FlowLayout());

        // array of string contating cities
        String s1[] = { "Jalpaiguri", "Mumbai", "Noida", "Kolkata", "New Delhi" };

        ArrayList<String> a1 = new ArrayList<String>();
        //a1 = getListofschema from backend
        a1.add("Geeks");
        a1.add("for");
        a1.add("Geeks");
        JLabel title = new JLabel("Generation of Lattice of Cuboid");
        title.setFont(new Font("Arial", Font.PLAIN, 20));

        String str[] = new String[a1.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < a1.size(); j++) {

            // Assign each value to String array
            str[j] = a1.get(j);
        }
        // create checkbox
        c1 = new JComboBox(str);

        // add ItemListener
        c1.addItemListener(s);

        // create labels
        l = new JLabel("Select the schema ");
        l1 = new JLabel("Nothing is selected");

        // set color of text


        // create a new panel
        JPanel p = new JPanel();

        p.add(l);

        // add combobox to panel
        p.add(c1);

        p.add(l1);
        p.add(title, BorderLayout.NORTH);
        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setBounds(300, 90, 900, 600);

        f.show();
    }
    public void itemStateChanged(ItemEvent e)
    {
        // if the state combobox is changed
        if (e.getSource() == c1) {

            l1.setText(c1.getSelectedItem() + " selected");
        }
    }
}