package Pojo.Schema;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "https://www.example.org/dimensionalAttribute")
public class Attribute {

    private String name;
    private Integer code;

    public Attribute() {
    }

    public Attribute(String name) {
        this.name = name;
    }

    @XmlAttribute
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
