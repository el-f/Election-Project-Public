import Controller.Controller;
import Model.Election;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Election election = Election.initDefault();
        View view = new View(stage);
        new Controller(election, view);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
