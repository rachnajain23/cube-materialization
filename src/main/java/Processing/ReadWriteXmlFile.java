package Processing;

import Pojo.Schema.StarSchema;
import Pojo.Specs.CuboidSpecList;
import Pojo.Specs.Spec;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class ReadWriteXmlFile {
    /* Starschema read/write */
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
    public StarSchema readStarSchema(String schemaName) {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String fName = currentDirectory + "/storage/" + schemaName + ".xml";
        try {
            File file = new File(fName);
            JAXBContext jaxbContext = JAXBContext.newInstance(StarSchema.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StarSchema starSchema = (StarSchema) jaxbUnmarshaller.unmarshal(file);

            return starSchema;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* CuboidSpecList read/write */

    public boolean createSpecIfNotExist(String schemaName) {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String fName = currentDirectory + "/storage/" + schemaName + "_spec.xml";
        File f = new File(fName);
        try {
            if(!f.exists()) {
                f.createNewFile();
                return true;
            }
        } catch (IOException e) {
            System.out.println("Error creating cuboidSpec. Please try again.");
            return false;
        }
        return false;
    }

    public CuboidSpecList readSpec(String schemaName) {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String fName = currentDirectory + "/storage/" + schemaName + "_spec.xml";
        File f = new File(fName);
        try {
            if (f.exists()) {
                JAXBContext jaxbContext = JAXBContext.newInstance(CuboidSpecList.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                CuboidSpecList specList = (CuboidSpecList) jaxbUnmarshaller.unmarshal(f);
                return specList;
            }
            else return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean writeCuboidSpec(CuboidSpecList specList, String schemaName) throws JAXBException, FileNotFoundException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        String fName = currentDirectory + "/storage/" + schemaName + "_spec.xml";
        File f = new File(fName);
        if(f.exists())
        {
            JAXBContext contextObj = JAXBContext.newInstance(CuboidSpecList.class);

            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(specList, new FileOutputStream(fName));
            return true;
        }
        else
            System.out.println("file not found");
        return false;
    }

    public void appendSpec(Spec s, String name) {
        try {
            CuboidSpecList cuboidSpecList = readSpec(name);
            cuboidSpecList.addSpec(s);
            writeCuboidSpec(cuboidSpecList, name);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
