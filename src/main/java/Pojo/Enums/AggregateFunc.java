package Pojo.Enums;


import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "https://www.example.org/aggregateFunc")
public enum AggregateFunc {
    SUM,
    COUNT,
    AVG,
    MEAN,
    MEDIAN,
    MODE
}
