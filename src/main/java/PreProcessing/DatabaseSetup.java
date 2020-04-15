package PreProcessing;

import Pojo.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Attr;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSetup {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    //variables
    Statement statement= null;
    Connection connection= null;
    public void control(StarSchema starSchema, String filePath){
        DatabaseSetup dbSetup = new DatabaseSetup();
        dbSetup.establishConnection();
        dbSetup.createDatabase(starSchema);
        dbSetup.addDimesionsToDB(starSchema,filePath);
//        dbSetup.addCompleteDataset(starSchema, filePath);
//        dbSetup.endConnection();
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
                 System.out.println(sql);
            statement.executeUpdate(sql);
            sql="USE "+ starSchema.getName()+";";
                System.out.println(sql);
            statement.executeUpdate(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    public void addDimesionsToDB(StarSchema starSchema,String filePath){
        try{
            for(Dimension dimension: starSchema.getDimension()){
                List<Attribute> attributeList= dimension.getAttributes();
                String sql= "CREATE TABLE "+ dimension.getName() + "("+ dimension.getName()+"_id VARCHAR(20)," + attributeList.get(0).getName() +" VARCHAR(100)";
                for(int i=1; i< attributeList.size(); ++i)
                    sql= sql+ ","+ attributeList.get(i).getName() +" VARCHAR(100)";
                sql+=");";
                    System.out.println(sql);
                statement.executeUpdate(sql);
                populateTables(filePath,dimension.getName());
            }

        }catch (SQLException se) {
            se.printStackTrace();
//        }catch (IOException ex) {
//            ex.printStackTrace();
        }
    }

    public void populateTables(String filepath,String sheetName){
//        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
//        XSSFSheet sheet = workbook.getName(sheetName);
//        int r = sheet.getPhysicalNumberOfRows();
//        int c = sheet.getRow(0).getPhysicalNumberOfCells();
//
//        //Storing the dimensions
//        dimensions = new String[c];
//        for (int i = 0; i < c; ++i)
//            dimensions[i] = String.valueOf(sheet.getRow(0).getCell(i));
////        System.out.print(Arrays.toString(dimensions));
//
//        //forming the base cuboid
//        r=r-1;
//        System.out.println(r + " "+ c);
//        baseCuboid = new String[r][c];
//        for (int i=0; i<r; ++i)
//        {
//            System.out.println(i);
//            for(int j=0; j<c;++j){
//                Cell cell = sheet.getRow(i+1).getCell(j);
//                switch (cell.getCellType())
//                {
//                    case Cell.CELL_TYPE_NUMERIC:
//                        baseCuboid[i][j]= cell.getNumericCellValue() + "";
//                        break;
//                    case Cell.CELL_TYPE_STRING:
//                        baseCuboid[i][j]= cell.getStringCellValue();
//                        break;
//                }
//            }
//        }
//        return true;

    }

    public void addCompleteDataset(StarSchema starSchema, String filePath){

    }

    public static void main(String[] args) {
        DatabaseSetup databaseSetup = new DatabaseSetup();
        StarSchema starSchema= databaseSetup.TESTING_GenerateSampleSchema();
        databaseSetup.control(starSchema, "BLAH");
    }

    public StarSchema TESTING_GenerateSampleSchema(){

        StarSchema starSchema= new StarSchema();

        List<Dimension> dimensionList= starSchema.getDimension();
        Dimension dimension= new Dimension();
        List<Attribute> attributeList= dimension.getAttributes();
        

        dimension.setName("customer");
        Attribute attribute= new Attribute(); attribute.setName("name");attributeList.add(attribute);
        attribute = new Attribute(); attribute.setName("age");attributeList.add(attribute);
        attribute = new Attribute(); attribute.setName("sex");attributeList.add(attribute);
        dimension.setAttributes((ArrayList<Attribute>) attributeList);
        dimensionList.add(dimension);

        dimension= new Dimension();
        dimension.setName("item");
        attributeList = new ArrayList<Attribute>();
        attribute = new Attribute(); attribute.setName("category");attributeList.add(attribute);
        attribute = new Attribute(); attribute.setName("subcategory");attributeList.add(attribute);
        dimension.setAttributes((ArrayList<Attribute>) attributeList);
        dimensionList.add(dimension);


        List<Fact> factList= starSchema.getFact();
        Fact fact= new Fact();
        List<AggregateFunc> aggregateFuncList = fact.getAggregateFuncs();

        fact.setName("selling price");
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
