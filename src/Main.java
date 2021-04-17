import controllers.ComplexController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Settings settings = new Settings();

        try {
            loadConfigFile(settings);
        } catch (FileNotFoundException e) {
            loadDefaultConfig(settings);
            createConfigFile(settings);
        }

        System.out.println("Welcome " + settings.getUserName());

        loadStartScreen(primaryStage, settings);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (ComplexController.unsavedTabs.size() > 0) {
                    Settings.confirmClose(event);
                }
            }
        });
    }

    private void loadStartScreen(Stage primaryStage, Settings settings) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resources/fxml/complex.fxml"));
        primaryStage.getIcons().add(new Image("resources/images/icon.png"));
        primaryStage.setTitle("gym");
        primaryStage.setScene(new Scene(root));

        primaryStage.setWidth(settings.getWidth());
        primaryStage.setHeight(settings.getHeight());
        primaryStage.setMaximized(settings.isMaximized());
        primaryStage.setFullScreen(settings.isFullScreen());

        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);

        primaryStage.show();
    }

    private void loadDefaultConfig(Settings settings) {
        System.out.println("Loading default settings");
        settings.setUserName("athlete");
    }

    private void loadConfigFile(Settings settings) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(settings.getHome() + "config.txt"));
        System.out.println("Config file found at " + settings.getHome() + "config.txt");

        String userName = sc.next();
        settings.setUserName(userName);
    }

    private void createConfigFile(Settings settings) {
        try {
            PrintWriter writer = new PrintWriter(settings.getHome() + "config.txt");
            StringBuilder file = new StringBuilder(settings.getUserName() + "\r\n");
            //athletes.stream().forEach(athlete -> file.append(athlete.toCsv()));
            writer.print(file);
            writer.close();
            System.out.println("New config file created");
        } catch (FileNotFoundException e) {
            System.out.println("Config file could not be created");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
