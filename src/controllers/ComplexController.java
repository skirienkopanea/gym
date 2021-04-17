package controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import objects.Settings;
import objects.WorkoutFile;

import java.util.ArrayList;

public class ComplexController {

    @FXML
    private TabPane tabPane;

    @FXML
    private RowConstraints welcomeMessageRow;

    @FXML
    private Label welcomeMessageLabel;

    private int createdTabsSoFar = 0;

    public static ArrayList<Tab> unsavedTabs = new ArrayList<>();

    public void createTab(ActionEvent actionEvent) {
        Tab tab = new Tab();

        String title = "New " + ++createdTabsSoFar;
        tab.setText(title);

        tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Stage stage = (Stage) tabPane.getScene().getWindow();
                stage.setTitle(tab.getText() + " - gym");
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

        SplitPane slitPane = new SplitPane();
        slitPane.setDisable(false);
        slitPane.setDividerPositions(0.5, 0.5);
        slitPane.setFocusTraversable(true);
        slitPane.setPrefHeight(-1);
        slitPane.setPrefWidth(-1);


        GridPane exercises = new GridPane();
        exercises.setMinWidth(200);

        Label exercisesTitle = new Label();
        exercisesTitle.setText("Exercises");

        exercises.add(exercisesTitle, 0, 0);

        slitPane.getItems().add(exercises);

        GridPane workouts = new GridPane();
        workouts.setMinWidth(200);

        Label workoutsTitle = new Label();
        workoutsTitle.setText("Workouts");

        workouts.add(workoutsTitle, 0, 0);

        slitPane.getItems().add(workouts);

        GridPane records = new GridPane();
        records.setMinWidth(200);

        Label recordsTitle = new Label();
        recordsTitle.setText("Records");

        records.add(recordsTitle, 0, 0);

        slitPane.getItems().add(records);

        tab.setContent(slitPane);

        WorkoutFile file = new WorkoutFile(tab);

        //saveTab(tab);

        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);


    }

    public static void saveTab(Tab tab) {
        //Actually save the file, then...

        unsavedTabs.remove(tab);

    }

    public static void setTabUnsaved(Tab tab){
        unsavedTabs.add(tab);
    }

    /**
     * On close from menu/shortcut
     * @param actionEvent
     */
    public void closeTabFromMenu(ActionEvent actionEvent) {
        int tabNumber = tabPane.getSelectionModel().getSelectedIndex();
        Tab tab = tabPane.getTabs().get(tabNumber);

        if (tabPane.getTabs().size() > 0) {

            if (unsavedTabs.contains(tab) && Settings.confirmClose(actionEvent)) {
                unsavedTabs.remove(tab);
                tabPane.getTabs().remove(tabNumber);
                updateTitleTabClose();
            }
        }
    }

    public void updateTitleTabClose() {
        if (tabPane.getTabs().size() == 0) {
            welcomeMessageRow.setMaxHeight(welcomeMessageRow.getPrefHeight());
            welcomeMessageLabel.setVisible(true);
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
