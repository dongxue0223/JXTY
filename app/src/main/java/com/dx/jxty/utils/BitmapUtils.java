package com.dx.jxty.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dongxue on 2017/3/29.
 */

public class BitmapUtils {

//    /**
//     * 多线程压缩图片的质量
//     *
//     * @param bitmap  内存中的图片
//     * @param imgPath 图片的保存路径
//     */
//    private static void compressImageByQuality(final Bitmap bitmap, final String imgPath) {
//        if (bitmap == null) {
//            return;
//        }
//        new Thread(new Runnable() {//开启多线程进行压缩处理
//            @Override
//            public void run() {
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                int options = 100;
//                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
//                while (baos.toByteArray().length > 100 * 1024) {//循环判断如果压缩后图片是否大于指定大小,大于继续压缩
//                    baos.reset();//重置baos即让下一次的写入覆盖之前的内容
//                    options -= 5;//图片质量每次减少5
//                    if (options <= 5) options = 5;//如果图片质量小于5，为保证压缩后的图片质量，图片最底压缩质量为5
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//将压缩后的图片保存到baos中
//                    if (options == 5) break;//如果图片的质量已降到最低则，不再进行压缩
//                }
//                saveMyBitmap(imgPath.substring(imgPath.lastIndexOf("/") + 1), bitmap);
//            }
//        }).start();
//    }
//
//    /**
//     * 按比例缩小图片的像素以达到压缩的目的
//     *
//     * @param imgPath
//     * @return
//     */
//    public static void compressImageByPixel(String imgPath){
//        if (imgPath == null) {
//            return;
//        }
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        newOpts.inJustDecodeBounds = true;//只读边,不读内容
//        BitmapFactory.decodeFile(imgPath, newOpts);
//        newOpts.inJustDecodeBounds = false;
//        int width = newOpts.outWidth;
//        int height = newOpts.outHeight;
//        float maxSize = 1200;
//        int be = 1;
//        if (width >= height && width > maxSize) {//缩放比,用高或者宽其中较大的一个数据进行计算
//            be = (int) (newOpts.outWidth / maxSize);
//            be++;
//        } else if (width < height && height > maxSize) {
//            be = (int) (newOpts.outHeight / maxSize);
//            be++;
//        }
//        newOpts.inSampleSize = be;//设置采样率
//        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
//        newOpts.inPurgeable = true;// 同时设置才会有效
//        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
//        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
//        compressImageByQuality(bitmap, imgPath);//压缩好比例大小后再进行质量压缩
//    }
//
////    ------------------

    /**
     * 压缩上传路径
     *
     * @param path
     * @return
     */
    public static String compressImageUpload(String path) {
        String filename = path.substring(path.lastIndexOf("/") + 1);
        Bitmap image = getImage(path);
        return saveMyBitmap(filename, image);
    }


    private static Bitmap imageCompressL(Bitmap bitmap) {
        double targetwidth = Math.sqrt(150.00 * 1000);
        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            double x = Math.max(targetwidth / bitmap.getWidth(), targetwidth / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale((float) x, (float) x);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    private static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 图片按比例大小压缩方法（根据路径获取图片并压缩）
     *
     * @param srcPath
     * @return
     */
    private static Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
        return imageCompressL(bitmap);
    }

    /**
     * 将压缩的bitmap保存到SDCard卡临时文件夹，用于上传
     *
     * @param filename
     * @param bit
     * @return
     */
    private static String saveMyBitmap(String filename, Bitmap bit) {
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/信天游/压缩图0/";
        String filePath = baseDir + filename;
        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File f = new File(filePath);
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            bit.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return filePath;
    }

    /**
     * 清除缓存文件
     */
    public static void deleteCacheFile() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/信天游压缩图/");
        RecursionDeleteFile(file);
    }

    /**
     * 递归删除
     */
    private static void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }
}
