package ui;

import converters.Converter;
import convertersController.ConverterController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import statistics.Statistics;
import statistics.StatisticsController;
import statistics.StatisticsModel;

import javax.inject.Inject;
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

    private ConverterController converterService = new ConverterController();

    @Inject
    protected StatisticsController statsController;
    private Statistics statisticsRepo;

    public void initialize() {
        statsController = new StatisticsController();
        this.statisticsRepo = statsController.getRepo();
        System.out.println(statisticsRepo.getClass());
        observableList.addAll(statisticsRepo.getItems());

        converterService.getMapConverters().forEach((k, v) -> convertersList.add(k));
        converter.setItems(convertersList);
        from.setItems(unitsList);
        into.setItems(unitsList);

        converter.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> updateUnits(newValue) );
        converterButton.setOnAction(e -> convertHandler());

        initTableView();
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
            statisticsRepo.putItem(statsModel);
            observableList.add(statsModel);
        }
    }

    private void updateUnits(String newValue) {
        Object obj = converterService.getMapConverters().get(newValue);
        unitsList.clear();
        unitsList.addAll(((Converter)obj).getUnits());
    }

    private void initTableView(){


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
