package Pojo;


//import jakarta.xml.bind.annotation.XmlAccessType;
//import jakarta.xml.bind.annotation.XmlAccessorType;
//import jakarta.xml.bind.annotation.XmlAttribute;
//import jakarta.xml.bind.annotation.XmlType;

import javax.xml.bind.annotation.*;

@XmlType(namespace = "https://www.example.org/dimension")
public class Dimension {
    private String name;
    private DimensionalAttribute dimensionalAttribute;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public DimensionalAttribute getDimensionalAttribute() {
        return dimensionalAttribute;
    }

    public void setDimensionalAttribute(DimensionalAttribute dimensionalAttribute) {
        this.dimensionalAttribute = dimensionalAttribute;
    }
}
