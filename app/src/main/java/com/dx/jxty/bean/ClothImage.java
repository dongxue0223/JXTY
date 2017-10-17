package com.dx.jxty.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by dongxue on 2017/10/10.
 */

public class ClothImage extends DataSupport implements Serializable {
    private String goodsStyleCode;
    private String goodsName;
    private String goodsColor;
    private String frontImgPath;
    private String backImgPath;

    public String getFrontImgPath() {
        return frontImgPath;
    }

    public void setFrontImgPath(String frontImgPath) {
        this.frontImgPath = frontImgPath;
    }

    public String getBackImgPath() {
        return backImgPath;
    }

    public void setBackImgPath(String backImgPath) {
        this.backImgPath = backImgPath;
    }

    public String getGoodsStyleCode() {
        return goodsStyleCode;
    }

    public void setGoodsStyleCode(String goodsStyleCode) {
        this.goodsStyleCode = goodsStyleCode;
    }

    public String getGoodsColor() {
        return goodsColor;
    }

    public void setGoodsColor(String goodsColor) {
        this.goodsColor = goodsColor;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Override
    public String toString() {
        return "ClothImage{" +
                "goodsStyleCode='" + goodsStyleCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsColor='" + goodsColor + '\'' +
                ", frontImgPath='" + frontImgPath + '\'' +
                ", backImgPath='" + backImgPath + '\'' +
                '}';
    }
}
