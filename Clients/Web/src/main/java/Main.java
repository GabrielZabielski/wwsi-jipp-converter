import ratpack.jackson.Jackson;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import statistics.Statistics;
import statistics.StatisticsController;

public class Main {
    public static void main(String... args) throws Exception {
        init();

        RatpackServer.start(server -> server
                .serverConfig(config -> config.baseDir(BaseDir.find()))
                .handlers(chain -> chain
                .get("name", ctx -> ctx.render(Jackson.json(statisticsRepo.getItems())))));
    }

    private static Statistics statisticsRepo;

    private static void init(){
        StatisticsController statsController = new StatisticsController();
        statisticsRepo = statsController.getRepo();
        if (statisticsRepo.getClass().getName().endsWith("Mock")){
            System.out.println(statsController.throwExceptionFromController());
        }
    }
}
