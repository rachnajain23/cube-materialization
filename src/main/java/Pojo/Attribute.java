package Pojo;

import javax.xml.bind.annotation.*;

@XmlType(namespace = "https://www.example.org/attribute")
public class Attribute {
    @XmlValue
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Attribute() {
    }

    public Attribute(String name) {
        this.name = name;
    }
}
