package com.dx.jxty.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by dongxue on 2017/10/17.
 * 女装对应新款表
 */

public class WNewStytle extends DataSupport {
    private String goodsStyleCode;
    private String newCode;

    private List<ImagePath> imagePaths;

    public List<ImagePath> getImagePaths() {
        return DataSupport.where("goodsStyleCode = ?", newCode).find(ImagePath.class);
    }

    public String getGoodsStyleCode() {
        return goodsStyleCode;
    }

    public void setGoodsStyleCode(String goodsStyleCode) {
        this.goodsStyleCode = goodsStyleCode;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }
}
