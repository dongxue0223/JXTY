package com.dx.jxty.adapter;

import android.content.Context;

import com.dx.jxty.R;
import com.dx.jxty.bean.WShow;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;

import java.util.List;

/**
 * Created by dongxue on 2017/8/28.
 */

public class WomanClothAdapter extends SuperBaseAdapter<WShow> {
    public WomanClothAdapter(Context context, List<WShow> data) {
        super(context, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, WShow item, int position) {
        holder.setText(R.id.tv_item_goods_code, item.getGoodsCode())
                .setText(R.id.tv_item_goods_name, item.getGoodsName())
                .setText(R.id.tv_item_goods_style_code, item.getGoodsStyleCode())
                .setText(R.id.tv_item_goods_brand, item.getGoodsBrand());
    }

    @Override
    protected int getItemViewLayoutId(int position, WShow item) {
        return R.layout.item_goods;
    }
}
