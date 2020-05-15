package Services;

import Pojo.Schema.StarSchema;
import Processing.DatabaseSetup;

public class LoadDataService {

    private DatabaseSetup databaseSetup= new DatabaseSetup();

    //Function to add data for the first time
    public boolean addDataService(StarSchema starSchema, String filepath){

        boolean ans = databaseSetup.addData(starSchema, filepath);
        return ans;
    }

    //function to append data to the already existing one
    public boolean appendDataService(StarSchema starSchema, String filepath){
        boolean ans = databaseSetup.appendData(starSchema, filepath);
        return ans;
    }

}
