package com.gv.cataloguer.controllers;

import com.gv.cataloguer.authenthication.validation.UserValidator;
import com.gv.cataloguer.start.Main;
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
    private static final User USER_GUEST = new User(Role.GUEST);
    public static User currentUser;

    public void logIn(ActionEvent actionEvent) {
        User user = UserValidator.checkLogin(loginField.getText(), passwordField.getText());
        if(user == null){
            setAuthenticationError();
            return;
        }
        forwardToMainPage(user);
    }

    public void signUp(ActionEvent actionEvent) {
        // TODO: upload register form
    }

    public void logInAsGuest(ActionEvent actionEvent) {
        forwardToMainPage(USER_GUEST);
    }

    private void setAuthenticationError(){
        errorLabel.setText(AUTHENTICATION_ERROR);
    }

    private void forwardToMainPage(User user){
        try {
            currentUser = user;
            Stage stage = Main.getMainStage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/main.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(true);
            stage.setMinHeight(Main.MIN_HEIGHT_OF_MAIN_WINDOW);
            stage.setMinWidth(Main.MIN_WIDTH_OF_MAIN_WINDOW);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
