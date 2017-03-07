package com.gv.cataloguer.start;

import com.gv.cataloguer.catalog.ResourceCatalog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Entry point of whole app
 */
public class Main extends Application{

    /** min size of form window in pixels*/
    public static final int MIN_WIDTH_OF_FORM_WINDOW = 600;

    /** min size of form window in pixels*/
    public static final int MIN_HEIGHT_OF_FORM_WINDOW = 354;

    /** min size of main window in pixels*/
    public static final int MIN_WIDTH_OF_MAIN_WINDOW = 840;

    /** min size of main window in pixels*/
    public static final int MIN_HEIGHT_OF_MAIN_WINDOW = 627;

    private static Stage mainStage;

    public void start(Stage primaryStage) throws Exception {
        ResourceCatalog.getInstance().updateCatalog();
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/form.fxml"));
        primaryStage.setMinWidth(MIN_WIDTH_OF_FORM_WINDOW);
        primaryStage.setMinHeight(MIN_HEIGHT_OF_FORM_WINDOW);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Cataloguer");
        primaryStage.getIcons().add(new Image(getClass().getClassLoader()
                .getResource("pictures/icons/favicon.jpg").toExternalForm()));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @return main stage of javaFx object
     */
    public static Stage getMainStage(){
        return mainStage;
    }

    /**
     * defines entry point of app
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
