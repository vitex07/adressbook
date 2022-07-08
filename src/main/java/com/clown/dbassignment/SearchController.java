package com.clown.dbassignment;
import Database.DataBase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchController
{
    @FXML
    Label pageLabel;
    @FXML
    TextField textField;
    @FXML AnchorPane pane1;
    @FXML AnchorPane pane2;
    @FXML AnchorPane pane3;
    @FXML AnchorPane pane4;
    @FXML AnchorPane pane5;
    @FXML AnchorPane pane6;
    @FXML AnchorPane pane7;
    @FXML AnchorPane pane8;
    public static ArrayList<ContactDetail> productArrayList = new ArrayList<ContactDetail>();
    AnchorPane[] paneList = new AnchorPane[8];
    @FXML
    JFXButton addButton;
    @FXML
    JFXCheckBox checkBox;
    @FXML
    JFXButton prePageButton;
    @FXML
    JFXButton nextPageButton;
    public static int pages = 0;
    public static int index = 0;
    public static int thisPage = 0;
    public static int size = 0;
    public static ResultSet res = null;
    public static int id;

    @FXML
    public void onAddButtonClick()
    {
        FXMLLoader fxmlLoader;
        Scene scene = null;
        fxmlLoader = new FXMLLoader(Search.class.getResource("addProduct.fxml"));
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
        stage.setTitle("productDetail");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() throws SQLException
    {
        /*回车检索*/
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    try {
                        initialize();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        index = 0;
        size = 0;
        thisPage = 1;
        productArrayList.clear();
        String text = textField.getText();
        if (checkBox.isSelected())
        {
            res = DataBase.searchProduct(text, 1);
        }
        else
        {
            res = DataBase.searchProduct(text, 0);
        }
        paneList[0] = pane1;
        paneList[1] = pane2;
        paneList[2] = pane3;
        paneList[3] = pane4;
        paneList[4] = pane5;
        paneList[5] = pane6;
        paneList[6] = pane7;
        paneList[7] = pane8;
        hideAll();

        while(res.next())
        {
            String imgPath = res.getString("showPicture");
//            System.out.println("D:/Project/java/adressbook/src/main/resources/Image"+imgPath);
            Image img = new Image("D:/Project/java/adressbook/src/main/resources/Image"+imgPath);
            productArrayList.add(new ContactDetail(res.getInt("Id")
            ,res.getString("name"),
                    res.getString("introduction"),
                    res.getString("stock"),
                    img,
                    res.getString("shelfTime"),
                    res.getString("sex"),
                    res.getString("detail")));
        }
        size = productArrayList.size();
        pages = (size/8)+1;
        pageLabel.setText(thisPage+"|"+pages);
        getIndexes();

        for (int i =0;i<8;i++)
        {
            int finalI = i;
            paneList[finalI].setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent)
                {
                    id=finalI;
                    activate();
                }
            });
        }
    }

    /*查询商品*/
    @FXML
    public void onSearchButtonClick() throws SQLException
    {
        initialize();
    }

    /*显示下一页*/
    @FXML
    public void onNextPageButtonClick()
    {
        hideAll();
        thisPage++;
        index+=8;
        getIndexes();
        pageLabel.setText(thisPage+"|"+pages);
    }

    /*显示上一页*/
    @FXML
    public void onPreviousPageButtonClick()
    {
        hideAll();
        thisPage-=1;
        index-=8;
        getIndexes();
        pageLabel.setText(thisPage+"|"+pages);
    }

    /*获取八个页面*/
    public void getIndexes()
    {
        System.out.println(thisPage);
        System.out.println(thisPage==1);
        if (thisPage<=1)
        {
            prePageButton.setVisible(false);
        }
        else
        {
            prePageButton.setVisible(true);
        }
        if (thisPage>=pages)
        {
            nextPageButton.setVisible(false);
        }
        else
        {
            nextPageButton.setVisible(true);
        }
        if (productArrayList.isEmpty())
        {
            return;
        }
        //循环获取八个页面
        int i = index;
        while (true)
        {
            /*从数据库中读取八个商品*/
            String tel = productArrayList.get(i).tel;
            String name = productArrayList.get(i).title;
            Image img = productArrayList.get(i).img;
            paneList[i%8].setVisible(true);
            ImageView imgView = (ImageView)paneList[i%8].getChildren().get(0);
            Label nameLabel = (Label)paneList[i%8].getChildren().get(1);
            Label priceLabel = (Label)paneList[i%8].getChildren().get(2);
            /*给组件设置商品元素*/
            imgView.setImage(img);
            nameLabel.setText(name);
            priceLabel.setText(""+tel);
            i++;
            /*添加完八个页面退出*/
            if (i%8==0 || i>=size)
            {
                break;
            }
        }
    }

    /*隐藏所有节点*/
    public void hideAll()
    {
        for (int i =0;i<8;i++)
        {
            paneList[i].setVisible(false);
        }
    }

    private void activate()
    {
        FXMLLoader fxmlLoader = null;
        Scene scene = null;
        fxmlLoader = new FXMLLoader(Search.class.getResource("productDetail.fxml"));
        try
        {
            Stage stage = new Stage();
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("detail");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("productDetail load failed");
        }
    }
}
