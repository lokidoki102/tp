package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import javafx.scene.image.Image;
import seedu.address.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    /**
     * Gets an {@code Image} from the specified path.
     */
    public static Image getImage(String imagePath) {
        requireNonNull(imagePath);
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     * Checks that {@code isValid} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException if {@code isValid} is false.
     */
    public static void checkArgument(Boolean isValid) {
        if (!isValid) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks that {@code isValid} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException with {@code errorMessage} if {@code isValid} is false.
     */
    public static void checkArgument(Boolean isValid, String errorMessage) {
        if (!isValid) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
