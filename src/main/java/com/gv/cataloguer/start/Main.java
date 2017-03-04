package com.gv.cataloguer.start;

import com.gv.cataloguer.catalog.ResourceCatalogInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    public static final int MIN_WIDTH_OF_FORM_WINDOW = 600;
    public static final int MIN_HEIGHT_OF_FORM_WINDOW = 354;
    public static final int MIN_WIDTH_OF_MAIN_WINDOW = 840;
    public static final int MIN_HEIGHT_OF_MAIN_WINDOW = 627;

    private static Stage mainStage;

    public void start(Stage primaryStage) throws Exception {
        Thread catalogInitializer = new Thread(new ResourceCatalogInitializer());
        catalogInitializer.start();
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/form.fxml"));
        primaryStage.setMinWidth(MIN_WIDTH_OF_FORM_WINDOW);
        primaryStage.setMinHeight(MIN_HEIGHT_OF_FORM_WINDOW);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Cataloguer");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getMainStage(){
        return mainStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
