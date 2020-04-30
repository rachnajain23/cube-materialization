package Pojo.Schema;


//import jakarta.xml.bind.annotation.XmlAccessType;
//import jakarta.xml.bind.annotation.XmlAccessorType;
//import jakarta.xml.bind.annotation.XmlAttribute;
//import jakarta.xml.bind.annotation.XmlType;

import Pojo.Schema.Attribute;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlType(namespace = "https://www.example.org/dimension")
public class Dimension {
    private String name;
    private ArrayList<Attribute> attributes;

    public Dimension()
    {
        attributes = new ArrayList<Attribute>();
    }

    @XmlAttribute
    public String getName() {
        return name;
    }
    public void setName(String Name) {
        this.name = Name;
    }


    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }
    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(Attribute a) {
        attributes.add(a);
    }

    @Override
    public String toString() {
        return "Dimension{" +
                "name='" + name + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
