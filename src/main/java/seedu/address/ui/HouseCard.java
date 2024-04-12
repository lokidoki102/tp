package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.house.Condominium;
import seedu.address.model.house.Hdb;
import seedu.address.model.house.House;

/**
 * An ui component that displays information of a {@code House}.
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

        setHouseType();
        setPostalCode();
        setPrice();
        setAddress();
    }

    //@@author zengzihui
    private void setHouseType() {
        houseType.setText("House Type: " + house.getHousingType().value);
    }

    //@@author zengzihui
    private void setPostalCode() {
        postalCode.setText("Postal Code: " + house.getPostalCode().value);
    }

    //@@author zengzihui
    private void setPrice() {
        price.setText("Price: $" + house.getPrice().value);
    }

    //@@author zengzihui
    private void setAddress() {
        String addr = house.getUnitNumber().value + " " + house.getStreet().value;
        if (house instanceof Hdb) {
            addr = getAddressForHdb((Hdb) house);
        } else if (house instanceof Condominium) {
            addr = getAddressForCondominium((Condominium) house);
        }
        address.setText(addr);
    }

    //@@author zengzihui
    private String getAddressForHdb(Hdb hdb) {
        String blockValue = hdb.getBlock().value;
        String levelValue = hdb.getLevel().value;
        return blockValue + " "
                + hdb.getStreet().value + " "
                + "#" + levelValue + "-" + hdb.getUnitNumber();
    }

    //@@author zengzihui
    private String getAddressForCondominium(Condominium condominium) {
        String blockValue = condominium.getBlock().value;
        String levelValue = condominium.getLevel().value;
        if (blockValue == null || blockValue.equals("N/A")) {
            return condominium.getStreet().value + " "
                    + "#" + levelValue + " - " + condominium.getUnitNumber();
        } else {
            return blockValue + " "
                    + condominium.getStreet().value + " "
                    + "#" + levelValue + " - " + condominium.getUnitNumber();
        }
    }
}
