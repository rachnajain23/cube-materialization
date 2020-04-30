package Processing;

import Pojo.Schema.StarSchema;
import Pojo.Specs.CuboidSpecList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class ReadWriteXmlFile {
//    private StarSchema s;
//    private String schemaName;

//    public ReadWriteXmlFile(StarSchema s) {
//        this.s = s;
//    }
//
//    public ReadWriteXmlFile(String schemaName) {
//        this.schemaName = schemaName;
//    }

    public boolean createSchemaIfNotExist(String schemaName) {
        String currentDirectory = System.getProperty("user.dir");
        String fName = currentDirectory + "/storage/" +schemaName + ".xml";
        File f = new File(fName);
        if(f.exists()) {
            System.out.println("Schema exists. Please use different name.");
            return true;
        }
        else {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating schema. Please try again.");
                return false;
            }
        }
        System.out.println("XML created successfully");
        return true;
    }

    public boolean writeStarSchema(StarSchema s) throws JAXBException, FileNotFoundException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String fName = currentDirectory + "/storage/" + s.getName() + ".xml";
        File f = new File(fName);
        System.out.println(f.exists());
        if(f.exists())
        {
            JAXBContext contextObj = JAXBContext.newInstance(StarSchema.class);

            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(s, new FileOutputStream(fName));
        }
        else
            System.out.println("file not found");
        return true;
    }

    // This reads schema in xml file and creates StarSchema Object
    public StarSchema readStarSchema(String SchemaName) {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        //TODO replace Test1 with schemaName
        String fName = currentDirectory + "/storage/" + SchemaName + ".xml";
        try {
            File file = new File(fName);
            JAXBContext jaxbContext = JAXBContext.newInstance(StarSchema.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StarSchema starSchema = (StarSchema) jaxbUnmarshaller.unmarshal(file);

            System.out.println(starSchema.getName());
            return starSchema;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


}
