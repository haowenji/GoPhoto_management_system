package model;

/**
 * This class is used for creating Tag objects in order to display all the tag's name via GUI.
 *
 * @see gui.AddTagController
 */
public class Tag {

    /**
     * A name of the tag.
     */
    private String name;

    /**
     * Tag's constructor that includes the name of the tag. This includes "@" as the prefix of
     * the name.
     *
     * @param name the name of the tag.
     */
    Tag(String name) {
        this.name = "@" + name;
    }

    /**
     * Gets the name of the tag. This includes "@" as the prefix of the name.
     *
     * @return the name of the tag.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the naked name of the tag. This doesn't include "@" as the prefix of the name.
     *
     * @return the naked name of the tag. This doesn't include "@" as the prefix of the name.
     */
    public String getOriginalName() {
        return this.name.substring(1);
    }
}
