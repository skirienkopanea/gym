package objects;

import javax.swing.JFileChooser;

public class Settings {
    private String home;
    private String userName;

    public Settings() {
         this.home = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "\\gym\\";
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
}
