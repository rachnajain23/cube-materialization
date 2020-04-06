package Pojo;

import utils.Type;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlType(namespace = "https://www.example.org/fact")
public class Fact {
    private String name;
    private Type type;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    @XmlAttribute
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
