package PreProcessing;

import Pojo.Attribute;
import Pojo.CuboidSpecList;
import Pojo.StarSchema;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class ExistingManipulation {
    String schemaName;
    CuboidSpecList cuboidSpecList;

    Comparator<Attribute> attributeComparator
            = Comparator.comparing(Attribute::getCode);

    public ExistingManipulation(CuboidSpecList cuboidSpecList, String schemaName){
        this.schemaName= schemaName;
        this.cuboidSpecList=cuboidSpecList;
    }

    public static void main(String[] args) {
        StarSchema starSchema= (new CuboidCreation()).readFromXml("store");
        CuboidSpecManipulation obj = new CuboidSpecManipulation("store");
        CuboidSpecList cuboidSpecList= obj.showAvailableSpec("store");
        ExistingManipulation qp= new ExistingManipulation(cuboidSpecList, "store");


        obj.setStarSchema();
        HashMap<Attribute, String> map = obj.getAttributes();
//        HashMap<Attribute, String> map1= map;
        System.out.println(map.toString());
        obj.checkConfigExist(map, "store");
//        qp.slice(map, "store");
    }


    public String getSampleQuery(OLAPOperation olapOperation){
        if(olapOperation.equals(OLAPOperation.ROLL_UP))
            return("ROLLUP <list of attributes> <list of aggregate functions with associated facts>\n" +
                    "    FROM StarSchemaName");
        else if(olapOperation.equals(OLAPOperation.SLICE))
            return ("SLICE <list of attributes>\n" +
                    "    FROM StarSchemaName\n" +
                    "    WHERE <condition on one attribute>");
        else if(olapOperation.equals(OLAPOperation.DICE))
            return(" DICE <list of attributes>\n" +
                    "    FROM StarSchemaName\n" +
                    "    WHERE <condition on multiple attributes>");
        else return "Invalid Selection";
    }

    public void slice(HashMap<Attribute,String> hashmap, String schemaName) {
        Map<Attribute, String> map = new TreeMap<Attribute, String>(attributeComparator);
        map.putAll(hashmap);
        String tableName="";
        for (Map.Entry<Attribute, String> entry : map.entrySet()) {
            tableName+=entry.getKey().getCode()+"_";
        }
        tableName= tableName.substring(0,tableName.length()-1);
        System.out.println(tableName);
        if(doesTableExist(tableName)){
            Connection con = JdbcConnection.getConnection(schemaName);
            //return table elements;
        }
    }

    public boolean doesTableExist(String tableName){
        for (String name: cuboidSpecList.getTables())
            if (tableName==name)
                return true;
        return false;
    }

}
