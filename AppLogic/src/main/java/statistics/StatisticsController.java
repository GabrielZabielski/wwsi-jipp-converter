package statistics;

import com.amazonaws.AmazonClientException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StatisticsController {
    private Exception exception;
    private String propertiesPath;

    public StatisticsController(){
        List<String> paths = new ArrayList<>();
        try {
            Files.walk(Paths.get("AppLogic"))
                    .filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().toLowerCase().endsWith(".properties"))
                    .forEach(path -> {
                        if (path.getFileName().toString().equals("config.properties")){
                            paths.add(0, path.toAbsolutePath().toString());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        propertiesPath = paths.get(0);
    }

    public Statistics getRepo() {
        Properties properties = new Properties();
        Statistics resp = null;

        try {
            properties.load(new FileReader(propertiesPath));
            String dbType = properties.getProperty("dbType");

            if (dbType.equals("DynamoDB")) {
                resp = new StatisticsRpoDynamoDB();
            } else if (dbType.equals("MariaDB")) {
                resp = new StatisticsRepoMariaDB();
            }
        } catch (IOException | AmazonClientException | SQLException ex){
            exception = ex;
            return new StatisticsRepoMock();
        }
        return resp;
    }

    public String throwExceptionFromController(){
        return exception.toString();
    }
}
