package com.dx.jxty.db;

import android.content.Context;
import android.util.Log;

import com.dx.jxty.bean.ClothImage;
import com.dx.jxty.bean.ClothSingleStytle;
import com.dx.jxty.bean.ManCloth;
import com.dx.jxty.utils.MyUtil;
import com.dx.jxty.utils.SpUtil;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by dongxue on 2017/10/16.
 */

public enum ClothDBManager {
    INSTANCE;
    private static final String TAG = "ClothDBManager";
    private static final String EXCEPTION = "exception";
    private static final String IS_READED_EXTRA_SOUND_DATA = "read";

    public void readData(Sheet sheet) {
        int Rows = sheet.getRows();
        DataSupport.deleteAll(ManCloth.class);
        //读取全部数据
        for (int i = 1; i < Rows; ++i) {
            ManCloth info = new ManCloth();
            String goodsName = (sheet.getCell(3, i)).getContents();
            String goodsStyleCode = (sheet.getCell(4, i)).getContents();
            String goodsColor = (sheet.getCell(5, i)).getContents();
            String goodsBrand = (sheet.getCell(13, i)).getContents();

            String batchNumber = (sheet.getCell(0, i)).getContents();
            String storeHouse = (sheet.getCell(1, i)).getContents();
            String goodsCode = (sheet.getCell(2, i)).getContents();

            String goodsColor2 = (sheet.getCell(6, i)).getContents();
            String goodsSize = (sheet.getCell(7, i)).getContents();
            String goodsPrice = (sheet.getCell(8, i)).getContents();
            String goodsCount = (sheet.getCell(9, i)).getContents();

            String goodsUnit = (sheet.getCell(10, i)).getContents();
            String goodsJinjia = (sheet.getCell(11, i)).getContents();
            String goodsXiaoji = (sheet.getCell(12, i)).getContents();
            String goodsSeason = (sheet.getCell(14, i)).getContents();
            String goodsType = (sheet.getCell(15, i)).getContents();
            String goodsDiscount = (sheet.getCell(16, i)).getContents();
            String goodsPifa = (sheet.getCell(17, i)).getContents();
            String goodsComponent = (sheet.getCell(18, i)).getContents();
            String goodsRemark = (sheet.getCell(19, i)).getContents();
            String goodsTag = (sheet.getCell(20, i)).getContents();

            info.setBatchNumber(batchNumber);
            info.setGoodsBrand(storeHouse);
            info.setGoodsCode(goodsCode);
            info.setGoodsName(goodsName);
            info.setGoodsStyleCode(goodsStyleCode);
            info.setGoodsColor(goodsColor);
            info.setGoodsColor2(goodsColor2);
            info.setGoodsSize(goodsSize);
            info.setGoodsPrice(Integer.parseInt(goodsPrice));
            info.setGoodsCount(Integer.parseInt(goodsCount));

            info.setGoodsUnit(goodsUnit);
            info.setGoodsJijia(goodsJinjia);
            info.setGoodsXiaoji(goodsXiaoji);
            info.setGoodsBrand(goodsBrand);
            info.setGoodsSeason(goodsSeason);
            info.setGoodsType(goodsType);
            info.setGoodsDiscount(goodsDiscount);
            info.setGoodsPifa(goodsPifa);
            info.setGoodsComponent(goodsComponent);
            info.setGoodsRemark(goodsRemark);
            info.setGoodsTag(goodsTag);
            info.save();
        }
    }

    //颜色和路径单建表
    public void readData1(Sheet sheet) {
        long startTime = System.currentTimeMillis();//记录开始时间
        int Rows = sheet.getRows();
        DataSupport.deleteAll(ClothSingleStytle.class);
        DataSupport.deleteAll(ClothImage.class);
        List<ClothSingleStytle> totalList = new ArrayList();
        boolean flag = false;
        for (int i = 1; i < Rows; ++i) {
            ClothSingleStytle info = new ClothSingleStytle();
            String goodsCode = (sheet.getCell(2, i)).getContents();
            String goodsName = (sheet.getCell(3, i)).getContents();
            String goodsStyleCode = (sheet.getCell(4, i)).getContents();
            String goodsColor = (sheet.getCell(5, i)).getContents();
            String goodsPrice = (sheet.getCell(8, i)).getContents();
            String goodsBrand = (sheet.getCell(13, i)).getContents();
            for (int j = 0; j < totalList.size(); j++) {
                ClothSingleStytle manCloth = totalList.get(j);
                if (goodsStyleCode.equals(manCloth.getGoodsStyleCode())) {
                    flag = true;
                    List<ClothImage> clothImages = DataSupport.where("goodsStyleCode =? and goodsColor=?", goodsStyleCode, goodsColor).find(ClothImage.class);
                    if (clothImages.size() == 0) {
                        ClothImage clothImage1 = new ClothImage();
                        clothImage1.setGoodsStyleCode(goodsStyleCode);
                        clothImage1.setGoodsName(goodsName);
                        clothImage1.setGoodsColor(goodsColor);
                        clothImage1.save();
                    }
                    break;
                } else {
                    flag = false;
                }
            }
            if (flag) {
                continue;
            }

            ClothImage clothImage1 = new ClothImage();
            clothImage1.setGoodsStyleCode(goodsStyleCode);
            clothImage1.setGoodsColor(goodsColor);
            clothImage1.save();
            info = new ClothSingleStytle(goodsCode, goodsName, goodsStyleCode, Integer.parseInt(goodsPrice), goodsBrand);
            totalList.add(info);
            info.save();
        }

        long endTime = System.currentTimeMillis();//记录结束时间
        float excTime = (float) (endTime - startTime) / 1000;
        MyUtil.i("查数据库执行时间：" + excTime + "s");//0.2-0.3
    }


    /**
     * 通过List筛选款号相同的数据
     * 读取测试excel数据到数据库里
     */
    public void readLocalExcelToDB(Context context) {
        try {
            InputStream is = context.getAssets().open("works.xls");
            Workbook book = Workbook.getWorkbook(is);
            // 获得第一个工作表对象
            readData(book.getSheet(0));
            readData1(book.getSheet(0));
            book.close();
            SpUtil.setBoolean(IS_READED_EXTRA_SOUND_DATA, false);
        } catch (Exception e) {
            SpUtil.setBoolean(IS_READED_EXTRA_SOUND_DATA, true);
            Log.e(TAG, EXCEPTION, e);
        }
    }

    /**
     * 通过List筛选款号相同的数据
     * 读取本地excel数据到数据库里
     */

    public void readExtraExcelToDB(Context context, String filePath) {
        try {
            File file = new File(filePath);
            InputStream is = new FileInputStream(file);
            Workbook book = Workbook.getWorkbook(is);
            // 获得第一个工作表对象
            readData(book.getSheet(0));
            readData1(book.getSheet(0));
            book.close();
            SpUtil.setBoolean(IS_READED_EXTRA_SOUND_DATA, false);
        } catch (Exception e) {
            SpUtil.setBoolean(IS_READED_EXTRA_SOUND_DATA, true);
            Log.e(TAG, EXCEPTION, e);
        }
    }
}
