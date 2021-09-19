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
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import objects.Exercise;
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

    private List<Object> selectedExercises = null;

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

        // Exercises
        ScrollPane exerciseScrollPane = new ScrollPane();
        GridPane exercises = new GridPane();
        exercises.setPadding(new Insets(7, 7, 5, 7));
        exercises.setVgap(5);

        Label exerciseHeader = new Label("Exercises");
        exerciseHeader.setStyle("-fx-font-size: 17");
        exercises.add(exerciseHeader, 0, 0);

        // Exercise Actions
        GridPane exerciseActions = new GridPane();
        exerciseActions.setHgap(5);

        Button train = new Button("Train selected");
        Button clear = new Button("Clear selection");

        exerciseActions.add(train, 0, 0);
        exerciseActions.add(clear, 1, 0);
        exercises.add(exerciseActions, 0, 1);

        // Exercise Records

        TableView tableView = new TableView();

        TableColumn<Exercise, String> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Exercise, String> column2 = new TableColumn<>("Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Exercise, String> column3 = new TableColumn<>("Description");
        column3.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Exercise, String> column4 = new TableColumn<>("Protraction");
        column4.setCellValueFactory(new PropertyValueFactory<>("protraction"));

        TableColumn<Exercise, String> column5 = new TableColumn<>("Retraction");
        column5.setCellValueFactory(new PropertyValueFactory<>("retraction"));

        TableColumn<Exercise, String> column6 = new TableColumn<>("Upward Rotation");
        column6.setCellValueFactory(new PropertyValueFactory<>("upwardRotation"));

        TableColumn<Exercise, String> column7 = new TableColumn<>("Downward rotation");
        column7.setCellValueFactory(new PropertyValueFactory<>("downwardRotation"));

        TableColumn<Exercise, String> column8 = new TableColumn<>("Depression");
        column8.setCellValueFactory(new PropertyValueFactory<>("depression"));

        TableColumn<Exercise, String> column9 = new TableColumn<>("Elevation");
        column9.setCellValueFactory(new PropertyValueFactory<>("elevation"));

        TableColumn<Exercise, String> column10 = new TableColumn<>("Internal rotation");
        column10.setCellValueFactory(new PropertyValueFactory<>("internalRotation"));

        TableColumn<Exercise, String> column11 = new TableColumn<>("External rotation");
        column11.setCellValueFactory(new PropertyValueFactory<>("externalRotation"));

        TableColumn<Exercise, String> column12 = new TableColumn<>("Hamstring");
        column12.setCellValueFactory(new PropertyValueFactory<>("hamstringEmphasis"));

        TableColumn<Exercise, String> column13 = new TableColumn<>("Quad");
        column13.setCellValueFactory(new PropertyValueFactory<>("quadricepsEmphasis"));

        TableColumn<Exercise, String> column14 = new TableColumn<>("Reference");
        column14.setCellValueFactory(new PropertyValueFactory<>("ratioReferenceExercise"));

        TableColumn<Exercise, String> column15 = new TableColumn<>("Ratio");
        column15.setCellValueFactory(new PropertyValueFactory<>("ratioOverReference"));

        TableColumn<Exercise, String> column16 = new TableColumn<>("Sources");
        column16.setCellValueFactory(new PropertyValueFactory<>("sources"));

        tableView.getColumns().addAll(column1, column2, column3, column4, column5, column6,
                column7, column8, column9, column10, column11, column12, column13, column14, column15, column16);

        loadExercises(tableView, workoutFile.getExerciseList());

        exercises.add(tableView, 0, 2);

        tableView.prefHeightProperty().bind(splitPane.heightProperty());
        tableView.prefWidthProperty().bind(splitPane.widthProperty());

        TableView.TableViewSelectionModel<Exercise> selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        //for the buttons
        ObservableList<Exercise> selectedItems = selectionModel.getSelectedItems();
        selectedItems.addListener(new ListChangeListener<Exercise>() {
            @Override
            public void onChanged(Change<? extends Exercise> change) {
                selectedExercises = Arrays.asList(change.getList().toArray());
                System.out.println("Selection changed: " + change.getList());
            }
        });

        selectionModel.clearSelection();

        exerciseScrollPane.setContent(exercises);
        splitPane.getItems().add(exerciseScrollPane);

        // Workouts
        ScrollPane workoutScrollPane = new ScrollPane();
        workoutScrollPane.setMinWidth(200);
        GridPane workouts = new GridPane();
        workouts.setPadding(new Insets(7, 7, 5, 7));

        Label workoutHeader = new Label("Workout");
        workoutHeader.setStyle("-fx-font-size: 17");
        workoutHeader.setPadding(new Insets(0, 0, 5, 0));

        workouts.add(workoutHeader, 0, 0);

        GridPane workoutRecords = new GridPane();
        workouts.add(workoutRecords, 0, 1);

        workoutScrollPane.setContent(workouts);
        splitPane.getItems().add(workoutScrollPane);


        // Strength Records
        GridPane records = new GridPane();

        Label recordsTitle = new Label();
        recordsTitle.setText("Records");

        records.add(recordsTitle, 0, 0);

        splitPane.getItems().add(records);


        tabFiles.put(tab, workoutFile);
        setTabUnsaved(tab);

        tab.setContent(splitPane);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private void loadExercises(TableView tableView, ArrayList<Exercise> exerciseList) {
        for (Exercise exercise : exerciseList) {
            tableView.getItems().add(exercise);
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
            Exercise.writeExerciseCsv(tabFiles.get(tab));
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
