package PreProcessing;

import Pojo.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Driver {

    // from ReadWriteXmlFile PSVM
    public static void main4(String[] args) throws JAXBException, FileNotFoundException {
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

//        s.addAggregateFn(AggregateFunc.AVG);
//        s.addAggregateFn((AggregateFunc.MEAN));
        s.addSingleFact(f);
        s.addSingleDimension(d);

        ReadWriteXmlFile w = new ReadWriteXmlFile(s);
        boolean test  = w.writeStarSchema();
    }

    // CuboidCreation PSVM
    public static void main2(String[] args) {
        CuboidCreation c = new CuboidCreation();
        //TODO Take User Input for schema name and pass it here
        StarSchema s = c.readStarSchemaFromXml("store");
//        DatabaseSetup db = new DatabaseSetup();
//        StarSchema s = db.TESTING_GenerateSampleSchema();
        try {
            ArrayList<String> queries = c.generateQueries(s);
            System.out.println(queries);
            boolean b = c.createCuboids(queries, s.getName());
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ExistingManipulation PSVM
    public static void main3(String[] args) {
        StarSchema starSchema= (new CuboidCreation()).readStarSchemaFromXml("store");
        CuboidSpecManipulation obj = new CuboidSpecManipulation("store");
        CuboidSpecList cuboidSpecList= obj.showAvailableSpec("store");
        ExistingManipulation qp= new ExistingManipulation(cuboidSpecList, "store");


        obj.setStarSchema();
        HashMap<Attribute, String> map = obj.getAttributes();
//        HashMap<Attribute, String> map1= map;
        System.out.println(map.toString());
        obj.checkConfigExist(map);
//        qp.slice(map, "store");
    }

    //CuboidSpecManipulation PSVM
    public static void main(String[] args) throws JAXBException, IOException {
        CuboidSpecManipulation cc = new CuboidSpecManipulation("test");
        CuboidCreation creation = new CuboidCreation();
        CuboidSpecList c = cc.readSpecFile();
//        System.out.println(c);
        HashMap<Attribute, String> a = cc.getAttributes();
        HashMap<Attribute, String> test = new HashMap<Attribute, String>();
        for (Map.Entry<Attribute, String> entry : a.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            if (entry.getKey().getCode() == 4 || entry.getKey().getCode() == 2 || entry.getKey().getCode() == 5)
                test.put(entry.getKey(),entry.getValue());
        }
        System.out.println(cc.checkConfigExist(test));
        System.out.println(cc.writeSpecInXml(test, "custom_name"));
        /* WIP*/
//        System.out.println(creation.generateQueryFromAttr(test, cc.schema));

//        for(Spec s: c.getSpeclist())
//            System.out.println("name: " + s.getName() + "\n" + s.getAttribute() + "\n");
    }
}
