package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CitizenInitForm extends Form {

    public TextField name, ID, yearOfBirth, numSickDays, party;
    public CheckBox carryWeapon, isWearingProtectionSuit;
    public final String type;

    public CitizenInitForm(String type) {
        this.type = type;
        HBox nameBox = new HBox();
        name = new TextField();
        View.alignTextField("Name: ", name, nameBox);
        HBox IDBox = new HBox();
        ID = new TextField();
        View.alignTextField("ID: ", ID, IDBox);
        HBox yobBox = new HBox();
        yearOfBirth = new TextField();
        View.alignTextField("Year Of Birth: ", yearOfBirth, yobBox);
        Text text = new Text("Please Fill all fields:");
        formView = new VBox(text, nameBox, IDBox, yobBox);
        formView.setAlignment(Pos.CENTER);
        VBox.setMargin(nameBox, new Insets(0, 350, 0, 0));
        VBox.setMargin(IDBox, new Insets(0, 350, 0, 0));
        VBox.setMargin(yobBox, new Insets(0, 350 ,0, 0));
        if (type.equalsIgnoreCase("sick") || type.equalsIgnoreCase("sickSoldier")) {
            numSickDays = new TextField();
            HBox numSickDaysBox = new HBox();
            View.alignTextField("Num Of Sickness Days: ", numSickDays, numSickDaysBox);
            isWearingProtectionSuit = new CheckBox("Protection Suit");
            formView.getChildren().addAll(numSickDaysBox, isWearingProtectionSuit);
            VBox.setMargin(numSickDaysBox, new Insets(0, 350, 0, 0));
        }
        if (type.equalsIgnoreCase("soldier") || type.equalsIgnoreCase("sickSoldier")) {
            carryWeapon = new CheckBox("Carrying Weapon");
            formView.getChildren().addAll(carryWeapon);
        }
        if (type.equalsIgnoreCase("nominee")) {
            party = new TextField();
            HBox partyBox = new HBox();
            View.alignTextField("Political Party: ", party, partyBox);
            formView.getChildren().add(partyBox);
            VBox.setMargin(partyBox, new Insets(0, 350, 0, 0));
        }
        submitButton = new Button("Submit");
        formView.getChildren().add(submitButton);
        formView.setSpacing(10);
    }

    public boolean getAllFilled() {
        if (name.getText().trim().isEmpty())
            return false;
        if (ID.getText().trim().isEmpty())
            return false;
        if (yearOfBirth.getText().trim().isEmpty())
            return false;
        if (type.equalsIgnoreCase("sick") || type.equalsIgnoreCase("sickSoldier")) {
            if (numSickDays.getText().trim().isEmpty())
                return false;
        }
        if (type.equalsIgnoreCase("nominee")) {
            return !party.getText().trim().isEmpty();
        }
        return true;
    }

}
