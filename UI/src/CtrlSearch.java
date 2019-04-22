import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CtrlSearch {

    @FXML
    private CheckBox chkListing = new CheckBox();
    @FXML
    private CheckBox chkHost = new CheckBox();
    @FXML
    private CheckBox chkReview = new CheckBox();
    @FXML
    private TextField searchTerms = new TextField();
    @FXML
    private Button btnSearch = new Button();

    private ArrayList<javafx.scene.control.CheckBox> chkBoxes = new ArrayList<CheckBox>();

    public void gotoViewMain() {
        CtrlMenu.changeView("Main");
    }

    public void gotoViewSearch() {
        CtrlMenu.changeView("Search");
    }

    public void gotoViewPredQuer() {
        CtrlMenu.changeView("Predefined queries");
    }

    public void gotoViewInsDel() {
        CtrlMenu.changeView("Insert/delete");
    }


    public void btnSearchClk() {
        ArrayList<Table> tables = getChkTables();
        String terms = searchTerms.getText();
        System.out.println(terms);
        //We got the tables now

        //Let's construct one window per selected tables
        tables.forEach(elem -> {

            //Create the new stage
            Stage resStage = new Stage();
            resStage.setTitle("Search results for terms in table : " + Utils.tableToString(elem));
            BorderPane myBorderPane = new BorderPane();
            resStage.setScene(new Scene(myBorderPane, MyApplication.width, MyApplication.height));


            //create result table for each table and put it in a scrollPane
            ScrollPane scroll = new ScrollPane();
            TableView resTableView = new TableView();
            scroll.setContent(resTableView);
            scroll.setFitToHeight(true);
            scroll.setFitToWidth(true);
            myBorderPane.setCenter(scroll);

            //will specify which column to search into for each table
            ArrayList<String> columnNames = Utils.getColumnsFromTable(elem);

            //build the query
            String query = "SELECT * FROM " + elem.toString() + " " + Utils.generateSubstringMatch(terms, columnNames);
            System.out.println(query + "==> query ready -> launching a new thread");

            //Create a new theard to launch asychronously
            Thread thread = new Thread(() -> {

                System.out.println("Query thread launched");
                //execute statement and insert them into the table view
                ResultSet res = Utils.executeQuery(query);
                System.out.println("ResultSet OK");

                //trick to run the update on the UI thread
                Platform.runLater(() -> putResInTable(columnNames, res, resTableView));

            });
            resStage.show();

            thread.start();

        });
    }

    private ArrayList<Table> getChkTables() {
        if (chkBoxes.size() <= 0) {
            chkBoxes.add(chkListing);
            chkBoxes.add(chkHost);
            chkBoxes.add(chkReview);
        }
        ArrayList<Table> res = new ArrayList<>();
        chkBoxes.forEach(elem -> {
            if (elem.isSelected()) res.add(Utils.StringToTable(elem.getText()));
        });

        return res;
    }


    /**
     * Method used to insert into a panel a tableview filled with a resulset
     *
     * @param res       the resulset of the executed query
     * @param resTableView the tableview in which the datas must be inserted
     */
    public static void putResInTable(ArrayList<String> columnNames, ResultSet res, TableView resTableView) {
        //List where the result data will be stored
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        //create columns, read result and insert data into the table
        try {
            for (int i = 0; i < columnNames.size(); ++i) {
                final int j = i;

                TableColumn col = new TableColumn(columnNames.get(i));

                //define what to do when receiving data from observable list
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                    if ((param.getValue().size()) > j){
                        final Object theValue = param.getValue().get(j);
                        if (theValue != null){
                            return new SimpleStringProperty(theValue.toString());
                        }
                    }
                    return null;
                });

                resTableView.getColumns().add(col);

            }


            //retrieve data from resulset
            while (res.next()) {
                //get data from result set by row
                ObservableList<String> row = FXCollections.observableArrayList();

                for (int i = 1; i < res.getMetaData().getColumnCount() + 1; ++i) {

                    row.add(res.getString(i));
                }

                data.add(row);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //put the data into table view
        System.out.println(data.size());
        resTableView.setItems(data);

        System.out.println("View updated !");

    }

}
