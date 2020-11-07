package model;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * This class generates a collection of image files located in or anywhere under the selected directory
 * The DirectoryManager program implements an application that generate a collection of
 * image files(as an ExistingImage objects) that located in or anywhere under the selected directory
 *
 * @see ExistingImage
 */
public class DirectoryManager {
    /**
     * Constructs a new DirectroyManager
     */
    public DirectoryManager() {
    }

    /**
     * Returns a list of image files located in the selected Directory.
     *
     * @param path the path of the selected file
     * @return a collection of images located in the selected Directory in an ArrayList of ExistingImage.
     * @throws IOException
     * @see ExistingImage
     */
    public ArrayList<ExistingImage> findLocalImages(String path) throws IOException {
        ArrayList<ExistingImage> imageCollection = new ArrayList<>();
        File[] files = new File(path).listFiles();
        for (int i = 0; i < files.length; i++) {
            if ((!files[i].isDirectory()) && isImage(files[i])) {
                String imageName = files[i].getName();
                imageCollection.add(new ExistingImage(imageName, files[i].getPath()));
            }
        }
        return imageCollection;
    }

    /**
     * Returns any image files existed under selected file(including the ones in the sub-directories under
     * the selected file by recursively searching.
     *
     * @param path The path of the selected file.
     * @return a collection of any images located under the selected Directory(including
     * the ones in sub-directories of the selected directory) in an ArrayList of ExistingImage.
     * @throws IOException
     * @see ExistingImage
     */
    public ArrayList<ExistingImage> findAllImages(String path) throws IOException {
        File dir = new File(path);
        if (!dir.isDirectory()) {
            return new ArrayList<>();
        } else {
            ArrayList<ExistingImage> imageCollection = findLocalImages(path);
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    imageCollection = arrayConcatenation(imageCollection, findAllImages(files[i].getPath()));
                }
            }
            return imageCollection;
        }
    }

    /**
     * A helper function of findAllImages(String path) for the recursion processes by concatenate two ArrayList
     * of ExistingImage
     *
     * @param arr1 the first ArrayList of ExistingImage that needed to be concatenated
     * @param arr2 the second ArrayList of ExistingImage that needed to be concatenated
     * @return a collection of any images in an ArrayList of ExistingImage by concatenating two ArrayLists
     * of ExistingImage.
     * @see{@link} findAllImages
     */
    private ArrayList<ExistingImage> arrayConcatenation(ArrayList<ExistingImage> arr1, ArrayList<ExistingImage> arr2) {
        ArrayList<ExistingImage> arrLst = new ArrayList<>();
        arrLst.addAll(arr1);
        arrLst.addAll(arr2);
        return arrLst;
    }

    /**
     * Returns if the input file is a image type
     *
     * @param file
     * @return boolean value that indicates if the input file is image type.
     * @throws IOException
     */
    private boolean isImage(File file) {
        try {
            return (ImageIO.read(file) != null);
        } catch (IOException e) {
            return false;
        }
    }

}
