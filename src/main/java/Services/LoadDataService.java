package Services;

import Pojo.Schema.StarSchema;
import Processing.DatabaseSetup;

public class LoadDataService {

    private DatabaseSetup databaseSetup= new DatabaseSetup();

    //Function to add data for the first time
    public void addDataService(StarSchema starSchema, String filepath){
        databaseSetup.addData(starSchema, filepath);
    }

    //function to append data to the already existing one
    public void appendDataService(StarSchema starSchema, String filepath){
        databaseSetup.appendData(starSchema, filepath);
    }

}
