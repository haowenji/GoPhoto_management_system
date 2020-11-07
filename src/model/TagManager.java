package model;

import java.io.*;
import java.lang.System;
import java.util.*;

/**
 * This class manipulates tags which are associated with pictures in system.
 *
 * @see Observer
 * @see Serializable
 * @see GoGoPhotoSystem
 * @see ImageManager
 * @see Observer
 * @see Serializable
 */
public class TagManager implements Observer, Serializable {

    /**
     * A tag hash map which stores and records tags information.
     */
    private HashMap<String, Integer> tags;

    /**
     * Creates a TagManager.
     */
    TagManager() {
        tags = new HashMap<>();
    }


    /**
     * Saves the tags into the input filePath.
     *
     * @param filePath the filepath that the tags are saved to.
     * @throws IOException indicates that Saving operation fails.
     */
    void saveToTagManagerFile(String filePath) throws IOException {
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the tagManager
        output.writeObject(tags);
        output.close();
    }

    /**
     * Returns a ArrayList of existing Tags in our system.
     *
     * @return the ArrayList of existing Tags in our system.
     * @see Tag
     */
    public ArrayList<Tag> getExistingTags() {
        Set<String> tagSet = tags.keySet();
        ArrayList<Tag> res = new ArrayList<>();
        for (String s : tagSet) {
            res.add(new Tag(s));

        }
        return res;
    }

    /**
     * Updates the tags information based on the inputs. If the input arg contains "U", it means this method will add
     * a new tag or increase the value of the tag by 1. Otherwise, this methods will decrease the value of the tag by 1.
     *
     * @param o   the Observable object which sends the notify.
     * @param arg the information(Array of String) which will be used to update tag's information.
     *            the first element of this Array is the tag name.
     *            the second element of this Array indicates that if it should add up tag's value or reduce it.
     * @see ImageManager
     */
    @Override
    public void update(Observable o, Object arg) {
        String key = ((String[]) arg)[0];
        String upOrDown = ((String[]) arg)[1];
        if (Objects.equals(upOrDown, "U")) {
            if (!tags.containsKey(key)) {
                tags.put(key, 1);
            } else {
                tags.put(key, tags.get(key) + 1);
            }
        } else {
            Integer value = tags.get(key);
            tags.put(key, value - 1);
        }
    }


    /**
     * Adds new tag to hash map(tags) with default value 0.
     *
     * @param newTag the tag that is added
     */
    public void addNewTag(String newTag) {
        tags.put(newTag, 0);
    }

    /**
     * Returns hash map tags.
     *
     * @return the hash map tags which store the tags information in our system.
     */
    public HashMap<String, Integer> getTags() {
        return tags;
    }

    /**
     * Sets input(hash map) to the tags.
     *
     * @param tags a hash map which stores tags information
     */
    public void setTags(HashMap<String, Integer> tags) {
        this.tags = tags;
    }

    /**
     * Deletes the input tag from hash map tags if the corresponding value of this tag in hash map is equal to 0.
     *
     * @param tag the tag need to be removed from tags
     * @return if the tag is removed successfully.
     */
    public boolean deleteTag(String tag) {
        Integer value = tags.get(tag);
        if (value == 0) {
            tags.remove(tag);
            return true;
        }
        return false;
    }
}



