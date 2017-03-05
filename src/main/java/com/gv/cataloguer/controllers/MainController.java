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
    private Button updateButton;

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
    private TreeTableView<Reference> treeTableView;

    @FXML
    private TreeTableColumn<Reference, String> nameColumn;

    @FXML
    private TreeTableColumn<Reference, Long> sizeColumn;

    @FXML
    private TreeTableColumn<Reference, String> lastModifiedColumn;

    @FXML
    private TextField searchField;

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
                addButton.setDisable(true);
                deleteButton.setDisable(true);
                break;
            case DEFAULT:
                deleteButton.setDisable(true);
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

    public void addNewResource(ActionEvent actionEvent) throws IOException {
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(Main.getMainStage());
        if (file != null) {
            String category = categories.getSelectionModel().getSelectedItem();
            ResourceCatalog.getInstance().addResourceToCatalog(category, file);
            refreshTableContentFromLocalCatalog(category);
        }
    }

    public void selectCategory(MouseEvent mouseEvent) {
        String category = categories.getSelectionModel().getSelectedItem();
        Reference rootReference = new Reference(category);
        treeTableView.setRoot(new TreeItem<>(rootReference));
        TreeItem rootItem = treeTableView.getRoot();
        rootItem.setExpanded(true);
        List<Reference> references = ResourceCatalog.getInstance().getCategory(category);
        for(Reference ref : references){
            rootItem.getChildren().add(new TreeItem<>(ref));
            rootReference.setSize(rootReference.getSize() + ref.getSize());
        }
    }

    public void searchResources(ActionEvent actionEvent) {
        String category = categories.getSelectionModel().getSelectedItem();
        String pattern = searchField.getText();
        List<Reference> foundReferences = ResourceCatalog.getInstance().searchReferences(category, pattern);
        TreeItem rootItem = treeTableView.getRoot();
        rootItem.getChildren().clear();
        for(Reference ref : foundReferences){
            rootItem.getChildren().add(new TreeItem<>(ref));
        }
    }

    private void refreshTableContentFromLocalCatalog(String category){
        List<Reference> references = ResourceCatalog.getInstance().getCategory(category);
        TreeItem rootItem = treeTableView.getRoot();
        rootItem.getChildren().clear();
        Reference rootReference = (Reference)rootItem.getValue();
        rootReference.setSize(0);
        for(Reference ref : references){
            rootItem.getChildren().add(new TreeItem<>(ref));
            rootReference.setSize(rootReference.getSize() + ref.getSize());
        }
    }

    public void updateCatalogFromRemoteDatabase(ActionEvent actionEvent) {
//        updateButton.setDisable(true);
//        ResourceCatalog.getInstance().updateCatalog();
//        updateButton.setDisable(false);
    }
}
