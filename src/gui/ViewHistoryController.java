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
import model.Image;
import model.ImageManager;
import model.UsedName;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is a controller to be responsible for all the operations when buttons are clicked in the
 * ViewRenamingHistoryScene. Turn to AddTagScene when goBack Button is clicked. Pop up an Alert box when
 * backToOldButton is clicked. ViewHistoryController implements Initializable and ChangeScens.
 *
 * @see Initializable
 * @see ChangeScene
 * @see Parent
 * @see Scene
 * @see Stage
 * @see Button
 * @see TableView
 * @see TableColumn
 * @see Image
 * @see UsedName
 */
public class ViewHistoryController implements Initializable, ChangeScene {

    /**
     * the button for GoBack
     */
    @FXML
    Button goBack;

    /**
     * the table for displaying all used name associated with the image
     */
    @FXML
    TableView<UsedName> table;

    /**
     * the table column in table.
     */
    @FXML
    TableColumn<UsedName, String> usedNameColumn;

    /**
     * Turns to AddTagScene when goBack Button is clicked.
     */
    public void goBackButtonClicked() throws IOException {
        changeScene(goBack, "../scenes/AddTagScene.fxml");
    }

    /**
     * Changes the name of the image back to the old version when backToOld Button is clicked. Pops up an Alert box
     * to indicate whether the change is successful or not.
     *
     * @see ImageManager
     * @see UsedName
     * @see AlertBox
     */
    public void backToOldButtonClicked() {
        ImageManager imageManager = Main.system.getImageManager();
        UsedName usedName = table.getSelectionModel().getSelectedItem();
        if (usedName != null) {
            imageManager.backToOldName(getChosenImage(), usedName.getName());
            drawTable(usedNameColumn, "name");
            AlertBox.display("GoGoPhoto", "Success!");
        } else {
            AlertBox.display("GoGoPhoto", "Please select an old name!");
        }
    }

    /**
     * Initializes the table for all used names associated with chosen image once the ViewRenamingHistory scene is
     * displayed.
     *
     * @param location  default
     * @param resources default
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawTable(usedNameColumn, "name");
    }

    /**
     * Returns the Image associated with the Controller, which is chosen by the user.
     *
     * @return the chosen Image
     */

    private Image getChosenImage() {
        if (LocalOnlyController.getChosenImage() != null) {
            return LocalOnlyController.getChosenImage();
        } else {
            return ListAllController.getChosenImage();
        }
    }

    /**
     * Draws the table for chosen Image's all used names.
     * @param usedNameColumn the usedName column for displaying all the used names of the image
     * @param columnName the name of the attribute of usedName for drawing the table
     *
     * @see Image
     * @see UsedName
     */
    private void drawTable(TableColumn<UsedName, String> usedNameColumn, String columnName) {
        usedNameColumn.setCellValueFactory(new PropertyValueFactory<>(columnName));
        Image chosenImage = getChosenImage();
        table.setItems(FXCollections.observableArrayList(chosenImage.getRenamingHistory()));
    }
}
