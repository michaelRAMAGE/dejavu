package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public abstract class CustomCreateViewController {

    @FXML
    protected Label ViewHelpText;

    @FXML
    protected TextField nameTextField;
    
    @FXML
    protected Button cancelCreationButton;

    @FXML
    protected Button createButton;

    @FXML
    abstract void onCancel(ActionEvent event) throws IOException; 
    
    @FXML
    abstract void onCreate(ActionEvent event) throws IOException;

}
