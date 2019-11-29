package model;

import java.util.List;

interface Statistics {
    void putItem(StatisticsModel stats);
    List<StatisticsModel> getItems();

}
