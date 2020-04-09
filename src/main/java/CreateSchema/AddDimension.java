package CreateSchema;

import Pojo.Dimension;
import Pojo.StarSchema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDimension implements ActionListener {
    private StarSchema s;
    private Dimension d;

    public AddDimension(StarSchema s, Dimension d) {
        this.s = s;
        this.d = d;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        s.addSingleDimension(d);
    }
}
