package com.dx.jxty.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.dx.jxty.utils.SpUtil;

import org.litepal.LitePal;

import butterknife.ButterKnife;

/**
 * Created by dongxue on 2017/10/9.
 */

public abstract class BaseActivity extends FragmentActivity {
    protected Context context;
    protected View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpUtil.setActivity(this);
        context = this;
        view = LayoutInflater.from(this).inflate(getLayoutId(), null);
        setContentView(view);
        ButterKnife.bind(this);

        LitePal.getDatabase();
        initView();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

}
