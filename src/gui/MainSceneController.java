package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is a controller to be responsible for all the operations when buttons are clicked in the
 * MainScene. Turn to LogBookScene when logBook Button is clicked, and turn to viewImageScene when viewImage Button
 * is clicked. And MainSceneController implements Initializable and ChangeScenes.
 * @see Initializable
 * @see ChangeScene
 * @see Parent
 * @see Stage
 * @see Button
 * @see DirectoryChooser
 * @see File
 */

public class MainSceneController implements Initializable, ChangeScene {

    /**
     * the path of the directory that user chooses
     */
    private static String path;

    /**
     * a button for entering logBook
     */
    @FXML
    Button logBook;

    /**
     * a button for choosing directory to view images
     */
    @FXML
    Button viewImage;

    /**
     * a button for viewing all tags
     */
    @FXML
    Button viewTags;


    /**
     * Turns to LogBookScene when logBook Button is clicked.
     *
     * @throws IOException indicates that entering logbook fails
     */
    @FXML
    public void logBookButtonClicked() throws IOException {
        changeScene(logBook, "../scenes/LogScene.fxml");
    }

    /**
     * lets the user choose a directory.
     * Sets path and turn to ViewImageScene then when viewImage Button is clicked.
     *
     * @throws IOException indicates that choosing directory or seting path or turning to ViewImageScene fails.
     * @see DirectoryChooser
     * @see Stage
     * @see File
     */
    @FXML
    public void viewImageButtonClicked() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage primaryStage = (Stage) viewImage.getScene().getWindow();
        File selectedDirectory =
                directoryChooser.showDialog(primaryStage);
        if (selectedDirectory != null) {
            path = selectedDirectory.getAbsolutePath();
            changeScene(viewImage, "../scenes/ViewImageScene.fxml");
        }

    }

    /**
     * Turns to ViewAllMyTagsScene when the viewTags is clicked.
     * @throws IOException indicates turning to ViewAllMyTagsScene fails.
     */
    public void ViewMyTagsButtonClicked() throws IOException {
        changeScene(viewTags, "../scenes/ViewAllMyTagsScene.fxml");
    }

    /**
     * Returns the path associated with the Controller, which is chosen by the user.
     *
     * @return path of chosen directory
     */
    public static String getPath() {
        return path;
    }

    /**
     * Initializes firstly once the main scene is displayed.
     *
     * @param location  default
     * @param resources default
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}