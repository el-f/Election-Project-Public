package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Arrays;

public class TypeForm extends Form {
    private final RadioButton regular, sick, soldier, sickSoldier;

    public TypeForm() {
        ToggleGroup toggleGroup = new ToggleGroup();
        regular = new RadioButton("Citizen");
        regular.setSelected(true);
        sick = new RadioButton("Sick Citizen");
        soldier = new RadioButton("Soldier");
        sickSoldier = new RadioButton("Sick Soldier");
        submitButton = new Button("Submit Type");
        Text text = new Text("Please Choose Type:");
        formView = new VBox(text, regular, sick, soldier, sickSoldier, submitButton);
        formView.setAlignment(Pos.CENTER_LEFT);
        formView.setSpacing(10);
        formView.getChildren()
                .forEach(child -> {
                    VBox.setMargin(child, new Insets(0, 0, 0, 400));
                    if (child instanceof RadioButton) {
                        ((RadioButton) child).setToggleGroup(toggleGroup);
                        View.addCursorHandling((RadioButton) child, formView);
                    }
                });
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
