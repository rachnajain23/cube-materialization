package Services;

import Pojo.Enums.OLAPOperation;
import Pojo.Schema.Attribute;
import Processing.OLAPQueries;

import java.sql.ResultSet;
import java.util.HashMap;

public class OLAPQueriesService {

    private OLAPQueries olapQueries;

    public OLAPQueriesService(String schemaName){
        olapQueries = new Processing.OLAPQueries(schemaName);
    }

    public String getSampleQueryService(OLAPOperation olapOperation){
        return olapQueries.getSampleQuery(olapOperation);
    }

    // Function is taking Hashmap<Attribute, String> of only those attributes on which rollup has to be done. Return type is List<String[]>
    public ResultSet rollupService(HashMap<Attribute, String> hashmap){
        return olapQueries.rollup(hashmap);
    }


    /*Function is taking Hashmap<Attribute, String> of only those attributes on which slice/dice has to be done. Return type is List<String[]>
    attribute inside the condition has to be written as dimensionName_attributeName.*/
    public ResultSet sliceOrDiceService(HashMap<Attribute, String> hashmap, String condition){
        return olapQueries.sliceOrDice(hashmap,condition);
    }

}
