package objects;

import controllers.ComplexController;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.Date;

public class WorkoutFile {
    private String path;
    private String title;
    private ArrayList<Exercise> exerciseList;
    private ArrayList<Workout> workoutList;
    private ArrayList<Record<Exercise,Integer,Integer,Integer,Date>> independentRecordsList;

    public WorkoutFile(Tab tab) {
        this.path = Settings.getHome();
        this.title = tab.getText();
        tab.setText(path + title+"*");
        this.exerciseList = Settings.getDefaultExerciseList();
        ComplexController.setTabUnsaved(tab);
    }
}
