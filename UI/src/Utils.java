import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Bunch of useful methods used in Main
 */

public class Utils {


    /**
     * Generate a part of the query for substring match
     *
     * @param search
     * @param columns
     * @return
     */
    public static String generateSubstringMatch(String search, List<String> columns, Table table) {

        StringBuilder sb = new StringBuilder();

        if (table == Table.HOST){
            sb.append("join all_users au on au.user_id = host_id ");
        }

        sb.append(" WHERE");

        columns.forEach(c -> {

            sb.append(" " + c + " LIKE '%" + search + "%'  or");

        });
        sb.delete(sb.length()-3,sb.length());

        if (table == Table.HOST){
            sb.append(" or au.user_id LIKE '%"+ search + "%' or au.user_name LIKE '%"+search+"%'");
        }

        return sb.toString();

    }


    public static String generateInsertQuery(Table table, List<String> columns, List<String> values) {
        StringBuilder sb = new StringBuilder();

        if(table == Table.HOST){
            sb.append("INSERT INTO ");
            sb.append("ALL_USERS (" + columns.get(0) + ", " +columns.get(1) + ") VALUES (");
            sb.append(values.get(1) + ", '" + values.get(0) + "')");

            executeQuery(sb.toString());

            columns = columns.subList(2,columns.size());
            values = values.subList(1,values.size());
        }

        sb.replace(0,sb.length(),"");

        sb.append("INSERT INTO ");
        sb.append(tableToString(table) + " (");

        columns.forEach(c -> sb.append(c + ", "));
        sb.delete(sb.length() - 2, sb.length());

        sb.append(") values (");

        values.forEach(v -> {
            if(v.length() == 0){
                sb.append("NULL, ");
            }else if(isInt(v)){
                sb.append(v + ", ");
            }else if (isDate(v)){
                sb.append("TO_DATE('" + v + "', 'YYYY-MM-DD'), ");
            }else{
                sb.append("'" + v + "', ");
            }
        });

        sb.delete(sb.length() - 2, sb.length());
        sb.append(")");

        return sb.toString();
    }


    public static String generateDeleteQuery(Table table, List<String> columns, List<String> values) {
        StringBuilder sb = new StringBuilder();

        if(table == Table.HOST){
            sb.append("DELETE FROM ");
            sb.append("ALL_USERS AU WHERE ");
            if(values.get(1).length() != 0) sb.append(columns.get(0) + " = " + values.get(1));
            if(values.get(1).length() != 0 && values.get(0).length() != 0) sb.append(" AND ");
            if(values.get(0).length() != 0) sb.append(columns.get(1) + " = '" + values.get(0) + "'");

            System.out.println(sb.toString() + "==> Execute query");

            executeQuery(sb.toString());

            columns = columns.subList(2,columns.size());
            values = values.subList(1,values.size());
        }
        sb.replace(0,sb.length(),"");

        sb.append("DELETE FROM " + Utils.tableToString(table) + " WHERE ");

        for (int i = 0; i < columns.size(); i++) {
            String v = values.get(i);
            if (v.length() == 0) {continue;}

            sb.append(columns.get(i) + "=");

            if(isInt(v)){
                sb.append(v);
            }else if (isDate(v)){
                sb.append("TO_DATE('" + v + "', 'YYYY-MM-DD')");
            }else{
                sb.append("'" + v + "'");
            }

            sb.append(" and ");
        }

        sb.delete(sb.length()-5, sb.length());

        //sb.append(";");
        return sb.toString();
    }


    /**
     * Method to covert from String to Table enum
     *
     * @param s the string to convert
     * @return the table corresponding to the string
     */
    public static Table StringToTable(String s) {

        switch (s) {

            case "LISTING":
                return Table.LISTING;
            case "HOST":
                return Table.HOST;
            case "REVIEW_COMMENTS":
                return Table.REVIEW_COMMENTS;

        }

        return Table.NONE;

    }


    /**
     * Method to covert from Table enum to string
     *
     * @param t the string to convert
     * @return the table corresponding to the string
     */
    public static String tableToString(Table t) {

        switch (t) {

            case LISTING:
                return "LISTING";
            case HOST:
                return "HOST";
            case REVIEW_COMMENTS:
                return "REVIEW_COMMENTS";

        }

        return "NONE";

    }


    /**
     * Initialize DB connection
     *
     * @return the database connection's instance
     */
    public static Connection getConnection() {


        //will only work with a VPN connection to EPFL's network

        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@cs322-db.epfl.ch:1521:ORCLCDB";
        String username = "C##DB2019_G51";
        String password = "DB2019_G51";


        Connection connection = null;

        try {
            // make the connection
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;

    }


    /**
     * Method to execute queries and return the resultset
     *
     * @param query the query to execute
     * @return teh resultset of the given query
     */
    public static ResultSet executeQuery(String query) {


        ResultSet res = null;

        try {
            Statement s = MyApplication.getDBConnection().createStatement();
            System.out.println("Executing query...");            //TODO remove sysout
            res = s.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Checks that a given string is a Date
     *
     * @param s the string to check
     * @return true if the string is a date false otherwise
     */
    public static boolean isDate(String s) {

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; ++i) {

            if (!Character.isDigit(c[i]) && c[i] != '-') {
                return false;
            }

        }

        return true;
    }

    /**
     * Checks that a given string is an int
     *
     * @param s the string to check
     * @return true if the string is a number false otherwise
     */
    public static boolean isInt(String s) {

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; ++i) {

            if (!Character.isDigit(c[i])) {
                return false;
            }

        }

        return true;
    }

    /**
     * Get the column names of a given table
     *
     * @param t the table
     * @return an arraylist containing the column names
     */
    public static ArrayList<String> getColumnsFromTable(Table t, Boolean withForeignKeys) {
        if (withForeignKeys == null) withForeignKeys = false;

        //will specify which column to search into for each table
        ArrayList<String> columnNames = new ArrayList<>();

        switch (t) {

            case LISTING:
                columnNames.add("listing_id");
                columnNames.add("name");
                columnNames.add("listing_url");
                columnNames.add("summary");
                columnNames.add("space");
                if (withForeignKeys) {
                    columnNames.add("NEIGHBOURHOOD_ID");
                    columnNames.add("host_id");
                }
                break;

            case HOST:
                columnNames.add("user_id");
                columnNames.add("user_name");
                columnNames.add("host_id");
                columnNames.add("host_about");
                columnNames.add("host_since");
                columnNames.add("host_url");
                columnNames.add("host_response_rate");
                columnNames.add("host_thumbnail_url");
                columnNames.add("host_picture_url");
                if (withForeignKeys) {
                    columnNames.add("NEIGHBOURHOOD_ID");
                    columnNames.add("host_response_time");
                }
                break;

            case REVIEW_COMMENTS:
                columnNames.add("review_id");
                columnNames.add("listing_id");
                columnNames.add("review_date");
                columnNames.add("comments");
                columnNames.add("user_id");
                break;

        }

        return columnNames;
    }

    public static void execAndDisplayQuery(String query, ArrayList<String> columnNames, String windowTitle) {

        if (query != null && query != "") {
            //Create the new stage
            Stage resStage = new Stage();
            resStage.setTitle("Search results for terms in table : " + windowTitle);
            BorderPane myBorderPane = new BorderPane();
            resStage.setScene(new Scene(myBorderPane, MyApplication.width, MyApplication.height));


            //create result table for each table and put it in a scrollPane
            ScrollPane scroll = new ScrollPane();
            TableView resTableView = new TableView();
            scroll.setContent(resTableView);
            scroll.setFitToHeight(true);
            scroll.setFitToWidth(true);
            myBorderPane.setCenter(scroll);

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

        } else System.out.println("#*** The query is not defined or null ! ***#");

    }

    /**
     * Method used to insert into a panel a tableview filled with a resulset
     *
     * @param res          the resulset of the executed query
     * @param resTableView the tableview in which the datas must be inserted
     */
    public static void putResInTable(ArrayList<String> columnNames, ResultSet res, TableView resTableView) {
        //If columnNames is null or empty, construct them from the resultSet res
        if ((columnNames == null || columnNames.size() == 0) && res != null) {
            try {
                columnNames = new ArrayList<>();
                ResultSetMetaData resMetaData = res.getMetaData();
                for (int i = 1; i < resMetaData.getColumnCount() + 1; i++) {
                    columnNames.add(resMetaData.getColumnName(i));
                }
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }

        //List where the result data will be stored
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        //create columns, read result and insert data into the table
        try {
            for (int i = 0; i < columnNames.size(); ++i) {
                final int j = i;

                TableColumn col = new TableColumn(columnNames.get(i));

                //define what to do when receiving data from observable list
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                    if ((param.getValue().size()) > j) {
                        final Object theValue = param.getValue().get(j);
                        if (theValue != null) {
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
