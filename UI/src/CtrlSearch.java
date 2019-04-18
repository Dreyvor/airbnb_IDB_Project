import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    @FXML
    private TableView resTable = new TableView();

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
        ArrayList<Main.Table> tables = getChkTables();
        String terms = searchTerms.getText();
        System.out.println(terms);
        //We got the tables now

        //Let's construct one window per selected tables
        tables.forEach(elem -> {
            Stage resStage = new Stage();
            resStage.setTitle("Search results for terms in table : " + Utils.TableToString(elem));
            resStage.setScene(new Scene(new Pane(), MyApplication.width, MyApplication.height));

            //create result table for each table
            TableView resTableView = new TableView();

            //will specify which column to search into for each table
            ArrayList<String> columnNames = new ArrayList<>();

            switch (elem) { //TODO: ADD COLUMNS HERE !
                case LISTING:
                    columnNames.add("Listing_id");
                    break;
                case HOST:
                    columnNames.add("Host_id");
                    break;
                case REVIEW_COMMENTS:
                    columnNames.add("Review_id");
                    break;
            }

            //build the query
            String query = "SELECT * FROM " + elem.toString() +" "+ Utils.GenerateSubstringMatch(terms,columnNames);
            System.out.println(query);

            //TODO : RUN THE QUERY ON THE DB ON VPN THROUGH SQLDEV (verify if ok with good connexion before UI)

            //Create columns to display results (SEE FAVORIS FireFox)
            /*columnNames.forEach(colType -> {
                TableColumn
            });*/

            resStage.show();
        });
    }

    private ArrayList<Main.Table> getChkTables() {
        if (chkBoxes.size() <= 0) {
            chkBoxes.add(chkListing);
            chkBoxes.add(chkHost);
            chkBoxes.add(chkReview);
        }
        ArrayList<Main.Table> res = new ArrayList<>();
        chkBoxes.forEach(elem -> {
            if (elem.isSelected()) res.add(Utils.StringToTable(elem.getText()));
        });

        return res;
    }

}
