package com.dx.jxty.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {
    public static final String FILE_NAME = "config";

    private static Activity activity;
    public static SharedPreferences sp;

    public static void setActivity(Activity act) {
        activity = act;
        sp = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public static void removeString(String key) {
        sp.edit().remove(key).commit();
    }

    public static String getString(String key) {
        return sp.getString(key, "");
    }

    public static void setBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }
}
