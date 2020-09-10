package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class Form {
    public VBox formView;
    protected Button submitButton;

    public void addEventHandlerToSubmitButton(EventHandler<ActionEvent> eventHandler) {
        submitButton.setOnAction(eventHandler);
        View.addCursorHandling(submitButton, formView);
        formView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                submitButton.fire();
            }
        });
    }

}
