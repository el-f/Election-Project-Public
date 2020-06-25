import Controller.Controller;
import Model.Election;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {

    @SuppressWarnings("unused") //controller is used.
    @Override
    public void start(Stage stage) throws Exception {
        Election election = Election.initDefault();
        View view = new View(stage);
        Controller controller = new Controller(election, view);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
