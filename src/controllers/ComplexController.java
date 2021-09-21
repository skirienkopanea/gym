package controllers;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplexController {

    @FXML
    private TabPane tabPane;

    @FXML
    private RowConstraints welcomeMessageRow;

    @FXML
    private Label welcomeMessageLabel;

    private int createdTabsSoFar = 0;

    private List<Object> selectedLifts = new ArrayList<>();
    private List<Object> selectedExercises = new ArrayList<>();

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
        workoutFile.setExerciseList(Settings.getDefaultExerciseList());

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

        loadLifts(tableView, workoutFile.getLiftList());

        exercises.add(tableView, 0, 2);

        tableView.prefHeightProperty().bind(splitPane.heightProperty());
        tableView.prefWidthProperty().bind(splitPane.widthProperty());

        TableView.TableViewSelectionModel<Lift> liftSelectionModel = tableView.getSelectionModel();
        liftSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        //for the buttons
        ObservableList<Lift> selectedLiftItems = liftSelectionModel.getSelectedItems();
        selectedLiftItems.addListener(new ListChangeListener<Lift>() {
            @Override
            public void onChanged(Change<? extends Lift> change) {
                selectedLifts = Arrays.asList(change.getList().toArray());
                System.out.println("Selected lifts: " + change.getList());
            }
        });

        clearLifts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                liftSelectionModel.clearSelection();
            }
        });

        exerciseScrollPane.setContent(exercises);
        splitPane.getItems().add(exerciseScrollPane);

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
                    }
                }
        );


        workoutView.getColumns().addAll(wColumn1, wColumn2, wColumn3, wColumn4);

        workouts.add(workoutView, 0, 2);

        workoutView.prefHeightProperty().bind(splitPane.heightProperty());
        workoutView.prefWidthProperty().bind(splitPane.widthProperty());
        workoutView.setEditable(true);

        TableView.TableViewSelectionModel<Exercise> exerciseSelectionModel = workoutView.getSelectionModel();
        exerciseSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        //for the buttons
        ObservableList<Exercise> selectedExerciseItems = exerciseSelectionModel.getSelectedItems();
        selectedExerciseItems.addListener(new ListChangeListener<Exercise>() {
            @Override
            public void onChanged(Change<? extends Exercise> change) {
                selectedExercises = Arrays.asList(change.getList().toArray());
            }
        });

        clearExercises.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exerciseSelectionModel.clearSelection();
            }
        });

        removeExercises.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectedExercises.forEach(exercise -> workoutView.getItems().remove(exercise));
            }
        });

        trainLifts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectedLifts.forEach(lift -> workoutView.getItems().add(new Exercise((Lift) lift,0,0,0)));
            }
        });

        splitPane.getItems().add(workoutScrollPane);


        // Stats
        ScrollPane statsScrollPane = new ScrollPane();
        statsScrollPane.setMaxWidth(300);
        GridPane stats = new GridPane();
        statsScrollPane.setContent(stats);
        statsScrollPane.setPadding(new Insets(7, 7, 5, 7));
        stats.setVgap(5);

        Label statsHeader = new Label("Workout Stats");
        statsHeader.setStyle("-fx-font-size: 17");

        stats.add(statsHeader, 0, 0);

        Label protraction = new Label("Protraction");
        stats.add(protraction,0,1);

        Label protractionValue = new Label(".5");
        stats.add(protractionValue,1,1);

        Label retraction = new Label("Retraction");
        stats.add(retraction,0,2);

        Label retractionValue = new Label(".75");
        stats.add(retractionValue,1,2);

        splitPane.getItems().add(statsScrollPane);


        tabFiles.put(tab, workoutFile);
        setTabUnsaved(tab);

        tab.setContent(splitPane);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
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
            Lift.writeExerciseCsv(tabFiles.get(tab));
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
