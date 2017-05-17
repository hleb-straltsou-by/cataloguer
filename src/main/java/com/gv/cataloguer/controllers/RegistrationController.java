package com.gv.cataloguer.controllers;

import com.gv.cataloguer.authenthication.dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RegistrationController {

    @FXML
    private TextField emailForm;

    @FXML
    private TextField nameForm;

    @FXML
    private PasswordField passwordForm;

    @FXML
    private PasswordField passwordRepeatForm;

    @FXML
    private ChoiceBox roleBox;

    private ApplicationContext context;

    private static final String USER_DAO_BEAN = "userDaoJDBC";

    private UserDao userDao;

    public static final int MAX_WIDTH_REGISTER_WINDOW = 324;

    public static final int MAX_HEIGHT_REGISTER_WINDOW = 320;

    @FXML
    private void initialize(){
        context = new ClassPathXmlApplicationContext("IoC/authenthication-context.xml");
        userDao = (UserDao)context.getBean(USER_DAO_BEAN);
    }

    public void okClicked(ActionEvent actionEvent) {
        Node source = (Node)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    public void cancelClicked(ActionEvent actionEvent) {
        Node source = (Node)actionEvent.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }
}
