package com.alvi.todolistapp;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene; // Import Parent
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));

        Parent root = fxmlLoader.load(); // Memuat FXML dan mendapatkan root Parent

        Scene scene = new Scene(root, 800, 600); // Membuat Scene
        // Memuat stylesheet CSS
        scene.getStylesheets().add(App.class.getResource("style.css").toExternalForm());

        stage.setTitle("My To-Do App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
