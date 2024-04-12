package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;


/**
 * Panel containing the details of the selected person.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonDetailsPanel.class);

    private Person displayedPerson;

    private HouseListPanel houseListPanel;

    @FXML
    private VBox personDetailsPane;
    @FXML
    private Label name;
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

    @FXML
    private VBox houseList;
    @FXML
    private StackPane houseListPanelPlaceholder;
    /**
     * Creates a {@code PersonDetailsPanel} with the given {@code ObservableList}.
     */
    public PersonDetailsPanel() {
        super(FXML);
    }

    public void setPersonDetails(Person person) {
        logger.info("----------------[SET PERSON DETAILS] setting person details");
        this.displayedPerson = person;
        setNameAndContactDetails();

        if (displayedPerson instanceof Buyer) {
            setBuyerDetails((Buyer) displayedPerson);
        } else if (displayedPerson instanceof Seller) {
            setSellerDetails((Seller) displayedPerson);
        }
    }

    private void setNameAndContactDetails() {
        name.setText(displayedPerson.getName().fullName);
        phone.setText(displayedPerson.getPhone().value);
        email.setText(displayedPerson.getEmail().value);
    }

    //@@author zengzihui
    private void setBuyerDetails(Buyer buyer) {
        budget.setVisible(true);
        housingType.setVisible(true);
        houseList.setVisible(false);

        housingType.setText(buyer.getPreferredHousingType().value);
        budget.setText("$" + buyer.getBudget().toString());
    }

    //@@author zengzihui
    private void setSellerDetails(Seller seller) {
        housingType.setVisible(false);
        budget.setVisible(false);
        houseList.setVisible(true);

        houseListPanel = new HouseListPanel(seller.getHouses());
        houseListPanelPlaceholder.getChildren().add(houseListPanel.getRoot());
    }


}




