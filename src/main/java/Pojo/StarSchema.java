package Pojo;


//import jakarta.xml.bind.annotationtation.XmlAttribute;
//import jakarta.xml.bind.annotation.XmlRootElement;
//import jakarta.xml.bind.annotation.XmlType;
//import jakarta.xml.bind.annotation.XmlAccessType;
//import jakarta.xml.bind.annotation.XmlAccessorType;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="starSchema")
@XmlType(namespace = "https://www.example.org/starSchema")

public class StarSchema {
    private long id;
    private Fact fact;
    private Dimension dimension;

    @XmlAttribute
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Fact getFact() {
        return fact;
    }

    public void setFact(Fact fact) {
        this.fact = fact;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
}
