package com.dx.jxty.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by dongxue on 2017/8/30.
 */

public class ManCloth extends DataSupport implements Serializable {
    @Override
    public String toString() {
        return "ManCloth{" +
                "batchNumber='" + batchNumber + '\'' +
                ", storeHouse='" + storeHouse + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsStyleCode='" + goodsStyleCode + '\'' +
                ", goodsColor='" + goodsColor + '\'' +
                ", goodsColor2='" + goodsColor2 + '\'' +
                ", goodsSize='" + goodsSize + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsCount=" + goodsCount +
                ", goodsUnit='" + goodsUnit + '\'' +
                ", goodsJijia='" + goodsJijia + '\'' +
                ", goodsXiaoji='" + goodsXiaoji + '\'' +
                ", goodsBrand='" + goodsBrand + '\'' +
                ", goodsSeason='" + goodsSeason + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", goodsDiscount='" + goodsDiscount + '\'' +
                ", goodsPifa='" + goodsPifa + '\'' +
                ", goodsComponent='" + goodsComponent + '\'' +
                ", goodsRemark='" + goodsRemark + '\'' +
                ", goodsTag='" + goodsTag + '\'' +
                ", firstImgPath='" + firstImgPath + '\'' +
                '}';
    }

    //    批次号 到仓库 商品编码(条码)	名称 商品款号 颜色 颜色 尺码 铭牌单价 数量
    //    单位  进价  小计金额  品牌 季节 类别 零售折扣 批发 成分 备注 标记
    private String batchNumber;
    private String storeHouse;
    private String goodsCode;
    private String goodsName;
    private String goodsStyleCode;
    private String goodsColor;
    private String goodsColor2;
    private String goodsSize;
    private int goodsPrice;
    private int goodsCount;

    private String goodsUnit;

    public String getGoodsJijia() {
        return goodsJijia;
    }

    public void setGoodsJijia(String goodsJijia) {
        this.goodsJijia = goodsJijia;
    }

    public String getGoodsXiaoji() {
        return goodsXiaoji;
    }

    public void setGoodsXiaoji(String goodsXiaoji) {
        this.goodsXiaoji = goodsXiaoji;
    }

    private String goodsJijia;
    private String goodsXiaoji;
    private String goodsBrand;
    private String goodsSeason;
    private String goodsType;
    private String goodsDiscount;
    private String goodsPifa;
    private String goodsComponent;
    private String goodsRemark;
    private String goodsTag;

    private String firstImgPath;

    public ManCloth() {
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getStoreHouse() {
        return storeHouse;
    }

    public void setStoreHouse(String storeHouse) {
        this.storeHouse = storeHouse;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public String getGoodsColor2() {
        return goodsColor2;
    }

    public void setGoodsColor2(String goodsColor2) {
        this.goodsColor2 = goodsColor2;
    }

    public String getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsBrand() {
        return goodsBrand;
    }

    public void setGoodsBrand(String goodsBrand) {
        this.goodsBrand = goodsBrand;
    }

    public String getGoodsSeason() {
        return goodsSeason;
    }

    public void setGoodsSeason(String goodsSeason) {
        this.goodsSeason = goodsSeason;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsDiscount() {
        return goodsDiscount;
    }

    public void setGoodsDiscount(String goodsDiscount) {
        this.goodsDiscount = goodsDiscount;
    }

    public String getGoodsPifa() {
        return goodsPifa;
    }

    public void setGoodsPifa(String goodsPifa) {
        this.goodsPifa = goodsPifa;
    }

    public String getGoodsComponent() {
        return goodsComponent;
    }

    public void setGoodsComponent(String goodsComponent) {
        this.goodsComponent = goodsComponent;
    }

    public String getGoodsRemark() {
        return goodsRemark;
    }

    public void setGoodsRemark(String goodsRemark) {
        this.goodsRemark = goodsRemark;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getFirstImgPath() {
        return firstImgPath;
    }

    public void setFirstImgPath(String firstImgPath) {
        this.firstImgPath = firstImgPath;
    }
}
