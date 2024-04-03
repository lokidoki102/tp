package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.house.*;
import seedu.address.model.person.Seller;

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
            String block_value = hdb.getBlock().value;
            block_value = block_value != null ? block_value : "N/A";
            block.setText("Block: " + block_value);

            String level_value = hdb.getLevel().value;
            level_value = level_value != null ? level_value : "N/A";
            level.setText("Level: " + level_value);
        } else if (house instanceof Condominium) {
            block.setVisible(true);
            level.setVisible(true);
            Condominium condominium = (Condominium) house;
            String block_value = condominium.getBlock().value;
            block_value = block_value != null ? block_value : "N/A";
            block.setText("Block: " + block_value);

            String level_value = condominium.getLevel().value;
            level_value = level_value != null ? level_value : "N/A";
            level.setText("Level: " + level_value);
        }
    }
}
