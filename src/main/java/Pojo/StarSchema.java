package Pojo;

import utils.AggregateFunc;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="starSchema")
@XmlType(namespace = "https://www.example.org/starSchema")

public class StarSchema {
    private long id;
    private Fact fact;
    private DimensionList dimensionList;
    private AggregateFunc aggregateFunc;

    @XmlAttribute
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AggregateFunc getAggregateFunc() {
        return aggregateFunc;
    }

    public void setAggregateFunc(AggregateFunc aggregateFunc) {
        this.aggregateFunc = aggregateFunc;
    }

    public Fact getFact() {
        return fact;
    }

    public void setFact(Fact fact) {
        this.fact = fact;
    }

    public DimensionList getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(DimensionList dimensionList) {
        this.dimensionList = dimensionList;
    }
}
