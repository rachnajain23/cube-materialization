package PreProcessing;

import Pojo.Attribute;
import Pojo.Dimension;
import Pojo.Fact;
import Pojo.StarSchema;
import utils.AggregateFunc;
import utils.Type;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchemaCreation {


    public StarSchema createSchema(String name){
        StarSchema starSchema = new StarSchema();
        starSchema.setName(name);
        return starSchema;
    }

    public void insertFact(StarSchema starSchema, String name, Type type){
        Fact fact = new Fact();
        fact.setName(name);
        fact.setType(type);
        starSchema.getFactList().getFact().add(fact);
    }

    public void insertDimension(StarSchema starSchema,String name, List<String> attributeNames){
        Dimension dimension = new Dimension();

        List<Attribute> attributeList= new ArrayList<Attribute>();
        for(String x: attributeNames){
            Attribute attribute = new Attribute();
            attribute.setName(x);
            attributeList.add(attribute);
        }

        dimension.setName(name);
        dimension.setAttribute(attributeList);
        starSchema.getDimensionList().getDimension().add(dimension);
    }

    public  void  insertAggregateFunc(StarSchema starSchema,AggregateFunc aggregateFunc){
        starSchema.getAggregateFuncList().getAggregateFunc().add(aggregateFunc);
    }

    public void saveSchema(StarSchema starSchema) throws JAXBException, IOException {
        Dimension dimension = new Dimension();
        JAXBContext contextObj = JAXBContext.newInstance(StarSchema.class);
        Marshaller marshallerObj = contextObj.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File file = new File(starSchema.getName()+".xml");
        file.createNewFile(); // it will do nothing if file already exists
        marshallerObj.marshal(starSchema, file); //new FileOutputStream(file, false)
    }
}
