package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.GoGoPhotoSystem;


/**
 * This class is the entrance that the user enters the application. It initializes a new program, creates a
 * new GoGoPhotoSystem associated with it. Reads from the ser file when getting initialized and save
 * the information into the ser file when closing the program. It extends class Application.
 *
 * @see Application
 * @see GoGoPhotoSystem
 * @see Parent
 * @see Stage
 */
public class Main extends Application {

    /**
     * a new GoGoPhotoSystem which is responsible for the operations of the whole backup.
     */
    public static GoGoPhotoSystem system;

    /**
     * This method launches the whole program.
     *
     * @param args the parameter to get started
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialize a new GoGoPhotoSystem and sets the initial settings for the primary scene, called when the program
     * is opened.
     *
     * @param primaryStage the primary stage to display everything
     * @throws Exception Indicates setting MainScene fails.
     * @see GoGoPhotoSystem
     * @see Parent
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        system = new GoGoPhotoSystem("tagFile.ser", "imageFile.ser");
        Parent root = FXMLLoader.load(getClass().getResource("../scenes/MainScene.fxml"));
        primaryStage.setTitle("GoGoPhoto");
        primaryStage.setScene(new Scene(root, 800, 494.5));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Saves the information stored in the ImageManager and TagManager into imageFile.ser and tagFile.ser, called
     * when the program is closed.
     *
     * @throws Exception Indicates the saving operation fails.
     */
    @Override
    public void stop() throws Exception {
        system.saveToImageManagerFile("imageFile.ser");
        system.saveToTagManagerFile("tagFile.ser");
    }


}

