package Processing;

import Pojo.Enums.AggregateFunc;
import Pojo.Schema.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CuboidCreation {

    ArrayList<String> generateQueries(StarSchema s) {
        ArrayList<Dimension> dim_list = s.getDimension();
        ArrayList<Fact> fact_list = s.getFact();
        StringBuilder cols, select, tables, condition, grpBy, cuboidName;
        ArrayList<String> queries = new ArrayList<String>();
        int dim_size = dim_list.size();
        int two_n =(int)Math.pow(2, dim_size);
        for (int i = 1 ; i < two_n; i++)
        {
            cuboidName = new StringBuilder("CREATE TABLE cuboid_");
            cols = new StringBuilder();
            select = new StringBuilder(" SELECT ");
            tables = new StringBuilder(" FROM ");
            condition = new StringBuilder(" WHERE ");
            grpBy = new StringBuilder(" GROUP BY ");
            for (int j = 0; j < dim_size; j++) {

                if(((i >> j) & 1) == 1)
                {
                    Dimension d = dim_list.get(j);
                    String attr_name = d.getAttributes().get(0).getName();
                    String dim_name = d.getName();
//                    grpBy.append(dim_name + "." + attr_name + ",");
                    grpBy.append(dim_name + "." + dim_name + "_id,");

                    tables.append(dim_name + ",");
                    cuboidName.append(dim_name + "_");
                    condition.append(dim_name + "." + dim_name + "_id" + "=" + "facts." + dim_name + "_id" + " AND ");
//                    condition.append(dim_name + "." + attr_name + "=" + "facts." + attr_name + " AND ");

                    for (Attribute a : d.getAttributes())
                        cols.append(dim_name + "." + a.getName() + ",");
                }
            }
            cuboidName.deleteCharAt(cuboidName.length()- 1);
            condition.delete(condition.length()-5,condition.length()-1);
            grpBy.deleteCharAt(grpBy.length()- 1);
            select.append(cols);
            for (Fact f : fact_list){
                ArrayList<AggregateFunc> agf_list = f.getAggregateFuncs();
                for (AggregateFunc fn : agf_list)
                    select.append(fn.toString() + "(" + f.getName() + ") as " + fn.toString() + "_" + f.getName() +" ,");
            }
            select.deleteCharAt(select.length()- 1);
            tables.append("facts");
            select.append(tables);
            select.append(condition);
            select.append(grpBy);
            cuboidName.append(select);
            queries.add(cuboidName.toString());
        }

        // add apex cuboid query here
        queries.add(CuboidCreation.apexCuboidQuery(s));
        return queries;
    }

    ArrayList<ArrayList<String>> generateQueryFromAttr(HashMap<Attribute, String> map, StarSchema schema) {
        ArrayList<ArrayList<String>> queries = new ArrayList<ArrayList<String>>();
        queries.add(new ArrayList<String>());
        queries.add(new ArrayList<String>());
        int size = map.size();
        int two_n =(int)Math.pow(2, size);
        StringBuilder cuboidName = new StringBuilder();
        StringBuilder selectCols = new StringBuilder();
        String query = new String();
        String apexName = new String();
        ArrayList<Attribute> attr_list = new ArrayList<Attribute>(map.keySet());
        for (int i = 1 ; i < two_n; i++) {
            cuboidName = new StringBuilder("c_");
            selectCols = new StringBuilder();
            query = new String();
            HashMap<String, ArrayList<Integer>> dimensionMap = new HashMap<String, ArrayList<Integer>>();
            for (int j = 0; j < size; j++) {
                if (((i >> j) & 1) == 1) {
                    Attribute a = attr_list.get(j);
                    System.out.println("+++" + a);
                    if (dimensionMap.containsKey(map.get(a))) {
                        ArrayList<Integer> list = dimensionMap.get(map.get(a));
                        list.add(a.getCode());
                    } else {
                        dimensionMap.put(map.get(a), new ArrayList<Integer>(a.getCode()));
                    }
                    selectCols.append(map.get(a) + "_" + a.getName() + ",");
                }
            }
            for (Fact f : schema.getFact()){
                ArrayList<AggregateFunc> agf_list = f.getAggregateFuncs();
                for (AggregateFunc fn : agf_list)
                    selectCols.append(fn.toString() + "(" + f.getName() + ") as " + fn.toString() + "_" + f.getName() +" ,");
            }
            cuboidName.deleteCharAt(cuboidName.length() - 1);
            selectCols.deleteCharAt(selectCols.length() - 1);
            if (i == two_n-1)
                apexName = cuboidName.toString() + "_apex";
                query = "CREATE TABLE " +  " SELECT " +
                    selectCols + " FROM base group by " + selectCols;
            queries.get(0).add(query); // query list
            queries.get(1).add(cuboidName.toString()); // cuboid Name list
        }
        query = "CREATE TABLE " + apexName + "SELECT ";
        for (Fact f : schema.getFact()){
            ArrayList<AggregateFunc> agf_list = f.getAggregateFuncs();
            for (AggregateFunc fn : agf_list)
                query += fn.toString() + "(" + f.getName() + ") as " + fn.toString() + "_" + f.getName() +" ,";
        }
        query = query.substring(0, query.length() - 1);
        query += " FROM base";
        queries.get(0).add(query);
        return queries;
    }

    // This takes queries generated and creates cuboids in DB except apex cuboid
    boolean createCuboids(ArrayList<String> queries, String schemaName) throws SQLException {
        Connection con = JdbcConnection.getConnection(schemaName);
        Statement stmt;
        try {
            if(con != null)
            {
                for (String q: queries) {
                    stmt = con.createStatement();
                    System.out.println(q);
                    stmt.executeUpdate(q);
                }
                con.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error creating cuboids");
        }
        return false;
    }

    static String apexCuboidQuery(StarSchema s) {
        ArrayList<Fact> f_list = s.getFact();
        StringBuilder query = new StringBuilder("CREATE TABLE apex SELECT ");
        for(Fact f : f_list) {
            for(AggregateFunc fn : f.getAggregateFuncs())
                query.append(fn.toString() + "(" + f.getName() + ") as " + fn.toString() + "_" + f.getName() +" ,");

        }
        query.deleteCharAt(query.length()- 1);
        query.append(" from facts");
        return query.toString();
    }

    public String generateDimensionalLattice(StarSchema starSchema){
        CuboidCreation cc = new CuboidCreation();
        ArrayList<String> queries =cc.generateQueries(starSchema);
        try {
            boolean b = cc.createCuboids(queries, starSchema.getName());
        } catch (SQLException e) {
            return "Error in creating dimensional cuboid";
        }
        return "true";
    }

}
