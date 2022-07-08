package com.clown.dbassignment;

import Database.DataBase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterController
{
    @FXML
    Label account;
    @FXML
    Label password;
    @FXML
    Label mail;
    @FXML
    Label nickName;
    @FXML
    JFXButton submitButton;
    @FXML
    TextField accountTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    TextField mailTextField;
    @FXML
    TextField nickNameTextField;

    public void onSubmitButtonClick()
    {
        /*获取输入的账号和密码*/
        String acc = accountTextField.getText();
        String pass = passwordTextField.getText();
        String ema = mailTextField.getText();
        String nick = nickNameTextField.getText();
        /*不允许输入为空*/
        if (acc.equals("") || pass.equals("") || ema.equals("") || nick.equals(""))
        {
            System.out.println("input can not be empty");
            return;
        }
        /*在数据库中添加对应账号和密码信息*/
        DataBase.addCustomer(acc,pass,ema,nick);
        System.out.println("suc");
        DataBase.closeConnection();
    }
}