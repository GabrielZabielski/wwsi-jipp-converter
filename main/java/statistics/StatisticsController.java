package statistics;

import com.amazonaws.AmazonClientException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class StatisticsController {

    public Statistics getRepo() throws IOException, SQLException, AmazonClientException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/config.properties"));

        String dbType = properties.getProperty("dbType");
        Statistics resp = null;
        if (dbType == null || dbType.isEmpty()){
            throw new IOException("Properties file is empty or doesn't exist.");
        }
        else if (dbType.equals("DynamoDB")){
            resp = new StatisticsRpoDynamoDB();
        }
        else if (dbType.equals("MariaDB")){
            resp = new StatisticsRepoMariaDB();
        }
        return resp;
    }

    public Statistics getMock(){
        return new StatisticsRepoMock();
    }
}
