package com.dx.jxty.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dongxue on 2017/10/16.
 * 男装显示表
 */

public class MShow extends DataSupport implements Serializable {
    private String goodsCode;
    private String goodsName;
    private String goodsStyleCode;
    private int goodsPrice;
    private String goodsBrand;
    private String firstImgPath;

    public List<ImagePath> getImagePaths() {
        return DataSupport.where("goodsStyleCode = ?", goodsStyleCode).find(ImagePath.class);
    }

    private List<ImagePath> imagePaths;

    public String getFirstImgPath() {
        return firstImgPath;
    }

    public void setFirstImgPath(String firstImgPath) {
        this.firstImgPath = firstImgPath;
    }

    public static String getSearchCode(String goodsCode) {
        List<ManCloth> manCloths = DataSupport.where("goodsCode like?", "%" + goodsCode + "%").find(ManCloth.class);
        String code = "";
        if (manCloths != null && manCloths.size() > 0) {
            code = manCloths.get(0).getGoodsStyleCode();
        }
        return code;
    }

    public MShow() {
    }

    public MShow(String goodsCode, String goodsName, String goodsStyleCode, int goodsPrice, String goodsBrand) {
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.goodsStyleCode = goodsStyleCode;
        this.goodsPrice = goodsPrice;
        this.goodsBrand = goodsBrand;
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

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsBrand() {
        return goodsBrand;
    }

    public void setGoodsBrand(String goodsBrand) {
        this.goodsBrand = goodsBrand;
    }

    @Override
    public String toString() {
        return "ClothSingleStytle{" +
                "goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsStyleCode='" + goodsStyleCode + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsBrand='" + goodsBrand + '\'' +
                '}';
    }
}
