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
    private Label price;

    /**
     * Creates a {@code HouseCard} with the given {@code Seller} to display.
     */
    public HouseCard(House house) {
        super(FXML);
        this.house = house;
        postalCode.setText("Postal Code: " + house.getPostalCode().value);
        street.setText("Street: " + house.getStreet().value);
        unitNumber.setText("Unit Number: " + house.getUnitNumber().value);
        price.setText("Price: " + house.getPrice().value);
        block.setVisible(false);
        level.setVisible(false);

        if (house instanceof Hdb) {
            block.setVisible(true);
            level.setVisible(true);
            Hdb hdb = (Hdb) house;
            String blockValue = hdb.getBlock().value;
            blockValue = blockValue != null ? blockValue : "N/A";
            block.setText("Block: " + blockValue);

            String levelValue = hdb.getLevel().value;
            levelValue = levelValue != null ? levelValue : "N/A";
            level.setText("Level: " + levelValue);
        } else if (house instanceof Condominium) {
            block.setVisible(true);
            level.setVisible(true);
            Condominium condominium = (Condominium) house;
            String blockValue = condominium.getBlock().value;
            blockValue = blockValue != null ? blockValue : "N/A";
            block.setText("Block: " + blockValue);

            String levelValue = condominium.getLevel().value;
            levelValue = levelValue != null ? levelValue : "N/A";
            level.setText("Level: " + levelValue);
        }
    }
}
