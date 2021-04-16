package objects;

import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.JFileChooser;
import java.util.ArrayList;
import java.util.Optional;

public class Settings {
    private static String home;
    private String userName;
    private static int unsavedFilesCount;
    private ArrayList<String> recentFiles;
    private boolean maximized;
    private boolean fullScreen;
    private int width;
    private int height;
    private int xCoordinate;
    private int yCoordinate;


    public Settings() {
        setHome(new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "\\gym\\");
        this.maximized = false;
        this.fullScreen = false;
        this.width = 900;
        this.height = 400;
    }

    public static String getHome() {
        return home;
    }

    public static void setHome(String home) {
        Settings.home = home;
    }

    public static boolean confirmClose(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("There are unsaved changes");
        alert.setContentText("Are you sure you want to close?");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("resources/images/icon.png"));

        Optional<ButtonType> closeResponse = alert.showAndWait();
        if (!ButtonType.OK.equals(closeResponse.get())) {
            event.consume();
            return false;
        }
        return true;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static int getUnsavedFilesCount() {
        return unsavedFilesCount;
    }

    public static void increaseUnsavedFilesCount() {
        unsavedFilesCount++;
    }

    public static void decreaseUnsavedFilesCount() {
        unsavedFilesCount--;
    }

    public ArrayList<String> getRecentFiles() {
        return recentFiles;
    }

    public void setRecentFiles(ArrayList<String> recentFiles) {
        this.recentFiles = recentFiles;
    }

    public boolean isMaximized() {
        return maximized;
    }

    public void setMaximized(boolean maximized) {
        this.maximized = maximized;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public static ArrayList<Exercise> getDefaultExerciseList() {
        return null;
    }
}
