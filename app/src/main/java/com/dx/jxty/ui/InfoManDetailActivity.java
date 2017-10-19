package com.dx.jxty.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dx.jxty.R;
import com.dx.jxty.adapter.ColorAdapter;
import com.dx.jxty.app.Globle;
import com.dx.jxty.base.CommonTitleActivity;
import com.dx.jxty.bean.ImagePath;
import com.dx.jxty.bean.MShow;
import com.dx.jxty.utils.MyUtil;
import com.dx.jxty.utils.StringUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoManDetailActivity extends CommonTitleActivity implements SuperBaseAdapter.OnRecyclerViewItemChildClickListener {

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

    @BindView(R.id.iv_first)
    ImageView ivFirst;
    @BindView(R.id.srv_goods_image)
    SuperRecyclerView srvGoodsImage;
    @BindView(R.id.iv_add)
    ImageView ivAdd;

    private MShow manCloth;
    private RequestOptions options;
    private ColorAdapter colorAdapter;
    private List<ImagePath> clothImages;
    private String resultColor;
    private List<LocalMedia> selectList = new ArrayList<>();
    private static final int REQUEST_TO_CAMERA_FIRST = 300;//首图拍照
    private static final int REQUEST_TO_GALLERY_FIRST = 301;//首图相册

    private static final int REQUEST_TO_CAMERA_FRONT = 101;//拍照正面
    private static final int REQUEST_TO_CAMERA_BACK = 102;//拍照背面
    private static final int REQUEST_TO_GALLERY_FRONT = 201;//相册正面
    private static final int REQUEST_TO_GALLERY_BACK = 202;//相册背面


    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView() {
        showCommonTitle("商品详情");
        ivAdd.setVisibility(View.GONE);
        clothImages = new ArrayList<>();
        String code = getIntent().getStringExtra("goodsStyleCode");
        manCloth = DataSupport.where("goodsStyleCode = ?", code).find(MShow.class).get(0);
        srvGoodsImage.setLayoutManager(new LinearLayoutManager(context));
        srvGoodsImage.setRefreshEnabled(false);
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
//        clothImages = DataSupport.where("goodsStyleCode =?", manCloth.getGoodsStyleCode()).find(ImagePath.class);
        clothImages = manCloth.getImagePaths();
        if (clothImages != null && clothImages.size() > 0) {
            colorAdapter = new ColorAdapter(context, clothImages);
            srvGoodsImage.setAdapter(colorAdapter);
            colorAdapter.setOnItemChildClickListener(InfoManDetailActivity.this);
        }
    }

    public static void actionStart(Context context, Object obj) {
        Intent intent = new Intent(context, InfoManDetailActivity.class);
        intent.putExtra("goodsStyleCode", (String) obj);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_first, R.id.tv_change_first, R.id.tv_img_pre})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_first:
                goCamera(REQUEST_TO_CAMERA_FIRST);
                break;
            case R.id.tv_change_first:
                goGallery(REQUEST_TO_GALLERY_FIRST);
                break;
            case R.id.tv_img_pre:
                if (!StringUtil.isEmpty(manCloth.getFirstImgPath())) {
                    ImageActivity.actionStart(context, manCloth.getFirstImgPath());
                }
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
                        String path = Globle.manPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + "首图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        renameFile(localMedia.getPath(), path);
                        manCloth.setFirstImgPath(path);
                        Glide.with(context)
                                .load(path)
                                .apply(options)
                                .into(ivFirst);
                        ContentValues values = new ContentValues();
                        values.put("firstImgPath", path);
                        DataSupport.updateAll(MShow.class, values, "goodsStyleCode = ?", manCloth.getGoodsStyleCode());
                    }
                    break;
                case REQUEST_TO_GALLERY_FIRST:
                    if (selectList.size() > 0) {
                        String path = Globle.manPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + "首图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        copyFile(localMedia.getPath(), path);
                        manCloth.setFirstImgPath(path);
                        Glide.with(context)
                                .load(path)
                                .apply(options)
                                .into(ivFirst);
                        ContentValues values = new ContentValues();
                        values.put("firstImgPath", path);
                        DataSupport.updateAll(MShow.class, values, "goodsStyleCode = ?", manCloth.getGoodsStyleCode());
                    }
                    break;
                case REQUEST_TO_CAMERA_FRONT:
                    if (selectList.size() > 0) {
                        String path = Globle.manPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + resultColor + "正面图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        renameFile(localMedia.getPath(), path);
                        ContentValues values = new ContentValues();
                        values.put("frontImgPath", path);
                        DataSupport.updateAll(ImagePath.class, values, "goodsStyleCode = ? and goodsColor =?", manCloth.getGoodsStyleCode(), resultColor);
                        updateList();
                    }
                    break;
                case REQUEST_TO_CAMERA_BACK:
                    if (selectList.size() > 0) {
                        String path = Globle.manPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + resultColor + "背面图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        renameFile(localMedia.getPath(), path);
                        ContentValues values = new ContentValues();
                        values.put("backImgPath", path);
                        DataSupport.updateAll(ImagePath.class, values, "goodsStyleCode = ? and goodsColor =?", manCloth.getGoodsStyleCode(), resultColor);
                        updateList();
                    }

                case REQUEST_TO_GALLERY_FRONT:
                    if (selectList.size() > 0) {
                        String path = Globle.manPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + resultColor + "正面图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        copyFile(localMedia.getPath(), path);
                        ContentValues values = new ContentValues();
                        values.put("frontImgPath", path);
                        DataSupport.updateAll(ImagePath.class, values, "goodsStyleCode = ? and goodsColor =?", manCloth.getGoodsStyleCode(), resultColor);
                        updateList();
                    }
                    break;

                case REQUEST_TO_GALLERY_BACK:
                    if (selectList.size() > 0) {
                        String path = Globle.manPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + resultColor + "背面图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        copyFile(localMedia.getPath(), path);
                        ContentValues values = new ContentValues();
                        values.put("backImgPath", path);
                        DataSupport.updateAll(ImagePath.class, values, "goodsStyleCode = ? and goodsColor =?", manCloth.getGoodsStyleCode(), resultColor);
                        updateList();
                    }
                    break;

            }
        }
    }

    private void renameFile(@NonNull String pathFrom, @NonNull String pathTo) {
        File fileFrom = new File(pathFrom);
        File fileTo = new File(pathTo);
        if (fileTo.exists()) {
            fileTo.delete();
        }
        fileFrom.renameTo(fileTo);
    }

    private void copyFile(@NonNull String pathFrom, @NonNull String pathTo) {
        try {
            PictureFileUtils.copyFile(pathFrom, pathTo);
        } catch (IOException e) {
            e.printStackTrace();
            MyUtil.i("IOException-----" + e.toString());
        }
    }

    private void updateList() {
        initData();
    }


    private void goCamera(int code) {
        PictureSelector.create(InfoManDetailActivity.this)
                .openCamera(PictureMimeType.ofAll())//openCamera//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .setOutputCameraPath("/信天游/男装")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .forResult(code);//结果回调onActivityResult code
    }

    private void goGallery(int code) {
        PictureSelector.create(InfoManDetailActivity.this)
                .openGallery(PictureMimeType.ofImage())//openCamera//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .setOutputCameraPath("/信天游/男装")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .isCamera(false)// 是否显示拍照按钮 true or false
                .forResult(code);//结果回调onActivityResult code
    }

    @Override
    public void onItemChildClick(SuperBaseAdapter adapter, View view, int position) {
        ImagePath clothImage = clothImages.get(position);
        resultColor = clothImage.getGoodsColor();
        switch (view.getId()) {
            case R.id.iv_item_front:
                goCamera(REQUEST_TO_CAMERA_FRONT);
                break;
            case R.id.iv_item_back:
                goCamera(REQUEST_TO_CAMERA_BACK);
                break;
            case R.id.tv_item_edit_front:
                goGallery(REQUEST_TO_GALLERY_FRONT);
                break;
            case R.id.tv_item_edit_back:
                goGallery(REQUEST_TO_GALLERY_BACK);
                break;
            case R.id.tv_item_pre_front:
                if (!StringUtil.isEmpty(clothImage.getFrontImgPath())) {
                    ImageActivity.actionStart(context, clothImage.getFrontImgPath());
                }
                break;
            case R.id.tv_item_pre_back:
                if (!StringUtil.isEmpty(clothImage.getBackImgPath())) {
                    ImageActivity.actionStart(context, clothImage.getBackImgPath());
                }
                break;
        }
    }
}
