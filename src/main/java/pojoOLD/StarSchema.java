package pojoOLD;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="starSchema")
@XmlType(namespace = "https://www.example.org/starSchema")
public class StarSchema {
    private String name;
    private FactList factList;
    private DimensionList dimensionList;
    private AggregateFuncList aggregateFuncList;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DimensionList getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(DimensionList dimensionList) {
        this.dimensionList = dimensionList;
    }

    public FactList getFactList() {
        return factList;
    }

    public void setFactList(FactList factList) {
        this.factList = factList;
    }

    public AggregateFuncList getAggregateFuncList() {
        return aggregateFuncList;
    }

    public void setAggregateFuncList(AggregateFuncList aggregateFuncList) {
        this.aggregateFuncList = aggregateFuncList;
    }

    public StarSchema() {
        dimensionList= new DimensionList();
        factList = new FactList();
        aggregateFuncList= new AggregateFuncList();
    }
}
