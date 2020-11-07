package model;

import filter.Filter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is created to use open-sourced filter functions to create new images with
 * selected filter effect on picture. AddFilterController class in GUI package uses the FilterManager
 * to create an image with filter effect.
 *
 * @see filter.Filter
 * @see gui.AddFilterController
 */
public class FilterManager {
    /**
     * Constructs a new FilterManager
     */
    public FilterManager() {
    }

    /**
     * Creates a new image file which is processed after adding the selected filter on the selected
     * image file. The image is created on the same path as the original image, but with an additional
     * "_copy" on its file name. This method is used via gui.AddFilterController.
     *
     * @param imagePath      The path of the selected image.
     * @param filterFunction The filter effect that will be added on the selected image.
     * @see filter.Filter
     * @see gui.AddFilterController
     */
    public void FilterPreview(String imagePath, Filter filterFunction) {
        BufferedImage image = null;
        BufferedImage afterFiltered = null;
        File f = null;

        //read image file
        try {
            f = new File(imagePath);
            image = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        //write filter
        try {
            String newPath = imagePath.substring(0, imagePath.lastIndexOf("."));
            String imageType = imagePath.substring(imagePath.lastIndexOf(".") + 1);
            newPath = newPath + "_copy" + "." + imageType;
            f = new File(newPath);
            afterFiltered = filterFunction.filter(image, afterFiltered);
            ImageIO.write(afterFiltered, imageType, f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }


    /**
     * Deleted the original image file which does not have filtered effect on it.
     * Called when the user chooses save the image with filter effect.
     * Called within gui.AddFilterControllern class.
     * Save the filtered image while deleting the original one.
     *
     * @param imagePath The path of the selected image.
     * @throws Exception Throws Exception when there is no such file to delete.
     * @see gui.AddFilterController
     */
    public void deleteOriginalImage(String imagePath) {
        try {
            File file = new File(imagePath);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
