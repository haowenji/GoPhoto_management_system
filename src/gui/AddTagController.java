package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Image;
import model.ImageManager;
import model.Tag;
import model.UsedName;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is a controller to be responsible for all the operations when buttons are clicked in the
 * AddTagScene. Turn to ListAllScene or LocalOnlyScene when goBack Button is clicked. Turn to ViewHistoryScene when
 * viewHistory Button is clicked. Move the Image to the target directory when moveImage Button is clicked. Add the tag
 * typed or selected to the Image when the AddTagButton is clicked. And delete the tag from the image if the delete
 * Button is clicked. It implements the interfaces Initializable and ChangeScene.
 *
 * @see Initializable
 * @see ChangeScene
 * @see Parent
 * @see Scene
 * @see Stage
 * @see Button
 * @see ImageManager
 * @see Image
 * @see AlertBox
 * @see TableView
 * @see TextField
 * @see TableColumn
 * @see Text
 * @see File
 * @see DirectoryChooser
 * @see ImageView
 */

public class AddTagController implements Initializable, ChangeScene {

    /**
     * the button for moving the image.
     */
    @FXML
    Button moveImage;

    /**
     * the button for viewing the renaming history.
     */
    @FXML
    Button viewHistory;

    /**
     * the button for going back to the listAllScene or localOnlyScene.
     */
    @FXML
    Button goBack;

    /**
     * the text for displaying the current absolute path.
     */
    @FXML
    Text text;

    /**
     * the image view for displaying the chosen image.
     */
    @FXML
    ImageView imageView;

    /**
     * the text field for users to add a new tag for the chosen image.
     */
    @FXML
    TextField newTag;

    /**
     * the table for displaying all the existing tags in the tag set belonging to the tag Manager.
     */
    @FXML
    TableView<Tag> table1;

    /**
     * the table for displaying all the tags associated with the chosen image.
     */
    @FXML
    TableView<Tag> table2;

    /**
     * the tag column in the table1.
     */
    @FXML
    TableColumn<UsedName, String> tagColumn1;

    /**
     * the tag column in the table2.
     */
    @FXML
    TableColumn<Tag, String> tagColumn2;

    /**
     * the button for adding a filter for the chosen image.
     */
    @FXML
    Button addFilter;

    /**
     * Changes the scene to ListAllScene or LocalOnlyScene, based on the former chosen directory, when
     * go back button is clicked.
     */
    public void goBackButtonClicked() {
        try {
            if (ListAllController.getChosenImage() != null) {
                changeScene(goBack, "../scenes/ListAllScene.fxml");
            } else {
                changeScene(goBack, "../scenes/LocalOnlyScene.fxml");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the tables for both tags with the image and all the tags, based on all the tags and the
     * Image that the user chooses;
     *
     * @param location  default
     * @param resources default
     * @see ImageView
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //display the image
        String path = getChosenImage().getPath();
        File f = new File(path);
        javafx.scene.image.Image chosenImage = null;
        try {
            chosenImage = new javafx.scene.image.Image(f.toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        imageView.setImage(chosenImage);
        drawTables();
    }

    /**
     * Changes the scene to ViewHistoryScene when the view history button is clicked.
     *
     * @throws IOException Indicates changing scene fails.
     */
    public void viewHistoryButtonClicked() throws IOException {
        changeScene(viewHistory, "../scenes/ViewRenamingHistoryScene.fxml");

    }

    /**
     * Lets the user choose a directory when the move image button is clicked. Move the image to that directory then.
     *
     * @throws IOException Indicates changing scene fails.
     * @see DirectoryChooser
     * @see Stage
     * @see File
     * @see ImageManager
     * @see AlertBox
     */
    public void moveTheImageButtonClicked() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage primaryStage = (Stage) moveImage.getScene().getWindow();
        File selectedDirectory =
                directoryChooser.showDialog(primaryStage);
        if (selectedDirectory != null) {
            String chosenDirectory = selectedDirectory.getAbsolutePath();
            ImageManager imageManager = Main.system.getImageManager();
            imageManager.moveImageToDirectory(getChosenImage(), chosenDirectory);
            String message = "   Your filter has been moved to \n   " + chosenDirectory + " successfully   ";
            AlertBox.display("GoGoPhoto", message);
            changeScene(moveImage, "../scenes/AddTagScene.fxml");
        }
    }

    /**
     * Adds the tags chosen from the table or typed in the text field to the image and redraw the table when
     * addTag Button is clicked. Pop out an alert box if the image already has had that tag.
     *
     * @see ImageManager
     * @see AlertBox
     * @see Image
     * @see TextField
     */

    public void addButtonClicked() {
        String tag = newTag.getText();
        ObservableList<Tag> selectedTags = table1.getSelectionModel().getSelectedItems();
        ImageManager imageManager = Main.system.getImageManager();
        if (!tag.equals("")) {
            newTag.setText("");
            if (imageManager.foundTag(getChosenImage(), tag)) {
                String message = "The image has this tag already!";
                AlertBox.display("GoGoPhoto", message);
            } else {
                imageManager.addTagToName(getChosenImage(), tag);
            }
        } else {
            if (selectedTags != null && selectedTags.size() != 0) {
                for (Tag selectedTag : selectedTags) {
                    Image image = getChosenImage();
                    String newTag = selectedTag.getOriginalName();
                    if (!imageManager.foundTag(image, newTag)) {
                        imageManager.addTagToName(image, newTag);
                    }
                }
            }
        }
        drawTables();
    }

    /**
     * Deletes the tags from the image and redraw the table when deleteTag Button is clicked.
     *
     * @see ImageManager
     * @see Image
     */
    public void deleteButtonClicked() {
        ObservableList<Tag> selectedTags = table2.getSelectionModel().getSelectedItems();
        ImageManager imageManager = Main.system.getImageManager();
        if (selectedTags != null) {
            for (Tag selectedTag : selectedTags) {
                Image image = getChosenImage();
                String tagToDelete = selectedTag.getOriginalName();
                imageManager.deleteTagFromName(image, tagToDelete);
            }
        }
        drawTables();
    }

    /**
     * Changes the scene to AddFilterScene when add filter Button is clicked.
     *
     * @throws IOException Indicates changing scene fails.
     */
    public void addFilterButtonClicked() throws IOException {
        changeScene(addFilter, "../scenes/AddFilterScene.fxml");
    }

    /**
     * Gets the image chosen from the listAllScene or LocalOnlyScene, based on the formerly chosen scene.
     *
     * @return Return the image chosen by the user.
     * @see LocalOnlyController
     * @see ListAllController
     */
    static Image getChosenImage() {
        if (LocalOnlyController.getChosenImage() != null) {
            return LocalOnlyController.getChosenImage();
        } else {
            return ListAllController.getChosenImage();
        }
    }

    /**
     * Draws table1 and table2 as well as the text for the absolute path of the chosen image.
     *
     * @see TableColumn
     * @see TableView
     * @see Text
     */
    private void drawTables() {
        table1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tagColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1.setItems(FXCollections.observableArrayList(Main.system.getTagManager().getExistingTags()));
        tagColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        table2.setItems(FXCollections.observableArrayList(getChosenImage().getTagList()));
        text.setText(getChosenImage().getPath());
    }
}
