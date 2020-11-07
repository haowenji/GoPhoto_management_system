package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ExistingImage;
import model.Image;
import model.ImageManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * LocalOnlyController is a controller to be responsible for all the operations when buttons are clicked in the
 * LocalOnlyScene. Turn to AddTagScene when chooseButton is clicked. And turn to MainScene when goBack
 * Button is clicked.
 *
 * @see Parent
 * @see Scene
 * @see Stage
 * @see Button
 * @see model.Image
 */
public class LocalOnlyController implements Initializable, ChangeScene {

    /**
     * The image to be chosen.
     */
    private static Image chosenImage;

    /**
     * A button to select a local image.
     */
    @FXML
    Button chooseButton;

    /**
     * Go back to the main page where to select root directory, view all tags or view logbook.
     */
    @FXML
    Button goBackButton;

    /**
     * Drawing the table for showing ExistingImages in the local directory.
     */
    @FXML
    private TableView<ExistingImage> table;

    /**
     * Drawing the column for showing the name of ExistingImages in the local directory.
     */
    @FXML
    private TableColumn<ExistingImage, String> nameColumn;

    /**
     * Drawing the column for showing the path of ExistingImages in the local directory.
     */
    @FXML
    private TableColumn<ExistingImage, String> pathColumn;

    /**
     * This method is called firstly once the LocalOnly scene is displayed. Draw the table based on the images in
     * the directory that the user chooses;
     *
     * @param location  default
     * @param resources default
     *
     * @throws IOException
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("path"));
        String path = MainSceneController.getPath();
        ArrayList<ExistingImage> allImages;
        try {
            allImages = Main.system.getDirectoryManager().findLocalImages(path);
            table.setItems(FXCollections.observableArrayList(allImages));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is called when when choose Button is clicked. Make the Image chosen by the user associated with the
     * controller. Turn to AddTag scene then.
     *
     * @throws IOException
     * @see model.ImageManager
     * @see model.Image
     * @see ChangeScene
     * @see Button
     */
    public void chooseButtonClicked() throws IOException {
        ImageManager imageManager = Main.system.getImageManager();
        ExistingImage selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Image targetImage = Main.system.getImageManager().foundImage(selected.getPath());
            if (targetImage == null) {
                chosenImage = new Image(selected.getImageName(), selected.getImageType(), selected.getPath());
                imageManager.addChosenImage(chosenImage);
            } else {
                chosenImage = targetImage;
            }
            changeScene(chooseButton, "../scenes/AddTagScene.fxml");
        }
    }

    /**
     * This method is called when when goBack Button is clicked. Turn to MainScene then.
     *
     * @see Button
     * @see ChangeScene
     */
    public void goBackButtonClicked() throws IOException {
        chosenImage = null;
        changeScene(goBackButton, "../scenes/MainScene.fxml");
    }

    /**
     * This method returns the Image associated with the Controller, which is chosen by the user.
     *
     * @return Image
     */
    static Image getChosenImage() {
        return chosenImage;
    }
}
