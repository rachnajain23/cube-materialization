package PreProcessing;

import Pojo.*;
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

    public boolean doesSchemaExist(String name){
        String currentDirectory = System.getProperty("user.dir");
        String fName = currentDirectory + "/storage/" +name + ".xml";
        File f = new File(fName);
        if(f.exists())
            return true;
        else
            return false;
    }

    public StarSchema newSchema(String name){
        StarSchema starSchema = new StarSchema();
        starSchema.setName(name);
        return starSchema;
    }

    public void insertFact(StarSchema starSchema, String name, Type type,List<AggregateFunc> aggregateFunc ){
        Fact fact = new Fact();
        fact.setName(name);
        fact.setType(type);
        fact.setAggregateFuncs((ArrayList<AggregateFunc>) aggregateFunc);
        starSchema.getFact().add(fact);
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
        dimension.setAttributes((ArrayList<Attribute>) attributeList);
        starSchema.getDimension().add(dimension);
    }

    public boolean writeSchema(StarSchema starSchema) throws JAXBException, FileNotFoundException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String fName = currentDirectory + "/storage/" + starSchema.getName() + ".xml";
        File f = new File(fName);
        System.out.println(f.exists());
        if(f.exists())
        {
            JAXBContext contextObj = JAXBContext.newInstance(StarSchema.class);

            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(starSchema, new FileOutputStream(fName));
        }
        else
            return false;
        return true;
    }

//    public boolean saveSchema(StarSchema starSchema) throws JAXBException, IOException {
//        String currentDirectory = System.getProperty("user.dir");
//        String fName = currentDirectory + "/storage/" + starSchema.getName() + ".xml";
//        File f = new File(fName);
//        if(!f.exists())
//            return false;
//        JAXBContext contextObj = JAXBContext.newInstance(StarSchema.class);
//        Marshaller marshallerObj = contextObj.createMarshaller();
//        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshallerObj.marshal(starSchema, new FileOutputStream(fName));
//            return true;
//    }


}
