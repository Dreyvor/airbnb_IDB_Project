import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

/**
 * Contains objects that are needed across the entire program
 */
public abstract class MyApplication {

    //Stage and scene to use them across controllers
    public static Stage window;
    public static Scene mainScene;
    public static Scene searchScene;
    public static Scene predQuerScene;
    public static Scene insDelScene;

    public static final int width = 800;
    public static final int height = 600;

    //DB connection following Singleton design pattern
    private static Connection sqlConnection = null;


    /**
     * Getter for Singleton Object Connection
     *
     * @return the DB Connection instance
     */
    public static Connection getDBConnection() {
        if (sqlConnection == null) {
            sqlConnection = Utils.getConnection();
        }
        return sqlConnection;
    }

}
