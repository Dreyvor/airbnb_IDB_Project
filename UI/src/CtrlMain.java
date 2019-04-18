import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CtrlMain implements Initializable {
    //enum for all tables
    enum Table {LISTINGS, HOSTS, REVIEWS, NONE}

    @FXML
    private TextArea txtArWelcome = new TextArea();

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtArWelcome.setText("Welcome to AirBnB.\n" +
                "from here you can search in our database, insert and delete items from tables and executes the asked queries.\n" +
                "To change views, you have to use the menu bar at top.\n\n" +
                "Have fun !");
    }

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
}
