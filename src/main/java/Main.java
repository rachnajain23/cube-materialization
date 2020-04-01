import javax.swing.*;
import java.io.IOException;


public class Main {

    static JLabel l;
    public static void main(String[] args) throws IOException {
        JFrame f = new JFrame("file chooser");

        f.setSize(800, 800);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("open");

        // make an object of the class filechooser
        FileChooser f1 = new FileChooser();
        button.addActionListener(f1);

        JPanel p = new JPanel();
        p.add(button);
        l = new JLabel("no file selected");
        p.add(l);
        f.add(p);

        f.show();

        // code here: moved to SheetSelector class

    }
}

