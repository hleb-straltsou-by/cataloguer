package com.gv.cataloguer.controllers;

import com.gv.cataloguer.authenthication.validation.UserValidator;
import com.gv.cataloguer.gui.start.Main;
import com.gv.cataloguer.models.Role;
import com.gv.cataloguer.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FormController {
    @FXML
    private Label errorLabel;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    private static final String AUTHENTICATION_ERROR = "Error! Please, check input email address or password...";
    private static final int MIN_WINDOW_WIDTH = 840;
    private static final int MIN_WINDOW_HEIGHT = 627;
    public static Role currentRole;

    public void logIn(ActionEvent actionEvent) {
        User user = UserValidator.checkLogin(loginField.getText(), passwordField.getText());
        if(user == null){
            setAuthenticationError();
            return;
        }
        forwardToMainPage(user.getRole());
    }

    public void signUp(ActionEvent actionEvent) {
        // TODO: upload register form
    }

    public void logInAsGuest(ActionEvent actionEvent) {
        forwardToMainPage(Role.GUEST);
    }

    private void setAuthenticationError(){
        errorLabel.setText(AUTHENTICATION_ERROR);
    }

    private void forwardToMainPage(Role role){
        try {
            currentRole = role;
            Stage stage = Main.getMainStage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/main.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(true);
            stage.setMinHeight(MIN_WINDOW_HEIGHT);
            stage.setMinWidth(MIN_WINDOW_WIDTH);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
