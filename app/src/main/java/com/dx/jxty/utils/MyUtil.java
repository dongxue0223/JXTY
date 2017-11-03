package com.dx.jxty.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class MyUtil {

    public static List<String> getTestString() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("aaa" + i);
        }
        return list;
    }

    public static void renameFile(@NonNull String pathFrom, @NonNull String pathTo) {
        File fileFrom = new File(pathFrom);
        File fileTo = new File(pathTo);
        if (fileTo.exists()) {
            fileTo.delete();
        }
        fileFrom.renameTo(fileTo);
    }

    public static void copyFile(@NonNull String pathFrom, @NonNull String pathTo) {
        try {
            PictureFileUtils.copyFile(pathFrom, pathTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goCamera(Activity activity, int code) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofAll())//openCamera//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .setOutputCameraPath("/信天游/女装")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .forResult(code);//结果回调onActivityResult code
    }

    public static void goGallery(Activity activity, int code) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())//openCamera//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .setOutputCameraPath("/信天游/女装")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .isCamera(false)// 是否显示拍照按钮 true or false
                .forResult(code);//结果回调onActivityResult code
    }


    public static int dpToPx(Context context, float dpValue) {//dp转换为px
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int pxToDp(Context context, float pxValue) {//px转换为dp
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //打印
    public static void i(String msg) {
//        Log.i("测试", "--------" + msg);
    }

    //吐司
    private static Toast toast;

    public static void showToast(final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            createToast(msg);
        } else {
            SpUtil.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createToast(msg);
                }
            });
        }
    }

    private static void createToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(SpUtil.getActivity(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    //加载框
    private static ProgressDialog progressDialog;

    public static void showLoading(Context context, String msg) {
        progressDialog = ProgressDialog.show(context, "", msg, true, true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
