package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.house.Condominium;
import seedu.address.model.house.Hdb;
import seedu.address.model.house.House;

/**
 * An UI component that displays information of a {@code House}.
 */
public class HouseCard extends UiPart<Region> {
    private static final String FXML = "HouseListCard.fxml";

    public final House house;

    @FXML
    private VBox houseCard;

    @FXML
    private Label houseType;

    @FXML
    private Label postalCode;

    @FXML
    private Label street;

    @FXML
    private Label unitNumber;

    @FXML
    private Label block;

    @FXML
    private Label level;
    @FXML
    private Label address;

    @FXML
    private Label price;

    /**
     * Creates a {@code HouseCard} with the given {@code Seller} to display.
     */
    public HouseCard(House house) {
        super(FXML);
        this.house = house;
        houseType.setText("House Type: " + house.getHousingType().value);
        postalCode.setText("Postal Code: " + house.getPostalCode().value);
        price.setText("Price: " + house.getPrice().value);
        String addr = house.getUnitNumber().value + " "
                        + house.getStreet().value;
        if (house instanceof Hdb) {
            Hdb hdb = (Hdb) house;
            String blockValue = hdb.getBlock().value;
            String levelValue = hdb.getLevel().value;
            addr = blockValue + " "
                    + hdb.getStreet().value + " "
                    + "#" + levelValue + "-" + hdb.getUnitNumber();
        } else if (house instanceof Condominium) {
            Condominium condominium = (Condominium) house;
            String blockValue = condominium.getBlock().value;
            String levelValue = condominium.getLevel().value;
            if (blockValue == null || blockValue.equals("N/A")) {
                addr = condominium.getStreet().value + " "
                        + "# " + levelValue + " - " + condominium.getUnitNumber();
            } else {
                addr = blockValue + " "
                        + condominium.getStreet().value + " "
                        + "# " + levelValue + " - " + condominium.getUnitNumber();
            }
        }
        address.setText(addr);

    }
}
