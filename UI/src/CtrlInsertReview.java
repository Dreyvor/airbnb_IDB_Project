import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CtrlInsertReview {
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
    private TextField txtFReview_id = new TextField();
    @FXML
    private TextField txtFListing_id = new TextField();
    @FXML
    private TextField txtFDate = new TextField();
    @FXML
    private TextField txtFComments = new TextField();
    @FXML
    private TextField txtFuser_id = new TextField();

    public void btnInsertClk() {
        System.out.println("Insert query lauched");
    }
}
