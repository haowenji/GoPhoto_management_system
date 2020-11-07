package gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.GoGoPhotoSystem;
import model.LogMessage;
import model.LoggerManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class is a controller to be responsible for all the operations when buttons are clicked in the
 * LogScene. Changes the scene to MainScene when goBack Button is clicked. It implements the interfaces Initializable
 * and ChangeScene.
 *
 * @see Initializable
 * @see ChangeScene
 * @see Parent
 * @see Scene
 * @see Stage
 * @see Button
 * @see TableView
 * @see TableColumn
 */

public class LogSceneController implements Initializable, ChangeScene {

    /**
     * the button for going back to the MainScene.
     */
    @FXML
    Button goBack;

    /**
     * the table for displaying all the logger records about name changing.
     */
    @FXML
    private TableView<LogMessage> table;

    /**
     * the time column in the table.
     */
    @FXML
    private TableColumn<LogMessage, String> timeColumn;

    /**
     * the name changing information column in the table.
     */
    @FXML
    private TableColumn<LogMessage, String> nameColumn;


    /**
     * Changes the scene to the MainScene, when go back button is clicked.
     */
    @FXML
    public void goBackButtonClicked() throws IOException {
        changeScene(goBack, "../scenes/MainScene.fxml");
    }

    /**
     * Initializes the tables for the logger records about name changing with both time column and name column
     *
     * @param location  default
     * @param resources default
     * @see LoggerManager
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        ArrayList<LogMessage> logging = new ArrayList<>();
        try {
            logging = LoggerManager.ReadLogMessages(GoGoPhotoSystem.logger, "LogHis.txt");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        table.setItems(FXCollections.observableArrayList(logging));
    }
}
