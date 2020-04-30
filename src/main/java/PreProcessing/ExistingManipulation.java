package PreProcessing;

import Pojo.*;
import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.*;

public class ExistingManipulation {
    private String schemaName;
    private CuboidSpecList cuboidSpecList;
    private static Connection connection;
    private StarSchema starSchema;
    private Comparator<Attribute> attributeComparator= Comparator.comparing(Attribute::getCode);

    public ExistingManipulation(CuboidSpecList cuboidSpecList, String schemaName) {
        this.schemaName = schemaName;
        this.cuboidSpecList = cuboidSpecList;
        connection=JdbcConnection.getConnection(schemaName);
        starSchema= (new CuboidCreation()).readStarSchemaFromXml(schemaName);
    }


    public String getSampleQuery(OLAPOperation olapOperation) {
        if (olapOperation.equals(OLAPOperation.ROLL_UP))
            return ("ROLLUP <list of attributes> <list of aggregate functions with associated facts>");

        else if (olapOperation.equals(OLAPOperation.SLICE))
            return ("SLICE <list of attributes>\n" +
                    "WHERE <condition on one attribute>;\n" +
                    "Example of condition-> dimensionName_attributeName > 5 ; eg customer_name='Gauri' ");

        else if (olapOperation.equals(OLAPOperation.DICE))
            return (" DICE <list of attributes>\n" +
                    "WHERE <condition on multiple attributes>;\n"+
                    "Example of condition-> dimensionName_attributeName > 5 ; eg customer_name='Gauri' ");

        else return "Invalid Selection";
    }

    public boolean doesTableExist(String tableName) {
        for (String name : cuboidSpecList.getTables())
            if (tableName.equals(name))
                return true;
        return false;
    }

    public ResultSet executeQuery(String query, String schemaName) throws SQLException {
//        Connection connection = JdbcConnection.getConnection(schemaName);
        Statement statement = connection.createStatement();
        System.out.println(query);
        return statement.executeQuery(query);
    }

    public List<String[]> getResult(ResultSet resultset) throws SQLException {
        System.out.println("generating result");
        ResultSetMetaData metaData = resultset.getMetaData();
        int cNo = metaData.getColumnCount();
//        System.out.println(cNo);
        String[] row = new String[cNo];
        List<String[]> result = new ArrayList<>();

        //to add column names as the first row.
        for (int i = 0; i < cNo; ++i)
            row[i] = metaData.getColumnName(i+1);
        result.add(row);
//        System.out.println(Arrays.toString(row));
        while (resultset.next()) {
            row = new String[cNo];
            for (int i = 0; i < cNo; i++) {
                Object obj = resultset.getObject(i + 1);
                row[i] = (obj == null) ? null : obj.toString();
            }
            result.add(row);
        }

        //print
        for(String[] x: result )
            System.out.println(Arrays.toString(x));
        return result;
    }


    // Fuction is taking Hashmap<Attribute, String> of only those attributes on which rollup has to be done. Return type is List<String[]>
    public List<String[]> rollup(HashMap<Attribute, String> hashmap) {
        Map<Attribute, String> map = new TreeMap<Attribute, String>(attributeComparator);
        map.putAll(hashmap);
        String tableName = "";
        String attributes="";
        String sql="";
        for (Map.Entry<Attribute, String> entry : map.entrySet()) {
            tableName += entry.getKey().getCode() + "_";
            attributes+= " "+ entry.getValue()+"_"+ entry.getKey().getName()+ ",";
        }
        attributes= attributes.substring(0, attributes.length()-1);
        tableName = tableName.substring(0, tableName.length() - 1);
        System.out.println(tableName);
        System.out.println(doesTableExist(tableName));
        if (doesTableExist(tableName))
        {
            sql = "SELECT * FROM " + tableName + ";";
//            String sql= "SELECT * from base";
            //return table elements;
        }
        else
        {
            String facts = "";
            for (Fact f : starSchema.getFact()) {
                ArrayList<AggregateFunc> agf_list = f.getAggregateFuncs();
                for (AggregateFunc fn : agf_list)
                    //SUM(sellingPrice) as SUM_sellingPrice,
                    facts += ", " + fn + "(" + f.getName() + ") as " + fn + "_" + f.getName();
            }
            sql = "SELECT " + attributes + facts +" FROM base GROUP BY " + attributes + ";";
        }
        try {
            ResultSet resultSet = executeQuery(sql, schemaName);
            return getResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Fuction is taking Hashmap<Attribute, String> of only those attributes on which slice/dice has to be done. Return type is List<String[]>
    //attribute inside the condition has to be written as dimensionName_attributeName.
    public List<String[]> sliceOrDice(HashMap<Attribute, String> hashmap, String condition) {
        Map<Attribute, String> map = new TreeMap<Attribute, String>(attributeComparator);
        map.putAll(hashmap);
        String tableName = "";
        String attributes="";
        String facts="";
        for (Map.Entry<Attribute, String> entry : map.entrySet()) {
            tableName += entry.getKey().getCode() + "_";
            attributes+= " "+ entry.getValue()+"_"+ entry.getKey().getName()+ ",";
        }
        attributes= attributes.substring(0, attributes.length()-1);
        tableName = tableName.substring(0, tableName.length() - 1);
        System.out.println(tableName);
        System.out.println(doesTableExist(tableName));
        String sql="";

        if (doesTableExist(tableName)) {
            for (Fact f : starSchema.getFact()){
                ArrayList<AggregateFunc> agf_list = f.getAggregateFuncs();
                for (AggregateFunc fn : agf_list)
//                SUM(SUM_sellingPrice) as SUM_sellingPrice
                    facts += ", "+ fn + "(" + fn+ "_"+ f.getName() + ") as " + fn+ "_"+ f.getName();
            }
            sql = "SELECT " + attributes + facts + " FROM " + tableName + " WHERE " + condition + " GROUP BY" + attributes + ";";
            sql = sql.substring(0, sql.length() - 1) + ";";
//            String sql= "SELECT * from base";
        }
        else {
            for (Fact f : starSchema.getFact()){
                ArrayList<AggregateFunc> agf_list = f.getAggregateFuncs();
                for (AggregateFunc fn : agf_list)
//                    SUM(sellingPrice) as SUM_sellingPrice,
                    facts += ", " + fn + "(" + f.getName() + ") as " + fn + "_" + f.getName();
                sql= "SELECT "+ attributes+ facts+ " FROM base WHERE "+ condition+ " GROUP BY "+ attributes+ ";";
            }
        }
        try {
            ResultSet resultSet = executeQuery(sql,schemaName);
            return getResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return table elements;
        return null;
    }


    public static void main(String[] args) {
        StarSchema starSchema = (new CuboidCreation()).readStarSchemaFromXml("store");
        CuboidSpecManipulation obj = new CuboidSpecManipulation("store");
        CuboidSpecList cuboidSpecList = obj.showAvailableSpec("store");
        ExistingManipulation qp = new ExistingManipulation(cuboidSpecList, "store");
        HashMap<Attribute, String> map = new HashMap<>();
        map.put(starSchema.getDimension().get(0).getAttributes().get(0), "customer");
//        map.put(starSchema.getDimension().get(0).getAttributes().get(1), "name");
//        map.put(starSchema.getDimension().get(1).getAttributes().get(1), "item");
        map.put(starSchema.getDimension().get(1).getAttributes().get(2), "item");
        map.put(starSchema.getDimension().get(0).getAttributes().get(3), "customer");
        System.out.println(map.toString());
//        obj.checkConfigExist(map, "store");
        qp.rollup(map);
        qp.sliceOrDice(map, "customer_customer_id>2");

    }
}

/*
    Query for doing attribute wise cuboid generation for specification with attribute 1_2_6_7 from store_spec. Database has to be setup for this.

        create table 1_2_6_7 as (
        SELECT  customer_customer_id, customer_name, item_category, item_subcategory, SUM(sellingPrice) as SUM_sellingPrice, COUNT(sellingPrice) as COUNT_sellingPrice, SUM(profit) as SUM_profit, AVG(profit) as AVG_profit
        from base
        group by customer_customer_id, customer_name,  item_category, item_subcategory);

        create table 1_2 as (
        SELECT customer_name, customer_customer_id, SUM(sellingPrice) as SUM_sellingPrice, COUNT(sellingPrice) as COUNT_sellingPrice, SUM(profit) as SUM_profit, AVG(profit) as AVG_profit
        from base
        group by customer_name, customer_customer_id);

        create table 1_6 as (
        SELECT  customer_customer_id, item_category, SUM(sellingPrice) as SUM_sellingPrice, COUNT(sellingPrice) as COUNT_sellingPrice, SUM(profit) as SUM_profit, AVG(profit) as AVG_profit
        from base
        group by customer_customer_id,  item_category);

        create table 1_7 as (
        SELECT  customer_customer_id, item_subcategory, SUM(sellingPrice) as SUM_sellingPrice, COUNT(sellingPrice) as COUNT_sellingPrice, SUM(profit) as SUM_profit, AVG(profit) as AVG_profit
        from base
        group by customer_customer_id, item_subcategory);

        create table 2_6 as (
        SELECT customer_name,  item_category, SUM(sellingPrice) as SUM_sellingPrice, COUNT(sellingPrice) as COUNT_sellingPrice, SUM(profit) as SUM_profit, AVG(profit) as AVG_profit
        from base
        group by customer_name,  item_category);

        create table 2_7 as (
        SELECT  customer_name, item_subcategory, SUM(sellingPrice) as SUM_sellingPrice, COUNT(sellingPrice) as COUNT_sellingPrice, SUM(profit) as SUM_profit, AVG(profit) as AVG_profit
        from base
        group by customer_name,item_subcategory);

        create table 6_7 as (
        SELECT item_category, item_subcategory, SUM(sellingPrice) as SUM_sellingPrice, COUNT(sellingPrice) as COUNT_sellingPrice, SUM(profit) as SUM_profit, AVG(profit) as AVG_profit
        from base
        group by item_category, item_subcategory);


        create table 1_2_6 as (
        SELECT  customer_customer_id, customer_name, item_category, SUM(sellingPrice) as SUM_sellingPrice, COUNT(sellingPrice) as COUNT_sellingPrice, SUM(profit) as SUM_profit, AVG(profit) as AVG_profit
        from base
        group by customer_customer_id, customer_name,  item_category);

        create table 1_2_7 as (
        SELECT  customer_customer_id, customer_name, item_subcategory, SUM(sellingPrice) as SUM_sellingPrice, COUNT(sellingPrice) as COUNT_sellingPrice, SUM(profit) as SUM_profit, AVG(profit) as AVG_profit
        from base
        group by customer_customer_id, customer_name, item_subcategory);

        create table 1_6_7 as (
        SELECT  customer_customer_id, item_category, item_subcategory,  SUM(sellingPrice) as SUM_sellingPrice, COUNT(sellingPrice) as COUNT_sellingPrice, SUM(profit) as SUM_profit, AVG(profit) as AVG_profit
        from base
        group by customer_customer_id, item_category, item_subcategory);

        create table 2_6_7 as (
        SELECT  customer_name, item_category, item_subcategory, SUM(sellingPrice) as SUM_sellingPrice, COUNT(sellingPrice) as COUTN_sellingPrice, SUM(profit) as SUM_profit, AVG(profit) as AVG_profit
        from base
        group by customer_name,  item_category, item_subcategory);

        Sample query for generating result when table is not already present
        SELECT customer_customer_id, item_category, item_subcategory,SUM(SUM_sellingPrice) as SUM_sellingPrice  ,COUNT(COUNT_sellingPrice) as COUNT_sellingPrice  ,
        SUM(SUM_profit) as SUM_profit  ,AVG(AVG_profit) as AVG_profit  FROM 1_6_7 WHERE customer_customer_id>2
        GROUP BY customer_customer_id, item_category, item_subcategory ;
*/