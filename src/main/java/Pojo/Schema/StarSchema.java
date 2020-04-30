package Pojo.Schema;


import Pojo.Enums.AggregateFunc;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement(name="starSchema")
@XmlType(namespace = "https://www.example.org/starSchema")
public class
StarSchema {
    private String name;
    private ArrayList<Fact> facts;
    private ArrayList<Dimension> dimensions;

    public StarSchema() {
        facts = new ArrayList<Fact>();
        dimensions = new ArrayList<Dimension>();
    }

    @XmlAttribute
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "factList")
    @XmlElement
    public ArrayList<Fact> getFact() {
        return facts;
    }
    public void setFact(ArrayList<Fact> facts) {
        this.facts = facts;
    }

    @XmlElementWrapper(name = "dimensionList")
    @XmlElement
    public ArrayList<Dimension> getDimension() {
        return dimensions;
    }
    public void setDimension(ArrayList<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public void addSingleDimension(Dimension d) {
        this.dimensions.add(d);
    }

    public void addSingleFact(Fact f) {
        this.facts.add(f);
    }

    @Override
    public String toString() {
        String s= "Name: "+ this.name+"\n";
        s+="Dimensions:\n";
        for (Dimension dimension: this.dimensions){
            s+= dimension.getName()+": ";
            for(Attribute attribute: dimension.getAttributes())
                s+= attribute.getName()+" ";
            s+="\n";
        }
        s+="Facts:\n";
        for (Fact fact: this.facts){
            s+= fact.getName()+": ";
            for (AggregateFunc aggregateFunc: fact.getAggregateFuncs())
                s+= aggregateFunc.toString().toLowerCase()+" ";
            s+="\n";
        }
        return s;
    }
}
