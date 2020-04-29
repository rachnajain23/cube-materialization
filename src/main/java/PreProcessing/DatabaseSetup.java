package PreProcessing;

import Pojo.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Attr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSetup {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database credentials
    private static final String USER = "root";
    static final String PASS = "root";

    //variables
    Statement statement= null;
    Connection connection= null;
    public void control(StarSchema starSchema, String filePath){
        DatabaseSetup dbSetup = new DatabaseSetup();
        dbSetup.establishConnection();
        dbSetup.createDatabase(starSchema);
        dbSetup.insertIntoDB(starSchema,filePath);
        dbSetup.createBaseCuboid(starSchema);
        dbSetup.endConnection();
    }


    public void establishConnection(){
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            statement = connection.createStatement();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }


    public void endConnection(){
        try{
            if(statement!=null)
                statement.close();
        }catch(SQLException se){
            se.printStackTrace();
        }// nothing we can do
        try{
            if(connection!=null)
                connection.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }


    public void createDatabase(StarSchema starSchema){
        try{
            String sql= "CREATE DATABASE "+ starSchema.getName()+ ";";
                 System.out.println(sql);//----------------------------------
            statement.executeUpdate(sql);
            sql="USE "+ starSchema.getName()+";";
                System.out.println(sql);//-----------------------------------
            statement.executeUpdate(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
    }


    public void insertIntoDB(StarSchema starSchema,String filePath){
        try{
            String sqlFacts= "CREATE TABLE facts(";
            for(Dimension dimension: starSchema.getDimension()){
                sqlFacts+= dimension.getName() + "_id VARCHAR(100)"+ ",";
                List<Attribute> attributeList= dimension.getAttributes();
                String sql= "CREATE TABLE "+ dimension.getName() + "("+ /*dimension.getName()+"_id VARCHAR(20)," +*/ attributeList.get(0).getName() +" VARCHAR(100)";
                for(int i=1; i< attributeList.size(); ++i)
                    sql = sql + "," + attributeList.get(i).getName() + " VARCHAR(100)";
                sql+=", PRIMARY KEY("+dimension.getName() + "_id));";
                System.out.println(sql);//-----------------------------------
                statement.executeUpdate(sql);
                populateTables(filePath,dimension.getName());
            }
            List<Fact> factList= starSchema.getFact();
            sqlFacts+= factList.get(0).getName()+ " int";
            for(int i=1; i< factList.size();++i)
                sqlFacts+= ","+ factList.get(i).getName()+ " int";
            sqlFacts+= ");";
            System.out.println(sqlFacts);//-----------------------------------
            statement.executeUpdate(sqlFacts);
            populateTables(filePath,"facts");
        }catch (SQLException se) {
            se.printStackTrace();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void populateTables(String filepath,String tableName) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filepath));
        XSSFSheet sheet = workbook.getSheet(tableName);
        int r = sheet.getPhysicalNumberOfRows();
        int c = sheet.getRow(0).getPhysicalNumberOfCells();

        for (int i = 1; i < r; ++i) {
            Cell cell = sheet.getRow(i).getCell(0);
            String sql = "INSERT INTO " + tableName + " VALUES(";
//            sql+="'"+ cell.get
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    double temp=cell.getNumericCellValue();
                    if(temp==(int)temp)
                        sql+=(int)temp;
                    else
                        sql+=temp;
//                    sql += cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_STRING:
                    sql += "'"+ cell.getStringCellValue()+"'";
                    break;
            }
            for (int j = 1; j < c; ++j) {
                cell = sheet.getRow(i).getCell(j);
//                sql+="'"+ cell.getStringCellValue()+"'";
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        double temp=cell.getNumericCellValue();
                        if(temp==(int)temp)
                            sql+= "," + (int)temp;
                        else
                            sql+="," + temp;
//                        sql += "," + cell.getNumericCellValue();
                        break;
                    case Cell.CELL_TYPE_STRING:
                        sql += ",'" + cell.getStringCellValue()+"'";
                        break;
                }
            }
            sql += ");";
            System.out.println(sql);
            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void createBaseCuboid(StarSchema starSchema){
        String sqlAttributes="";
        String sql="";
        int flag=0;
        for (Dimension dimension: starSchema.getDimension()){
            String name= dimension.getName();
            String primaryKey= dimension.getAttributes().get(0).getName();
            sql+="JOIN "+name+" ON facts."+primaryKey+"="+name+"."+primaryKey+" ";
            for (Attribute attribute: dimension.getAttributes()){
                if(flag++==0)
                    sqlAttributes += name+"."+attribute.getName()+" "+name+"_"+attribute.getName()+" ";
                else
                    sqlAttributes+= ","+ name+"."+attribute.getName()+" "+name+"_"+attribute.getName()+" ";
            }
        }
        for (Fact fact: starSchema.getFact())
            sqlAttributes+=",facts."+fact.getName()+" ";
        sql="CREATE TABLE base( SELECT "+sqlAttributes+"FROM facts "+sql+");";
        System.out.println(sql);//-------------------------------------------------------------------------
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        DatabaseSetup databaseSetup = new DatabaseSetup();
        StarSchema starSchema= databaseSetup.TESTING_GenerateSampleSchema();
        databaseSetup.control(starSchema, "/home/gauri/Desktop/IIITB/DM/Project/cubematerialization/store.xlsx");
    }


    public StarSchema TESTING_GenerateSampleSchema(){

        StarSchema starSchema= new StarSchema();

        List<Dimension> dimensionList= starSchema.getDimension();
        Dimension dimension= new Dimension();
        List<Attribute> attributeList= dimension.getAttributes();
        

        dimension.setName("customer");
        Attribute attribute= new Attribute(); attribute.setName("customer_id");attributeList.add(attribute);
        attribute = new Attribute(); attribute.setName("name");attributeList.add(attribute);
        attribute = new Attribute(); attribute.setName("age");attributeList.add(attribute);
        attribute = new Attribute(); attribute.setName("sex");attributeList.add(attribute);
        dimension.setAttributes((ArrayList<Attribute>) attributeList);
        dimensionList.add(dimension);

        dimension= new Dimension();
        dimension.setName("item");
        attributeList = new ArrayList<Attribute>();
        attribute = new Attribute(); attribute.setName("item_id");attributeList.add(attribute);
        attribute = new Attribute(); attribute.setName("category");attributeList.add(attribute);
        attribute = new Attribute(); attribute.setName("subcategory");attributeList.add(attribute);
        dimension.setAttributes((ArrayList<Attribute>) attributeList);
        dimensionList.add(dimension);


        List<Fact> factList= starSchema.getFact();
        Fact fact= new Fact();
        List<AggregateFunc> aggregateFuncList = fact.getAggregateFuncs();

        fact.setName("sellingPrice");
        fact.setType(Type.NUMERIC);
        aggregateFuncList.add(AggregateFunc.SUM);
        aggregateFuncList.add(AggregateFunc.AVG);
        aggregateFuncList.add(AggregateFunc.COUNT);
        fact.setAggregateFuncs((ArrayList<AggregateFunc>) aggregateFuncList);
        factList.add(fact);

        fact= new Fact();
        fact.setName("profit");
        fact.setType(Type.NUMERIC);
        aggregateFuncList= new ArrayList<AggregateFunc>();
        aggregateFuncList.add(AggregateFunc.SUM);
        aggregateFuncList.add(AggregateFunc.AVG);
        fact.setAggregateFuncs((ArrayList<AggregateFunc>) aggregateFuncList);
        factList.add(fact);

        starSchema.setName("store");
        starSchema.setDimension((ArrayList<Dimension>) dimensionList);
        starSchema.setFact((ArrayList<Fact>) factList);

        System.out.println(starSchema.toString());
        return starSchema;
    }

}
