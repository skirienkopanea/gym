package controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import objects.Exercise;
import objects.Settings;
import objects.WorkoutFile;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComplexController {

    @FXML
    private TabPane tabPane;

    @FXML
    private RowConstraints welcomeMessageRow;

    @FXML
    private Label welcomeMessageLabel;

    private int createdTabsSoFar = 0;

    public static ArrayList<Tab> unsavedTabs = new ArrayList<>();

    private Map<Tab,WorkoutFile> tabFiles = new HashMap<>();

    public void createTab(ActionEvent actionEvent) {
        Tab tab = new Tab();

        String title = "New " + ++createdTabsSoFar;
        tab.setText(title);

        tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Stage stage = (Stage) tabPane.getScene().getWindow();
                stage.setTitle(tabFiles.get(tab).getPath() + tab.getText() + " - gym");
            }
        });

        tab.setOnCloseRequest(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                int tabNumber = tabPane.getSelectionModel().getSelectedIndex();
                Tab tab = tabPane.getTabs().get(tabNumber);
                if (unsavedTabs.contains(tab) && Settings.confirmClose(event)){
                    unsavedTabs.remove(tab);
                }
            }
        });

        tab.setOnClosed(new EventHandler<Event>() {
            /**
             * On close from tab x button
             * @param event
             */
            @Override
            public void handle(Event event) {
                updateTitleTabClose();
            }
        });

        welcomeMessageRow.setMaxHeight(0);
        welcomeMessageLabel.setVisible(false);
        tabPane.setVisible(true);
        tabPane.setMaxHeight(tabPane.getPrefHeight());

        SplitPane splitPane = new SplitPane();
        splitPane.setDisable(false);
        splitPane.setDividerPositions(0.5, 0.5);
        splitPane.setFocusTraversable(true);


        WorkoutFile workoutFile = new WorkoutFile(tab);
        workoutFile.setExerciseList(Settings.getDefaultExerciseList());

        // Exercises
        ScrollPane exerciseScrollPane = new ScrollPane();
        GridPane exercises = new GridPane();
        exercises.setPadding(new Insets(7,7,5,7));
        exercises.setVgap(5);

        Label exerciseHeader = new Label("Exercises");
        exerciseHeader.setStyle("-fx-font-size: 17");
        exercises.add(exerciseHeader, 0, 0);

        // Exercise Actions
        GridPane exerciseActions = new GridPane();
        exerciseActions.setHgap(5);
        exerciseActions.add(new Button("Add"),0,0);
        exerciseActions.add(new Button("Edit"),1,0);
        exerciseActions.add(new Button("Select All"),2,0);
        exerciseActions.add(new Button("Train Selected"),3,0);
        exerciseActions.add(new Button("Delete Selected"),4,0);
        exercises.add(exerciseActions,0,1);

        // Exercise Records
        GridPane exerciseTable = new GridPane();
        exerciseTable.setGridLinesVisible(true);

        Label exerciseName = new Label("Name");
        exerciseName.setPadding(new Insets(2.5,2.5,2.5,2.5));
        exerciseTable.add(exerciseName,0,0);

        Label exerciseScapula = new Label("Scapula/Arm");
        exerciseScapula.setPadding(new Insets(2.5,2.5,2.5,2.5));
        exerciseTable.add(exerciseScapula,1,0);

        Label exerciseKnee = new Label("Quadriceps/Hamstring");
        exerciseKnee.setPadding(new Insets(2.5,2.5,2.5,2.5));
        exerciseTable.add(exerciseKnee,2,0);

        Label exerciseRatioReference = new Label("Strength Reference");
        exerciseRatioReference.setPadding(new Insets(2.5,2.5,2.5,2.5));
        exerciseTable.add(exerciseRatioReference,3,0);

        Label exerciseRatio = new Label("Ratio");
        exerciseRatio.setPadding(new Insets(2.5,2.5,2.5,2.5));
        exerciseTable.add(exerciseRatio,4,0);

        Label exerciseSources = new Label("Sources");
        exerciseSources.setPadding(new Insets(2.5,2.5,2.5,2.5));
        exerciseTable.add(exerciseSources,5,0);
        loadExercises(exerciseTable,workoutFile.getExerciseList());
        exercises.add(exerciseTable,0,2);

        exerciseScrollPane.setContent(exercises);
        splitPane.getItems().add(exerciseScrollPane);

        // Workouts
        ScrollPane workoutScrollPane = new ScrollPane();
        workoutScrollPane.setMinWidth(200);
        GridPane workouts = new GridPane();
        workouts.setPadding(new Insets(7,7,5,7));

        Label workoutHeader = new Label("Workout");
        workoutHeader.setStyle("-fx-font-size: 17");
        workoutHeader.setPadding(new Insets(0,0,5,0));

        workouts.add(workoutHeader, 0, 0);

        GridPane workoutRecords = new GridPane();
        workouts.add(workoutRecords,0,1);

        workoutScrollPane.setContent(workouts);
        splitPane.getItems().add(workoutScrollPane);



        // Strength Records
        GridPane records = new GridPane();

        Label recordsTitle = new Label();
        recordsTitle.setText("Records");

        records.add(recordsTitle, 0, 0);

        splitPane.getItems().add(records);



        tabFiles.put(tab,workoutFile);
        //TODO: Remove this and only set TabUnsaved when there are actual changes.
        setTabUnsaved(tab);

        tab.setContent(splitPane);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private void loadExercises(GridPane exerciseRecords, ArrayList<Exercise> exerciseList) {
        int i = 1;


        for (Exercise exercise : exerciseList) {
            Label exerciseName = new Label(exercise.getName());
            exerciseName.setPadding(new Insets(2.5,2.5,2.5,2.5));
            exerciseRecords.add(exerciseName,0,i);
            exerciseName.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    exerciseName.setStyle("-fx-background-color: red");
                }
            });

            GridPane scapularGrid = new GridPane();
            scapularGrid.setAlignment(Pos.CENTER_LEFT);
            scapularGrid.setPadding(new Insets(2.5,2.5,2.5,2.5));
            Label protraction = new Label("Protraction");
            Label retraction = new Label("Retraction");
            Label upwardRotation = new Label("Upward rotation");
            Label downwardRotation = new Label("Downward rotation");
            Label depression = new Label("Depression");
            Label elevation = new Label("Elevation");
            Label internalRotation = new Label("Internal Rotation");
            Label externalRotation = new Label("External Rotation");
            int j = 0;
            if (exercise.getProtraction()==1){
                scapularGrid.add(protraction,0,j);
                j++;
            }
            if (exercise.getRetraction()==1){
                scapularGrid.add(retraction,0,j);
                j++;
            }
            if (exercise.getUpwardRotation()==1){
                scapularGrid.add(upwardRotation,0,j);
                j++;
            }
            if (exercise.getDownwardRotation()==1){
                scapularGrid.add(downwardRotation,0,j);
                j++;
            }
            if (exercise.getDepression()==1){
                scapularGrid.add(depression,0,j);
                j++;
            }
            if (exercise.getElevation()==1){
                scapularGrid.add(elevation,0,j);
                j++;
            }
            if (exercise.getInternalRotation()==1){
                scapularGrid.add(internalRotation,0,j);
                j++;
            }
            if (exercise.getExternalRotation()==1){
                scapularGrid.add(externalRotation,0,j);
                j++;
            }
            exerciseRecords.add(scapularGrid,1,i);

            GridPane legs = new GridPane();
            legs.setAlignment(Pos.CENTER);
            legs.setPadding(new Insets(2.5,2.5,2.5,2.5));
            //Spinner<Double> quadriceps = new Spinner<>();
            //quadriceps.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0,1,0,0.1));
            //quadriceps.setMaxWidth(70);

            if (exercise.getQuadricepsEmphasis() != 0 || exercise.getHamstringEmphasis() != 0){
                Label quadriceps = new Label(String.valueOf(exercise.getQuadricepsEmphasis()));
                Label separator = new Label("/");
                Label hamstring = new Label(String.valueOf(exercise.getHamstringEmphasis()));

                legs.add(quadriceps,0,0);
                legs.add(separator,1,0);
                legs.add(hamstring,2,0);
            }
            exerciseRecords.add(legs,2,i);

            Exercise exerciseReference = new Exercise("notFound","");
            for (Exercise ex : exerciseList){
                if (ex.getId().equals(exercise.getRatioReferenceExercise())){
                    exerciseReference = ex;
                }
            }

            GridPane strengthReference = new GridPane();
            strengthReference.setAlignment(Pos.CENTER);
            Label referenceLabel = new Label(exerciseReference.getName());
            strengthReference.add(referenceLabel,0,0);
            exerciseRecords.add(strengthReference,3,i);

            GridPane strengthRatio = new GridPane();
            strengthRatio.setAlignment(Pos.CENTER);
            strengthRatio.setPadding(new Insets(2.5,2.5,2.5,2.5));
            Label ratioLabel = new Label(String.valueOf(exerciseReference.getRatioOverReference()));
            strengthRatio.add(ratioLabel,0,0);
            exerciseRecords.add(strengthRatio,4,i);



            GridPane linksGrid = new GridPane();
            linksGrid.setAlignment(Pos.CENTER_LEFT);
            linksGrid.setPadding(new Insets(2.5,2.5,2.5,2.5));
            j=0;
            for (String link : exercise.getSources()){
                Hyperlink source = new Hyperlink();
                source.setText(link);
                source.setOnAction(e -> {
                    if(Desktop.isDesktopSupported())
                    {
                        try {
                            Desktop.getDesktop().browse(new URI(link));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (URISyntaxException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                //ClipMenu
                final ContextMenu contextMenu = new ContextMenu();
                MenuItem copy = new MenuItem("Copy");
                contextMenu.getItems().addAll(copy);
                copy.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ClipboardContent content = new ClipboardContent();
                        content.putString(link);
                        Clipboard.getSystemClipboard().setContent(content);
                    }
                });
                source.setPadding(new Insets(0,0,0,0));
                source.setContextMenu(contextMenu);
                linksGrid.add(source,0,j);
                j++;
            }
            exerciseRecords.add(linksGrid,5,i);

            i++;
        }
    }

    public void saveTab(ActionEvent actionEvent) {
        int tabNumber = tabPane.getSelectionModel().getSelectedIndex();
        if (tabNumber != -1){
            Tab tab = tabPane.getTabs().get(tabNumber);
            saveTab(tab);
        }
    }

    public void saveTab(Tab tab) {
        //Actually save the file, then...
        try {
            Exercise.writeExerciseCsv(tabFiles.get(tab));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        String substr = tabFiles.get(tab).getTitle();
        tab.setText(substr);

        Stage stage = (Stage) tabPane.getScene().getWindow();
        stage.setTitle(tabFiles.get(tab).getPath() + tab.getText() + " - gym");

        unsavedTabs.remove(tab);

    }

    public void setTabUnsaved(Tab tab){
        tab.setText(tab.getText()+"*");

        Stage stage = (Stage) tabPane.getScene().getWindow();
        stage.setTitle(tabFiles.get(tab).getPath() + tab.getText() + " - gym");

        unsavedTabs.add(tab);
    }

    /**
     * On close from menu/shortcut
     * @param actionEvent
     */
    public void closeTabFromMenu(ActionEvent actionEvent) {
        int tabNumber = tabPane.getSelectionModel().getSelectedIndex();
        if (tabNumber == -1){
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.close();
            return;
        }

        Tab tab = tabPane.getTabs().get(tabNumber);

        if (tabPane.getTabs().size() > 0) {

            if (unsavedTabs.contains(tab) && Settings.confirmClose(actionEvent)) {
                unsavedTabs.remove(tab);
                tabPane.getTabs().remove(tabNumber);
                updateTitleTabClose();
            } else if (!unsavedTabs.contains(tab)){
                tabPane.getTabs().remove(tabNumber);
                updateTitleTabClose();
            }
        }
    }

    public void updateTitleTabClose() {
        if (tabPane.getTabs().size() == 0) {
            welcomeMessageRow.setMaxHeight(welcomeMessageRow.getPrefHeight());
            welcomeMessageLabel.setVisible(true);
            tabPane.setVisible(false);
            tabPane.setMaxHeight(0);

            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setTitle("gym");
        }
    }

    public void helpPopUp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Source code available at github.com/skirienkopanea/gym");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("resources/images/icon.png"));

        alert.showAndWait();
    }
}
