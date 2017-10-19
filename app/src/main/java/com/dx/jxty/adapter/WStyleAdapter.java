package com.dx.jxty.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dx.jxty.R;
import com.dx.jxty.bean.ImagePath;
import com.dx.jxty.bean.ImagePath;
import com.dx.jxty.bean.WNewStytle;
import com.dx.jxty.ui.ImageActivity;
import com.dx.jxty.utils.MyUtil;
import com.dx.jxty.utils.StringUtil;
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

    @Override
    protected void convert(BaseViewHolder holder, WNewStytle item, int position) {
        holder.setText(R.id.tv_item_new_code, "款号:" + item.getNewCode());
    }

    @Override
    protected int getItemViewLayoutId(int position, WNewStytle item) {
        return R.layout.item_w_style;
    }
}
