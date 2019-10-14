package seedu.address.ui.diary;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import seedu.address.ui.UiPart;

/**
 * Custom JavaFX component controller for the edit box in the diary page.
 * It is backed by a {@link TextArea}.
 */
public class DiaryEditBox extends UiPart<VBox> {
    private static final String FXML = "diary/DiaryEditBox.fxml";

    private Consumer<String> textChangeHandler;

    @FXML
    private TextArea textEditor;

    DiaryEditBox(Consumer<String> textChangeHandler) {
        super(FXML);
        this.textChangeHandler = textChangeHandler;
    }

    String getText() {
        return textEditor.getText();
    }

    void setText(String text) {
        textEditor.setText(text);
    }

    @FXML
    private void handleTextChange() {
        textChangeHandler.accept(textEditor.getText());
    }
}
