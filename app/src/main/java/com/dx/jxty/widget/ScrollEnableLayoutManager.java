package com.dx.jxty.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by dongxue on 2017/4/7.
 * recycleview 静止滑动
 */

public class ScrollEnableLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled = false;

    public ScrollEnableLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
