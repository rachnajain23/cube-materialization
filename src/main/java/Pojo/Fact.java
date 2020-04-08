package Pojo;

//import jakarta.xml.bind.annotation.XmlAccessType;
//import jakarta.xml.bind.annotation.XmlAccessorType;
//import jakarta.xml.bind.annotation.XmlAttribute;
//import jakarta.xml.bind.annotation.XmlType;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
