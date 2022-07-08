package com.clown.dbassignment;

import Database.DataBase;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application
{
    public static BorderPane root = null;
    public static AnchorPane leftPane = null;
    public static AnchorPane emptyPane = null;

    @Override
    public void start(Stage mainStage)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Search.class.getResource("main.fxml"));
        FXMLLoader leftLoader = new FXMLLoader(Search.class.getResource("functionSelect.fxml"));
        FXMLLoader emptyLoader = new FXMLLoader(Search.class.getResource("empty.fxml"));
        try
        {
            root = new BorderPane(fxmlLoader.load());
            leftPane = new AnchorPane((AnchorPane)leftLoader.load());
            emptyPane = new AnchorPane((AnchorPane)emptyLoader.load());

            root.setLeft(leftPane);
            root.setCenter(emptyPane);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("mainFxml load failed");
        }

        mainStage.setTitle("deal");
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setWidth(700);
        mainStage.setHeight(500);
        mainStage.show();
    }

    /*切换到搜索页面*/
    @FXML
    public void onSearchButtonClick() throws IOException
    {
        if (!isLogin())
        {
            return;
        }
        FXMLLoader searchLoader = new FXMLLoader(Search.class.getResource("search.fxml"));
        AnchorPane searchPane = new AnchorPane((AnchorPane)searchLoader.load());
        root.setCenter(searchPane);
    }

    /*切换到登录页面*/
    @FXML
    public void onLoginButtonClick() throws IOException
    {
        if (!isLogin())
        {
            FXMLLoader loginLoader = new FXMLLoader(Search.class.getResource("login.fxml"));
            AnchorPane loginPane = new AnchorPane((AnchorPane)loginLoader.load());
            root.setCenter(loginPane);
        }
    }

    /*以表格显示*/
    @FXML
    public void onTableButtonClick() throws SQLException
    {
        if (!isLogin())
        {
            return;
        }
        BorderPane borderPane = new Table();
        root.setCenter(borderPane);
    }
    public static void main(String[] args)
    {
        launch(args);
    }
    /*检查是否登录*/
    private Boolean isLogin()
    {
        return DataBase.Id==-1?false:true;
    }
}
