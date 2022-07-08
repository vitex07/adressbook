package com.clown.dbassignment;

import Database.DataBase;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController
{
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private JFXCheckBox isShopper;
    @FXML
    protected void onLoginButtonClick() throws SQLException
    {
        String user = userName.getText();
        String pass = password.getText();
        /*不允许输入为空*/
        if (user.equals("") || pass.equals(""))
        {
            System.out.println("input can not be empty");
            return;
        }
        ResultSet res = DataBase.getCustomer(user, pass);;

        /*账号或密码错误*/
        if (!res.next())
        {
            System.out.println("User or password wrong");
        }
        /*登录成功*/
        else
        {
            System.out.println("Login Succeeded");
            DataBase.Id = res.getInt("customerId");
        }
        if (res != null)
        {
            res.close();
        }
        DataBase.closeConnection();
    }

    public void onRegisterButtonClick()
    {
        Register register = new Register();
        register.show();
    }
}