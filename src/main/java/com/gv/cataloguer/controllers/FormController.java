package com.gv.cataloguer.controllers;

import com.gv.cataloguer.authenthication.validation.UserValidator;
import com.gv.cataloguer.cryptography.CryptographerXOR;
import com.gv.cataloguer.logging.AppLogger;
import com.gv.cataloguer.models.Role;
import com.gv.cataloguer.models.User;
import com.gv.cataloguer.start.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * This is a controller class, which is used to bind gui and business logic
 * according MVC pattern. Controller for the view represented in main.fxml file
 */
public class FormController {

    @FXML
    /** object for representing errors */
    private Label errorLabel;

    @FXML
    /** object used for input login of user */
    private TextField loginField;

    @FXML
    /** object used for input password of user */
    private PasswordField passwordField;

    /** object used to enter into main page as guest */
    private static final User USER_GUEST = new User("Guest", Role.GUEST);

    /** object for storing entered user */
    public static User currentUser;

    private ApplicationContext context = new ClassPathXmlApplicationContext("IoC/authenthication-context.xml");

    private static final String VALIDATOR_BEAN = "userValidator";

    /** validator object used to authenticate users*/
    private UserValidator validator = (UserValidator)context.getBean(VALIDATOR_BEAN);

    /**
     * checks if input password and login are valid, if it is,
     * then method forward user to the main page
     * @param actionEvent - JafaFx event for binding view and controller
     */
    public void logIn(ActionEvent actionEvent) {
        User user = validator.checkLogin(loginField.getText(), CryptographerXOR.getInstance()
                .encrypt(passwordField.getText()));
        if(user == null){
            setAuthenticationError();
            return;
        }
        forwardToMainPage(user);
    }

    /**
     * loads register form for the new user for input initial register data.
     * @param actionEvent - JafaFx event for binding view and controller
     */
    public void signUp(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/register.fxml"));
            Scene scene = new Scene(root);
            stage.setMinHeight(RegistrationController.MAX_HEIGHT_REGISTER_WINDOW);
            stage.setMinWidth(RegistrationController.MAX_WIDTH_REGISTER_WINDOW);
            stage.setMaxHeight(RegistrationController.MAX_HEIGHT_REGISTER_WINDOW);
            stage.setMaxWidth(RegistrationController.MAX_WIDTH_REGISTER_WINDOW);
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getClassLoader()
                    .getResource("pictures/icons/favicon.jpg").toExternalForm()));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.setTitle("Registration");
            stage.show();
        } catch (IOException e) {
            AppLogger.getLogger().error(e);
        }
    }

    /**
     * forward unregistered user to the main page as guest.
     * @param actionEvent - JafaFx event for binding view and controller
     */
    public void logInAsGuest(ActionEvent actionEvent) {
        forwardToMainPage(USER_GUEST);
    }

    /**
     * sets authentication error message to the errorLabel
     */
    private void setAuthenticationError(){
        errorLabel.setText("Error! Please, check input email address or password...");
    }

    /**
     * sets up main page and registered entered user in currentUser static variable
     * @param user - object of entered user.
     */
    private void forwardToMainPage(User user){
        try {
            currentUser = user;
            Stage stage = Main.getMainStage();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/main.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(true);
            stage.setMinHeight(Main.MIN_HEIGHT_OF_MAIN_WINDOW);
            stage.setMinWidth(Main.MIN_WIDTH_OF_MAIN_WINDOW);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            AppLogger.getLogger().error(e.getMessage());
        }
    }
}
