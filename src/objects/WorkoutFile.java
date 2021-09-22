package objects;

import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.*;

public class WorkoutFile {
    private String path;
    private String title;
    public ArrayList<Lift> lifts;
    private ArrayList<Exercise> workout;
    private boolean saveAs;
    private boolean unSaved;

    public void setLifts(ArrayList<Lift> lifts) {
        this.lifts = lifts;
    }

    public ArrayList<Exercise> getWorkout() {
        return workout;
    }

    public void setWorkout(ArrayList<Exercise> workout) {
        this.workout = workout;
    }


    public WorkoutFile(String title) {
        this.title = title;
        this.path = Settings.getHome();
        this.lifts = Settings.getDefaultExerciseList();
        this.workout = new ArrayList<>();
        this.saveAs = true; //keep like this as default for new created workouts
        this.unSaved = false;
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
            fileChooser.setInitialDirectory(new File(path));
            fileChooser.setInitialFileName(workoutFile.getTitle());
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GYM", "*.gym"));

            File file = fileChooser.showSaveDialog(new Stage());

            path = file.getParent() + "\\";
            workoutFile.setTitle(file.getName().substring(0, file.getName().length() - 4));
            workoutFile.setPath(path);
        }

        System.out.println(path);


        StringBuilder str = new StringBuilder(listToString(workoutFile.getLifts(), Lift.class));
        str.append(listToString(workoutFile.getWorkout(), Exercise.class));

        PrintWriter writer = new PrintWriter(path + workoutFile.getTitle() + ".gym");
        writer.print(str);
        writer.close();

        System.out.println(str);
        workoutFile.setSaveAs(false);

    }

    private static String listToString(List objects, Class c) {
        Field[] fields = c.getDeclaredFields();
        StringBuilder str = new StringBuilder("\r\n" + c.getName() + "s\r\n");
        for (Field field : fields) {
            str.append(field.getName() + ",");
        }
        str.deleteCharAt(str.length() - 1);
        str.append("\r\n");

        for (Object object : objects) {
            Field.setAccessible(fields, true);
            for (Field field : fields) {
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

    public static WorkoutFile open(WorkoutFile workoutFile) throws FileNotFoundException {

        String path = workoutFile.getPath();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open workout");
        fileChooser.setInitialDirectory(new File(path));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("GYM", "*.gym"));

        File file = fileChooser.showOpenDialog(new Stage());

        path = file.getParent() + "\\";

        WorkoutFile openFile = new WorkoutFile("");
        openFile.setTitle(file.getName().substring(0, file.getName().length() - 4));
        openFile.setPath(path);
        openFile.setSaveAs(false);

        Scanner sc = new Scanner(new File(file.getAbsolutePath()));

        ArrayList<Lift> lifts = readLifts(sc);
        openFile.setLifts(lifts);
        openFile.setWorkout(readExercises(sc,lifts));

        return openFile;

    }

    public static ArrayList<Lift> readLifts(Scanner sc) {
        sc.nextLine(); //skip empty line
        sc.useDelimiter(",|[\\r\\n]+");
        ArrayList<Lift> lifts = new ArrayList<>();
        if (sc.hasNext("objects.Lifts")) {
            System.out.println(sc.nextLine()); //skip object.Class
            System.out.println(sc.nextLine()); //skip object field headers
            Field[] fields = Lift.class.getDeclaredFields();
            Field.setAccessible(fields, true);
            while (!sc.hasNext("objects.Exercises")) {

                Lift lift = new Lift("", "");

                for (Field field : fields) {
                    try {
                        System.out.print(field.getType().getSimpleName());
                        switch(field.getType().getSimpleName()){
                            case "String":
                                String str = sc.next();
                                System.out.println(": " + str);
                                field.set(lift, str);
                                break;
                            case "double":
                                double d = sc.nextDouble();
                                System.out.println(": " + d);
                                field.set(lift,d);
                                break;
                            case "ArrayList":
                                String arr = sc.next();
                                ArrayList<String> strs = new ArrayList<>();
                                Arrays.asList(arr.substring(1,arr.length()-1).split("\\. ")).stream()
                                .forEach(s -> strs.add(s));
                                System.out.println(strs);
                                field.set(lift,strs);
                                break;
                        }

                    } catch (IllegalAccessException e){

                }

            }
                lifts.add(lift);
        }

    }
        return lifts;
}

    public static ArrayList<Exercise> readExercises(Scanner sc, ArrayList<Lift> lifts) {
        sc.nextLine(); //skip empty line
        sc.nextLine(); //somehow needs to skip another line...
        sc.useDelimiter(",|[\\r\\n]+");
        ArrayList<Exercise> workout = new ArrayList<>();
        if (sc.hasNext("objects.Exercises")) {
            System.out.println(sc.nextLine()); //skip object.Class
            System.out.println(sc.nextLine()); //skip object field headers

            Field[] fields = Exercise.class.getDeclaredFields();
            Field.setAccessible(fields, true);
            while (sc.hasNextLine()) {

                Exercise exercise = new Exercise(new Lift("404","404n"),0,0,0);

                for (Field field : fields) {
                    try {
                        System.out.print(field.getType().getSimpleName());
                        switch(field.getType().getSimpleName()){
                            case "Lift":
                                String id = sc.next();
                                Lift l = Lift.getLift(id, lifts);
                                System.out.println(" (" + id + "): " + l);
                                field.set(exercise,l);
                                break;
                            case "String":
                                String str = sc.next();
                                System.out.println(": " + str);
                                field.set(exercise, str);
                                break;
                            case "int":
                                int n = sc.nextInt();
                                System.out.println(": " + n);
                                field.set(exercise,n);
                                break;
                        }

                    } catch (IllegalAccessException e){

                    } catch (NoSuchElementException e){
                        return workout; //skip 404 default lift
                    }

                }
                workout.add(exercise);
            }

        }
        return workout;
    }
}

