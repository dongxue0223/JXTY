package com.dx.jxty.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dongxue on 2017/8/28.
 */

public class ExtraVoiceDBHelper extends SQLiteOpenHelper {
    public static final String TABLE_EXTRA_VOICE_INFO = "Person";
    public static String DATA_BASE_NAME = "ExtraVoiceCfg.db";
    private static final int VERSION = 1;
    private static ExtraVoiceDBHelper instance;

    public static ExtraVoiceDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ExtraVoiceDBHelper(context);
        }
        return instance;
    }

    public ExtraVoiceDBHelper(Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableUser(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateTableUser(db, oldVersion, newVersion);
    }

    /**
     * 创建用户表
     */
    public void createTableUser(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EXTRA_VOICE_INFO + " (_id INTEGER PRIMARY KEY AUTOINCREMENT"
                + ",code TEXT NOT NULL, name TEXT ,age TEXT, gender TEXT)");
    }

    /**
     * 更新用户表
     */
    public void updateTableUser(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXTRA_VOICE_INFO);
            createTableUser(db);
        }
    }
}
