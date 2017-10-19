package com.dx.jxty.db;

import android.content.Context;
import android.util.Log;

import com.dx.jxty.bean.ImagePath;
import com.dx.jxty.bean.MShow;
import com.dx.jxty.bean.ManCloth;
import com.dx.jxty.bean.WNewStytle;
import com.dx.jxty.bean.WShow;
import com.dx.jxty.bean.WomanCloth;
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

    //读男装
    public void readMExtraExcelToDB(Context context, String filePath) {
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

    //读取全部数据
    public void readData(Sheet sheet) {
        int Rows = sheet.getRows();
        DataSupport.deleteAll(ManCloth.class);

        for (int i = 1; i < Rows; ++i) {
            ManCloth info = new ManCloth();
            String batchNumber = (sheet.getCell(0, i)).getContents();
            String storeHouse = (sheet.getCell(1, i)).getContents();
            String goodsCode = (sheet.getCell(2, i)).getContents();
            String goodsName = (sheet.getCell(3, i)).getContents();
            String goodsStyleCode = (sheet.getCell(4, i)).getContents();
            String goodsColor = (sheet.getCell(5, i)).getContents();

            String goodsColor2 = (sheet.getCell(6, i)).getContents();
            String goodsSize = (sheet.getCell(7, i)).getContents();
            String goodsPrice = (sheet.getCell(8, i)).getContents();
            String goodsCount = (sheet.getCell(9, i)).getContents();

            String goodsUnit = (sheet.getCell(10, i)).getContents();
            String goodsJinjia = (sheet.getCell(11, i)).getContents();
            String goodsXiaoji = (sheet.getCell(12, i)).getContents();
            String goodsBrand = (sheet.getCell(13, i)).getContents();
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

    //筛选同款,颜色和路径单建表
    public void readData1(Sheet sheet) {
        long startTime = System.currentTimeMillis();//记录开始时间
        int Rows = sheet.getRows();
        DataSupport.deleteAll(MShow.class);
        DataSupport.deleteAll(ImagePath.class, "type =?", "0");
        List<MShow> totalList = new ArrayList();
        boolean flag = false;
        for (int i = 1; i < Rows; ++i) {
            MShow info = new MShow();
            String goodsCode = (sheet.getCell(2, i)).getContents();
            String goodsName = (sheet.getCell(3, i)).getContents();
            String goodsStyleCode = (sheet.getCell(4, i)).getContents();
            String goodsColor = (sheet.getCell(5, i)).getContents();
            String goodsPrice = (sheet.getCell(8, i)).getContents();
            String goodsBrand = (sheet.getCell(13, i)).getContents();
            for (int j = 0; j < totalList.size(); j++) {
                MShow manCloth = totalList.get(j);
                if (goodsStyleCode.equals(manCloth.getGoodsStyleCode())) {
                    flag = true;
                    List<ImagePath> clothImages = DataSupport.where("goodsStyleCode =? and goodsColor=?", goodsStyleCode, goodsColor).find(ImagePath.class);
                    if (clothImages.size() == 0) {
                        ImagePath clothImage1 = new ImagePath();
                        clothImage1.setType("0");
                        clothImage1.setGoodsStyleCode(goodsStyleCode);
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

            ImagePath clothImage1 = new ImagePath();
            clothImage1.setType("0");
            clothImage1.setGoodsStyleCode(goodsStyleCode);
            clothImage1.setGoodsColor(goodsColor);
            clothImage1.save();
            info = new MShow(goodsCode, goodsName, goodsStyleCode, Integer.parseInt(goodsPrice), goodsBrand);
            totalList.add(info);
            info.save();
        }

        long endTime = System.currentTimeMillis();//记录结束时间
        float excTime = (float) (endTime - startTime) / 1000;
        MyUtil.i("查数据库执行时间：" + excTime + "s");//0.2-0.3
    }


    /**
     * 读取测试excel数据到数据库里
     * 通过List筛选款号相同的数据
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

    public void readWLocalExcelToDB(Context context) {
        try {
            InputStream is = context.getAssets().open("manCloth.xls");
            Workbook book = Workbook.getWorkbook(is);
            readWData(book.getSheet(0));
            readWData1(book.getSheet(0));
            book.close();
            SpUtil.setBoolean(IS_READED_EXTRA_SOUND_DATA, false);
        } catch (Exception e) {
            SpUtil.setBoolean(IS_READED_EXTRA_SOUND_DATA, true);
            Log.e(TAG, EXCEPTION, e);
        }
    }


    //读女装
    public void readWExtraExcelToDB(Context context, String filePath) {
        try {
            File file = new File(filePath);
            InputStream is = new FileInputStream(file);
            Workbook book = Workbook.getWorkbook(is);
            // 获得第一个工作表对象
            readWData(book.getSheet(0));
            readWData1(book.getSheet(0));
            book.close();
            SpUtil.setBoolean(IS_READED_EXTRA_SOUND_DATA, false);
        } catch (Exception e) {
            SpUtil.setBoolean(IS_READED_EXTRA_SOUND_DATA, true);
            Log.e(TAG, EXCEPTION, e);
        }
    }

    //读取全部数据
    public void readWData(Sheet sheet) {
        int Rows = sheet.getRows();
        DataSupport.deleteAll(WomanCloth.class);

        for (int i = 1; i < Rows; ++i) {
            WomanCloth info = new WomanCloth();
            String batchNumber = (sheet.getCell(0, i)).getContents();
            String storeHouse = (sheet.getCell(1, i)).getContents();
            String goodsCode = (sheet.getCell(2, i)).getContents();
            String goodsName = (sheet.getCell(3, i)).getContents();
            String goodsStyleCode = (sheet.getCell(4, i)).getContents();
            String goodsColor = (sheet.getCell(5, i)).getContents();
            String goodsBrand = (sheet.getCell(13, i)).getContents();

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

    //筛选同款,颜色和路径单建表
    public void readWData1(Sheet sheet) {
        long startTime = System.currentTimeMillis();//记录开始时间
        DataSupport.deleteAll(WShow.class);
        int Rows = sheet.getRows();
        List<WShow> totalList = new ArrayList();
        boolean flag = false;
        for (int i = 1; i < Rows; ++i) {
            WShow info = new WShow();
            String goodsCode = (sheet.getCell(2, i)).getContents();
            String goodsName = (sheet.getCell(3, i)).getContents();
            String goodsStyleCode = (sheet.getCell(4, i)).getContents();

            String goodsPrice = (sheet.getCell(8, i)).getContents();
            String goodsBrand = (sheet.getCell(13, i)).getContents();
            for (int j = 0; j < totalList.size(); j++) {
                WShow manCloth = totalList.get(j);
                if (goodsStyleCode.equals(manCloth.getGoodsStyleCode())) {
                    flag = true;
//                    List<ImagePath> clothImages = DataSupport.where("goodsStyleCode =? and goodsColor=?", goodsStyleCode, goodsColor).find(ImagePath.class);
//                    if (clothImages.size() == 0) {
//                        ImagePath clothImage1 = new ImagePath();
//                        clothImage1.setGoodsStyleCode(goodsStyleCode);
//                        clothImage1.setGoodsColor(goodsColor);
//                        clothImage1.save();
//                    }
                    break;
                } else {
                    flag = false;
                }
            }
            if (flag) {
                continue;
            }

//            ImagePath clothImage1 = new ImagePath();
//            clothImage1.setGoodsStyleCode(goodsStyleCode);
//            clothImage1.setGoodsColor(goodsColor);
//            clothImage1.save();
            info = new WShow(goodsCode, goodsName, goodsStyleCode, Integer.parseInt(goodsPrice), goodsBrand);
            totalList.add(info);
            info.save();
        }

        long endTime = System.currentTimeMillis();//记录结束时间
        float excTime = (float) (endTime - startTime) / 1000;
        MyUtil.i("查数据库执行时间：" + excTime + "s");//0.2-0.3
    }

}
