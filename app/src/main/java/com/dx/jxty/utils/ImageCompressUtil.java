package com.dx.jxty.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dx.jxty.app.Globle;

import net.bither.util.NativeUtil;

import java.io.File;

import static com.dx.jxty.app.Globle.manPath;
import static com.dx.jxty.app.Globle.womanPath;

/**
 * Created by dongxue on 2017/10/12.
 */

public class ImageCompressUtil {
    public static void compressImg(int type) {
        String parentPath = type == 0 ? manPath : womanPath;
        testFile();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;// 同时设置才会有效
        options.inInputShareable = true;//。当系统内存不够时候图片自动被回收
        File fileDirectory = new File(parentPath);
        if (fileDirectory.isDirectory()) {
            for (File f : fileDirectory.listFiles()) {
                String path = f.getPath();
                MyUtil.i("path" + path);
                if (path.endsWith(".JPG") || path.endsWith(".JPEG") || path.endsWith(".PNG")) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path, options);
                    NativeUtil.compressBitmap(bitmap, saveFilePath(path));
                }
            }
        }
    }

    private static void testFile() {
        File dir = new File(Globle.compressPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public static String saveFilePath(String path) {
        String filename = path.substring(path.lastIndexOf("/") + 1);
        String filePath = Globle.compressPath + "/" + filename;
        return filePath;
    }
}
