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
        postalCode.setText(house.getPostalCode().value);
        street.setText(house.getStreet().value);
        unitNumber.setText(house.getUnitNumber().value);
        price.setText(house.getPrice().value);

        if (house instanceof Hdb) {
            Block block = ((Hdb) house).getBlock();
            Level level = ((Hdb) house).getLevel();
            houseCard.getChildren().add(new Label("Block: " + (block != null ? block.value : "N/A")));
            houseCard.getChildren().add(new Label("Level: " + (level != null ? level.value : "N/A")));
        } else if (house instanceof Condominium) {
            Block block = ((Condominium) house).getBlock();
            Level level = ((Condominium) house).getLevel();
            houseCard.getChildren().add(new Label("Block: " + (block != null ? block.value : "N/A")));
            houseCard.getChildren().add(new Label("Level: " + (level != null ? level.value : "N/A")));
        }
    }

}
