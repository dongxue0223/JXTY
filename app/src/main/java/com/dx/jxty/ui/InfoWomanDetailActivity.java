package com.dx.jxty.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dx.jxty.R;
import com.dx.jxty.adapter.WStyleAdapter;
import com.dx.jxty.app.Globle;
import com.dx.jxty.base.CommonTitleActivity;
import com.dx.jxty.bean.WNewStytle;
import com.dx.jxty.bean.WShow;
import com.dx.jxty.utils.MyUtil;
import com.dx.jxty.utils.StringUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InfoWomanDetailActivity extends CommonTitleActivity implements SuperBaseAdapter.OnItemClickListener {

    @BindView(R.id.tv_goods_code)
    TextView tvGoodsCode;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_style_code)
    TextView tvGoodsStyleCode;
    @BindView(R.id.tv_goods_color)
    TextView tvGoodsColor;
    @BindView(R.id.tv_goods_size)
    TextView tvGoodsSize;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_hecheng)
    TextView tvHecheng;
    @BindView(R.id.iv_first)
    ImageView ivFirst;
    @BindView(R.id.srv_goods_image)
    SuperRecyclerView srvGoodsImage;

    private WShow manCloth;
    private RequestOptions options;
    private List<LocalMedia> selectList = new ArrayList<>();
    private static final int REQUEST_TO_CAMERA_FIRST = 300;//首图拍照
    private static final int REQUEST_TO_GALLERY_FIRST = 301;//首图相册

    private List<WNewStytle> wNewStytles = new ArrayList<>();
    private WStyleAdapter wStyleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView() {
        showCommonTitle("商品详情");
        tvHecheng.setVisibility(View.VISIBLE);
        srvGoodsImage.setLayoutManager(new LinearLayoutManager(context));
        srvGoodsImage.setRefreshEnabled(false);
        String code = getIntent().getStringExtra("goodsStyleCode");
        manCloth = DataSupport.where("goodsStyleCode = ?", code).find(WShow.class).get(0);
        tvGoodsCode.setText(manCloth.getGoodsCode());
        tvGoodsName.setText(manCloth.getGoodsBrand() + " " + manCloth.getGoodsName());
        tvGoodsStyleCode.setText(manCloth.getGoodsStyleCode());
        tvGoodsPrice.setText("¥" + manCloth.getGoodsPrice());
        options = new RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        if (!StringUtil.isEmpty(manCloth.getFirstImgPath())) {
            Glide.with(context)
                    .load(manCloth.getFirstImgPath())
                    .apply(options)
                    .into(ivFirst);
        }
    }

    @Override
    protected void initData() {
        wNewStytles = manCloth.getwNewStytleList();//DataSupport.where("goodsStyleCode = ?", manCloth.getGoodsStyleCode()).find(WNewStytle.class);
        if (wNewStytles.size() == 0) {
            MyUtil.showToast("点击加号,添加新款");
        }
        wStyleAdapter = new WStyleAdapter(context, wNewStytles);
        srvGoodsImage.setAdapter(wStyleAdapter);
        wStyleAdapter.setOnItemClickListener(this);
    }

    public static void actionStart(Context context, Object obj) {
        Intent intent = new Intent(context, InfoWomanDetailActivity.class);
        intent.putExtra("goodsStyleCode", (String) obj);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_first, R.id.tv_change_first, R.id.tv_img_pre, R.id.iv_add, R.id.tv_hecheng})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_first:
                MyUtil.goCamera(this, REQUEST_TO_CAMERA_FIRST);
                break;
            case R.id.tv_change_first:
                MyUtil.goGallery(this, REQUEST_TO_GALLERY_FIRST);
                break;
            case R.id.tv_img_pre:
                if (!StringUtil.isEmpty(manCloth.getFirstImgPath())) {
                    ImageActivity.actionStart(context, manCloth.getFirstImgPath());
                }
                break;
            case R.id.iv_add:
                if (wNewStytles != null && wNewStytles.size() > 0) {
                    WNewStytle wNewStytle = new WNewStytle();
                    wNewStytle.setGoodsStyleCode(manCloth.getGoodsStyleCode());
                    wNewStytle.setNewCode(manCloth.getGoodsStyleCode() + "" + String.valueOf(wNewStytles.size() + 1));
                    wNewStytle.save();
                    wNewStytles.add(wNewStytle);
                    wStyleAdapter.notifyDataSetChanged();
                } else {
                    WNewStytle wNewStytle = new WNewStytle();
                    wNewStytle.setGoodsStyleCode(manCloth.getGoodsStyleCode());
                    wNewStytle.setNewCode(manCloth.getGoodsStyleCode() + "1");
                    wNewStytle.save();
                    wNewStytles.add(wNewStytle);
                    wStyleAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_hecheng:
                ImageSelectActivity.actionStart(context, manCloth);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectList = PictureSelector.obtainMultipleResult(data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TO_CAMERA_FIRST:
                    if (selectList.size() > 0) {
                        String path = Globle.womanPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + "首图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        MyUtil.renameFile(localMedia.getPath(), path);
                        manCloth.setFirstImgPath(path);
                        Glide.with(context)
                                .load(path)
                                .apply(options)
                                .into(ivFirst);
                        ContentValues values = new ContentValues();
                        values.put("firstImgPath", path);
                        DataSupport.updateAll(WShow.class, values, "goodsStyleCode = ?", manCloth.getGoodsStyleCode());
                    }
                    break;
                case REQUEST_TO_GALLERY_FIRST:
                    if (selectList.size() > 0) {
                        String path = Globle.womanPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + "首图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        MyUtil.copyFile(localMedia.getPath(), path);
                        manCloth.setFirstImgPath(path);
                        Glide.with(context)
                                .load(path)
                                .apply(options)
                                .into(ivFirst);
                        ContentValues values = new ContentValues();
                        values.put("firstImgPath", path);
                        DataSupport.updateAll(WShow.class, values, "goodsStyleCode = ?", manCloth.getGoodsStyleCode());
                    }
                    break;
            }
        }
    }

    private void updateList() {
        initData();
    }


    @Override
    public void onItemClick(View view, Object item, int position) {
        WStytleShowActivity.actionStart(this, item);
    }
}
