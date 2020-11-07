package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * This class is a controller to be responsible for all the operations when buttons are clicked in the
 * ViewAllMyTagsScene. Turn to MainScene when goBack Button is clicked. Adds tag typed when the addButton is clicked.
 * Deletes a or multiple tags chosen when the delete button is clicked.
 * ViewAllMyTagsController implements Initializable and ChangeScene.
 *
 * @see Initializable
 * @see ChangeScene
 * @see Parent
 * @see Scene
 * @see Stage
 * @see Button
 */

public class ViewAllMyTagsController implements Initializable, ChangeScene {

    /**
     * a button for go back
     */
    @FXML
    Button goBack;

    /**
     * the text field for users to add a new tag for the chosen image.
     */
    @FXML
    TextField newTag;

    /**
     * the table for displaying all the existing tags in the tag set belonging to the tag Manager.
     */
    @FXML
    TableView<Tag> table;

    /**
     * the tagColumn in the table
     */
    @FXML
    TableColumn<UsedName, String> tagColumn;


    /**
     * Turns to ListAllScene or LocalOnlyScene then based on
     * the former chosen directory when goBack Button is clicked.
     */
    public void goBackButtonClicked() {
        try {
            changeScene(goBack, "../scenes/MainScene.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the tables firstly for both tags with the image and all the tags, based on all the tag and
     * the Image that the user chooses when the AddTagScene is displayed.
     *
     * @param location  default
     * @param resources default
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawTable();
    }


    /**
     * Adds the tag to the image and redraw the table when addTag Button is clicked. Pops up an Alert box
     * to indicate whether the change is successful or not.
     *
     * @see TagManager
     * @see AlertBox
     */

    public void addButtonClicked() {
        String tag = newTag.getText();
        TagManager tagManager = Main.system.getTagManager();
        if (!tag.equals("")) {
            newTag.setText("");
            if (tagManager.getTags().containsKey(tag)) {
                String message = "You already have this tag already!";
                AlertBox.display("GoGoPhoto", message);
            } else {
                tagManager.addNewTag(tag);
            }
        } else {
            AlertBox.display("GoGoPhoto", "Please type something!");
        }
        drawTable();
    }

    /**
     * Deletes the tag from the image and redraw the table when deleteButton is clicked.
     */
    public void deleteButtonClicked() {
        ObservableList<Tag> selectedTags = table.getSelectionModel().getSelectedItems();

        TagManager tagManager = Main.system.getTagManager();
        Boolean fail = false;
        Boolean select;
        select = selectedTags.size() != 0;
        String message = "";
        StringBuilder tags = new StringBuilder();
        if (selectedTags.size() != 0) {
            for (Tag tag : selectedTags) {
                if (!tagManager.deleteTag(tag.getOriginalName())) {
                    tags.append(" ").append(tag.getName());
                    message = "    Sorry, you can not delete" + tags + "    \n" + "    They are still been used!    ";
                    fail = true;
                }
            }
        }
        drawTable();

        if (!fail && select) {
            AlertBox.display("GoGoPhoto", "Success!");
        } else if (!fail) {
            AlertBox.display("GoGoPhoto", "Please choose tags to delete");
        } else {
            AlertBox.display("GoGoPhoto", message);
        }
    }


    /**
     * draw the table based on all tags in TagManager.
     */
    private void drawTable() {
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.setItems(FXCollections.observableArrayList(Main.system.getTagManager().getExistingTags()));

    }
}
