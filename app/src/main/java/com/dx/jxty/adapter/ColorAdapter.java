package com.dx.jxty.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dx.jxty.R;
import com.dx.jxty.bean.ClothImage;
import com.dx.jxty.utils.StringUtil;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;

import java.util.List;

/**
 * Created by dongxue on 2017/10/11.
 */

public class ColorAdapter extends SuperBaseAdapter<ClothImage> {
    private RequestOptions options = new RequestOptions()
            .centerCrop()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE);
    private Context context;

    public ColorAdapter(Context context, List<ClothImage> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, ClothImage item, int position) {
        ImageView ivFront = holder.getView(R.id.iv_item_front);
        ImageView ivBack = holder.getView(R.id.iv_item_back);
        holder.setText(R.id.tv_item_color, item.getGoodsColor())
                .setVisible(R.id.tv_item_edit_front, !StringUtil.isEmpty(item.getFrontImgPath()))
                .setVisible(R.id.tv_item_edit_back, !StringUtil.isEmpty(item.getBackImgPath()))
                .setVisible(R.id.tv_item_pre_front, !StringUtil.isEmpty(item.getFrontImgPath()))
                .setVisible(R.id.tv_item_pre_back, !StringUtil.isEmpty(item.getBackImgPath()))
                .setOnClickListener(R.id.tv_item_edit_front, new OnItemChildClickListener())
                .setOnClickListener(R.id.tv_item_edit_back, new OnItemChildClickListener())
                .setOnClickListener(R.id.tv_item_pre_front, new OnItemChildClickListener())
                .setOnClickListener(R.id.tv_item_pre_back, new OnItemChildClickListener())
                .setOnClickListener(R.id.iv_item_front, new OnItemChildClickListener())
                .setOnClickListener(R.id.iv_item_back, new OnItemChildClickListener());
        if (!StringUtil.isEmpty(item.getFrontImgPath())) {
            Glide.with(context)
                    .load(item.getFrontImgPath())
                    .apply(options)
                    .into(ivFront);
        }

        if (!StringUtil.isEmpty(item.getBackImgPath())) {
            Glide.with(context)
                    .load(item.getBackImgPath())
                    .apply(options)
                    .into(ivBack);
        }
    }

    @Override
    protected int getItemViewLayoutId(int position, ClothImage item) {
        return R.layout.item_color;
    }
}
