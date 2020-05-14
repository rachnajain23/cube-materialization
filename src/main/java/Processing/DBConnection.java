package Processing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {


    /*
    TODO Put these 4 fields in a config file and fetch from there
    * */
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static  String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASS = "1234";


    public static Connection getConnection(String name)
    {
        DB_URL = "jdbc:mysql://localhost/";
        DB_URL+=name;
        System.out.println(DB_URL);
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnwithoutDB() {
        DB_URL = "jdbc:mysql://localhost/";
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return null;
    }
    public static void endConnection(Connection connection) {
        try{
            if(connection!=null)
                connection.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }
}
