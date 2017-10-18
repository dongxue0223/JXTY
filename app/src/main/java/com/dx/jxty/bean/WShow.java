package com.dx.jxty.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by dongxue on 2017/10/18.
 * 女装显示表
 */

public class WShow extends MShow {
    public WShow() {
    }

    public WShow(String goodsCode, String goodsName, String goodsStyleCode, int goodsPrice, String goodsBrand) {
        super(goodsCode, goodsName, goodsStyleCode, goodsPrice, goodsBrand);
    }

    public List<WNewStytle> getwNewStytleList() {
        return DataSupport.where("newCode = ?", getGoodsStyleCode()).find(WNewStytle.class);
    }

    private List<WNewStytle> wNewStytleList;
}
