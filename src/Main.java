import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import objects.Settings;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Settings settings = new Settings();

        try {
            loadConfigFile(settings);
        } catch (FileNotFoundException e) {
            System.out.println("Loading default settings");
            settings.setUserName("athlete");
            createConfigFile(settings);
        }
        System.out.println("Welcome " + settings.getUserName());

        Parent root = FXMLLoader.load(getClass().getResource("resources/fxml/sample.fxml"));
        primaryStage.getIcons().add(new Image("resources/images/icon.png"));
        primaryStage.setTitle("gym");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
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
            StringBuilder file = new StringBuilder("Athlete\r\n");
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
