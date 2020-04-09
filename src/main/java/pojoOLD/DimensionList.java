package pojoOLD;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlType(namespace = "https://www.example.org/dimensionList")
public class DimensionList {
    private List<Dimension> dimension;

    public List<Dimension> getDimension() {
        if (dimension == null) {
            dimension = new ArrayList<Dimension>();
        }
        return dimension;
    }

    public void setDimension(List<Dimension> dimensionList) {
        this.dimension = dimensionList;
    }

    public DimensionList() {
    }
}
