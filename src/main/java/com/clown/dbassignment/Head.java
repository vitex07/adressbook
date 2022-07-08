package com.clown.dbassignment;

public class Head
{
    String name;
    String introduction;
    String tel;
    String time;
    String sex;
    String group;

    public Head(String name, String introduction, String tel, String time, String sex, String group) {
        this.name = name;
        this.introduction = introduction;
        this.tel = tel;
        this.time = time;
        this.sex = sex;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getTel() {
        return tel;
    }

    public String getTime() {
        return time;
    }

    public String getSex() {
        return sex;
    }

    public String getGroup() {
        return group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
