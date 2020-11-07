package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is used to track all the image changes.
 *
 * @see ImageManager
 * @see UsedName*
 */
public class Image implements Serializable {
    /**
     * The name of this image without the filename extension.
     */
    private String name;
    /**
     * The file path of this image.
     */
    private String path;
    /**
     * The filename extension of this image, which is the suffix after ".".
     */
    private String type;
    /**
     * A collection of tags that have been added to this image. This collection is represented by a ArrayList
     * Each tag is only added into this collection once, so there are no duplicated tags in this ArrayList.
     */
    private ArrayList<String> tags;
    /**
     * A collection of used names that have been added to this image including the original name of this image.
     * This collection is a ArrayList of {@link UsedName}.
     */
    private ArrayList<UsedName> renamingHistory;


    /**
     * Initializes image by passing in images information include name, path and filename extension.
     * Meanwhile, creates ArrayLists for collecting tags and all the names that are used by this image.
     * The full name (including the filename extension) of this image is added into the ArrayList
     * of used name as initialization.
     *
     * @param name The name of this Image not including the filename extension.
     * @param type the filename extension of this image.
     * @param path The path of this image represented by String.
     */
    public Image(String name, String type, String path) {
        this.name = name;
        this.type = type;
        this.tags = new ArrayList<>();
        this.path = path;
        this.renamingHistory = new ArrayList<>();
        this.renamingHistory.add(new UsedName(this.name + "." + this.type));
    }

    /**
     * Returns the filename Extension of this image represented by String.
     *
     * @return the filename extension.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the name of this image without the filename extension represented by String.
     *
     * @return the name of this images without the filename extension
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of this image by using giving new file name
     * Name is the name of this images without filename extension. It is initialized
     * as this {@link Image} object is generated, but can be reset by providing new image name using this method.
     *
     * @param newName new name of the image.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Returns an ArrayList of {@link #tags} of this image.
     *
     * @return an ArrayList of tags of this image.
     */
    public ArrayList<Tag> getTagList() {
        ArrayList<Tag> tagContainer = new ArrayList<>();
        for (String tag : this.tags) {
            tagContainer.add(new Tag(tag));
        }
        return tagContainer;
    }

    /**
     * Returns an ArrayList of names this image has ever used. The ArrayList is a collection of {@link UsedName}
     * for the convenience of interactions with GUI
     *
     * @return an ArrayList of used names represented by {@link UsedName} objects.
     */
    public ArrayList<UsedName> getRenamingHistory() {
        return renamingHistory;
    }

    /**
     * Adds the new name to this image's {@link #renamingHistory}.
     *
     * @param newName the name of the image that to be added to the ArrayList
     *                of the renaming history.
     */
    void addNewNameToHistory(String newName) {
        this.renamingHistory.add(new UsedName(newName + "." + this.type));
    }

    /**
     * Returns the string representation of this image's path.
     *
     * @return file path of this image.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Sets the file path of this image by using giving new path
     * File path of this image is initialized as this {@link Image} object is generated,
     * but can be reset by providing new path using this method.
     *
     * @param newPath Sets the image's path to be newPath.
     */
    public void setPath(String newPath) {
        this.path = newPath;
    }

    /**
     * Returns an collection of string representing the tags of this image.
     *
     * @return the collection of {@link #tags} of this image
     */
    public ArrayList<String> getTags() {
        return this.tags;
    }

    /**
     * Returns the full name of the image that includes filename extension.
     *
     * @return the full name includes the filename extension of this image
     */
    String getFullName() {
        return this.name + "." + this.type;
    }
}
