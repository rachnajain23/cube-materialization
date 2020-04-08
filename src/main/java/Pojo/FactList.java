package Pojo;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
@XmlType(namespace = "https://www.example.org/factList")
public class FactList {
    private List<Fact> fact;

    public List<Fact> getFact() {
        if (fact == null) {
            fact = new ArrayList<Fact>();
        }
        return  fact;
    }

    public void setFacts(List<Fact> fact) {
        this.fact = fact;
    }

    public FactList() {
    }
}
