package gui;

import filter.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.FilterManager;
import model.Image;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is a controller to be responsible for preview the filter effect on an image file
 * and save the filter effect of the original image file. When the user decide to save the change,
 * the original image file is replaced. Turns to AddTagScene when goBack button is clicked. It implements the interfaces
 * Initializable and ChangeScene.
 *
 * @see Button
 * @see model.Image
 * @see model.FilterManager
 * @see ChangeScene
 * @see Initializable
 */
public class AddFilterController implements Initializable, ChangeScene {

    /**
     * the image view for displaying the chosen image.
     */
    @FXML
    ImageView imageView;

    /**
     * the button for changing the scene from image preview scene to addButton scene.
     */
    @FXML
    Button goBack;

    /**
     * the Filter effect selected.
     */
    private Filter curFilter;

    /**
     * Changes the scene to AddTagScene when go back button is clicked.
     *
     * @throws IOException Indicates changing scene fails.
     */
    public void goBackButtonClicked() throws IOException {
        changeScene(goBack, "../scenes/AddTagScene.fxml");
    }

    /**
     * Saves the image with the filter effect on the same path as the original image file.
     * This is done by creating a filtered image by using FilterPreview method in FilterManager
     * class. Then, deletes the original image file by calling deleteOriginalImage in
     * FilterManager class. Finally, renames the generated filter image to the same name as
     * the original image file. Finally, pops out an alert box to notify the users that the filter
     * is added on the image successfully.
     *
     * @see model.FilterManager
     * @see AlertBox
     * @see File
     */

    public void saveButtonClicked() {
        if (curFilter != null) {
            Image image = AddTagController.getChosenImage();
            String oldPath = image.getPath();
            FilterManager filterManager = Main.system.getFilterManager();
            filterManager.FilterPreview(oldPath, curFilter);
            filterManager.deleteOriginalImage(oldPath);
            File oldFile = new File(oldPath);
            File newFile = new File(makeCopyPath(image));
            newFile.renameTo(oldFile);
            AlertBox.display("GoGoPhoto", "Save the change successfully!");
        }
    }

    /**
     * Draws the chosen image with no filter effect.
     *
     * @see File
     * @see javafx.scene.image.Image
     * @see ImageView
     */
    public void originalButtonClicked() {
        String path = AddTagController.getChosenImage().getPath();
        File f = new File(path);
        javafx.scene.image.Image chosenImage = null;
        try {
            chosenImage = new javafx.scene.image.Image(f.toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        imageView.setImage(chosenImage);
        curFilter = null;
    }

    /**
     * Creates a image with Emboss filter effect with the same image path as the original image
     * file. The original image file is then be replaced.
     *
     * @see EmbossFilter
     */
    public void embossButtonClicked() {
        EmbossFilter embossFilter = new EmbossFilter();
        drawAfterFilter(embossFilter);
    }

    /**
     * Creates a image with Stamp filter effect with the same image path as the original image
     * file. The original image file is then be replaced.
     *
     * @see StampFilter
     */
    public void stampButtonClicked() {
        StampFilter stampFilter = new StampFilter();
        drawAfterFilter(stampFilter);
    }

    /**
     * Creates a image with Equalize filter effect with the same image path as the original image
     * file. The original image file is then be replaced.
     *
     * @see EqualizeFilter
     */
    public void equalizeButtonClicked() {
        EqualizeFilter equalizeFilter = new EqualizeFilter();
        drawAfterFilter(equalizeFilter);
    }

    /**
     * Creates a image with Pointillize filter effect with the same image path as the original image
     * file. The original image file is then be replaced.
     *
     * @see PointillizeFilter
     */
    public void pointillizeButtonClicked() {
        PointillizeFilter pointillizeFilter = new PointillizeFilter();
        drawAfterFilter(pointillizeFilter);
    }

    /**
     * Creates a image with Sparkle filter effect with the same image path as the original image
     * file. The original image file is then be replaced.
     *
     * @see SparkleFilter
     */
    public void sparkleButtonClicked() {
        SparkleFilter sparkleFilter = new SparkleFilter();
        drawAfterFilter(sparkleFilter);
    }
    /**
     * Creates a image with Marble filter effect with the same image path as the original image
     * file. The original image file is then be replaced.
     *
     * @see MarbleFilter
     */
    public void marbleButtonClicked() {
        MarbleFilter marbleFilter = new MarbleFilter();
        drawAfterFilter(marbleFilter);
    }

    /**
     * Creates a image with JavaLnF filter effect with the same image path as the original image
     * file. The original image file is then be replaced.
     *
     * @see JavaLnFFilter
     */
    public void javaLnFButtonClicked() {
        JavaLnFFilter javaLnFFilter = new JavaLnFFilter();
        drawAfterFilter(javaLnFFilter);
    }

    /**
     * Creates a image with oil filter effect with the same image path as the original image
     * file. The original image file is then be replaced.
     *
     * @see OilFilter
     */
    public void oilButtonClicked() {
        OilFilter oilFilter = new OilFilter();
        drawAfterFilter(oilFilter);
    }

    /**
     * Initializes the scene and draw the original picture  that the user chooses.
     *
     * @param location  default
     * @param resources default
     * @see ImageView
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String path = AddTagController.getChosenImage().getPath();
        File f = new File(path);
        javafx.scene.image.Image chosenImage = null;
        try {
            chosenImage = new javafx.scene.image.Image(f.toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        imageView.setImage(chosenImage);
    }

    /**
     * Creates a path with an extra "_copy" in the file name of the given image.
     * This path is used when an Filtered image is created.
     *
     * @param image The original image. It's path will be extracted then.
     * @return A string with the given image's path but with a different file name. The last part
     *         of the path, the complete file name, is represented as the original image's name without
     *         file extension, + "_copy" + "." + image type.
     */
    private String makeCopyPath(Image image) {
        String originalPath = image.getPath();
        String newPath = originalPath.substring(0, originalPath.lastIndexOf("."));
        String imageType = originalPath.substring(originalPath.lastIndexOf(".") + 1);
        newPath = newPath + "_copy" + "." + imageType;
        return newPath;
    }

    /**
     * Draws an Image with the given filter effect on GUI. Firstly, creates the filtered image by
     * calling FilterPreview in FilterManager class. Draws the filtered image and then deletes
     * the after-filtered image.
     *
     * @param filter The filter function to perform on the given image.
     * @see model.FilterManager
     * @see ImageView
     * @see File
     * @see Image
     */
    private void drawAfterFilter(Filter filter) {
        curFilter = filter;
        FilterManager filterManager = Main.system.getFilterManager();
        filterManager.FilterPreview(AddTagController.getChosenImage().getPath(), filter);
        Image chosenImage = AddTagController.getChosenImage();
        String copyPath = makeCopyPath(chosenImage);
        File f = new File(copyPath);
        javafx.scene.image.Image filterImage = null;
        try {
            filterImage = new javafx.scene.image.Image(f.toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        imageView.setImage(filterImage);
        f.delete();
    }
}
