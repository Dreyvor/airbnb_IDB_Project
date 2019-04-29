import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CtrlInsertListing {
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

    public void btnInsertClk() {
        System.out.println("Insert query lauched");
    }
}
