package com.dx.jxty.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by dongxue on 2017/8/28.
 */

public class Person extends DataSupport {
    @Override
    public String toString() {
        return "Person{" +
                "id='" + code + '\'' +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public Person() {
    }

    public Person(String id, String name, String age, String gender) {
        this.code = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;
    private String name;
    private String age;
    private String gender;

    private String fontImg;

    public String getFontImg() {
        return fontImg;
    }

    public void setFontImg(String fontImg) {
        this.fontImg = fontImg;
    }

    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }

    private String backImg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
