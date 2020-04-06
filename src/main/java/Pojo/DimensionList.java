package Pojo;

import javax.xml.bind.annotation.*;

@XmlType(namespace = "https://www.example.org/dimensionList")
public class DimensionList {
    private Dimension dimension;

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
}
