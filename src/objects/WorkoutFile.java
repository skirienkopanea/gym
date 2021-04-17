package objects;

import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.Date;

public class WorkoutFile {
    private Tab tab;
    private String path;
    private String title;
    private ArrayList<Exercise> exerciseList;
    private ArrayList<Workout> workoutList;
    private ArrayList<Record<Exercise,Integer,Integer,Integer,Date>> independentRecordsList;

    public WorkoutFile(Tab tab) {
        this.tab = tab;
        this.path = Settings.getHome();
        this.title = tab.getText();
        this.exerciseList = Settings.getDefaultExerciseList();
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

    public ArrayList<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public ArrayList<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(ArrayList<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    public ArrayList<Record<Exercise, Integer, Integer, Integer, Date>> getIndependentRecordsList() {
        return independentRecordsList;
    }

    public void setIndependentRecordsList(ArrayList<Record<Exercise, Integer, Integer, Integer, Date>> independentRecordsList) {
        this.independentRecordsList = independentRecordsList;
    }
}
