package ui;

import convertersController.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import model.Statistics;

public class Controller {
@FXML
    TableView<Statistics> table;
@FXML
    Pane pane;

@FXML
    TableColumn dataId;
@FXML
    TableColumn data;
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


    public void initialize() throws InstantiationException, IllegalAccessException {
        Service x = new Service();
        System.out.println(x.listConverters());


        initTableView();
    }
    private void initTableView(){
        Statistics st = new Statistics("dupa", "to", 100);
        Statistics st1 = new Statistics("dupa", "to", 2000);
        Statistics st2 = new Statistics("dupa", "to", 300);
        st.setDataId(0);
        st1.setDataId(1);
        st2.setDataId(2);

        ObservableList<Statistics> observableList = FXCollections.observableArrayList(st, st1, st2);
        dataId.setText("Id");
        data.setText("Data Time");
        unitFrom.setText("Unit From");
        unitTo.setText("Unit To");
        value.setText("Value");

        dataId.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("dataId"));
        data.setCellValueFactory(new PropertyValueFactory<Statistics, String>("date"));
        unitFrom.setCellValueFactory(new PropertyValueFactory<Statistics, String>("unitFrom"));
        unitTo.setCellValueFactory(new PropertyValueFactory<Statistics, String>("unitTo"));
        value.setCellValueFactory(new PropertyValueFactory<Statistics, Integer>("input"));
        table.setItems(observableList);
        table.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
            @Override
            public Boolean call(TableView.ResizeFeatures resizeFeatures) {
                return true;
            }
        });
    }
}
