package Processing;

import Pojo.Enums.AggregateFunc;
import Pojo.Enums.Type;
import Pojo.Schema.*;
import Pojo.Specs.CuboidSpecList;
import Pojo.Specs.Spec;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DatabaseSetup {

    //variables
    Statement statement= null;
    Connection connection= null;

    public boolean appendData(StarSchema starSchema, String filepath) {
        try {
            connection = DBConnection.getConnection(starSchema.getName());
            statement = connection.createStatement();
            for (Dimension d : starSchema.getDimension())
                populateTables(filepath, d.getName());
            populateTables(filepath, "facts");
            statement.executeUpdate("DROP TABLE base");
            createBaseCuboid(starSchema);
            boolean reg = regenerateCuboids(starSchema);
            DBConnection.endConnection(connection);
            return reg;
        } catch (IOException io) {
            io.printStackTrace();
            return false;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addData(StarSchema starSchema, String filepath) {
        try {
            connection = DBConnection.getConnwithoutDB();
            statement = connection.createStatement();
            createDatabase(starSchema);
            insertIntoDB(starSchema, filepath);
            createBaseCuboid(starSchema);
            DBConnection.endConnection(connection);
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private void createDatabase(StarSchema starSchema){
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

    private void insertIntoDB(StarSchema starSchema,String filePath){
        try{
            String sqlFacts= "CREATE TABLE facts(";
            for(Dimension dimension: starSchema.getDimension()){
                sqlFacts+= dimension.getName() + "_id VARCHAR(100)"+ ",";
                List<Attribute> attributeList= dimension.getAttributes();
                String sql= "CREATE TABLE "+ dimension.getName() + "("+ /*dimension.getName()+"_id VARCHAR(20)," +*/ attributeList.get(0).getName() +" VARCHAR(100)";
                for(int i=1; i< attributeList.size(); ++i)
                    sql = sql + "," + attributeList.get(i).getName() + " VARCHAR(100)";
                sql+=", PRIMARY KEY("+ attributeList.get(0).getName() + "));";
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

    private void populateTables(String filepath,String tableName) throws IOException {
        System.out.println(filepath);
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filepath));
        System.out.println(workbook.toString());
        XSSFSheet sheet = workbook.getSheet(tableName);
        System.out.println(sheet.toString());
        int r = sheet.getPhysicalNumberOfRows();
        int c = sheet.getRow(0).getPhysicalNumberOfCells();

        System.out.println(r + " " + c);

        for (int i = 1; i < r; ++i) {
            Cell cell = sheet.getRow(i).getCell(0);
            String sql = "INSERT INTO " + tableName + " VALUES(";
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
                System.out.println("breaking here?");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("or here?");
                e.printStackTrace();
            }
        }
    }

    private void createBaseCuboid(StarSchema starSchema){
        String sqlAttributes="";
        String sql="";
        int flag=0;
        for (Dimension dimension: starSchema.getDimension()){
            String name= dimension.getName();
            String primaryKey= dimension.getAttributes().get(0).getName();
            sql+="JOIN "+name+" ON facts."+name+"_id"+"="+name+"."+primaryKey+" ";
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

    private boolean regenerateCuboids(StarSchema starSchema) {
        try {
            boolean ans = true;
            ReadWriteXmlFile rw= new ReadWriteXmlFile();
            CuboidSpecList specList = (rw.readSpec(starSchema.getName()));
            for (String table : specList.getTables()) {
                System.out.println("Drop table" + table);
                statement.executeUpdate("DROP TABLE " + table );
            }
            specList.setTables(new ArrayList<>());
            rw.writeCuboidSpec(specList, starSchema.getName());
            for(Spec s : specList.getSpeclist()) {
                List<Integer> codes = s.getAttribute().stream().map(o -> o.getCode()).collect(toList());
                boolean cuboidGen;
                HashMap<Attribute, String> map = new HashMap<>();
                for(Dimension d: starSchema.getDimension()) {
                    for (Attribute arr : d.getAttributes()) {
                        if (codes.contains(arr.getCode())){
                            map.put(arr, d.getName());
                        }
                    }
                }
                System.out.println(map);
                CuboidSpecManipulation cc = new CuboidSpecManipulation(starSchema.getName());
                cuboidGen = cc.generateCuboidsFromAttr(map);
                ans = ans && cuboidGen;
            }
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws SQLException {
        DatabaseSetup databaseSetup = new DatabaseSetup();
//        databaseSetup.createNewDB("");

        //StarSchema starSchema= databaseSetup.TESTING_GenerateSampleSchema();
        //databaseSetup.control(starSchema, "/home/gauri/Desktop/IIITB/DM/Project/cubematerialization/store.xlsx");
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
