package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/** This interface is responsible for changing the scene. Each class implementing this interface can call the default
 * method to change the scene.
 *
 * @see Parent
 * @see Stage
 * @see Scene
 */
public interface ChangeScene {
    /**
     * Changes the scene from the current one where the button is located to the target scene file.
     *
     * @param button a button located in the current scene
     * @param sceneFile the target scene that is going to be changed to
     * @throws IOException Indicates changing scene fails.
     */
    default void changeScene(Button button, String sceneFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(sceneFile));
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
