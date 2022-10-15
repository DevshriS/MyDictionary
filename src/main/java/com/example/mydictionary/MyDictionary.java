package com.example.mydictionary;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MyDictionary extends Application {
    private Group tilegroup = new Group();
    int xLine = 20;
    int yLine = 20;
    int yLine2 = 50;
    DictionaryUsingHashMap dictionary;

    private Pane createContent() {
        Pane root = new Pane();
        root.setPrefSize(400, 300);
        root.getChildren().addAll(tilegroup);

        dictionary = new DictionaryUsingHashMap();

        TextField wordText = new TextField("Accio Word");
        wordText.setTranslateX(xLine);
        wordText.setTranslateY(yLine);

        Label meaningLabel = new Label("I Am Meaning");
        meaningLabel.setTranslateX(xLine);
        meaningLabel.setTranslateY(yLine2);

        Button searchButton = new Button("Search");
        searchButton.setTranslateX(xLine + 200);
        searchButton.setTranslateY(yLine);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String word = wordText.getText();
                if (word.isBlank()) {
                    meaningLabel.setText("Please Enter a Word");
                } else {
                    meaningLabel.setText(dictionary.findMeaning(word));
                }
            }
        });
        tilegroup.getChildren().addAll(wordText, searchButton, meaningLabel);
        return root;
    }

    public void connectToDatabase() {
        final String DB_URL = "jdbc:mysql://localhost:3306/my_dictionary";
        final String USER = "root";
        final String PASS = "Asdf@1234";
        System.out.println("Connecting to database");
        String newId = "select * from word_list";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(newId);
        ) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + rs.getString("word") + rs.getString("meaning"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        connectToDatabase();
        Scene scene = new Scene(createContent());
        stage.setTitle("My Dictionary!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}