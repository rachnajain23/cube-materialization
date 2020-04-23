package PreProcessing;

import Pojo.CuboidSpecList;
import Pojo.Spec;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CuboidSpecCreation {

    public static  void main(String[] args) throws JAXBException, FileNotFoundException {
        CuboidSpecList cc = new CuboidSpecList();
        CuboidSpecList c = showAvailableSpec("hello");
        for(Spec s: c.getSpeclist())
            System.out.println("name: " + s.getName() + "\n" + s.getAttribute() + "\n");
    }

    static CuboidSpecList showAvailableSpec(String schemaName) {

        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        //TODO replace "here" with schemaName
        String fName = currentDirectory + "/storage/" + "here" + "_spec.xml";
        try {
            File file = new File(fName);
            JAXBContext jaxbContext = JAXBContext.newInstance(CuboidSpecList.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            CuboidSpecList specList = (CuboidSpecList) jaxbUnmarshaller.unmarshal(file);
            return specList;
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static boolean writeSpecInXml(CuboidSpecList cuboidSpecList) throws JAXBException, IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
        //TODO replace "here" with schemaName
        String fName = currentDirectory + "/storage/" + "here" + "_spec.xml";
        try {
            File f = new File(fName);
            System.out.println(f.exists());
            JAXBContext jaxbContext = JAXBContext.newInstance(CuboidSpecList.class);
            Marshaller marshallerObj = jaxbContext.createMarshaller();
            if(f.exists())
            {

                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                CuboidSpecList oldlist = (CuboidSpecList) jaxbUnmarshaller.unmarshal(f);
                for (Spec s : cuboidSpecList.getSpeclist())
                    oldlist.addSpec(s);
                marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshallerObj.marshal(oldlist, new FileOutputStream(fName));
            }
            else {
                f.createNewFile();
                marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshallerObj.marshal(cuboidSpecList, new FileOutputStream(fName));
            }
        } catch (JAXBException jxb) {
            jxb.printStackTrace();
            return false;
        } catch (IOException io) {
            io.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
