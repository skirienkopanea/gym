package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.swing.JFileChooser;

public class SampleController {
    @FXML
    Label helloWorld;

    public void sayHelloWorld(ActionEvent actionEvent) {
        helloWorld.setText("Hello World!");
    //    Scanner input = new Scanner(new File("/"))
    }
}
