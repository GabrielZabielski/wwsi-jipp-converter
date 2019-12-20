import com.fasterxml.jackson.databind.ObjectMapper;
import converters.Converter;
import convertersController.ConverterController;
import jdk.nashorn.internal.runtime.regexp.joni.WarnCallback;
import ratpack.jackson.Jackson;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import statistics.Statistics;
import statistics.StatisticsController;
import statistics.StatisticsModel;

import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static ratpack.jackson.Jackson.jsonNode;

public class Main {
    public static void main(String... args) throws Exception {
        init();
        ConverterController converterService = new ConverterController();
        Map<String, Object> converterMap = converterService.getMapConverters();



        RatpackServer.start(server -> server
                .serverConfig(config -> config.baseDir(BaseDir.find()))
                .handlers(chain -> chain.files(f -> f
                        .dir("templates")
                        .indexFiles("index.html"))
                        .get("statistic", ctx -> ctx.render(Jackson.json(statisticsRepo.getItems())))
                        .get("converter", ctx -> ctx.render(Jackson.json(converterMap.keySet())))
                        .get("converter/:type", ctx -> ctx.
                                render(Jackson.json(((Converter) converterMap
                                        .get(ctx.getPathTokens().get("type"))).getUnits())))
                        .get("converter/:type/value", ctx -> {
                            double x = ((Converter)converterMap.get(ctx.getPathTokens().get("type")))
                                    .convert(ctx.getRequest().getQueryParams().get("from"),
                                            ctx.getRequest().getQueryParams().get("to"),
                                            Double.parseDouble(ctx.getRequest().getQueryParams().get("input")));
                            statisticsRepo.putItem(new StatisticsModel(
                                    ctx.getPathTokens().get("type"),
                                    ctx.getRequest().getQueryParams().get("from"),
                                    ctx.getRequest().getQueryParams().get("to"),
                                    x
                            ));
                            Map<String, String> out = new HashMap<>();
                            out.put("output", Double.toString(x));
                            ctx.render(Jackson.json(out));
                        })

                ));
    }
//                        .get("converter/:type", ctx -> ctx.
//                                render(Jackson.json(((Converter)converterMap
//                                        .get(ctx.getPathTokens().get("type"))).getUnits())))
//                        .put("converter/:type", ctx -> System.out.println(ctx.getContext().getResponse().getStatus().getCode()))
//                        .post("converter/:type", ctx -> {
//                            System.out.println(ctx.getResponse().getStatus().getCode());
//                            ctx.getRequest().getBody().then(req -> {
//                                ObjectMapper om = new ObjectMapper();
//                                Map<String, String> map = om.readValue(req.getText(), Map.class);
//                                System.out.println(map.toString());
//                                ctx.render(Jackson.json(statisticsRepo.getItems()));
//                            });
//                        })
//                        .all(ctx -> {
//                            System.out.println(ctx.getResponse().getStatus().getCode());
//                        })
//                );


    private static Statistics statisticsRepo;

    private static void init(){
        StatisticsController statsController = new StatisticsController();
        statisticsRepo = statsController.getRepo();
        if (statisticsRepo.getClass().getName().endsWith("Mock")){
            System.out.println(statsController.throwExceptionFromController());
        }

    }
}
