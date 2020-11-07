package model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements photo system that sets up TagManager,
 * ImageManager, DirectoryManager and logger. It also give access to get the TagManager,
 * ImageManager and DirectoryManager by getter method.
 *
 * @see TagManager
 * @see ImageManager
 * @see DirectoryManager
 * @see LoggerManager
 */
public class GoGoPhotoSystem {
    /**
     * A TagManager that manages Tag objects
     *
     * @see TagManager
     */
    private TagManager tagManager;

    /**
     * A ImageManager that manages Image objects
     *
     * @see ImageManager
     */
    private ImageManager imageManager;

    private FilterManager filterManager;

    /**
     * Creates a new logger to track the logging history.
     *
     * @see LoggerManager
     */
    public static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Creates a new DirectoryManager to operate and display the selected directory
     *
     * @see DirectoryManager
     */
    private DirectoryManager directoryManager;

    /**
     * Creates a new GoGoPhotoSystem with given tage and image  files' path.
     *
     * @param tagFilePath   a file path of storage file to read or create.
     * @param imageFilePath a file path of storage file to read or create.
     * @throws ClassNotFoundException indicates that initializing GoGoPhotoSystem fails.
     * @throws IOException            indicates that initializing GoGoPhotoSystem fails.
     */
    public GoGoPhotoSystem(String tagFilePath, String imageFilePath) throws ClassNotFoundException, IOException {

        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File tagManagerFile = new File(tagFilePath);
        File imageManagerFile = new File(imageFilePath);

        directoryManager = new DirectoryManager();
        filterManager = new FilterManager();
        imageManager = new ImageManager();
        tagManager = new TagManager();
        imageManager.addObserver(tagManager);


        if (tagManagerFile.exists()) {
            readFromTagManagerFile(tagFilePath);
        } else {
            tagManagerFile.createNewFile();
        }

        if (imageManagerFile.exists()) {
            readFromImageManagerFile(imageFilePath);
        } else {
            imageManagerFile.createNewFile();
        }
        MyFormatter formatter = new MyFormatter();
        logger.setLevel(Level.ALL);
        Handler fileHandler = new FileHandler("LogHis.txt", true);
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(fileHandler);
        fileHandler.setFormatter(formatter);


    }

    /**
     * Reads the stored file based on the input file path and assign deserialize result to tagManager.tags.
     *
     * @param path the path of the stored file
     * @throws ClassNotFoundException indicates that reading file from path fails
     */
    private void readFromTagManagerFile(String path) throws ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            //deserialize
            tagManager.setTags((HashMap<String, Integer>) input.readObject());
            input.close();
        } catch (IOException ex) {
            java.lang.System.out.println("Cannot read from input.");
        }
    }

    /**
     * Reads the stored file based on the input file path and assign deserialize result to imageManager.images.
     *
     * @param path the path where the file is read from.
     * @throws ClassNotFoundException indicates that read from path fails
     */
    private void readFromImageManagerFile(String path) throws ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            //deserialize
            imageManager.setImages((ArrayList<Image>) input.readObject());
            input.close();
        } catch (IOException ex) {
            java.lang.System.out.println("Cannot read from input.");
        }
    }


    /**
     * Saves the data to the input file path by calling saveToTagManagerFile method of tagManager.
     *
     * @param filePath the path to the location that the data is stored
     * @throws IOException indicates that saving to file path fails
     * @see TagManager
     */
    public void saveToTagManagerFile(String filePath) throws IOException {
        tagManager.saveToTagManagerFile(filePath);
    }


    /**
     * Saves the data to the input file path by calling saveToImageManagerFile method of imageManager.
     *
     * @param filePath the path to the location that the data is stored
     * @throws IOException indicates that saving to file path fails
     * @see ImageManager
     */
    public void saveToImageManagerFile(String filePath) throws IOException {
        imageManager.saveToImageManagerFile(filePath);
    }


    /**
     * Returns the directoryManager.
     *
     * @return the directoryManager that is returned
     */
    public DirectoryManager getDirectoryManager() {
        return directoryManager;
    }

    /**
     * Returns the tagManager
     *
     * @return the tagManager that is returned
     */
    public TagManager getTagManager() {
        return tagManager;
    }

    /**
     * Returns the imageManager.
     *
     * @return the imageManager that is returned
     */
    public ImageManager getImageManager() {
        return imageManager;
    }

    /**
     * Returns the FilterManager.
     *
     * @return the FilterManager that is returned
     */
    public FilterManager getFilterManager() {
        return filterManager;
    }
}