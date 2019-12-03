package statistics;

import propertiesInjector.Property;

import javax.inject.Inject;

public class StatisticsController {
    @Inject
    @Property("dbType")
    private String dbType;
    private Statistics repo;

    public Statistics getRepo(){
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
