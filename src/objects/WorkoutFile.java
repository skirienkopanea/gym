package objects;

import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class WorkoutFile {
    private Tab tab;
    private String path;
    private String title;
    public ArrayList<Lift> lifts;

    public void setLifts(ArrayList<Lift> lifts) {
        this.lifts = lifts;
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
        this.lifts = Settings.getDefaultExerciseList();
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

    public ArrayList<Lift> getLifts() {
        return lifts;
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

    public static void write(WorkoutFile workoutFile) throws FileNotFoundException {

        String path = workoutFile.getPath();

        if (workoutFile.isSaveAs()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save workout");
            fileChooser.setInitialDirectory(new File(workoutFile.getPath()));
            fileChooser.setInitialFileName(workoutFile.getTitle());
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GYM", "*.gym"));

            File file = fileChooser.showSaveDialog(new Stage());

            path = file.getParent() + "\\";
            workoutFile.setTitle(file.getName().substring(0,file.getName().length()-4));
            workoutFile.setPath(path);
        }

        System.out.println(path);


        StringBuilder str = new StringBuilder(listToString(workoutFile.getLifts(),Lift.class));
        str.append(listToString(workoutFile.getWorkout(),Exercise.class));

        PrintWriter writer = new PrintWriter(path + workoutFile.getTitle() + ".gym" );
        writer.print(str);
        writer.close();

        System.out.println(str);
        workoutFile.setSaveAs(false);

    }

    private static String listToString(List objects, Class c) {
        Field[] fields = c.getDeclaredFields();
        StringBuilder str = new StringBuilder(c.getName() + "s\r\n");
        for (Field field : fields) {
            str.append(field.getName() + ",");
        }
        str.deleteCharAt(str.length() - 1);
        str.append("\r\n");

        for (Object object : objects) {
            for (Field field : fields) {
                Field.setAccessible(fields, true);
                try {
                    str.append(String.valueOf(field.get(object)).replaceAll(",", ".") + ",");
                } catch (IllegalAccessException e) {
                    str.append(",");
                    e.printStackTrace();
                }
            }
            str.deleteCharAt(str.length() - 1);
            str.append("\r\n");
        }
        return str.toString();
    }
}
