package com.dx.jxty.utils;

import java.io.File;

import static com.dx.jxty.app.Globle.manPath;
import static com.dx.jxty.app.Globle.womanPath;

/**
 * Created by dongxue on 2017/10/12.
 */

public class ImageCompressUtil {
    public static void compressImg(int type) {
        String parentPath = type == 0 ? manPath : womanPath;
//        String parentPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/信天游";
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
