package com.gv.cataloguer.controllers;

import com.gv.cataloguer.catalog.ResourceCatalog;
import com.gv.cataloguer.start.Main;
import com.gv.cataloguer.models.Reference;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    private ListView<String> categories;

    @FXML
    private Label logInLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private TreeTableView<Reference> treeTableView;

    @FXML
    private TreeTableColumn<Reference, String> nameColumn;

    @FXML
    private TreeTableColumn<Reference, Long> sizeColumn;

    @FXML
    private TreeTableColumn<Reference, String> lastModifiedColumn;

    private static final FileChooser fileChooser = new FileChooser();

    @FXML
    private void initialize(){
        logInLabel.setText("You are entered as:\n" + FormController.currentUser.getName());
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
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
        sizeColumn.setCellValueFactory(new TreeItemPropertyValueFactory("size"));
        lastModifiedColumn.setCellValueFactory(new TreeItemPropertyValueFactory("lastModified"));
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
            String category = categories.getSelectionModel().getSelectedItem();
            ResourceCatalog.getInstance().addResourceToCatalog(category, file);
        }
    }

    public void selectCategory(MouseEvent mouseEvent) {
        String category = categories.getSelectionModel().getSelectedItem();
        treeTableView.setRoot(new TreeItem<>(new Reference(category)));
        TreeItem rootItem = treeTableView.getRoot();
        rootItem.setExpanded(true);
        List<Reference> references = ResourceCatalog.getInstance().getCategory(category);
        for(Reference ref : references){
            rootItem.getChildren().add(new TreeItem<>(ref));
        }
//        rootItem.getChildren().add(new TreeItem<String>("Music"));
//        rootItem.getChildren().add(new TreeItem<String>("Movies"));
//        rootItem.getChildren().add(new TreeItem<String>("Books"));
//        rootItem.getChildren().add(new TreeItem<String>("Documents"));
    }
}
