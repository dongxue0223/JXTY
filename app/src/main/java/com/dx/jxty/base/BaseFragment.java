package com.dx.jxty.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dx.jxty.utils.SpUtil;

import butterknife.ButterKnife;

/**
 * Created by dongxue on 2017/3/28.
 */

public abstract class BaseFragment extends Fragment {

    protected View view;
    protected Context context;
    protected Bundle savedInstanceState;
    protected LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
//        context = SpUtil.getActivity();
        context = getActivity();

        layoutManager = new LinearLayoutManager(context);

        initView();
        initData();
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onResume() {
        super.onResume();
        SpUtil.setActivity(getActivity());
    }
}
