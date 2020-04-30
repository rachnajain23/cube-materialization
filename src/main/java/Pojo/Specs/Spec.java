package Pojo.Specs;

import Pojo.Schema.Attribute;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;


public class Spec {

    private String name;
    private String customName;
    private ArrayList<Attribute> attribute;

    public Spec() {
        attribute = new ArrayList<Attribute>();
    }

    @XmlAttribute
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getCustomName() { return customName; }
    public void setCustomName(String customName) { this.customName = customName; }

    @XmlElementWrapper(name = "attributeList")
    @XmlElement
    public ArrayList<Attribute> getAttribute() {
        return attribute;
    }
    public void setAttribute(ArrayList<Attribute> attribute) {
        this.attribute = attribute;
    }

    public void addAttribute(Attribute attribute)
    {
        this.attribute.add((attribute));
    }

    @Override
    public String toString() {
        return "Spec{" +
                "name='" + name + '\'' +
                ", customName='" + customName + '\'' +
                ", attribute=" + attribute +
                '}';
    }
}
