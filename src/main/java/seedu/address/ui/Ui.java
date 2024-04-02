package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.model.State;
import seedu.address.model.person.Person;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Displays Person details */
    void showPersonDetails(Person person);

    void updateUiLayout(State newState);

}
