import javafx.stage.Stage;

public class CtrlMenu {


    public static void changeView(String newView){
        switch (newView){
            case "Main":
                MyApplication.window.setScene(MyApplication.mainScene);
                break;
            case "Search":
                MyApplication.window.setScene(MyApplication.searchScene);
                break;
            case "Predefined queries":
                MyApplication.window.setScene(MyApplication.predQuerScene);
                break;
            case "Insert/delete":
                MyApplication.window.setScene(MyApplication.insDelScene);
                break;
        }
    }
}
