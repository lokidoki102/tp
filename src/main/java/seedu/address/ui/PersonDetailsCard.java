package seedu.address.ui;

import java.util.Comparator;
import javax.swing.plaf.synth.Region;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Person;

import java.awt.*;

public class PersonDetailsCard extends UiPart<Region> {
    private static final String FXML = "PersonDetailsCard.fxml";
    public final Person displayedPerson;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
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
     * Create a {@code PersonDetailsCard} with the given {@code Person} and index to display.
     */
    public PersonDetailsCard(Person person) {
        super(FXML);
        this.displayedPerson = person;
        name.setText(displayedPerson.getName().fullName);
        phone.setText(displayedPerson.getPhone().value);
        email.setText(displayedPerson.getEmail().value);
        housingType.setText(displayedPerson.getHousingType().value);

        if (person instanceof Buyer) {
            Buyer buyer = (Buyer) person;
            budget.setText("$" + buyer.getBudget().toString());
        }
    }


}
