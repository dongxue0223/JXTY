package com.dx.jxty.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dx.jxty.R;
import com.dx.jxty.bean.ImagePath;
import com.dx.jxty.bean.ImagePath;
import com.dx.jxty.bean.WNewStytle;
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

public class WStyleAdapter extends SuperBaseAdapter<WNewStytle> {
    private Context context;

    public WStyleAdapter(Context context, List<WNewStytle> data) {
        super(context, data);
        this.context = context;
    }

    private List<ImagePath> all = new ArrayList<>();
    private ColorAdapter colorAdapter;

    @Override
    protected void convert(BaseViewHolder holder, WNewStytle item, int position) {
        final RecyclerView view = holder.getView(R.id.srv_item);
//        view.setRefreshEnabled(false);
        view.setLayoutManager(new ScrollEnableLayoutManager(context));
//        all = DataSupport.findAll(ImagePath.class, 1);
        colorAdapter = new ColorAdapter(context, item.getImagePaths());
        view.setAdapter(colorAdapter);
        holder.setOnClickListener(R.id.iv_item_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePath clothImage = new ImagePath();
                clothImage.setGoodsColor("haha");
                all.add(clothImage);
                colorAdapter = new ColorAdapter(context, all);
                view.setAdapter(colorAdapter);
//                colorAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, WNewStytle item) {
        return R.layout.item_w_style;
    }
}
