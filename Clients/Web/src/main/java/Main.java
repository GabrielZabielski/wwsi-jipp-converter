import convertersController.ConverterController;
import jdk.nashorn.internal.runtime.regexp.joni.WarnCallback;
import ratpack.jackson.Jackson;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import statistics.Statistics;
import statistics.StatisticsController;

import java.security.CodeSource;
import java.util.logging.Logger;

public class Main {
    public static void main(String... args) throws Exception {
        init();
        ConverterController converterService = new ConverterController();

        RatpackServer.start(server -> server
                .serverConfig(config -> config.baseDir(BaseDir.find()))
                .handlers(chain -> chain.files(f -> f
                        .dir("templates")
                        .indexFiles("index.html"))
                        .get("statistic", ctx -> ctx.render(Jackson.json(statisticsRepo.getItems())))
                        .get("converter", ctx -> ctx.render(Jackson.json(converterService.getMapConverters().keySet())))
                ));
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
