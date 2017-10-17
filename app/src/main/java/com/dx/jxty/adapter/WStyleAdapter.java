package com.dx.jxty.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dx.jxty.R;
import com.dx.jxty.bean.ClothImage;
import com.dx.jxty.widget.ScrollEnableLayoutManager;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongxue on 2017/10/16.
 */

public class WStyleAdapter extends SuperBaseAdapter<String> {
    private Context context;

    public WStyleAdapter(Context context, List<String> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    private List<ClothImage> all = new ArrayList<>();
    private ColorAdapter colorAdapter;

    @Override
    protected void convert(BaseViewHolder holder, String item, int position) {
        final RecyclerView view = holder.getView(R.id.srv_item);
//        view.setRefreshEnabled(false);
        view.setLayoutManager(new ScrollEnableLayoutManager(context));
        all = DataSupport.findAll(ClothImage.class, 1);
        colorAdapter = new ColorAdapter(context, all);
        view.setAdapter(colorAdapter);
        holder.setOnClickListener(R.id.iv_item_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClothImage clothImage = new ClothImage();
                clothImage.setGoodsName("1231");
                clothImage.setGoodsColor("haha");
                all.add(clothImage);
                colorAdapter = new ColorAdapter(context, all);
                view.setAdapter(colorAdapter);
//                colorAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, String item) {
        return R.layout.item_w_style;
    }
}
