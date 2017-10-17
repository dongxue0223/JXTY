package com.dx.jxty.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static int dpToPx(Context context, float dpValue) {//dp转换为px
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int pxToDp(Context context, float pxValue) {//px转换为dp
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }


    //打印
    public static void i(String msg) {
        Log.i("测试", "--------" + msg);
    }

    //解析data
    public static String parseData(String response, String key) {
        String data = "";
        try {
            JSONObject json = new JSONObject(response.toString());
            data = json.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
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
//        LinearLayout linearLayout = (LinearLayout) toast.getView();
//        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
//        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
//        messageTextView.setTextSize(15);
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    //获取屏幕宽度
    public static int getWindowWidth() {
        WindowManager manager = (WindowManager) SpUtil.getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        return width;
    }

    //获取屏幕高度
    public static int getWindowHeight() {
        WindowManager manager = (WindowManager) SpUtil.getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int height = display.getHeight();
        return height;
    }

    //加载框
    private static ProgressDialog progressDialog;

    public static void showLoading(String msg) {
        progressDialog = ProgressDialog.show(SpUtil.getActivity(), "", msg, true, true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void dismissLoading() {
        progressDialog.dismiss();
    }

    public static String getNowData() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return year + "/" + (month + 1) + "/" + day;
    }

    public static int getNowYear() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        return year;
    }

    public static int getNowMonth() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        return month + 1;
    }

    public static int getNowDay() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static String getNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getYYMMDD() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getlongTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static int getPercent(float per) {
        NumberFormat PercentFormat = NumberFormat.getPercentInstance();
        String perStr = PercentFormat.format(per);
        return Integer.valueOf(perStr.substring(0, perStr.length() - 1));
    }

    public static String StringToDate(String date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(date));
    }

    public static String StringToTime(String date) {
        return new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(new Date(date));
    }

    public static String StringTopercent(String Obj) {
        DecimalFormat format = new DecimalFormat("0%");
        float pro = Float.valueOf(Obj);
        String str = format.format(pro);
        return str;
    }

    public static String DateFormatDate(String Obj, String fromType, String toType) {
        SimpleDateFormat df = new SimpleDateFormat(fromType);
        Date date = null;
        try {
            date = df.parse(Obj);
        } catch (ParseException e) {
            date = new Date();
            e.printStackTrace();
        }
        return new SimpleDateFormat(toType).format(date);
    }


    public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                MyUtil.i("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                MyUtil.i("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
