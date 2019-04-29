import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CtrlInsertHost {
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


    public void btnInsertClk() {
        System.out.println("Insert query lauched");
    }
}
