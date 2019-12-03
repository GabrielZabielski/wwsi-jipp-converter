package statistics;

import java.io.IOException;
import java.util.Properties;

public class StatisticsController {
    private Statistics repo;

    public Statistics getRepo(){
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String dbType = properties.getProperty("dbType");
        if (dbType.equals("DynamoDB")){
            repo = new StatisticsRpoDynamoDB();
        }
        else if (dbType.equals("MariaDB")){
            repo = new StatisticsRepoMariaDB();
        }
        else {
            System.out.print("dupa");
        }
        return repo;
    }
}
