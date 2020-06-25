package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class BallotInitForm extends Form {
    public TextField address;
    public final String type;

    public BallotInitForm(String type) {
        this.type = type;
        address = new TextField();
        HBox addressBox = new HBox();
        Text text = new Text("Please Enter Address For Ballot Box:");
        View.alignTextField("Address: ", address, addressBox);
        submitButton = new Button("Submit");
        formView = new VBox(text, addressBox, submitButton);
        formView.setSpacing(10);
        VBox.setMargin(addressBox,new Insets(0,350,0,0));
        formView.setAlignment(Pos.CENTER);
    }

    public boolean getAllFilled() {
        return !address.getText().trim().isEmpty();
    }
}
