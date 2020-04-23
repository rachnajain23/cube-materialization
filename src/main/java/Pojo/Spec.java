package Pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;


public class Spec {

    private String name;
    private ArrayList<String> attribute;

    public Spec() {
        attribute = new ArrayList<String>();
    }

    @XmlAttribute
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "dimensionList")
    @XmlElement
    public ArrayList<String> getAttribute() {
        return attribute;
    }
    public void setAttribute(ArrayList<String> attribute) {
        this.attribute = attribute;
    }

    public void addAttribute(String att)
    {
        this.attribute.add((att));
    }
}
