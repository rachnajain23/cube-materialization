package PreProcessing;

import Pojo.*;
import org.w3c.dom.Attr;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchemaCreation {

//    public boolean doesSchemaExist(String name){
//        String currentDirectory = System.getProperty("user.dir");
//        String fName = currentDirectory + "/storage/" +name + ".xml";
//        File f = new File(fName);
//        if(f.exists())
//            return true;
//        else
//            return false;
//    }
//
    public StarSchema newSchema(String name){
        StarSchema starSchema = new StarSchema();
        starSchema.setName(generateName(name));
        return starSchema;
    }

    public void insertFact(StarSchema starSchema, String name, Type type,ArrayList<AggregateFunc> aggregateFunc ){
        Fact fact = new Fact();
        fact.setName(generateName(name));
        fact.setType(type);
        fact.setAggregateFuncs(aggregateFunc);
        starSchema.getFact().add(fact);
    }

    public void insertDimension(StarSchema starSchema,String name, List<String> attributeNames){
        Dimension dimension = new Dimension();

        List<Attribute> attributeList= new ArrayList<Attribute>();
        for(String x: attributeNames){
            Attribute attribute = new Attribute();
            attribute.setName(generateName(x));
            attributeList.add(attribute);
        }

        dimension.setName(generateName(name));
        dimension.setAttributes((ArrayList<Attribute>) attributeList);
        starSchema.getDimension().add(dimension);
    }

//    public boolean writeSchema(StarSchema starSchema) throws JAXBException, FileNotFoundException {
//        String currentDirectory = System.getProperty("user.dir");
//        System.out.println(currentDirectory);
//        String fName = currentDirectory + "/storage/" + starSchema.getName() + ".xml";
//        File f = new File(fName);
//        System.out.println(f.exists());
//        if(f.exists())
//        {
//            JAXBContext contextObj = JAXBContext.newInstance(StarSchema.class);
//
//            Marshaller marshallerObj = contextObj.createMarshaller();
//            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshallerObj.marshal(starSchema, new FileOutputStream(fName));
//        }
//        else
//            return false;
//        return true;
//    }


    public String addNameToFile(String name) throws IOException {
        System.out.println("here");
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String fName = currentDirectory + "/storage/Schema_List.txt";
        File file= new File(fName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return "Error creating Schema_list.";
            }
        }
        FileWriter writer = new FileWriter(file, true);
        writer.write(name+ " ");
//        writer.flush();
        writer.close();
        return "true";
    }

    public static void main(String[] args) throws IOException {
        SchemaCreation sc = new SchemaCreation();
        sc.addNameToFile("Departmental_Store");
        (new DatabaseSetup()).TESTING_GenerateSampleSchema().toString();
        sc.getSchemaList();
    }

    public String[] getSchemaList() throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String fName = currentDirectory + "/storage/Schema_List.txt";
        File file= new File(fName);
        if(!file.exists())
            return null;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        st = br.readLine();
        String[] words = st.split("\\s+");
        System.out.println(Arrays.toString(words));
        return words;
    }

    public void generateAttributeCode(StarSchema starSchema){
        int cnt=1;
        for(Dimension dimension: starSchema.getDimension())
            for(Attribute attribute: dimension.getAttributes()){
                attribute.setCode(cnt++);
            }
    }

    //Three things are being done in writeSchemaOuter function.
    // 1. Adding file name the Schema_list file(which contains names of all files)
    // 2. generation of attribute codes
    // 3. Creating a star schema file.
    public String writeSchemaOuter(StarSchema starSchema) throws IOException, JAXBException {
        String s= addNameToFile(starSchema.getName());
        if(s!="true")
            return s;
        else
        {
            generateAttributeCode(starSchema);
            WriteXmlFile writeXmlFile= new WriteXmlFile(starSchema);
            if(writeXmlFile.writeSchema())
                return "Schema created successfully";
            else
                return "Error occurred in schema creation";
        }
    }

    public String generateName(String s){
        s=s.trim();
        if(s.contains(" "))
            s= s.replaceAll(" ", "_");
        return s;
    }
}
