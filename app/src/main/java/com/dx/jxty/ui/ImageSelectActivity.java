package com.dx.jxty.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.dx.jxty.R;
import com.dx.jxty.adapter.PintuAdapter;
import com.dx.jxty.base.CommonTitleActivity;
import com.dx.jxty.bean.ImagePath;
import com.dx.jxty.bean.WShow;
import com.dx.jxty.utils.MyUtil;
import com.dx.jxty.utils.StringUtil;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageSelectActivity extends CommonTitleActivity implements SuperBaseAdapter.OnItemClickListener {
    @BindView(R.id.srv_pintu)
    SuperRecyclerView superRecyclerView;
    private WShow manCloth;
    private List<ImagePath> imagePaths;
    private ArrayList<Map> images;
    private PintuAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_select;
    }

    @Override
    protected void initView() {
        showCommonTitle("选择图片");
        superRecyclerView.setRefreshEnabled(false);
        superRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));
    }

    @Override
    protected void initData() {

        manCloth = (WShow) getIntent().getSerializableExtra("WShow");
        imagePaths = manCloth.getwImagePathList();
        images = new ArrayList<>();
        if (imagePaths != null && imagePaths.size() > 0) {
            for (int i = 0; i < imagePaths.size(); i++) {
                Map map = new HashMap();
                if (!StringUtil.isEmpty(imagePaths.get(i).getFrontImgPath())) {
                    map.put("path", imagePaths.get(i).getFrontImgPath());
                    map.put("isCheck", false);
                    images.add(map);
                }else continue;
            }
            adapter = new PintuAdapter(context, images);
            adapter.setOnItemClickListener(this);
            superRecyclerView.setAdapter(adapter);
        } else {
            MyUtil.showToast("暂时没有图片");
        }
    }

    public static void actionStart(Context context, Object obj) {
        Intent intent = new Intent(context, ImageSelectActivity.class);
        intent.putExtra("WShow", (WShow) obj);
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(View view, Object item, int position) {
        boolean check = (boolean) images.get(position).get("isCheck");
        if (check) {
            images.get(position).put("isCheck", false);
        } else {
            images.get(position).put("isCheck", true);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.tv_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
                ArrayList<Map> selectList = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    Map map = images.get(i);
                    if ((boolean) map.get("isCheck")) {
                        selectList.add(map);
                    }
                }
                if (selectList.size() >= 4) {
                    PintuActivity.actionStart(context, manCloth, selectList);
                } else {
                    MyUtil.showToast("最少选择四张图片");
                }
                break;
        }
    }
}
