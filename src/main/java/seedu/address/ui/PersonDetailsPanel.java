package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.house.HousingType;
import seedu.address.model.person.*;

import java.util.logging.Logger;

import static seedu.address.model.util.SampleDataUtil.getTagSet;

/**
 * Panel containing the details of the selected person.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonDetailsPanel.class);

    public Person displayedPerson;

    @FXML
    private VBox personDetailsPane;
    @FXML
    private Label name;
    //    @FXML
//    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label housingType;
    @FXML
    private Label budget;
    @FXML
    private FlowPane tags;


    /**
     * Creates a {@code PersonDetailsPanel} with the given {@code ObservableList}.
     */
    public PersonDetailsPanel() {
        super(FXML);
    }

    public void setPersonDetails(Person person) {
        this.displayedPerson = person;
        name.setText(displayedPerson.getName().fullName);
        phone.setText(displayedPerson.getPhone().value);
        email.setText(displayedPerson.getEmail().value);
        housingType.setText(displayedPerson.getHousingType().value);

        if (displayedPerson instanceof Buyer) {
            Buyer buyer = (Buyer) displayedPerson;
            budget.setText("$" + buyer.getBudget().toString());
        }
    }

}




