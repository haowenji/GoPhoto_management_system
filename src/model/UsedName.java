package model;

import java.io.Serializable;

/**
 * This class is used to display every used name of an Image via GUI.
 *
 * @see Serializable
 * @see gui.ViewHistoryController
 * @see Serializable
 */
public class UsedName implements Serializable {
    /**
     * A used name of image
     */
    private String name;

    /**
     * Creates an UsedName object includes the name of the used name.
     *
     * @param name the name that is used to set.
     */
    UsedName(String name) {
        this.name = name;
    }

    /**
     * Gets the used name's name.
     *
     * @return the name of an used name of an image.
     */
    public String getName() {
        return this.name;
    }
}
