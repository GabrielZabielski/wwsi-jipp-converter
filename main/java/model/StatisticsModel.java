package model;

import java.util.UUID;

public class StatisticsModel {
    private String dataId;
    private String converter;
    private String date;
    private String unitFrom;
    private String unitTo;
    private Double input;

    public StatisticsModel(String converter, String unitFrom, String unitTo, double input) {
        long dateTime = System.currentTimeMillis();

        this.dataId = new String(String.valueOf(UUID.randomUUID()));
        this.converter = new String(converter);
        this.date = new String(Long.toString(dateTime));
        this.unitFrom = new String(unitFrom);
        this.unitTo = new String(unitTo);
        this.input = new Double(input);
    }
    public StatisticsModel(){}


    public String getDataId() {
        return dataId;
    }

    public String getConverter() {
        return converter;
    }

    public String getDate() {
        return date;
    }

    public String getUnitFrom() {
        return unitFrom;
    }

    public String getUnitTo() {
        return unitTo;
    }

    public Double getInput() {
        return input;
    }


    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public void setConverter(String converter) {
        this.converter = converter;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUnitFrom(String unitFrom) {
        this.unitFrom = unitFrom;
    }

    public void setUnitTo(String unitTo) {
        this.unitTo = unitTo;
    }

    public void setInput(Double input) {
        this.input = input;
    }
}
