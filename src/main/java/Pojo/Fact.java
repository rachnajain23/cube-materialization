package Pojo;

//import jakarta.xml.bind.annotation.XmlAccessType;
//import jakarta.xml.bind.annotation.XmlAccessorType;
//import jakarta.xml.bind.annotation.XmlAttribute;
//import jakarta.xml.bind.annotation.XmlType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlType(namespace = "https://www.example.org/fact")
public class Fact {
    private String name;
    private Type type;

    @XmlElementWrapper(name = "AggregateFuncList")
    @XmlElement
    private List<AggregateFunc> aggregateFuncs;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<AggregateFunc> getAggregateFuncs() {
        return aggregateFuncs;
    }

    public void setAggregateFuncs(List<AggregateFunc> aggregateFuncs) {
        this.aggregateFuncs = aggregateFuncs;
    }

    public Fact() {
        aggregateFuncs= new ArrayList<AggregateFunc>();
    }

    public void addAggregateFn(AggregateFunc f) {
        this.aggregateFuncs.add(f);
    }
}
