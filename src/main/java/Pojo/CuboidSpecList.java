package Pojo;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name="cuboidSpec")
public class CuboidSpecList {
    ArrayList<Spec> speclist;
    ArrayList<String> tables;

    public CuboidSpecList() {
        speclist = new ArrayList<Spec>();
        tables = new ArrayList<String>();
    }

    @XmlElement(name = "spec")
    public ArrayList<Spec> getSpeclist() {
        return speclist;
    }
    public void setSpeclist(ArrayList<Spec> speclist) {
        this.speclist = speclist;
    }

    public void addSpec(Spec s) {
        speclist.add(s);
    }

    @XmlElementWrapper(name="cuboidList")
    @XmlElement(name = "table")
    public ArrayList<String> getTables() {
        return tables;
    }
    public void setTables(ArrayList<String> tables) {
        this.tables = tables;
    }

    public void addTables(ArrayList<String> tables) {
        this.getTables().addAll(tables);
    }

    @Override
    public String toString() {
        return "CuboidSpecList{" +
                "speclist=" + speclist +
                ", tables=" + tables +
                '}';
    }
}
