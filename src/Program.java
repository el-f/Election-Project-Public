import Controller.Controller;
import Model.Election;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       new Controller(Election.initDefault(), new View(stage));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
