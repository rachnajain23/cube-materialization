package Pojo;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name="cuboidSpec")
public class CuboidSpecList {
    ArrayList<Spec> speclist;

    public CuboidSpecList() {
        speclist = new ArrayList<Spec>();
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

//    @Override
//    public String toString() {
//        return speclist.toString();
//    }
}
