package CreateSchema;

import Pojo.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.*;
import java.util.ArrayList;

public class WriteXmlFile {
    private StarSchema s;
    private String schemaName;

    public WriteXmlFile(StarSchema s) {
        this.s = s;
    }

    public WriteXmlFile(String schemaName) {
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

    public boolean writeSchema() throws JAXBException, FileNotFoundException {
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

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        StarSchema s = new StarSchema();
        s.setName("test1");
        Dimension d = new Dimension();
        d.setName("Product");
        ArrayList<Attribute> attr_list = new ArrayList<Attribute>();
        Attribute a = new Attribute();
        a.setName("Prod_id");
        Attribute a1 = new Attribute();
        a1.setName("sub_category");
        attr_list.add(a);
        attr_list.add(a1);
        d.setAttributes(attr_list);
        Fact f = new Fact();
        f.setName("Sales");
        f.setType(Type.NUMERIC);

        s.addAggregateFn(AggregateFunc.AVG);
        s.addAggregateFn((AggregateFunc.MEAN));
        s.addSingleFact(f);
        s.addSingleDimension(d);

        WriteXmlFile w = new WriteXmlFile(s);
        boolean test  = w.writeSchema();
    }
}
