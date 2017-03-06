package com.gv.cataloguer.controllers;

import com.gv.cataloguer.authenthication.dao.UserDaoSingleton;
import com.gv.cataloguer.browsing.DesktopBrowser;
import com.gv.cataloguer.catalog.ResourceCatalog;
import com.gv.cataloguer.models.Role;
import com.gv.cataloguer.models.User;
import com.gv.cataloguer.start.Main;
import com.gv.cataloguer.models.Reference;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.util.Date;
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

    @FXML
    private Label statusLabel;

    private static FileChooser fileChooser = new FileChooser();
    private static final int BYTES_IN_MB = 1024*1024;
    private static final int MILLIS_IN_HOUR = 60*60*1000;
    private static final int HOURS_IN_DAY = 24;

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
        treeTableView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() > 1){
                    Reference ref = treeTableView.getSelectionModel().getSelectedItem().getValue();
                    DesktopBrowser.openFile(ref.getResource());
                }
            }
        });
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

    public void selectCategory(MouseEvent mouseEvent) {
        String category = categories.getSelectionModel().getSelectedItem();
        Reference rootReference = new Reference(category);
        treeTableView.setRoot(new TreeItem<>(rootReference));
        TreeItem rootItem = treeTableView.getRoot();
        rootItem.getChildren().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {

            }
        });
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

    public void updateCatalogFromRemoteDatabase(ActionEvent actionEvent) {
//        updateButton.setDisable(true);
//        ResourceCatalog.getInstance().updateCatalog();
//        updateButton.setDisable(false);
    }

    public void addNewResource(ActionEvent actionEvent) throws IOException {
        fileChooser.setTitle("Open Resource File");
        setExtensionFilters(categories.getSelectionModel().getSelectedItem());
        File file = fileChooser.showOpenDialog(Main.getMainStage());
        if (file != null) {
            if(checkUser(FormController.currentUser)) {
                executeUpdateToCatalog(file);
            }else{
                addNewResourceCheckingLimits(file);
            }
        }
    }

    private void executeUpdateToCatalog(File file){
        String category = categories.getSelectionModel().getSelectedItem();
        ResourceCatalog.getInstance().addResourceToCatalog(category, file);
        refreshTableContentFromLocalCatalog(category);
    }

    private void addNewResourceCheckingLimits(File file){
        int userId = FormController.currentUser.getUserId();
        Object[] lastUpdateAndTraffic = UserDaoSingleton.getInstance()
                .getLastUpdateAndTraffic(userId);
        Date currentDate = new Date();
        if(lastUpdateAndTraffic[0] == null && (file.length() / (BYTES_IN_MB)) <= Role.getDefaultUserLimit()){
            executeUpdateToCatalog(file);
            UserDaoSingleton.getInstance().setLastUpdateAndTraffic(userId, new Date(),
                    (int)file.length() / BYTES_IN_MB);
        } else if((currentDate.getTime() - ((Date)lastUpdateAndTraffic[0]).getTime()) / (MILLIS_IN_HOUR)
                > HOURS_IN_DAY && (file.length() / (BYTES_IN_MB)) <= Role.getDefaultUserLimit()){
            executeUpdateToCatalog(file);
            UserDaoSingleton.getInstance().setLastUpdateAndTraffic(userId, new Date(),
                    (int)file.length() / BYTES_IN_MB);
        } else if(((int)lastUpdateAndTraffic[1]*BYTES_IN_MB + (int)file.length()) / BYTES_IN_MB <= Role.getDefaultUserLimit()){
            executeUpdateToCatalog(file);
            UserDaoSingleton.getInstance().setLastUpdateAndTraffic(userId, new Date(),
                    (int)file.length() / BYTES_IN_MB + (int)lastUpdateAndTraffic[1]);
        } else {
            sendFailMessage();
        }
    }

    private void sendFailMessage(){
        statusLabel.setText("Error! You have been reached your limit of adding\nnew resources per day...");
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

    private void setExtensionFilters(String category){
        switch (category){
            case "music":
                setExtensionFiltersForMusic();
                break;
            case "movies":
                setExtensionFiltersForMovies();
                break;
            case "books":
                setExtensionFiltersForBooks();
                break;
            case "documents":
                setExtensionFiltersForDocuments();
                break;
            default:
                break;
        }
    }

    private void setExtensionFiltersForMusic(){
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3", "*.mp3"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
    }

    private void setExtensionFiltersForMovies(){
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("AVI", "*.avi"),
                new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
    }

    private void setExtensionFiltersForBooks(){
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("DJVU", "*.djvu"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
    }

    private void setExtensionFiltersForDocuments(){
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("DOC", "*.doc"),
                new FileChooser.ExtensionFilter("DOCX", "*.docx"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
    }

    private boolean checkUser(User currentUser){
        switch (currentUser.getRole()){
            case ADMIN:
                return true;
            default:
                return false;
        }
    }
}
