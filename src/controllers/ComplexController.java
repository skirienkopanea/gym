package controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import javax.swing.*;

public class ComplexController {

    @FXML
    private TabPane tabPane;

    @FXML
    private RowConstraints welcomeMessageRow;

    @FXML
    private Label welcomeMessageLabel;


    public void createTab(ActionEvent actionEvent) {
        Tab tab = new Tab();

        String title = "New " + (tabPane.getTabs().size() + 1);
        tab.setText(title);

        tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Stage stage = (Stage) tabPane.getScene().getWindow();
                stage.setTitle(tab.getText() + " - gym");
            }
        });

        tab.setOnClosed(new EventHandler<Event>() {
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
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }


    public void closeTabFromMenu(ActionEvent actionEvent) {
        System.out.println("close attempt");
        if (tabPane.getTabs().size() > 0) {
            int tabNumber = tabPane.getSelectionModel().getSelectedIndex();
            tabPane.getTabs().remove(tabNumber);
            updateTitleTabClose();
        }
    }

    public void updateTitleTabClose(){
        if (tabPane.getTabs().size()==0) {
            welcomeMessageRow.setMaxHeight(welcomeMessageRow.getPrefHeight());
            welcomeMessageLabel.setVisible(true);
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setTitle("Welcome - gym");
        }
    }
}
