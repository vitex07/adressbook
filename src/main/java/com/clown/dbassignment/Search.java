package com.clown.dbassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Search extends Application
{
    @Override
    public void start(Stage mainStage)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Search.class.getResource("search.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(fxmlLoader.load());
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("searchFxml load failed");
        }
        mainStage.setTitle("deal");
        mainStage.setScene(scene);
        mainStage.show();
    }
}
