package objects;

import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.JFileChooser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Settings {
    private static String home;
    private String userName;
    private ArrayList<String> recentFiles;
    private boolean maximized;
    private boolean fullScreen;
    private int width;
    private int height;
    private int xCoordinate;
    private int yCoordinate;

    public Settings() {
        setHome(new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "\\gym\\");
        this.maximized = true;
        this.fullScreen = false;
        this.width = 800;
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

    public static ArrayList<Exercise> getDefaultExerciseList() { //TO DO: replace with reading from a config file

        ArrayList<Exercise> result = new ArrayList<>();

        Exercise squat = new Exercise("squat","Back Squat");
        squat.setDescription("Barbell back squat. " +
                "Olympic lift ratios regard the high bar squat. " +
                "Powerlifting ratios regard the lowbar squat");
        squat.setHamstringEmphasis(.40);
        squat.setQuadricepsEmphasis(.60);

        ArrayList<String> squatSources = new ArrayList<>();
        squatSources.add("https://exrx.net/WeightExercises/Quadriceps/BBSquat");
        squatSources.add("https://www.catalystathletics.com/exercise/77/Back-Squat/");
        squat.setSources(squatSources);

        result.add(squat);

        Exercise frontSquat = new Exercise("frontSquat","Front Squat");
        frontSquat.setDescription("Olympic front squat");
        frontSquat.setHamstringEmphasis(.25);
        frontSquat.setQuadricepsEmphasis(.75);
        frontSquat.setRatioReferenceExercise("squat");
        frontSquat.setRatioOverReference(.875);

        ArrayList<String> frontSquatSources = new ArrayList<>();
        frontSquatSources.add("https://exrx.net/WeightExercises/Quadriceps/BBFrontSquat");
        frontSquat.setSources(frontSquatSources);

        result.add(frontSquat);

        Exercise clean = new Exercise("clean","Clean");
        clean.setDescription("Olympic Clean");
        clean.setElevation(1);
        clean.setHamstringEmphasis(.75);
        clean.setQuadricepsEmphasis(.25);
        clean.setRatioReferenceExercise("squat");
        clean.setRatioOverReference(.775);

        ArrayList<String> cleanSources = new ArrayList<>();
        cleanSources.add("https://exrx.net/WeightExercises/OlympicLifts/Clean");
        clean.setSources(cleanSources);

        result.add(clean);

        Exercise cleanAndJerk = new Exercise("cleanAndJerk","Clean & Jerk");
        cleanAndJerk.setDescription("Olympic Clean & Jerk");
        cleanAndJerk.setUpwardRotation(1);
        cleanAndJerk.setElevation(1);
        cleanAndJerk.setInternalRotation(1);
        cleanAndJerk.setHamstringEmphasis(.75);
        cleanAndJerk.setQuadricepsEmphasis(.25);
        cleanAndJerk.setRatioReferenceExercise("squat");
        cleanAndJerk.setRatioOverReference(.750);

        ArrayList<String> cleanAndJerkSources = new ArrayList<>();
        cleanAndJerkSources.add("https://exrx.net/WeightExercises/OlympicLifts/CleanAndJerk");
        cleanAndJerk.setSources(cleanAndJerkSources);

        result.add(cleanAndJerk);

        Exercise powerClean = new Exercise("powerClean","Power Clean");
        powerClean.setDescription("Olympic Power Clean");
        powerClean.setElevation(1);
        powerClean.setHamstringEmphasis(.85);
        powerClean.setQuadricepsEmphasis(.15);
        powerClean.setRatioReferenceExercise("squat");
        powerClean.setRatioOverReference(.650);

        ArrayList<String> powerCleanSources = new ArrayList<>();
        powerCleanSources.add("https://exrx.net/WeightExercises/OlympicLifts/PowerClean");
        powerClean.setSources(powerCleanSources);

        result.add(powerClean);

        Exercise snatch = new Exercise("snatch","Snatch");
        snatch.setDescription("Olympic Snatch");
        snatch.setElevation(1);
        snatch.setHamstringEmphasis(.85);
        snatch.setQuadricepsEmphasis(.15);
        snatch.setRatioReferenceExercise("squat");
        snatch.setRatioOverReference(.65);

        ArrayList<String> snatchSources = new ArrayList<>();
        snatchSources.add("https://exrx.net/WeightExercises/OlympicLifts/Snatch");
        snatch.setSources(snatchSources);

        result.add(snatch);

        Exercise deadlift = new Exercise("deadlift","Deadlift");
        deadlift.setDescription("Powerlifting Deadlift");
        deadlift.setElevation(1);
        deadlift.setHamstringEmphasis(.9);
        deadlift.setQuadricepsEmphasis(.1);
        deadlift.setRatioReferenceExercise("squat");
        deadlift.setRatioOverReference(1.25);

        ArrayList<String> deadliftSources = new ArrayList<>();
        deadliftSources.add("https://exrx.net/WeightExercises/GluteusMaximus/BBDeadlift");
        deadlift.setSources(deadliftSources);

        result.add(deadlift);

        Exercise cleanPull = new Exercise("cleanPull","Clean Pull");
        cleanPull.setDescription("Olympic Clean Pull");
        cleanPull.setElevation(1);
        cleanPull.setHamstringEmphasis(.9);
        cleanPull.setQuadricepsEmphasis(.1);
        cleanPull.setRatioReferenceExercise("squat");
        cleanPull.setRatioOverReference(1);

        ArrayList<String> cleanPullSources = new ArrayList<>();
        cleanPullSources.add("https://www.youtube.com/watch?v=1WuSiyM-knI");
        cleanPull.setSources(cleanPullSources);

        result.add(cleanPull);

        Exercise row = new Exercise("row","Pendlay Row");
        row.setDescription("Bent-over Barbell Row orthogonal to the ground");
        row.setRetraction(1);
        row.setRatioReferenceExercise("powerClean");
        row.setRatioOverReference(1.15);

        ArrayList<String> rowSources = new ArrayList<>();
        rowSources.add("https://exrx.net/WeightExercises/BackGeneral/BBBentOverRow");
        row.setSources(rowSources);

        result.add(row);

        Exercise bench = new Exercise("bench","Bench Press");
        bench.setDescription("Powerlifting Bench Press");
        bench.setProtraction(1);
        bench.setInternalRotation(1);
        bench.setRatioReferenceExercise("row");
        bench.setRatioOverReference(1);

        ArrayList<String> benchSources = new ArrayList<>();
        benchSources.add("https://exrx.net/WeightExercises/PectoralSternal/BBBenchPress");
        bench.setSources(benchSources);

        result.add(bench);

        Exercise press = new Exercise("press","Military Press");
        press.setDescription("Standing Barbell Shoulder Press");
        press.setUpwardRotation(1);
        press.setInternalRotation(1);
        press.setRatioReferenceExercise("bench");
        press.setRatioOverReference(0.6);

        ArrayList<String> pressSources = new ArrayList<>();
        pressSources.add("https://exrx.net/WeightExercises/DeltoidAnterior/BBMilitaryPress");
        press.setSources(pressSources);

        result.add(press);

        Exercise jerk = new Exercise("jerk","Jerk");
        jerk.setDescription("Olympic Split Jerk");
        jerk.setUpwardRotation(1);
        jerk.setInternalRotation(1);
        jerk.setRatioReferenceExercise("press");
        jerk.setRatioOverReference(2);

        ArrayList<String> jerkSources = new ArrayList<>();
        jerkSources.add("https://exrx.net/WeightExercises/OlympicLifts/SplitJerk");
        jerk.setSources(jerkSources);

        result.add(jerk);

        Exercise pushPress = new Exercise("pushPress","Push Press");
        pushPress.setDescription("Olympic Push Press");
        pushPress.setUpwardRotation(1);
        pushPress.setInternalRotation(1);
        pushPress.setRatioReferenceExercise("press");
        pushPress.setRatioOverReference(1.25);

        ArrayList<String> pushPressSources = new ArrayList<>();
        pushPressSources.add("https://exrx.net/WeightExercises/OlympicLifts/PushPress");
        pushPress.setSources(pushPressSources);

        result.add(pushPress);

        Exercise dip = new Exercise("dip","Dip");
        dip.setDescription("Parallel Bars Calisthenic Dip. Ratio assumes body weight + added weight");
        dip.setProtraction(1);
        dip.setDepression(1);
        dip.setInternalRotation(1);
        dip.setRatioReferenceExercise("bench");
        dip.setRatioOverReference(1.320);

        ArrayList<String> dipSources = new ArrayList<>();
        dipSources.add("https://exrx.net/WeightExercises/PectoralSternal/BWChestDip");
        dip.setSources(dipSources);

        result.add(dip);

        Exercise chinUp = new Exercise("chinUp","Chin-up");
        chinUp.setDescription("Calisthenic Chin-up. Ratio assumes body weight + added weight");
        chinUp.setDownwardRotation(1);
        chinUp.setRatioReferenceExercise("dip");
        chinUp.setRatioOverReference(1.32);

        ArrayList<String> chinUpSources = new ArrayList<>();
        chinUpSources.add("https://exrx.net/WeightExercises/LatissimusDorsi/BWUnderhandChinup");
        chinUp.setSources(chinUpSources);

        result.add(chinUp);

        Exercise pullUp = new Exercise("pullUp","Pull-up");
        pullUp.setDescription("Calisthenic Pull-up. Ratio assumes body weight + added weight");
        pullUp.setDownwardRotation(1);
        pullUp.setInternalRotation(1);
        pullUp.setRatioReferenceExercise("chinUp");
        pullUp.setRatioOverReference(0.95);

        ArrayList<String> pullUpSources = new ArrayList<>();
        pullUpSources.add("https://exrx.net/WeightExercises/LatissimusDorsi/BWPullup");
        pullUp.setSources(pullUpSources);

        result.add(pullUp);

        Exercise facePull = new Exercise("facePull","Face-pull");
        facePull.setDescription("Cable/rope double biceps pose row");
        facePull.setRetraction(1);
        facePull.setExternalRotation(1);

        ArrayList<String> facePullSources = new ArrayList<>();
        facePullSources.add("https://www.youtube.com/watch?v=Q18p2QtQAes");
        facePull.setSources(facePullSources);

        result.add(facePull);

        Exercise sideRise = new Exercise("sideRise","Side Rise");
        sideRise.setDescription("Dumbbell/cable shoulder lateral rises");
        sideRise.setUpwardRotation(1);

        ArrayList<String> sideRiseSources = new ArrayList<>();
        sideRiseSources.add("https://exrx.net/WeightExercises/DeltoidLateral/DBLateralRaise");
        sideRise.setSources(sideRiseSources);

        result.add(sideRise);

        Exercise shrug = new Exercise("shrug","Shrug");
        shrug.setDescription("Dumbbell/barbell shrugs");
        shrug.setUpwardRotation(1);

        ArrayList<String> shrugSources = new ArrayList<>();
        shrugSources.add("https://exrx.net/WeightExercises/TrapeziusUpper/DBShrug");
        shrug.setSources(shrugSources);

        result.add(shrug);

        Exercise latPullDown = new Exercise("latPullDown","Lat Pull-down");
        latPullDown.setDescription("Stiffed arm lat pull-down (bent-over pullover)");
        latPullDown.setDepression(1);

        ArrayList<String> latPullDownSources = new ArrayList<>();
        latPullDownSources.add("https://exrx.net/WeightExercises/LatissimusDorsi/CBBentoverPullover");
        latPullDown.setSources(latPullDownSources);

        result.add(latPullDown);

        Exercise rotatorCuff = new Exercise("rotatorCuff","Rotator Cuff");
        rotatorCuff.setDescription("External rotation for rotator cuff");
        rotatorCuff.setExternalRotation(1);

        ArrayList<String> rotatorCuffSources = new ArrayList<>();
        rotatorCuffSources.add("https://exrx.net/WeightExercises/Infraspinatus/DBUprightExternalRotation");
        rotatorCuff.setSources(rotatorCuffSources);

        result.add(rotatorCuff);

        return result;
    }
}
