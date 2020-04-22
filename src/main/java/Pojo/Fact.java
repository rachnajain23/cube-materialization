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
    private ArrayList<AggregateFunc> aggregateFuncs;

    public Fact() {
        aggregateFuncs= new ArrayList<AggregateFunc>();
    }

    @XmlAttribute
    public String getName() {
        return name;
    }
    public void setName(String Name) {
        this.name = Name;
    }

    @XmlElement
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    @XmlElementWrapper(name = "aggregateFuncList")
    @XmlElement
    public ArrayList<AggregateFunc> getAggregateFuncs() {
        return aggregateFuncs;
    }
    public void setAggregateFuncs(ArrayList<AggregateFunc> aggregateFuncs) {
        this.aggregateFuncs = aggregateFuncs;
    }

    public void addAggregateFn(AggregateFunc f) {
        this.aggregateFuncs.add(f);
    }

    @Override
    public String toString() {
        return "Fact{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", aggregateFuncs=" + aggregateFuncs +
                '}';
    }
}
