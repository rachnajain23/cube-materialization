package CreateSchema;

import Pojo.Fact;
import Pojo.StarSchema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFact implements ActionListener {
    private StarSchema s;
    private Fact f;

    public AddFact(StarSchema s, Fact f) {
        this.s = s;
        this.f = f;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        s.addSingleFact(f);
    }
}
