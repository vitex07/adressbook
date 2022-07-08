package com.clown.dbassignment;

import Database.DataBase;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Table extends BorderPane
{
    public Table() throws SQLException {
        super();
        /*添加表*/
        TableView table = new TableView<>();
        TableColumn firstNameCol = new TableColumn("姓名");
        firstNameCol.setMinWidth(40);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn secondNameCol = new TableColumn<>("简介");
        secondNameCol.setMinWidth(50);
        secondNameCol.setCellValueFactory(new PropertyValueFactory<>("introduction"));
        TableColumn thirdNameCol = new TableColumn<>("电话");
        thirdNameCol.setMinWidth(50);
        thirdNameCol.setCellValueFactory(new PropertyValueFactory<>("tel"));
        TableColumn col4 = new TableColumn<>("时间");
        col4.setMinWidth(40);
        col4.setCellValueFactory(new PropertyValueFactory<>("time"));
        TableColumn col5 = new TableColumn<>("性别");
        col5.setMinWidth(40);
        col5.setCellValueFactory(new PropertyValueFactory<>("sex"));
        TableColumn col6 = new TableColumn<>("组名");
        col6.setMinWidth(40);
        col6.setCellValueFactory(new PropertyValueFactory<>("group"));

        table.getColumns().addAll(firstNameCol,secondNameCol,thirdNameCol,col4,col5,col6);
        ResultSet res = DataBase.getGroup();

        /*查看组*/
        ObservableList<String> strList = FXCollections.observableArrayList("all");
        while(res.next())
        {
            strList.add(res.getString("detail"));
        }
        ListView<String> listView = new ListView<>(strList);
        listView.setMaxWidth(60);
        listView.setItems(strList);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                table.getItems().clear();
                Pattern r = Pattern.compile("value: (.*)]");
                String groupName = listView.getSelectionModel().selectedItemProperty().toString();
                Matcher m = r.matcher(groupName);
                m.find();
                ResultSet res = DataBase.getContact(m.group(1));

                while (true)
                {
                    try
                    {
                        if (!res.next())
                            break;
                        table.getItems().add(new Head(res.getString("name"),
                                res.getString("introduction"),
                                res.getString("stock"),
                                res.getString("stock"),
                                res.getString("sex"),
                                res.getString("detail")));
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.out.println(listView.getSelectionModel().selectedItemProperty());
        JFXButton addGroupButton = new JFXButton("增加组");
        JFXButton deleteGroupButton = new JFXButton(" 删除组");
        TextField field = new TextField();
        field.setLayoutX(350);
        field.setMaxWidth(100);
        addGroupButton.setLayoutX(300);
        deleteGroupButton.setLayoutX(500);

        addGroupButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                String temp = field.getText();
                if (temp.equals(""))
                {
                    return;
                }
                DataBase.addGroup(temp);
            }
        });

        deleteGroupButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Pattern r = Pattern.compile("value: (.*)]");
                String groupName = listView.getSelectionModel().selectedItemProperty().toString();
                Matcher m = r.matcher(groupName);
                m.find();
            DataBase.deleteGroup(m.group(1));
            }
        });

        AnchorPane pane = new AnchorPane(addGroupButton,field,deleteGroupButton);
        this.setTop(pane);
        this.setRight(listView);
        this.setCenter(table);
    }
}
