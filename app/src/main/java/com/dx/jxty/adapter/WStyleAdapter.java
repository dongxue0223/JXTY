package com.dx.jxty.adapter;

import android.content.Context;

import com.dx.jxty.R;
import com.dx.jxty.bean.WNewStytle;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;

import java.util.List;

/**
 * Created by dongxue on 2017/10/16.
 */

public class WStyleAdapter extends SuperBaseAdapter<WNewStytle> {

    public WStyleAdapter(Context context, List<WNewStytle> data) {
        super(context, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, WNewStytle item, int position) {
        holder.setText(R.id.tv_item_new_code, "款号:" + item.getNewCode());
    }

    @Override
    protected int getItemViewLayoutId(int position, WNewStytle item) {
        return R.layout.item_w_style;
    }
}
