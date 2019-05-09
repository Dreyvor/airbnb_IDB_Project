import javafx.fxml.FXML;

import javafx.scene.control.*;

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
            // Specify which column to search into for each table
            ArrayList<String> columnNames = Utils.getColumnsFromTable(elem, true);

            if(elem == Table.HOST){
                columnNames = new ArrayList<>(columnNames.subList(2, columnNames.size()));
                columnNames.add("user_id");
                columnNames.add("user_name");
            }

            // Build the query
            String query = "SELECT * FROM " + elem.toString() + " " + Utils.generateSubstringMatch(terms, columnNames, elem);
            String windowTitle = Utils.tableToString(elem);

            //Execute query and display the results
            Utils.execAndDisplayQuery(query, columnNames, windowTitle);
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

}
