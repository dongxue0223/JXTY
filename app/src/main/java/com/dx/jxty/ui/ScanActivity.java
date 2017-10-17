package com.dx.jxty.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceView;

import com.dx.jxty.R;
import com.dx.jxty.utils.MyUtil;
import com.zxing.activity.CaptureActivity;
import com.zxing.view.ViewfinderView;

public class ScanActivity extends CaptureActivity {
    public static final String TAG = "ScanActivity";

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, ScanActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        init(this, (SurfaceView) findViewById(R.id.svCameraScan), (ViewfinderView) findViewById(R.id.vfvCameraScan));
    }
}

