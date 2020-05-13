package Services;

import Pojo.Schema.Attribute;
import Processing.CuboidSpecManipulation;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CuboidSpecService {

    private CuboidSpecManipulation cuboidSpecManipulation;

    public CuboidSpecService(String schemaName){
        cuboidSpecManipulation= new CuboidSpecManipulation(schemaName);
    }

    public LinkedHashMap<Attribute, String> getAttributesService(){
        return cuboidSpecManipulation.getAttributes();
    }

    public String checkConfigExistService(HashMap<Attribute, String> attributes){
        return cuboidSpecManipulation.checkConfigExist(attributes);
    }

    public boolean writeSpecService(HashMap<Attribute, String> attributes, String name){
        return cuboidSpecManipulation.writeSpec(attributes, name);
    }
}
