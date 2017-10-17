package com.dx.jxty.utils;

import android.os.Environment;

import com.dx.jxty.app.Globle;
import com.dx.jxty.bean.ClothImage;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongxue on 2017/10/12.
 */

public class ImageCompressUtil {

    public static void compressImg(String a) {
        List<ClothImage> clothImageList = DataSupport.findAll(ClothImage.class);
        List<File> fileList = new ArrayList<>();
        for (ClothImage c : clothImageList) {
            if (!StringUtil.isEmpty(c.getFrontImgPath())) {
                File f = new File(c.getFrontImgPath());
                MyUtil.i(f.getAbsolutePath());
                MyUtil.i(BitmapUtils.compressImageUpload(f.getAbsolutePath()));
            }

            if (!StringUtil.isEmpty(c.getBackImgPath())) {
                File f = new File(c.getBackImgPath());
                BitmapUtils.compressImageUpload(f.getAbsolutePath());
            }
        }
    }


    public static void compressImg() {
        String parentPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/信天游";
        File fileDirectory = new File(parentPath);
        if (fileDirectory.isDirectory()) {
            for (File f : fileDirectory.listFiles()) {
                String path = f.getPath();
                MyUtil.i("path" + f.getPath());
                if (path.endsWith(".JPG") || path.endsWith(".JPEG") || path.endsWith(".PNG")) {
                    File compressFile = new File(path);
                    BitmapUtils.compressImageUpload(compressFile.getAbsolutePath());
                }
            }
        }

    }
}
