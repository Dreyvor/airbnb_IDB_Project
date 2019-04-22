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
    public static String generateSubstringMatch(String search, List<String> columns) {

        StringBuilder sb = new StringBuilder();

        sb.append(" WHERE");

        columns.forEach(c -> {

            sb.append(" " + c + " LIKE '%" + search + "%'  or");


        });

        String res = sb.toString();

        return res.substring(0, res.length() - 3);

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
    public static ArrayList<String> getColumnsFromTable(Table t) {

        //will specify which column to search into for each table
        ArrayList<String> columnNames = new ArrayList<>();

        switch (t) {

            case LISTING:
                columnNames.add("listing_id");
                columnNames.add("name");
                columnNames.add("listing_url");
                columnNames.add("summary");
                columnNames.add("space");
                break;

            case HOST:
                columnNames.add("host_id");
                columnNames.add("host_about");
                columnNames.add("host_since");
                columnNames.add("host_url");
                columnNames.add("host_response_rate");
                columnNames.add("host_thumbnail_url");
                columnNames.add("host_picture_url");
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
}
