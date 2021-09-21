package controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import objects.Exercise;
import objects.Lift;
import objects.Settings;
import objects.WorkoutFile;

import java.io.FileNotFoundException;
import java.util.*;

public class ComplexController {

    @FXML
    private TabPane tabPane;

    @FXML
    private RowConstraints welcomeMessageRow;

    @FXML
    private Label welcomeMessageLabel;

    @FXML
    private Label protractionValue = new Label("0.0");
    private Label retractionValue = new Label("0.0");
    private Label upwardRotationValue = new Label("0.0");
    private Label downwardRotationValue = new Label("0.0");
    private Label depressionValue = new Label("0.0");
    private Label elevationValue = new Label("0.0");
    private Label internalRotationValue = new Label("0.0");
    private Label externalRotationValue = new Label("0.0");
    private Label hamstringEmphasisValue = new Label("0.0");
    private Label quadricepsEmphasisValue = new Label("0.0");

    private Label horizontalRatioValue = new Label("NaN");
    private Label upwardRatioValue = new Label("NaN");
    private Label depressionRatioValue = new Label("NaN");
    private Label rotationRatioValue = new Label("NaN");
    private Label quadRatioValue = new Label("NaN");

    private int createdTabsSoFar = 0;

    private ArrayList<Exercise> Workout = new ArrayList<>();

    public static ArrayList<Tab> unsavedTabs = new ArrayList<>();

    private Map<Tab, WorkoutFile> tabFiles = new HashMap<>();

    public void createTab(ActionEvent actionEvent) {
        Tab tab = new Tab();

        String title = "New " + ++createdTabsSoFar;
        tab.setText(title);

        tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Stage stage = (Stage) tabPane.getScene().getWindow();
                stage.setTitle((tabFiles.get(tab).isUnSaved() ? "* " : "") + tabFiles.get(tab).getPath() + tabFiles.get(tab).getTitle() + ".gym");
            }
        });

        tab.setOnCloseRequest(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                int tabNumber = tabPane.getSelectionModel().getSelectedIndex();
                Tab tab = tabPane.getTabs().get(tabNumber);
                if (unsavedTabs.contains(tab) && Settings.confirmClose(event)) {
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
        workoutFile.setLifts(Settings.getDefaultExerciseList());

        // Lifts
        ScrollPane exerciseScrollPane = new ScrollPane();
        GridPane exercises = new GridPane();
        exercises.setPadding(new Insets(7, 7, 5, 7));
        exercises.setVgap(5);

        Label exerciseHeader = new Label("Lifts");
        exerciseHeader.setStyle("-fx-font-size: 17");
        exercises.add(exerciseHeader, 0, 0);

        // Lift Actions
        GridPane exerciseActions = new GridPane();
        exerciseActions.setHgap(5);

        Button trainLifts = new Button("Train selected");
        Button clearLifts = new Button("Clear selection");

        exerciseActions.add(trainLifts, 0, 0);
        exerciseActions.add(clearLifts, 1, 0);
        exercises.add(exerciseActions, 0, 1);

        // Lift Records

        TableView tableView = new TableView();

        TableColumn<Lift, String> column2 = new TableColumn<>("Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Lift, String> column3 = new TableColumn<>("Description");
        column3.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Lift, String> column4 = new TableColumn<>("Protraction");
        column4.setCellValueFactory(new PropertyValueFactory<>("protraction"));

        TableColumn<Lift, String> column5 = new TableColumn<>("Retraction");
        column5.setCellValueFactory(new PropertyValueFactory<>("retraction"));

        TableColumn<Lift, String> column6 = new TableColumn<>("Upward Rotation");
        column6.setCellValueFactory(new PropertyValueFactory<>("upwardRotation"));

        TableColumn<Lift, String> column7 = new TableColumn<>("Downward rotation");
        column7.setCellValueFactory(new PropertyValueFactory<>("downwardRotation"));

        TableColumn<Lift, String> column8 = new TableColumn<>("Depression");
        column8.setCellValueFactory(new PropertyValueFactory<>("depression"));

        TableColumn<Lift, String> column9 = new TableColumn<>("Elevation");
        column9.setCellValueFactory(new PropertyValueFactory<>("elevation"));

        TableColumn<Lift, String> column10 = new TableColumn<>("Internal rotation");
        column10.setCellValueFactory(new PropertyValueFactory<>("internalRotation"));

        TableColumn<Lift, String> column11 = new TableColumn<>("External rotation");
        column11.setCellValueFactory(new PropertyValueFactory<>("externalRotation"));

        TableColumn<Lift, String> column12 = new TableColumn<>("Hamstring");
        column12.setCellValueFactory(new PropertyValueFactory<>("hamstringEmphasis"));

        TableColumn<Lift, String> column13 = new TableColumn<>("Quad");
        column13.setCellValueFactory(new PropertyValueFactory<>("quadricepsEmphasis"));

        TableColumn<Lift, String> column14 = new TableColumn<>("Reference");
        column14.setCellValueFactory(new PropertyValueFactory<>("ratioReferenceExercise"));

        TableColumn<Lift, String> column15 = new TableColumn<>("Ratio");
        column15.setCellValueFactory(new PropertyValueFactory<>("ratioOverReference"));

        TableColumn<Lift, String> column16 = new TableColumn<>("Sources");
        column16.setCellValueFactory(new PropertyValueFactory<>("sources"));

        tableView.getColumns().addAll(column2, column3, column4, column5, column6,
                column7, column8, column9, column10, column11, column12, column13, column14, column15, column16);

        loadLifts(tableView, workoutFile.getLifts());

        exercises.add(tableView, 0, 2);

        tableView.prefHeightProperty().bind(splitPane.heightProperty());
        tableView.prefWidthProperty().bind(splitPane.widthProperty());

        TableView.TableViewSelectionModel<Lift> liftSelectionModel = tableView.getSelectionModel();
        liftSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        //for the buttons

        clearLifts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                liftSelectionModel.clearSelection();
            }
        });

        exerciseScrollPane.setContent(exercises);


        // Stats
        ScrollPane statsScrollPane = new ScrollPane();
        statsScrollPane.setMaxWidth(350);
        GridPane stats = new GridPane();
        statsScrollPane.setContent(stats);
        statsScrollPane.setPadding(new Insets(7, 7, 5, 7));
        stats.setVgap(5);

        Label statsHeader = new Label("Workout Stats");
        statsHeader.setStyle("-fx-font-size: 17");

        stats.add(statsHeader, 0, 0);

        Label protraction = new Label("Protraction");
        stats.add(protraction, 0, 1);
        stats.add(protractionValue, 1, 1);

        Label retraction = new Label("Retraction");
        stats.add(retraction, 0, 2);
        stats.add(retractionValue, 1, 2);

        Label upwardRotation = new Label("Up rotation");
        stats.add(upwardRotation, 0, 3);
        stats.add(upwardRotationValue, 1, 3);

        Label downwardRotation = new Label("Down rotation");
        stats.add(downwardRotation, 0, 4);
        stats.add(downwardRotationValue, 1, 4);

        Label depression = new Label("Depression");
        stats.add(depression, 0, 5);
        stats.add(depressionValue, 1, 5);

        Label elevation = new Label("Elevation");
        stats.add(elevation, 0, 6);
        stats.add(elevationValue, 1, 6);

        Label internalRotation = new Label("Internal rotation");
        stats.add(internalRotation, 0, 7);
        stats.add(internalRotationValue, 1, 7);

        Label externalRotation = new Label("External rotation");
        stats.add(externalRotation, 0, 8);
        stats.add(externalRotationValue, 1, 8);

        Label hamstringEmphasis = new Label("Ham emphasis");
        stats.add(hamstringEmphasis, 0, 9);
        stats.add(hamstringEmphasisValue, 1, 9);

        Label quadricepsEmphasis = new Label("Quad emphasis");
        stats.add(quadricepsEmphasis, 0, 10);
        stats.add(quadricepsEmphasisValue, 1, 10);

        stats.add(new Separator(), 0, 11);

        Label horizontalRatio = new Label("Horizontal ratio");
        stats.add(horizontalRatio, 0, 12);
        stats.add(horizontalRatioValue, 1, 12);

        Label upwardRatio = new Label("Upward ratio");
        stats.add(upwardRatio, 0, 13);
        stats.add(upwardRatioValue, 1, 13);

        Label depressionRatio = new Label("Depression ratio");
        stats.add(depressionRatio, 0, 14);
        stats.add(depressionRatioValue, 1, 14);

        Label rotationRatio = new Label("Rotation ratio");
        stats.add(rotationRatio, 0, 15);
        stats.add(rotationRatioValue, 1, 15);

        Label quadRatio = new Label("Quad ratio");
        stats.add(quadRatio, 0, 16);
        stats.add(quadRatioValue, 1, 16);

        stats.add(new Separator(), 0, 17);

        Label disclaimer = new Label("Ratios regard push/pull movements: \n" +
                "H = protraction/retraction\n" +
                "U = upward/downward rotation\n" +
                "D = depression/elevation\n" +
                "R = internal/external rotation\n" +
                "Q = quadriceps/hamstring emphasis");
        stats.add(disclaimer, 0, 18);

        // Workouts
        ScrollPane workoutScrollPane = new ScrollPane();
        workoutScrollPane.setMinWidth(300);
        GridPane workouts = new GridPane();
        workoutScrollPane.setContent(workouts);
        workouts.setPadding(new Insets(7, 7, 5, 7));
        workouts.setVgap(5);

        Label workoutHeader = new Label("Workout");
        workoutHeader.setStyle("-fx-font-size: 17");

        workouts.add(workoutHeader, 0, 0);

        // workout Actions
        GridPane workoutActions = new GridPane();
        workoutActions.setHgap(5);

        Button removeExercises = new Button("Remove selected");
        Button clearExercises = new Button("Clear selection");

        workoutActions.add(removeExercises, 0, 0);
        workoutActions.add(clearExercises, 1, 0);
        workouts.add(workoutActions, 0, 1);


        TableView workoutView = new TableView();

        TableView.TableViewSelectionModel<Exercise> exerciseSelectionModel = workoutView.getSelectionModel();
        exerciseSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);


        TableColumn<Exercise, String> wColumn1 = new TableColumn<>("Name");
        wColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Exercise, Integer> wColumn2 = new TableColumn<>("Weight");
        wColumn2.setCellValueFactory(new PropertyValueFactory<>("weight"));
        wColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        wColumn2.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Exercise, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Exercise, Integer> t) {
                        ((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).setWeight(t.getNewValue());
                        Integer newValue = t.getNewValue();
                        System.out.println(newValue);
                        tabFiles.get(tab).setWorkout(new ArrayList<>(workoutView.getItems()));
                    }
                }
        );


        TableColumn<Exercise, Integer> wColumn3 = new TableColumn<>("Sets");
        wColumn3.setCellValueFactory(new PropertyValueFactory<>("sets"));
        wColumn3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        wColumn3.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Exercise, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Exercise, Integer> t) {
                        ((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSets(t.getNewValue());
                        Integer newValue = t.getNewValue();
                        System.out.println(newValue);
                        updateStats(workoutView);
                        tabFiles.get(tab).setWorkout(new ArrayList<>(workoutView.getItems()));
                    }
                }
        );


        TableColumn<Exercise, Integer> wColumn4 = new TableColumn<>("Reps");
        wColumn4.setCellValueFactory(new PropertyValueFactory<>("reps"));
        wColumn4.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        wColumn4.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Exercise, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Exercise, Integer> t) {
                        ((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).setReps(t.getNewValue());
                        Integer newValue = t.getNewValue();
                        System.out.println(newValue);
                        updateStats(workoutView);
                        tabFiles.get(tab).setWorkout(new ArrayList<>(workoutView.getItems()));
                    }
                }
        );

        TableColumn<Exercise, String> wColumn5 = new TableColumn<>("Comments");
        wColumn5.setCellValueFactory(new PropertyValueFactory<>("comments"));
        wColumn5.setCellFactory(TextFieldTableCell.forTableColumn());
        wColumn5.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Exercise, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Exercise, String> t) {
                        ((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).setComments(t.getNewValue());
                        String newValue = t.getNewValue();
                        System.out.println(newValue);
                        tabFiles.get(tab).setWorkout(new ArrayList<>(workoutView.getItems()));
                    }
                }
        );


        workoutView.getColumns().addAll(wColumn1, wColumn2, wColumn3, wColumn4, wColumn5);

        workouts.add(workoutView, 0, 2);

        workoutView.prefHeightProperty().bind(splitPane.heightProperty());
        workoutView.prefWidthProperty().bind(splitPane.widthProperty());
        workoutView.setEditable(true);


        clearExercises.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exerciseSelectionModel.clearSelection();
            }
        });

        removeExercises.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exerciseSelectionModel.getSelectedItems().forEach(exercise -> workoutView.getItems().remove(exercise));
                updateStats(workoutView);
                tabFiles.get(tab).setWorkout(new ArrayList<>(workoutView.getItems()));
            }
        });

        trainLifts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                liftSelectionModel.getSelectedItems().forEach(lift -> workoutView.getItems().add(new Exercise((Lift) lift, 0, 0, 0)));
                updateStats(workoutView);
                tabFiles.get(tab).setWorkout(new ArrayList<>(workoutView.getItems()));
            }
        });


        splitPane.getItems().add(exerciseScrollPane);
        splitPane.getItems().add(workoutScrollPane);
        splitPane.getItems().add(statsScrollPane);


        tabFiles.put(tab, workoutFile);
        setTabUnsaved(tab);

        tab.setContent(splitPane);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private void updateStats(TableView workoutView) {

        Workout = new ArrayList<>(workoutView.getItems());

        double protraction = 0;
        double retraction = 0;
        double upwardRotation = 0;
        double downwardRotation = 0;
        double depression = 0;
        double elevation = 0;
        double internalRotation = 0;
        double externalRotation = 0;
        double hamstringEmphasis = 0;
        double quadricepsEmphasis = 0;

        for (Exercise exercise : Workout) {
            int volume = exercise.getSets() * exercise.getReps();
            protraction += volume * exercise.getLift().getProtraction();
            retraction += volume * exercise.getLift().getRetraction();
            upwardRotation += volume * exercise.getLift().getUpwardRotation();
            downwardRotation += volume * exercise.getLift().getDownwardRotation();
            depression += volume * exercise.getLift().getDepression();
            elevation += volume * exercise.getLift().getElevation();
            internalRotation += volume * exercise.getLift().getInternalRotation();
            externalRotation += volume * exercise.getLift().getExternalRotation();
            hamstringEmphasis += volume * exercise.getLift().getHamstringEmphasis();
            quadricepsEmphasis += volume * exercise.getLift().getQuadricepsEmphasis();

        }

        protractionValue.setText(String.valueOf(protraction));
        retractionValue.setText(String.valueOf(retraction));
        upwardRotationValue.setText(String.valueOf(upwardRotation));
        downwardRotationValue.setText(String.valueOf(downwardRotation));
        depressionValue.setText(String.valueOf(depression));
        elevationValue.setText(String.valueOf(elevation));
        internalRotationValue.setText(String.valueOf(internalRotation));
        externalRotationValue.setText(String.valueOf(externalRotation));
        hamstringEmphasisValue.setText(String.valueOf(hamstringEmphasis));
        quadricepsEmphasisValue.setText(String.valueOf(quadricepsEmphasis));

        horizontalRatioValue.setText(String.valueOf(protraction/retraction));
        upwardRatioValue.setText(String.valueOf(upwardRotation/downwardRotation));
        depressionRatioValue.setText(String.valueOf(depression/elevation));
        rotationRatioValue.setText(String.valueOf(internalRotation/externalRotation));
        quadRatioValue.setText(String.valueOf(quadricepsEmphasis/hamstringEmphasis));


    }

    private void loadLifts(TableView tableView, ArrayList<Lift> liftList) {
        for (Lift lift : liftList) {
            tableView.getItems().add(lift);
        }
    }

    public void saveTab(ActionEvent actionEvent) {
        int tabNumber = tabPane.getSelectionModel().getSelectedIndex();
        if (tabNumber != -1) {
            Tab tab = tabPane.getTabs().get(tabNumber);
            saveTab(tab);
        }
    }

    public void saveTab(Tab tab) {

        try {
            WorkoutFile.write(tabFiles.get(tab));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return;
        }

        String substr = tabFiles.get(tab).getTitle();
        tab.setText(substr);

        Stage stage = (Stage) tabPane.getScene().getWindow();
        stage.setTitle(tabFiles.get(tab).getPath() + tabFiles.get(tab).getTitle() + ".gym");

        tabFiles.get(tab).setUnSaved(false);

        unsavedTabs.remove(tab);

    }

    public void saveAs(ActionEvent actionEvent) {
        int tabNumber = tabPane.getSelectionModel().getSelectedIndex();
        if (tabNumber != -1) {
            Tab tab = tabPane.getTabs().get(tabNumber);
            saveAs(tab);
        }
    }

    public void saveAs(Tab tab) {
        tabFiles.get(tab).setSaveAs(true);
        saveTab(tab);
    }

    public void setTabUnsaved(Tab tab) {
        tab.setText("* " + tab.getText());

        Stage stage = (Stage) tabPane.getScene().getWindow();
        stage.setTitle("* " + tabFiles.get(tab).getPath() + tabFiles.get(tab).getTitle() + ".gym");

        tabFiles.get(tab).setUnSaved(true);

        unsavedTabs.add(tab);
    }

    /**
     * On close from menu/shortcut
     *
     * @param actionEvent
     */
    public void closeTabFromMenu(ActionEvent actionEvent) {
        int tabNumber = tabPane.getSelectionModel().getSelectedIndex();
        if (tabNumber == -1) {
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
            } else if (!unsavedTabs.contains(tab)) {
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

    public void helpPopUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Source code available at github.com/skirienkopanea/gym");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("resources/images/icon.png"));

        alert.showAndWait();
    }
}
