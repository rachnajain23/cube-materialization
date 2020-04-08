package Pojo;

import utils.Type;

import javax.xml.bind.annotation.*;

@XmlType(namespace = "https://www.example.org/fact")
public class Fact {
    @XmlValue
    private String name;
    @XmlAttribute
    private Type type;

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

    public Fact() {
    }
}
