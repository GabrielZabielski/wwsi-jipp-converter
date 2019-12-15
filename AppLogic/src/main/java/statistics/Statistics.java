package statistics;

import java.util.List;

public interface Statistics {
    void putItem(StatisticsModel stats);
    List<StatisticsModel> getItems();
}
