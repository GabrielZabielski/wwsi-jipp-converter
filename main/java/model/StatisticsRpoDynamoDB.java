package model;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StatisticsRpoDynamoDB implements Statistics {
//    converter-statistics
    AmazonDynamoDB client;
    DynamoDB service;
    Table table;

    public StatisticsRpoDynamoDB() {
        this.client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider("main"))
                .withRegion("eu-central-1")
                .build();
        this.service = new DynamoDB(client);
        this.table = service.getTable("converter-statistics");
    }

    public void putItem(StatisticsModel stats){
        PutItemOutcome outcome = table.putItem(new Item()
                .withPrimaryKey("UID", stats.getDataId())
                .withString("converter", stats.getConverter())
                .withString("date", stats.getDate())
                .withString("unitFrom", stats.getUnitFrom())
                .withString("unitTo", stats.getUnitTo())
                .withDouble("input", stats.getInput()));
    }

    public List<StatisticsModel> getItems(){
        ScanSpec scan = new ScanSpec();

        ItemCollection<ScanOutcome> items = table.scan(scan);
        List<StatisticsModel> stats = new ArrayList<StatisticsModel>();
        Iterator<Item> iter = items.iterator();
        Item item = null;
        while(iter.hasNext()){
            item = iter.next();
            StatisticsModel model = new StatisticsModel();
            model.setDataId(item.getString("UID"));
            model.setConverter(item.getString("converter"));
            model.setDate(item.getString("date"));
            model.setUnitFrom(item.getString("unitFrom"));
            model.setUnitTo(item.getString("unitTo"));
            model.setInput(item.getDouble("input"));

            stats.add(model);
        }
        return stats;
    }

}
