package com.gv.cataloguer.controllers;

import com.gv.cataloguer.authenthication.dao.UserDao;
import com.gv.cataloguer.browsing.DesktopBrowser;
import com.gv.cataloguer.catalog.ResourceCatalog;
import com.gv.cataloguer.email.concurrency.EmailSender;
import com.gv.cataloguer.email.concurrency.EmailSenderWithAttachment;
import com.gv.cataloguer.logging.AppLogger;
import com.gv.cataloguer.models.Reference;
import com.gv.cataloguer.models.Role;
import com.gv.cataloguer.models.User;
import com.gv.cataloguer.start.Main;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is a controller class, which is used to bind gui and business logic
 * according MVC pattern. Controller for the view represented in main.fxml file
 */
public class MainController {

    @FXML
    /** object of updateButton, that initiate updating local catalog
     * from remote data storage*/
    private Button updateButton;

    @FXML
    /** object of addButton, that initiate adding new resources to remote
     * data storage an to the local catalog */
    private Button addButton;

    @FXML
    /** object of deleteButton, that initiate deleting resources from remote server
     * and local catalog */
    private Button deleteButton;

    @FXML
    /** object of suggestButton, that initiate selecting and uploading to the admin email address
     * suggesting resource from guest */
    private Button suggestButton;

    @FXML
    /** object for representing text of suggestion to adding new resource to the remote catalog */
    private Label suggestLabel;

    @FXML
    /** list of categories represented in both local and remote catalog */
    private ListView<String> categories;

    @FXML
    /** object for representing name of entered user */
    private Label logInLabel;

    @FXML
    /** tree object for representing resources according category */
    private TreeTableView<Reference> treeTableView;

    @FXML
    /** column of treeTableObject with name property of stored resource reference */
    private TreeTableColumn<Reference, String> nameColumn;

    @FXML
    /** column of treeTableObject with size property of stored resource reference */
    private TreeTableColumn<Reference, Long> sizeColumn;

    @FXML
    /**  column of treeTableObject with lastModified property of stored resource reference */
    private TreeTableColumn<Reference, String> lastModifiedColumn;

    @FXML
    /** object for input string pattern for searching resource references in local catalog */
    private TextField searchField;

    @FXML
    /** object for representing status information of executing or not executing operation with catalog*/
    private Label statusLabel;

    /** object for selecting resources from users desktop */
    private static FileChooser fileChooser = new FileChooser();

    /** variable for storing count of bytes in megabytes */
    private static final int BYTES_IN_MB = 1024*1024;

    /** variable for storing count of millis in hour */
    private static final int MILLIS_IN_HOUR = 60*60*1000;

    /** variable for storing count of hours in day */
    private static final int HOURS_IN_DAY = 24;

    private ApplicationContext authenticationContext = new ClassPathXmlApplicationContext("IoC/authenthication-context.xml");

    private static final String USER_DAO_BEAN = "userDaoJDBC";

    private UserDao userDao = (UserDao) authenticationContext.getBean(USER_DAO_BEAN);

    private ApplicationContext emailContext = new ClassPathXmlApplicationContext("IoC/email-context.xml");

    private static final String EMAIL_SENDER_BEAN = "emailSender";

    private static final String EMAIL_SENDER_WITH_ATTACHMENT_BEAN = "emailSenderWithAttachment";

    private static final String EMAIL_SERVICE_BEAN = "emailServiceGmail";

    private ResourceCatalog catalog = ResourceCatalog.getInstance();

    @FXML
    /**
     * initializes entered users name and visible elements according users role,
     * also method sets type of cell properties in columns of treeTableView object
     * and initiate event filter for Mouse event in treeTable view object
     */
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

    /**
     * returns user to the form page with authentication procedure
     * @param actionEvent - JafaFx event for binding view and controller
     */
    public void logout(ActionEvent actionEvent) {
        try {
            Stage stage = Main.getMainStage();
            stage.close();
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
            AppLogger.getLogger().error(e.getMessage());
        }
    }

    /**
     * handles users mouse click on list of categories and sets references of selected
     * category in treeTableView object
     * @param mouseEvent - JafaFx event for binding view and controller
     */
    public void selectCategory(MouseEvent mouseEvent) {
        String category = categories.getSelectionModel().getSelectedItem();
        Reference rootReference = new Reference(category);
        treeTableView.setRoot(new TreeItem<>(rootReference));
        TreeItem rootItem = treeTableView.getRoot();
        rootItem.setExpanded(true);
        List<Reference> references = catalog.getCategory(category);
        for(Reference ref : references){
            rootItem.getChildren().add(new TreeItem<>(ref));
            rootReference.setSize(rootReference.getSize() + ref.getSize());
        }
    }


    /**
     * gets input text pattern form searchField object and find all references that
     * correspond to it, after that sets found references to the treeTableVies object
     * @param actionEvent - JafaFx event for binding view and controller
     */
    public void searchResources(ActionEvent actionEvent) {
        String category = categories.getSelectionModel().getSelectedItem();
        String pattern = searchField.getText();
        List<Reference> foundReferences = catalog.searchReferences(category, pattern);
        TreeItem rootItem = treeTableView.getRoot();
        rootItem.getChildren().clear();
        for(Reference ref : foundReferences){
            rootItem.getChildren().add(new TreeItem<>(ref));
        }
    }

    /**
     * initiate executing thread, that synchronizes resources from remote data storage
     * with resources in local catalog
     * @param actionEvent - JafaFx event for binding view and controller
     */
    public void updateCatalogFromRemoteDatabase(ActionEvent actionEvent) {
        updateButton.setDisable(true);
        catalog.updateCatalog();
        updateButton.setDisable(false);
    }

    /**
     * opens resource in users desktop and checks users permission on adding new
     * resources, after that if permissions are correspond adds new resource to
     * remote and local catalog
     * @param actionEvent - JafaFx event for binding view and controller
     */
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

    /**
     * deletes resource from remote, local catalogs and from list of references
     * according reference object and selected category
     * @param actionEvent - JafaFx event for binding view and controller
     */
    public void deleteSelectedResource(ActionEvent actionEvent) {
        Reference ref = treeTableView.getSelectionModel().getSelectedItem().getValue();
        String category = categories.getSelectionModel().getSelectedItem();
        catalog.deleteResourceFromCatalog(category, ref);
        refreshTableContentFromLocalCatalog(category);
    }

    /**
     * opens resource from users desktop and after that initiate executing thread that
     * sends to the admin's email suggesting resource
     * @param actionEvent - JafaFx event for binding view and controller
     */
    public void suggestResource(ActionEvent actionEvent) {
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(Main.getMainStage());
        if(file != null){
            String from = "ka1oken4by@gmail.com";
            String to = "gleb.streltsov.4by@gmail.com";
            String subject = "Proposal to add new resources";
            String messageText = "Hey! Anonymous guest suggest to add new resource to remote catalog." +
                    "" +
                    " Suggesting resource is attached below";
            List<String> destinationList = new ArrayList<>(1);
            destinationList.add(to);
            EmailSenderWithAttachment sender = (EmailSenderWithAttachment)emailContext.getBean(EMAIL_SENDER_WITH_ATTACHMENT_BEAN);
            sender.setFrom(from);
            sender.setDestinationList(destinationList);
            sender.setSubject(subject);
            sender.setMessageText(messageText);
            sender.setResource(file);
            sender.start();
        }
    }

    /**
     * adds resource to the the remote and local catalog according selected category,
     * then refresh content of treeTableView object and invoke method that notifies
     * all registered users, that content of the remote catalog was updated
     * @param file - resource that will be added to the local and remote catalog
     */
    private void executeUpdateToCatalog(File file){
        String category = categories.getSelectionModel().getSelectedItem();
        catalog.addResourceToCatalog(category, file);
        refreshTableContentFromLocalCatalog(category);
        sendNotificationToRegisteredUsers();
    }

    /**
     * gets all emails of registered users from remote data storage and initiate thread that
     * sends send notification to the users emails that content of remote catalog was updated
     */
    private void sendNotificationToRegisteredUsers(){
        List<String> emails = userDao.getAllUserEmails();
        String from = "ka1oken4by@gmail.com";
        String subject = "Catalog notification";
        String messageText = "Hey! New resources were added to catalog! Check them out!";
        EmailSender sender = (EmailSender)emailContext.getBean(EMAIL_SENDER_BEAN);
        sender.setFrom(from);
        sender.setDestinationList(emails);
        sender.setSubject(subject);
        sender.setMessageText(messageText);
        sender.start();
    }

    /**
     * checks if user has permissions to add new resource to remote data storage, if it is
     * then resourse will be added to the remote data storage and registered users in app
     * will be notified by email
     * @param file - resource for adding to the remote data storage
     */
    private void addNewResourceCheckingLimits(File file){
        int userId = FormController.currentUser.getUserId();
        Object[] lastUpdateAndTraffic = userDao
                .getLastUpdateAndTraffic(userId);
        Date currentDate = new Date();
        if(lastUpdateAndTraffic[0] == null && (file.length() / (BYTES_IN_MB)) <= Role.getDefaultUserLimit()){
            executeUpdateToCatalog(file);
            userDao.setLastUpdateAndTraffic(userId, new Date(),
                    (int)file.length() / BYTES_IN_MB);
            sendNotificationToRegisteredUsers();
        } else if((currentDate.getTime() - ((Date)lastUpdateAndTraffic[0]).getTime()) / (MILLIS_IN_HOUR)
                > HOURS_IN_DAY && (file.length() / (BYTES_IN_MB)) <= Role.getDefaultUserLimit()){
            executeUpdateToCatalog(file);
            userDao.setLastUpdateAndTraffic(userId, new Date(),
                    (int)file.length() / BYTES_IN_MB);
            sendNotificationToRegisteredUsers();
        } else if(((int)lastUpdateAndTraffic[1]*BYTES_IN_MB + (int)file.length()) / BYTES_IN_MB <= Role.getDefaultUserLimit()){
            executeUpdateToCatalog(file);
            userDao.setLastUpdateAndTraffic(userId, new Date(),
                    (int)file.length() / BYTES_IN_MB + (int)lastUpdateAndTraffic[1]);
            sendNotificationToRegisteredUsers();
        } else {
            sendFailMessage("Error! You have been reached your limit of adding\nnew resources per day...");
        }
    }

    /**
     * Sets error message in the status label object
     */
    private void sendFailMessage(String errorMessage){
        statusLabel.setText(errorMessage);
    }

    /**
     * refreshes content of treeTableView object according resources that stored in local catalog
     * @param category - name of category with files
     */
    private void refreshTableContentFromLocalCatalog(String category){
        List<Reference> references = catalog.getCategory(category);
        TreeItem rootItem = treeTableView.getRoot();
        rootItem.getChildren().clear();
        Reference rootReference = (Reference)rootItem.getValue();
        rootReference.setSize(0);
        for(Reference ref : references){
            rootItem.getChildren().add(new TreeItem<>(ref));
            rootReference.setSize(rootReference.getSize() + ref.getSize());
        }
    }

    /**
     * sets available extensions for the selecting file in the desktop that
     * correspond to the category
     * @param category - name of category with files
     */
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

    /**
     *  sets available extensions for the selecting file in the fileChooser object according
     *  music category
     */
    private void setExtensionFiltersForMusic(){
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3", "*.mp3"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
    }

    /**
     *  sets available extensions for the selecting file in the fileChooser object according
     *  movies category
     */
    private void setExtensionFiltersForMovies(){
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("AVI", "*.avi"),
                new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
    }

    /**
     *  sets available extensions for the selecting file in the fileChooser object according
     *  music books
     */
    private void setExtensionFiltersForBooks(){
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("DJVU", "*.djvu"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
    }

    /**
     *  sets available extensions for the selecting file in the fileChooser object according
     *  documents category
     */
    private void setExtensionFiltersForDocuments(){
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("DOC", "*.doc"),
                new FileChooser.ExtensionFilter("DOCX", "*.docx"),
                new FileChooser.ExtensionFilter("All", "*.*")
        );
    }

    /**
     * checks users role and return boolean value
     * @param currentUser - user entered to the app
     * @return true - if users role is ADMIN
     *         false - if users role is different
     */
    private boolean checkUser(User currentUser){
        switch (currentUser.getRole()){
            case ADMIN:
                return true;
            default:
                return false;
        }
    }
}
