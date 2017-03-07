package com.gv.cataloguer.start;

import com.gv.cataloguer.catalog.ResourceCatalog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{

    public static final int MIN_WIDTH_OF_FORM_WINDOW = 600;
    public static final int MIN_HEIGHT_OF_FORM_WINDOW = 354;
    public static final int MIN_WIDTH_OF_MAIN_WINDOW = 840;
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

    public static Stage getMainStage(){
        return mainStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
