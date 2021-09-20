package objects;

import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.Date;

public class WorkoutFile {
    private Tab tab;
    private String path;
    private String title;
    public ArrayList<Lift> liftList;

    public void setLiftList(ArrayList<Lift> liftList) {
        this.liftList = liftList;
    }

    public ArrayList<Exercise> getWorkout() {
        return workout;
    }

    public void setWorkout(ArrayList<Exercise> workout) {
        this.workout = workout;
    }

    private ArrayList<Exercise> workout;
    private boolean saveAs;
    private boolean unSaved;

    public WorkoutFile(Tab tab) {
        this.tab = tab;
        this.path = Settings.getHome();
        this.title = tab.getText();
        this.liftList = Settings.getDefaultExerciseList();
        this.workout = new ArrayList<>();
        this.saveAs = true;
        this.unSaved = false;
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Lift> getLiftList() {
        return liftList;
    }

    public void setExerciseList(ArrayList<Lift> liftList) {
        this.liftList = liftList;
    }

    public boolean isSaveAs() {
        return saveAs;
    }

    public void setSaveAs(boolean saveAs) {
        this.saveAs = saveAs;
    }

    public boolean isUnSaved() {
        return unSaved;
    }

    public void setUnSaved(boolean unSaved) {
        this.unSaved = unSaved;
    }
}
