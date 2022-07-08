package com.clown.dbassignment;

import Database.DataBase;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactDetailController
{
    @FXML
    JFXToggleButton toggleButton;
    @FXML
    Label sexLabel;
    @FXML
    TextArea introductionArea;
    @FXML
    ImageView productImg;
    @FXML
    Label titleLabel;
    @FXML
    Label telLabel;
    @FXML
    Label classLabel;
    @FXML
    Label shelfTimeLabel;
    @FXML
    TextField titleTextField;
    @FXML
    TextField telTextField;
    @FXML
    AnchorPane selectPane;
    @FXML
    Label groupLabel;
    @FXML
    TextArea textArea;
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    /*初始化商品详情UI*/
    public void initialize() throws SQLException
    {
        int id = SearchController.index+SearchController.id;
        System.out.println(id);
        /*由被选中的联系人ID获取详细信息*/
        System.out.println(id);
        productImg.setImage(SearchController.productArrayList.get(id).img);
        titleLabel.setText(SearchController.productArrayList.get(id).title);
        telLabel.setText(SearchController.productArrayList.get(id).tel+"");
        classLabel.setText(SearchController.productArrayList.get(id).group);
        shelfTimeLabel.setText(SearchController.productArrayList.get(id).time);
        sexLabel.setText(SearchController.productArrayList.get(id).sex);
        introductionArea.setText(SearchController.productArrayList.get(id).attribute);

        titleTextField.setText(SearchController.productArrayList.get(id).title);
        telTextField.setText(SearchController.productArrayList.get(id).tel+"");


        ResultSet res = DataBase.getGroup();
        /*添加查看组*/
        ObservableList<String> strList = FXCollections.observableArrayList("all");
//        HashMap<String, Integer> map = new HashMap<String, Integer>();
        while(res.next())
        {
            strList.add(res.getString("detail"));
            //哈希表存储字符串到Id的映射
            map.put(res.getString("detail"),res.getInt("classId"));
        }
        ListView<String> listView = new ListView<>(strList);
        listView.setMaxWidth(60);
        listView.setMaxHeight(100);
        listView.setItems(strList);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                Pattern r = Pattern.compile("value: (.*)]");
                String groupName = listView.getSelectionModel().selectedItemProperty().toString();
                Matcher m = r.matcher(groupName);
                m.find();
                System.out.println(groupName);
                groupLabel.setText(m.group(1));
            }
        });
        /*添加到UI*/
        selectPane.getChildren().addAll(listView);
    }

    @FXML
    public void onDeleteButtonClick()
    {
        DataBase.deleteContact(telLabel.getText());
        textArea.setText("delete success");
    }

    @FXML
    public void onUpdateButtonClick()
    {
        String name = titleTextField.getText();
        String tel = telTextField.getText();
        int groupId = map.get(groupLabel.getText());
        String sexNew = toggleButton.isSelected()?"女":"男";
        int Id = SearchController.productArrayList.get(SearchController.id).contactId;
        DataBase.updateContact(name,tel,Id,sexNew,groupId);
        textArea.setText("update success");
    }
}
