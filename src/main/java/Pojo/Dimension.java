package Pojo;


//import jakarta.xml.bind.annotation.XmlAccessType;
//import jakarta.xml.bind.annotation.XmlAccessorType;
//import jakarta.xml.bind.annotation.XmlAttribute;
//import jakarta.xml.bind.annotation.XmlType;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlType(namespace = "https://www.example.org/dimension")
public class Dimension {
    private String name;
    private ArrayList<Attribute> attribute;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }


    public ArrayList<Attribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(ArrayList<Attribute> attribute) {
        this.attribute = attribute;
    }

    public void addAttribute(Attribute a) {
        attribute.add(a);
    }
}
