package Pojo;

import javax.xml.bind.annotation.*;

@XmlType(namespace = "https://www.example.org/dimension")
public class Dimension {
    private String name;
    private Attribute attribute;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}
