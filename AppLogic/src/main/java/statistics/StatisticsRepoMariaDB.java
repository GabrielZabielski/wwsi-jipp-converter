package statistics;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatisticsRepoMariaDB implements Statistics {
    Connection connection;
    private static final String INSERT = "INSERT INTO Converter" +
            "(UID, converter, date, input, unitFrom, unitTo) VALUES " +
            " (?, ?, ?, ?, ?, ?);";

    private static final String QUERY = "Select * from Converter";

    StatisticsRepoMariaDB() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:mariadb://converter-database.csjh09bxtvwl.eu-central-1.rds.amazonaws.com:3306/converter",
                "admin",
                "ZAQ!SeR2wsx");
    }

    public void putItem(StatisticsModel stats) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, stats.getDataId());
            statement.setString(2, stats.getConverter());
            statement.setString(3, stats.getDate());
            statement.setDouble(4, stats.getInput());
            statement.setString(5, stats.getUnitFrom());
            statement.setString(6, stats.getUnitTo());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<StatisticsModel> getItems() {
        List<StatisticsModel> stats = new ArrayList<StatisticsModel>();
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            ResultSet items = statement.executeQuery();

            while (items.next()){
                StatisticsModel model = new StatisticsModel();
                model.setDataId(items.getString("UID"));
                model.setConverter(items.getString("converter"));
                model.setDate(items.getString("date"));
                model.setUnitFrom(items.getString("unitFrom"));
                model.setUnitTo(items.getString("unitTo"));
                model.setInput(items.getDouble("input"));

                stats.add(model);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return stats;
    }
}
