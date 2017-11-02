package com.dx.jxty.base;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dx.jxty.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dongxue on 2017/3/30.
 */

public abstract class CommonTitleActivity extends BaseActivity {
    @BindView(R.id.ll_common_title)
    public LinearLayout llCommonTiTle;
    @BindView(R.id.tv_middle_title)
    public TextView middleTitle;
    @BindView(R.id.iv_go_back)
    public ImageView ivGoBack;
    @BindView(R.id.tv_right_title)
    public TextView tvRightTitle;
    @BindView(R.id.iv_right_title)
    public ImageView ivRightTitle;
    @BindView(R.id.tv_left_title)
    public TextView tvLeftTitle;

    private Boolean isWhiteTitle = false;

    @OnClick({R.id.iv_go_back, R.id.tv_left_title})
    public void onViewTitlleClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                this.finish();
                break;
            case R.id.tv_left_title:
                break;
        }
    }

    protected void showCommonTitle(String title) {
        if (isWhiteTitle) {
//            Eyes.translucentStatusBar(this);
//            llCommonTiTle.setBackgroundColor(Color.WHITE);
//            middleTitle.setTextColor(getResources().getColor(R.color.tvBlack));
//            ivGoBack.setImageResource(R.drawable.right_go);
//            ivGoBack.setRotation(180);
            middleTitle.setText(title);
        } else {
//            Eyes.translucentStatusBar(this,true);
            middleTitle.setText(title);
        }
    }

    protected void setIsWhiteTitle(boolean mIsWhiteTitle) {
        this.isWhiteTitle = mIsWhiteTitle;
    }
}
