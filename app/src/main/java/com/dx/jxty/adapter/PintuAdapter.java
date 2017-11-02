package com.dx.jxty.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dx.jxty.R;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by dongxue on 2017/11/2.
 */

public class PintuAdapter extends SuperBaseAdapter<Map> {
    private final Context context;

    public PintuAdapter(Context context, List<Map> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, Map item, int position) {
        RequestOptions r = new RequestOptions();
        r.centerCrop().override(100,100);
        Glide.with(context).load(new File((String) item.get("path"))).apply(r).into((ImageView) holder.getView(R.id.iv_item));
    }

    @Override
    protected int getItemViewLayoutId(int position, Map item) {
        return R.layout.item_image;
    }
}
