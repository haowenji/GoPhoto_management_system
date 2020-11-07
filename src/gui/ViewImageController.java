package gui;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.*;
import model.DirectoryManager;
import model.ExistingImage;
import model.Image;
import model.ImageManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class is a controller to be responsible for all the operations when buttons are clicked in the
 * ViewImageScene. Turn to ListAllScene when listAll Button is clicked, and turn to LocalOnlyScene when localOnly
 * Button is clicked. ViewImageController implements Initializable and ChangeScens.
 *
 * @see Initializable
 * @see ChangeScene
 * @see Parent
 * @see Scene
 * @see Stage
 * @see Button
 * @see DirectoryManager
 * @see ImageManager
 * @see Image
 * @see ExistingImage
 */
public class ViewImageController implements Initializable, ChangeScene {

    /**
     * the button for listing all images in the directory
     */
    @FXML
    private Button listAll;

    /**
     * the button for listing local image in the directory
     */
    @FXML
    private Button localOnly;

    /**
     * Turns to ViewImage scene when listAll Button is clicked.
     *
     * @throws IOException indicates that turning to ViewImage scene fails
     */
    public void listAllButtonClicked() throws IOException {
        changeScene(listAll, "../scenes/ListAllScene.fxml");
    }

    /**
     * Turns to LocalOnlyScene when localOnly Button is clicked.
     *
     * @throws IOException indicates that turning to LocalOnlyScene fails.
     */

    public void localOnlyButtonClicked() throws IOException {
        changeScene(localOnly, "../scenes/LocalOnlyScene.fxml");
    }

    /**
     * Initializes firstly once the ViewImage scene is displayed.
     *
     * @param location  default
     * @param resources default
     *
     * @see DirectoryManager
     * @see ImageManager
     * @see Image
     * @see ExistingImage
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DirectoryManager directoryManager = Main.system.getDirectoryManager();
        ImageManager imageManager = Main.system.getImageManager();
        String path = MainSceneController.getPath();
        try {
            ArrayList<ExistingImage> allExistingImages = directoryManager.findAllImages(path);
            for (ExistingImage e : allExistingImages) {
                Image image = new Image(e.getImageName(), e.getImageType(), e.getPath());
                if (imageManager.foundImage(image.getPath()) == null){
                    imageManager.addImage(image);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
