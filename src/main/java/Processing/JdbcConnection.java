package Processing;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnection {


    /*
    TODO Put these 4 fields in a config file and fetch from there
    TODO Also needs to replace DB name with schema name when a user selects some schema to manipulate
    * */
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static  String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASS = "root";


    public static Connection getConnection(String name)
    {
        DB_URL+=name;
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
}
