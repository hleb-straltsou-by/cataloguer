package com.gv.cataloguer.gui.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    private static final int MIN_WINDOW_WIDTH = 600;
    private static final int MIN_WINDOW_HEIGHT = 354;
    private static Stage mainStage;

    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/form.fxml"));
        primaryStage.setMinWidth(MIN_WINDOW_WIDTH);
        primaryStage.setMinHeight(MIN_WINDOW_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Cataloguer");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("css/form.css");
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
