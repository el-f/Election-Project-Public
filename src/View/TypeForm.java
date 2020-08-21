package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TypeForm extends Form {
    private final RadioButton regular;
    private final RadioButton sick;
    private final RadioButton soldier;
    private final RadioButton sickSoldier;

    public TypeForm() {
        ToggleGroup toggleGroup = new ToggleGroup();
        regular = new RadioButton("Citizen");
        regular.setToggleGroup(toggleGroup);
        regular.setSelected(true);
        sick = new RadioButton("Sick Citizen");
        sick.setToggleGroup(toggleGroup);
        soldier = new RadioButton("Soldier");
        soldier.setToggleGroup(toggleGroup);
        sickSoldier = new RadioButton("Sick Soldier");
        sickSoldier.setToggleGroup(toggleGroup);
        submitButton = new Button("Submit Type");
        Text text = new Text("Please Choose Type:");
        formView = new VBox(text, regular, sick, soldier, sickSoldier, submitButton);
        formView.setAlignment(Pos.CENTER_LEFT);
        formView.setSpacing(10);
        VBox.setMargin(text,new Insets(0,0,0,400));
        VBox.setMargin(regular,new Insets(0,0,0,400));
        VBox.setMargin(sick,new Insets(0,0,0,400));
        VBox.setMargin(soldier,new Insets(0,0,0,400));
        VBox.setMargin(sickSoldier,new Insets(0,0,0,400));
        VBox.setMargin(submitButton,new Insets(0,0,0,400));

    }

    public String getType() {
        if (regular.isSelected())
            return "regular";
        else if (sick.isSelected())
            return "sick";
        else if (soldier.isSelected())
            return "soldier";
        else if (sickSoldier.isSelected())
            return "sickSoldier";
        else return null;
    }

    public boolean getIsAtLeastOneSelected() {
        return regular.isSelected() || sick.isSelected() || soldier.isSelected() || sickSoldier.isSelected();
    }

}
