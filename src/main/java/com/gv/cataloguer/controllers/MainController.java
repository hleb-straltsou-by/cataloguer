package com.gv.cataloguer.controllers;

import com.gv.cataloguer.gui.start.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

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
    private TableView tableView;

    @FXML
    private Label logInLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private TreeView<String> treeView;

    private static final FileChooser fileChooser = new FileChooser();

    @FXML
    private void initialize(){
        logInLabel.setText("You are entered as:\n" + FormController.currentUser.getName());
        treeView.setRoot(new TreeItem<String>("Catalog"));
        TreeItem rootItem = treeView.getRoot();
        rootItem.setExpanded(true);
        rootItem.getChildren().add(new TreeItem<String>("Music"));
        rootItem.getChildren().add(new TreeItem<String>("Movies"));
        rootItem.getChildren().add(new TreeItem<String>("Books"));
        rootItem.getChildren().add(new TreeItem<String>("Documents"));
        switch (FormController.currentUser.getRole()){
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

    public void logout(ActionEvent actionEvent) {
        try {
            Stage stage = Main.getMainStage();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/form.fxml"));
            stage.setMinWidth(Main.MIN_WIDTH_OF_FORM_WINDOW);
            stage.setMinHeight(Main.MIN_HEIGHT_OF_FORM_WINDOW);
            stage.setMaxWidth(Main.MIN_WIDTH_OF_FORM_WINDOW);
            stage.setMaxHeight(Main.MIN_HEIGHT_OF_FORM_WINDOW);
            stage.setResizable(false);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addNewFile(ActionEvent actionEvent) throws IOException {
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(Main.getMainStage());
        if (file != null) {
            treeView.getRoot().getChildren().add(new TreeItem<String>(file.getName()));
        }
    }
}
