package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Statistics {
    private SimpleIntegerProperty dataId;
    private SimpleStringProperty date;
    private SimpleStringProperty unitFrom;
    private SimpleStringProperty unitTo;
    private SimpleIntegerProperty input;

    public Statistics(String unitFrom, String unitTo, int input) {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date dateTime = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(dateTime));

        this.dataId = new SimpleIntegerProperty(0);
        this.date = new SimpleStringProperty(formatter.format(dateTime));
        this.unitFrom = new SimpleStringProperty(unitFrom);
        this.unitTo = new SimpleStringProperty(unitTo);
        this.input = new SimpleIntegerProperty(input);
    }

    public void setDataId(int dataId) {
        this.dataId.set(dataId);
    }

    public int getDataId() {
        return dataId.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getUnitFrom() {
        return unitFrom.get();
    }

    public String getUnitTo() {
        return unitTo.get();
    }

    public int getInput() {
        return input.get();
    }
}
