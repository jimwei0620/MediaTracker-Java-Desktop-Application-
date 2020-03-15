package ui.consistency;

import javafx.scene.text.Text;

public class ErrorTextHandler {

    // EFFECTS: clear error text
    public static void clearErrorText(Text errorText) {
        errorText.setText("");
    }

    // EFFECTS: set error text to internal error
    public static void internalError(Text errorText) {
        errorText.setText("Internal Error!");
    }

    //EFFECTS: set error text to "Name cannot be empty"
    public static void emptyNameError(Text errorText) {
        errorText.setText("Name cannot be empty!");
    }

    //EFFECTS: set error text to "type with that name already exists!"
    public static void keyAlreadyExistsError(String type, Text errorText) {
        errorText.setText(type + " with that name already exists!");
    }

    //EFFECTS: set error text to "nothing is selected!"
    public static void nothingSelectedError(Text errorText) {
        errorText.setText("Nothing is selected!");
    }

}
