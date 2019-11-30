package ui;

import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import converters.Converter;
import convertersController.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import statistics.StatisticsModel;
import statistics.StatisticsRepoMariaDB;

import java.text.SimpleDateFormat;

public class Controller {
@FXML
    TableView<StatisticsModel> table;
@FXML
    Pane pane;


@FXML
    TableColumn dataId;
@FXML
    TableColumn date;
@FXML
    TableColumn converterType;
@FXML
    TableColumn unitFrom;
@FXML
    TableColumn unitTo;
@FXML
    TableColumn value;


@FXML
    ChoiceBox<String> converter;
@FXML
    ChoiceBox<String> from;
@FXML
    ChoiceBox<String> into;
@FXML
    TextField input;
@FXML
    Button converterButton;
@FXML
    Label result;

private ObservableList<String> convertersList = FXCollections.observableArrayList();
private ObservableList<String> unitsList = FXCollections.observableArrayList();
private ObservableList<StatisticsModel> observableList = FXCollections.observableArrayList();


private Service converterService = new Service();

    public void initialize() {

        initTableView();

        converterService.getMapConverters().forEach((k, v) -> convertersList.add(k));
        converter.setItems(convertersList);
        from.setItems(unitsList);
        into.setItems(unitsList);

        converter.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> updateUnits(newValue) );
        converterButton.setOnAction(e -> convertHandler());
    }

    private void convertHandler() {
        if (converter.getValue() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Converter type is empty");
            a.show();
        }
        else {
            Object obj = converterService.getMapConverters().get(converter.getValue());
            double conversionResult = ((Converter)obj)
                    .convert(from.getValue(),
                             into.getValue(),
                             Double.valueOf(input.getText()));
            result.setText(Double.toString(conversionResult));
            StatisticsModel statsModel = new StatisticsModel(
                    converter.getValue(),
                    from.getValue(),
                    into.getValue(),
                    conversionResult);
//            StatisticsRpoDynamoDB stats = new StatisticsRpoDynamoDB();
            StatisticsRepoMariaDB mdb = new StatisticsRepoMariaDB();
            try {
                mdb.putItem(statsModel);
                observableList.add(statsModel);
            } catch (AmazonDynamoDBException ex){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText(ex.toString());
                a.setTitle("AmazonDynamoDBException");
                a.show();
            }
        }
    }

    private void updateUnits(String newValue) {
        Object obj = converterService.getMapConverters().get(newValue);
        unitsList.clear();
        unitsList.addAll(((Converter)obj).getUnits());
    }

    private void initTableView(){
//        StatisticsRpoDynamoDB stats = new StatisticsRpoDynamoDB();
        StatisticsRepoMariaDB mdb = new StatisticsRepoMariaDB();
        observableList.addAll(mdb.getItems());


        dataId.setText("Id");
        converterType.setText("converter");
        date.setText("Data Time");
        unitFrom.setText("Unit From");
        unitTo.setText("Unit To");
        value.setText("Value");


        dataId.setCellValueFactory(new PropertyValueFactory<StatisticsModel, Integer>("dataId"));
        converterType.setCellValueFactory(new PropertyValueFactory<StatisticsModel, String>("converter"));
        date.setCellValueFactory(new PropertyValueFactory<StatisticsModel, String>("date"));
        unitFrom.setCellValueFactory(new PropertyValueFactory<StatisticsModel, String>("unitFrom"));
        unitTo.setCellValueFactory(new PropertyValueFactory<StatisticsModel, String>("unitTo"));
        value.setCellValueFactory(new PropertyValueFactory<StatisticsModel, Integer>("input"));
        table.setItems(observableList);


        table.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
            @Override
            public Boolean call(TableView.ResizeFeatures resizeFeatures) {
                return true;
            }
        });
        date.setCellFactory(column -> {
            TableCell<StatisticsModel, String> cell = new TableCell<StatisticsModel, String>() {
                private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    }
                    else {
                        String date = format.format(Long.parseLong(item));
                        setText(date);
                    }
                }
            };

            return cell;
        });
    }
}
