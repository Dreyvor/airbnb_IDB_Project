import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;

public class CtrlInsDelHost {
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

    @FXML
    private TextField txtFHost_name= new TextField();
    @FXML
    private TextField txtFHost_id = new TextField();
    @FXML
    private TextField txtFHost_about = new TextField();
    @FXML
    private TextField txtFHost_since = new TextField();
    @FXML
    private TextField txtFHost_url = new TextField();
    @FXML
    private TextField txtFHost_response_rate = new TextField();
    @FXML
    private TextField txtFHost_thumbnail_url = new TextField();
    @FXML
    private TextField txtFHost_picture_url = new TextField();
    @FXML
    private TextField txtFNeigbourhood_id = new TextField();
    @FXML
    private TextField txtFHost_response_time = new TextField();

    private ArrayList<TextField> txtFields = new ArrayList<>();
    private Table table;
    private ArrayList<String> columns;

    public void initialize() {
        txtFields.add(txtFHost_name);
        txtFields.add(txtFHost_id);
        txtFields.add(txtFHost_about);
        txtFields.add(txtFHost_since);
        txtFields.add(txtFHost_url);
        txtFields.add(txtFHost_response_rate);
        txtFields.add(txtFHost_thumbnail_url);
        txtFields.add(txtFHost_picture_url);
        txtFields.add(txtFNeigbourhood_id);
        txtFields.add(txtFHost_response_time);
        table = Utils.StringToTable("HOST");
        columns = Utils.getColumnsFromTable(table, true);
    }


    public void btnInsertClk() {
        Thread t = new Thread(() -> {
            ArrayList<String> values = new ArrayList<>();
            txtFields.forEach(txtF -> {
                values.add(txtF.getText());
            });
            String query = Utils.generateInsertQuery(table, columns, values);
            System.out.println(query + "\n ==> This query will be executed !");
            Utils.executeQuery(query);

            System.out.println("Insert done");
        });

        t.start();
    }

    public void btnDeleteClk() {
        Thread t = new Thread(() -> {
            ArrayList<String> values = new ArrayList<>();

            txtFields.forEach(txtF -> {
                values.add(txtF.getText());
            });

            String query = Utils.generateDeleteQuery(table, columns, values);
            System.out.println(query + "\n ==> This query will be executed !");
            Utils.executeQuery(query);

            System.out.println("Delete done");
        });

        t.start();
    }
}
