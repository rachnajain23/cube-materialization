package Pojo.Specs;

import Pojo.Enums.AggregateFunc;
import Pojo.Schema.Attribute;

public class ThresholdAggFunc {
    private int minVal;
    private int maxVal;
    private AggregateFunc aggregateFunc;
    private String factName;

    public int getMinVal() {
        return minVal;
    }

    public void setMinVal(int minVal) {
        this.minVal = minVal;
    }

    public int getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(int maxVal) {
        this.maxVal = maxVal;
    }

    public AggregateFunc getAggregateFunc() {
        return aggregateFunc;
    }

    public void setAggregateFunc(AggregateFunc aggregateFunc) {
        this.aggregateFunc = aggregateFunc;
    }

    public String getFactName() {
        return factName;
    }

    public void setFactName(String factName) {
        this.factName = factName;
    }

    @Override
    public String toString() {
        return "ThresholdAggFunc{" +
                "minVal=" + minVal +
                ", maxVal=" + maxVal +
                ", aggregateFunc=" + aggregateFunc +
                ", factName='" + factName + '\'' +
                '}';
    }
}
