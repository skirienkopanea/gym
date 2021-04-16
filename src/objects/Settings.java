package objects;

import javax.swing.JFileChooser;
import java.util.ArrayList;

public class Settings {
    private String home;
    private String userName;
    private String title;
    private ArrayList<String> recentFiles;
    private boolean maximized;
    private boolean fullScreen;
    private int width;
    private int height;
    private int xCoordinate;
    private int yCoordinate;



    public Settings() {
         this.home = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "\\gym\\";
         this.title = "Welcome";
         this.maximized = false;
         this.fullScreen = false;
         this.width = 600;
         this.height = 400;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
