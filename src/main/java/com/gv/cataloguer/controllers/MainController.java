package com.gv.cataloguer.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button suggestButton;

    @FXML
    private Label suggestLabel;

    @FXML
    private void initialize(){
        switch (FormController.currentRole){
            case ADMIN:
                suggestButton.setVisible(false);
                suggestLabel.setVisible(false);
                break;
            case GUEST:
                addButton.setVisible(false);
                deleteButton.setVisible(false);
                break;
            case DEFAULT:
                deleteButton.setVisible(false);
                suggestButton.setVisible(false);
                suggestLabel.setVisible(false);
                break;
        }
    }
}
