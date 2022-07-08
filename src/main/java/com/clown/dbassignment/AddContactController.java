package com.clown.dbassignment;

import Database.DataBase;
import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddContactController
{
    @FXML
    TextField productNameField;
    @FXML
    TextField introductionField;
    @FXML
    TextField telField;
    @FXML
    AnchorPane subPane;
    @FXML
    Label groupLabel;
    @FXML
    JFXToggleButton sexToggleButton;
    @FXML
    ImageView imageView;
    String name;
    String introduction;
    String tel;
    String imgPath;
    String sex;
    int classId;
    HashMap<String, Integer> map = new HashMap<String, Integer>();

    public void initialize() throws SQLException
    {
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
                /*被选中时获得组的id供添加联系人*/
                classId = map.get(m.group(1));
            }
        });
        subPane.getChildren().add(listView);
    }

    @FXML
    public void onSubmitButtonClick()
    {
        name = productNameField.getText();
        introduction = introductionField.getText();
        tel = telField.getText();
        if (sexToggleButton.isSelected()) {sex = "女";}
        else{sex = "男";}
        DataBase.insertProduct(name,introduction,tel,imgPath,sex,classId);
        FXMLLoader fxmlLoader;
        Scene scene = null;
        fxmlLoader = new FXMLLoader(Search.class.getResource("insertAlert.fxml"));
        try
        {
            scene = new Scene(fxmlLoader.load());

        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.out.println("productDetail load failed");
        }

        Stage stage = new Stage();
        stage.setTitle("alert");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onUploadButtonClick() throws IOException
    {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("图片类型","*.jpg","*.png","*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File source = fileChooser.showOpenDialog(stage);
        Matcher m = Pattern.compile(".+(.JPEG|.j        peg|.JPG|.jpg|.png|.PNG)$").matcher(source.toString());
        m.find(); String target = getRandomString() + m.group(1);
        String path = "D:\\Project\\java\\adressbook\\src\\main\\resources\\Image\\Commercialcity\\commercialcity\\"+target;
        System.out.println(path);

        /*写入图片到指定路径*/
        File dest = new File(path);
        InputStream input = new FileInputStream(source);
        OutputStream output = new FileOutputStream(dest);
        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buf)) > 0)
        {
            output.write(buf, 0, bytesRead);
        }
        Image image = new Image(path);
        imageView.setImage(image);
        imgPath = "/Commercialcity/commercialcity/"+target;
        input.close();
        output.close();
    }
    /* 生成随机字符串*/
    private String getRandomString()
    {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        int length = 7;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++)
        {
            int number = random.nextInt(62);
            stringBuffer.append(str.charAt(number));
        }
        return stringBuffer.toString();
    }
}
