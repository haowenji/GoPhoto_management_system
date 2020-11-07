package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.logging.Level;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * This class is responsible for move a image to another directory,
 * rename an image, add tag to an image's name,
 * delete tag from an image's name(while keep the original suffix).
 * This class is also responsible for renaming an Image back to one of its used name.
 * Besides, it also serializes the ALL images in the arraylist of ImageManager.
 * This helps GUI to display all the images in the arraylist.
 *
 * @see TagManager
 * @see Image
 * @see Observable
 * @see Serializable
 * @see gui.AddTagController
 * @see gui.ListAllController
 * @see gui.LocalOnlyController
 * @see gui.ViewHistoryController
 * @see gui.ViewImageController
 * @see Observable
 * @see Serializable
 */
public class ImageManager extends Observable implements Serializable {
    /**
     * an ArrayList of Image.
     */
    private ArrayList<Image> images;

    /**
     * Initializes ImageManager object, meanwhile, creating an Arraylist that is used
     * to hold images.
     */
    ImageManager() {
        this.images = new ArrayList<>();
    }

    /**
     * Adds an image to the ArrayList of ImageManager.
     *
     * @param image the picture to be added to the ArrayList
     */
    public void addImage(Image image) {
        String[] nameParts = image.getName().split(" @");
        if (nameParts.length > 1) {
            images.add(image);
            for (int i = 1; i < nameParts.length; i++) {
                updateUpTags(image, nameParts[i]);
            }
        }
    }

    /**
     * Adds input image to images and sets up tags of the image.
     *
     * @param image an image that need to be added.
     */
    public void addChosenImage(Image image) {
        String[] nameParts = image.getName().split(" @");
        images.add(image);
        for (int i = 1; i < nameParts.length; i++) {
            updateUpTags(image, nameParts[i]);
        }
    }


    /**
     * Gets the Image object given a file path. returns null if the image object
     * doesn't exist.
     *
     * @param path the path given to find the Image object.
     * @return the Image object given a file path. returns null if the image object
     * cannot be found
     */
    public Image foundImage(String path) {
        for (int i = 0; i < this.images.size(); i++) {
            if (this.images.get(i).getPath().equals(path)) {
                return this.images.get(i);
            }
        }
        return null;
    }

    /**
     * Changes the selected image's actual file name to newName. The given newName does not include the file type
     * suffix. Adds the newName to the image's renamingHistory and the path of the image changes accordingly.
     *
     *
     * @param image   the image that need to be renamed.
     * @param newName the new name that will be used for the image.
     */
    void renameFile(Image image, String newName) {
        String oldPath = image.getPath().toString();
        String newPath = oldPath.substring(0, oldPath.lastIndexOf(File.separator));
        newPath = newPath + File.separator + newName + "." + image.getType();
        File oldFile = new File(oldPath);
        image.setName(newName);
        image.setPath(newPath);
        image.addNewNameToHistory(newName);
        File newFile = new File(newPath);
        oldFile.renameTo(newFile);

    }

    /**
     * Moves image file to a new location. Also, sets the new path to image.
     *
     * @param image    the image that need to be moved
     * @param new_path the new location path
     * @throws IOException indicates that moving image file to new location fails.
     */
    public void moveImageToDirectory(Image image, String new_path) throws IOException {
        String newImagePath = new_path + File.separator + image.getName() + "." + image.getType();
        Files.move(Paths.get(image.getPath()), Paths.get(newImagePath), REPLACE_EXISTING);
        image.setPath(newImagePath);
    }

    /**
     * Sets image name as a old version name(input name). And adjusts corresponding tag records in image's tag set.
     * Also, records the renaming record.
     *
     * @param image the image that need to be resetted name
     * @param name  the name that the image's name  need to be set.
     * @see GoGoPhotoSystem
     */
    public void backToOldName(Image image, String name) {
        String oldName = name.substring(0, name.lastIndexOf("."));
        String curName = image.getName();
        ArrayList<String> curTags = (ArrayList<String>) image.getTags().clone();
        renameFile(image, oldName);
        String[] oldTagsArray = oldName.split(" @");
        ArrayList<String> oldTags = new ArrayList<>();
        oldTags.addAll(Arrays.asList(oldTagsArray).subList(1, oldTagsArray.length));
        for (String tag : curTags) {
            if (!oldTags.contains(tag)) {
                updateDownTags(image, tag);
            }
        }
        for (String tag : oldTags) {
            if (!curTags.contains(tag)) {
                updateUpTags(image, tag);
            }
        }
        GoGoPhotoSystem.logger.log(Level.FINE, "Changes from current name: " + curName +
                ", to new name: " + image.getFullName());
    }

    /**
     * Delete a tag from the given image. The given tag name to be deleted doesn't include "@" prefix. When this method
     * is called, the actual image file's name is also updated. Also, the new file name is added to the
     * renamingHistory and the tag is deleted from the image's tag set. TagManager will then be notified and update its
     * tag set as the tag is deleted from the Image.
     *
     * @param image the image that is manipulated
     * @param tag   the tag that is deleted.
     */
    public void deleteTagFromName(Image image, String tag) {

        String[] nameParts = image.getName().split(" @");
        StringBuilder newFileName = new StringBuilder();
        newFileName.append(nameParts[0]);
        for (int i = 1; i < nameParts.length; i++) {
            if (!nameParts[i].equals(tag)) {
                newFileName.append(" @");
                newFileName.append(nameParts[i]);
            }
        }
        String oldName = image.getFullName();
        this.renameFile(image, newFileName.toString());
        updateDownTags(image, tag);
        GoGoPhotoSystem.logger.log(Level.FINE, "Old Name: " + oldName + ", New Name: " + image.getFullName());


    }

    /**
     * Adds a tag to the given image. The given tag name to be added doesn't include "@" prefix. When this method is
     * called, the actual image file's name is also updated. Also, the new name is added to the renamingHistory and the
     * tag is added to the image's tag set. TagManager will then be notified and update its tag set as a new tag is
     * added to an Image.
     *
     * @param image The image to be added tag on
     * @param tag   the tag to be added on.
     * @see Observable
     * @see TagManager
     */
    public void addTagToName(Image image, String tag) {
        String oldName = image.getFullName();
        String newFileName = image.getName() + " @" + tag;
        this.renameFile(image, newFileName);
        updateUpTags(image, tag);
        GoGoPhotoSystem.logger.log(Level.FINE, "Old Name: " + oldName + ", New Name" + image.getFullName());
    }

    /**
     * Saves the images(the arraylist of images in the ImageManager) to the given filePath
     *
     * @param filePath saves the image to the given path
     * @throws IOException indicates that Saving the images fails.
     */
    void saveToImageManagerFile(String filePath) throws IOException {
        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        // serialize the imageManager
        output.writeObject(images);
        output.close();
    }


    /**
     * Returns whether the given image's tags include the newTag.
     *
     * @param image  the image to be checked whether it has the given newTag
     * @param newTag the tag to be check whether the image has
     * @return returns true if the given image's tags include the newTag.
     */
    public boolean foundTag(Image image, String newTag) {
        for (Tag t : image.getTagList()) {
            if (t.getName().equals("@" + newTag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds input tag to image's tag set. And it will notify the change to all observers.
     *
     * @param image the image that is manipulated.
     * @param tag   the tag that is added to the image.
     * @see Observable
     * @see TagManager
     */
    private void updateUpTags(Image image, String tag) {
        image.getTags().add(tag);
        String[] tagAndType = {tag, "U"};
        setChanged();
        notifyObservers(tagAndType);
        //add a suffix "U" here to let Observers know that we want to update up
    }

    /**
     * Removes input tag from image's tag set. And it will notify the change to all observers.
     *
     * @param image the image that is manipulated.
     * @param tag   the tag that is removed to the image.
     * @see Observable
     * @see TagManager
     */
    private void updateDownTags(Image image, String tag) {
        image.getTags().remove(tag);
        String[] tagAndType = {tag, "D"};
        setChanged();
        notifyObservers(tagAndType);
        //add a suffix "D" here to let Observers know that we want to update down
    }

    /**
     * Sets input images as ImageManager's image set.
     *
     * @param images the image set that is set as ImageManager's image set.
     * @see Observable
     * @see TagManager
     * @see GoGoPhotoSystem
     */
    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    /**
     * Returns ImageManager's image set.
     *
     * @return the ImageManager's image set.
     */
    public ArrayList<Image> getImages() {
        return images;
    }
}
