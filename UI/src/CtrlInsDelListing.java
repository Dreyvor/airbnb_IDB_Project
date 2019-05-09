import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CtrlInsDelListing {
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
    private TextField txtFListing_id = new TextField();
    @FXML
    private TextField txtFName = new TextField();
    @FXML
    private TextField txtFListing_url = new TextField();
    @FXML
    private TextField txtFSummary = new TextField();
    @FXML
    private TextField txtFSpace = new TextField();
    @FXML
    private TextField txtFNeighbourghood_id = new TextField();
    @FXML
    private TextField txtFHost_id = new TextField();

    private ArrayList<TextField> txtFields = new ArrayList<>();
    private Table table;
    private ArrayList<String> columns;

    public void initialize() {
        txtFields.add(txtFListing_id);
        txtFields.add(txtFName);
        txtFields.add(txtFListing_url);
        txtFields.add(txtFSummary);
        txtFields.add(txtFSpace);
        txtFields.add(txtFNeighbourghood_id);
        txtFields.add(txtFHost_id);
        table = Utils.StringToTable("LISTING");
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
