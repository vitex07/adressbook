package com.clown.dbassignment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Alert extends Stage
{
    public Alert()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Search.class.getResource("alert.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(fxmlLoader.load());
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("alert load failed");
        }
        this.setTitle("alert");
        this.setScene(scene);
    }
}
