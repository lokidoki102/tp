package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.house.House;


/**
 * Panel containing the list of houses.
 */
public class HouseListPanel extends UiPart<Region> {
    private static final String FXML = "HouseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(HouseListPanel.class);

    @FXML
    private ListView<House> houseListView;

    /**
     * Creates a {@code HouseListPanel} with the given {@code ObservableList}.
     */
    public HouseListPanel(ObservableList<House> houseList) {
        super(FXML);
        houseListView.setItems(houseList);
        houseListView.setCellFactory(listView -> new HouseListPanel.HouseListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code House} using a {@code HouseCard}.
     */
    class HouseListViewCell extends ListCell<House> {
        @Override
        protected void updateItem(House house, boolean empty) {
            super.updateItem(house, empty);

            if (empty || house == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HouseCard(house).getRoot());
            }
        }
    }
}
