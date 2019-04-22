

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
    //enum Table{LISTING, HOST, REVIEW_COMMENTS, NONE}

    public static void main(String[] args){
        launch(args);   //launch the javaFX app (goes to start method)
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Start connection with database


        MyApplication.window = primaryStage;

        //Load FXML and create scenes
        Parent mainViewLoader = FXMLLoader.load(getClass().getResource("Layouts/mainView.fxml"));
        MyApplication.mainScene = new Scene(mainViewLoader,MyApplication.width,MyApplication.height);
        Parent searchViewLoader = FXMLLoader.load(getClass().getResource("Layouts/searchView.fxml"));
        MyApplication.searchScene = new Scene(searchViewLoader,MyApplication.width, MyApplication.height);
        Parent predQuerViewLoader = FXMLLoader.load(getClass().getResource("Layouts/predQuerView.fxml"));
        MyApplication.predQuerScene = new Scene(predQuerViewLoader, MyApplication.width, MyApplication.height);
        Parent insDelViewLoader = FXMLLoader.load(getClass().getResource("Layouts/insDelView.fxml"));
        MyApplication.insDelScene = new Scene(insDelViewLoader, MyApplication.width, MyApplication.height);

        //set titles of components during initialization
        primaryStage.setTitle("Welcome to AirBnb");

        //add scene to the stage and displays it
        primaryStage.setScene(MyApplication.searchScene); //TODO: CORRECT PRIMARY STAGE TO "mainScene"
        primaryStage.show();
    }

}



