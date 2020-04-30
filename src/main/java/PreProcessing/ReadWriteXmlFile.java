package PreProcessing;

import Pojo.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.ArrayList;

public class ReadWriteXmlFile {
    private StarSchema s;
    private String schemaName;

    public ReadWriteXmlFile(StarSchema s) {
        this.s = s;
    }

    public ReadWriteXmlFile(String schemaName) {
        this.schemaName = schemaName;
    }

    public String schemaExist() {
        String currentDirectory = System.getProperty("user.dir");
        String fName = currentDirectory + "/storage/" +schemaName + ".xml";
        File f = new File(fName);
        if(f.exists())
            return "Schema exists. Please use different name.";
        else {
            try {
                f.createNewFile();
            } catch (IOException e) {
                return "Error creating schema. Please try again.";
            }
        }
        return "XML created successfully";
    }

    public boolean writeStarSchema() throws JAXBException, FileNotFoundException {
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


}
