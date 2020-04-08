package Pojo;

import utils.AggregateFunc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(namespace = "https://www.example.org/aggFuncList")
public class AggregateFuncList {
    private List<AggregateFunc> aggregateFunc;

    public List<AggregateFunc> getAggregateFunc() {
        if (aggregateFunc == null) {
            aggregateFunc = new ArrayList<AggregateFunc>();
        }
        return aggregateFunc;
    }

    public void setAggregateFunc(List<AggregateFunc> aggregateFunc) {
        this.aggregateFunc = aggregateFunc;
    }

    public AggregateFuncList() {
    }
}
