package com.dx.jxty.adapter;

import android.content.Context;

import com.dx.jxty.R;
import com.dx.jxty.bean.MShow;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;

import java.util.List;

/**
 * Created by dongxue on 2017/8/28.
 */

public class ManClothAdapter extends SuperBaseAdapter<MShow> {
    public ManClothAdapter(Context context, List<MShow> data) {
        super(context, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, MShow item, int position) {
        holder.setText(R.id.tv_item_goods_code, item.getGoodsCode())
                .setText(R.id.tv_item_goods_name, item.getGoodsName())
                .setText(R.id.tv_item_goods_style_code, item.getGoodsStyleCode())
                .setText(R.id.tv_item_goods_brand, item.getGoodsBrand());
    }

    @Override
    protected int getItemViewLayoutId(int position, MShow item) {
        return R.layout.item_goods;
    }
}
