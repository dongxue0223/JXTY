package com.dx.jxty.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by dongxue on 2017/10/10.
 * 图片路径表
 */

public class ImagePath extends DataSupport implements Serializable {
    private String type;//0男装 1女装
    private String goodsStyleCode;
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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ImagePath{" +
                "type='" + type + '\'' +
                ", goodsStyleCode='" + goodsStyleCode + '\'' +
                ", goodsColor='" + goodsColor + '\'' +
                ", frontImgPath='" + frontImgPath + '\'' +
                ", backImgPath='" + backImgPath + '\'' +
                '}';
    }
}
