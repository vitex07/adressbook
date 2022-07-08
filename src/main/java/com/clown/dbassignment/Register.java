package com.clown.dbassignment;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Register extends Stage
{
    public Register()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Search.class.getResource("register.fxml"));
        Scene scene = null;
        try
        {
            scene = new Scene(fxmlLoader.load());
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("register load failed");
        }
        this.setTitle("register");
        this.setScene(scene);
    }
}
