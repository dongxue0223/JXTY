package com.dx.jxty.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dx.jxty.R;
import com.dx.jxty.base.BaseActivity;
import com.dx.jxty.bean.ManCloth;
import com.dx.jxty.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dongxue on 2017/10/9.
 */

public class ImageActivity extends BaseActivity {
    @BindView(R.id.iv_goods)
    ImageView ivGoods;
    private RequestOptions options;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView() {
        options = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
    }

    @Override
    protected void initData() {
        String path = getIntent().getStringExtra("imagePath");
        if (!StringUtil.isEmpty(path)) {
            Glide.with(context)
                    .load(path)
                    .apply(options)
                    .into(ivGoods);
        }
    }

    public static void actionStart(Context context, Object obj) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("imagePath", (String) obj);
        context.startActivity(intent);
    }
}
