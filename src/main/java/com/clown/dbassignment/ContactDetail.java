package com.clown.dbassignment;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ContactDetail extends Stage
{
    int contactId;
    String title;
    String attribute;
    String tel;
    Image img;
    String time;
    String sex;
    String group;

    public static int id;


    public ContactDetail(int contactId, String title, String attribute, String tel, Image img, String time, String sex, String group) {
        this.contactId = contactId;
        this.title = title;
        this.attribute = attribute;
        this.tel = new String(tel);
        this.img = img;
        this.time = time;
        this.sex = sex;
        this.group = group;
    }
}
