package objects;

import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.JFileChooser;
import java.util.ArrayList;
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

    public static ArrayList<Lift> getDefaultExerciseList() { //TO DO: replace with reading from a config file

        ArrayList<Lift> result = new ArrayList<>();

        Lift squat = new Lift("squat","Back Squat");
        squat.setDescription("Barbell back squat.");
        squat.setHamstringEmphasis(.40);
        squat.setQuadricepsEmphasis(.60);

        ArrayList<String> squatSources = new ArrayList<>();
        squatSources.add("https://exrx.net/WeightExercises/Quadriceps/BBSquat");
        squatSources.add("https://www.catalystathletics.com/exercise/77/Back-Squat/");
        squat.setSources(squatSources);

        result.add(squat);

        Lift frontSquat = new Lift("frontSquat","Front Squat");
        frontSquat.setDescription("Olympic front squat");
        frontSquat.setHamstringEmphasis(.25);
        frontSquat.setQuadricepsEmphasis(.75);

        ArrayList<String> frontSquatSources = new ArrayList<>();
        frontSquatSources.add("https://exrx.net/WeightExercises/Quadriceps/BBFrontSquat");
        frontSquat.setSources(frontSquatSources);

        result.add(frontSquat);

        Lift clean = new Lift("clean","Clean");
        clean.setDescription("Olympic Clean");
        clean.setElevation(1);
        clean.setHamstringEmphasis(.75);
        clean.setQuadricepsEmphasis(.25);

        ArrayList<String> cleanSources = new ArrayList<>();
        cleanSources.add("https://exrx.net/WeightExercises/OlympicLifts/Clean");
        clean.setSources(cleanSources);

        result.add(clean);

        Lift cleanAndJerk = new Lift("cleanAndJerk","Clean & Jerk");
        cleanAndJerk.setDescription("Olympic Clean & Jerk");
        cleanAndJerk.setUpwardRotation(1);
        cleanAndJerk.setElevation(1);
        cleanAndJerk.setInternalRotation(1);
        cleanAndJerk.setHamstringEmphasis(.75);
        cleanAndJerk.setQuadricepsEmphasis(.25);

        ArrayList<String> cleanAndJerkSources = new ArrayList<>();
        cleanAndJerkSources.add("https://exrx.net/WeightExercises/OlympicLifts/CleanAndJerk");
        cleanAndJerk.setSources(cleanAndJerkSources);

        result.add(cleanAndJerk);

        Lift powerClean = new Lift("powerClean","Power Clean");
        powerClean.setDescription("Olympic Power Clean");
        powerClean.setElevation(1);
        powerClean.setHamstringEmphasis(.85);
        powerClean.setQuadricepsEmphasis(.15);

        ArrayList<String> powerCleanSources = new ArrayList<>();
        powerCleanSources.add("https://exrx.net/WeightExercises/OlympicLifts/PowerClean");
        powerClean.setSources(powerCleanSources);

        result.add(powerClean);

        Lift snatch = new Lift("snatch","Snatch");
        snatch.setDescription("Olympic Snatch");
        snatch.setElevation(1);
        snatch.setHamstringEmphasis(.85);
        snatch.setQuadricepsEmphasis(.15);

        ArrayList<String> snatchSources = new ArrayList<>();
        snatchSources.add("https://exrx.net/WeightExercises/OlympicLifts/Snatch");
        snatch.setSources(snatchSources);

        result.add(snatch);

        Lift deadlift = new Lift("deadlift","Deadlift");
        deadlift.setDescription("Powerlifting Deadlift");
        deadlift.setElevation(1);
        deadlift.setHamstringEmphasis(.9);
        deadlift.setQuadricepsEmphasis(.1);

        ArrayList<String> deadliftSources = new ArrayList<>();
        deadliftSources.add("https://exrx.net/WeightExercises/GluteusMaximus/BBDeadlift");
        deadlift.setSources(deadliftSources);

        result.add(deadlift);

        Lift cleanPull = new Lift("cleanPull","Clean Pull");
        cleanPull.setDescription("Olympic Clean Pull");
        cleanPull.setElevation(1);
        cleanPull.setHamstringEmphasis(.9);
        cleanPull.setQuadricepsEmphasis(.1);

        ArrayList<String> cleanPullSources = new ArrayList<>();
        cleanPullSources.add("https://www.youtube.com/watch?v=1WuSiyM-knI");
        cleanPull.setSources(cleanPullSources);

        result.add(cleanPull);

        Lift row = new Lift("row","Pendlay Row");
        row.setDescription("Bent-over Barbell Row orthogonal to the ground");
        row.setRetraction(1);

        ArrayList<String> rowSources = new ArrayList<>();
        rowSources.add("https://exrx.net/WeightExercises/BackGeneral/BBBentOverRow");
        row.setSources(rowSources);

        result.add(row);

        Lift bench = new Lift("bench","Bench Press");
        bench.setDescription("Powerlifting Bench Press");
        bench.setProtraction(1);
        bench.setInternalRotation(1);

        ArrayList<String> benchSources = new ArrayList<>();
        benchSources.add("https://exrx.net/WeightExercises/PectoralSternal/BBBenchPress");
        bench.setSources(benchSources);

        result.add(bench);

        Lift press = new Lift("press","Military Press");
        press.setDescription("Standing Barbell Shoulder Press");
        press.setUpwardRotation(1);
        press.setInternalRotation(1);

        ArrayList<String> pressSources = new ArrayList<>();
        pressSources.add("https://exrx.net/WeightExercises/DeltoidAnterior/BBMilitaryPress");
        press.setSources(pressSources);

        result.add(press);

        Lift jerk = new Lift("jerk","Jerk");
        jerk.setDescription("Olympic Split Jerk");
        jerk.setUpwardRotation(1);
        jerk.setInternalRotation(1);

        ArrayList<String> jerkSources = new ArrayList<>();
        jerkSources.add("https://exrx.net/WeightExercises/OlympicLifts/SplitJerk");
        jerk.setSources(jerkSources);

        result.add(jerk);

        Lift pushPress = new Lift("pushPress","Push Press");
        pushPress.setDescription("Olympic Push Press");
        pushPress.setUpwardRotation(1);
        pushPress.setInternalRotation(1);

        ArrayList<String> pushPressSources = new ArrayList<>();
        pushPressSources.add("https://exrx.net/WeightExercises/OlympicLifts/PushPress");
        pushPress.setSources(pushPressSources);

        result.add(pushPress);

        Lift dip = new Lift("dip","Dip");
        dip.setDescription("Parallel Bars Calisthenic Dip.");
        dip.setProtraction(1);
        dip.setDepression(1);
        dip.setInternalRotation(1);

        ArrayList<String> dipSources = new ArrayList<>();
        dipSources.add("https://exrx.net/WeightExercises/PectoralSternal/BWChestDip");
        dip.setSources(dipSources);

        result.add(dip);

        Lift chinUp = new Lift("chinUp","Chin-up");
        chinUp.setDescription("Calisthenic Chin-up.");
        chinUp.setDownwardRotation(1);

        ArrayList<String> chinUpSources = new ArrayList<>();
        chinUpSources.add("https://exrx.net/WeightExercises/LatissimusDorsi/BWUnderhandChinup");
        chinUp.setSources(chinUpSources);

        result.add(chinUp);

        Lift pullUp = new Lift("pullUp","Pull-up");
        pullUp.setDescription("Calisthenic Pull-up.");
        pullUp.setDownwardRotation(1);
        pullUp.setInternalRotation(1);

        ArrayList<String> pullUpSources = new ArrayList<>();
        pullUpSources.add("https://exrx.net/WeightExercises/LatissimusDorsi/BWPullup");
        pullUp.setSources(pullUpSources);

        result.add(pullUp);

        Lift facePull = new Lift("facePull","Face-pull");
        facePull.setDescription("Cable/rope double biceps pose row");
        facePull.setRetraction(1);
        facePull.setExternalRotation(1);

        ArrayList<String> facePullSources = new ArrayList<>();
        facePullSources.add("https://www.youtube.com/watch?v=Q18p2QtQAes");
        facePull.setSources(facePullSources);

        result.add(facePull);

        Lift sideRise = new Lift("sideRise","Side Rise");
        sideRise.setDescription("Dumbbell/cable shoulder lateral rises");
        sideRise.setUpwardRotation(1);

        ArrayList<String> sideRiseSources = new ArrayList<>();
        sideRiseSources.add("https://exrx.net/WeightExercises/DeltoidLateral/DBLateralRaise");
        sideRise.setSources(sideRiseSources);

        result.add(sideRise);

        Lift shrug = new Lift("shrug","Shrug");
        shrug.setDescription("Dumbbell/barbell shrugs");
        shrug.setElevation(1);

        ArrayList<String> shrugSources = new ArrayList<>();
        shrugSources.add("https://exrx.net/WeightExercises/TrapeziusUpper/DBShrug");
        shrug.setSources(shrugSources);

        result.add(shrug);

        Lift latPullDown = new Lift("latPullDown","Lat Pull-down");
        latPullDown.setDescription("Stiffed arm lat pull-down (bent-over pullover)");
        latPullDown.setDepression(1);

        ArrayList<String> latPullDownSources = new ArrayList<>();
        latPullDownSources.add("https://exrx.net/WeightExercises/LatissimusDorsi/CBBentoverPullover");
        latPullDown.setSources(latPullDownSources);

        result.add(latPullDown);

        Lift rotatorCuff = new Lift("rotatorCuff","Rotator Cuff");
        rotatorCuff.setDescription("External rotation for rotator cuff");
        rotatorCuff.setExternalRotation(1);

        ArrayList<String> rotatorCuffSources = new ArrayList<>();
        rotatorCuffSources.add("https://exrx.net/WeightExercises/Infraspinatus/DBUprightExternalRotation");
        rotatorCuff.setSources(rotatorCuffSources);

        result.add(rotatorCuff);

        return result;
    }
}
