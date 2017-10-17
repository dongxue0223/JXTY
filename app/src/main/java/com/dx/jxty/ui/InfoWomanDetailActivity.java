package com.dx.jxty.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import com.dx.jxty.adapter.WStyleAdapter;
import com.dx.jxty.app.Globle;
import com.dx.jxty.base.CommonTitleActivity;
import com.dx.jxty.bean.ClothImage;
import com.dx.jxty.bean.ManCloth;
import com.dx.jxty.utils.MyUtil;
import com.dx.jxty.utils.StringUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InfoWomanDetailActivity extends CommonTitleActivity implements SuperBaseAdapter.OnItemClickListener, SuperBaseAdapter.OnRecyclerViewItemChildClickListener {

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

    private ManCloth manCloth;
    private RequestOptions options;
    private ColorAdapter colorAdapter;
    private List<ClothImage> clothImages;
    private String resultColor;
    private List<LocalMedia> selectList = new ArrayList<>();
    private static final int REQUEST_TO_FIRST = 100;//首图
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
        clothImages = new ArrayList<>();
        String code = getIntent().getStringExtra("goodsStyleCode");
        manCloth = DataSupport.where("goodsStyleCode = ?", code).find(ManCloth.class).get(0);
        srvGoodsImage.setLayoutManager(new LinearLayoutManager(context));
        srvGoodsImage.setRefreshEnabled(false);
        tvGoodsCode.setText(manCloth.getGoodsCode());
        tvGoodsName.setText(manCloth.getGoodsBrand() + " " + manCloth.getGoodsName() + " " + manCloth.getGoodsSeason());
        tvGoodsColor.setText(manCloth.getGoodsColor());
        tvGoodsStyleCode.setText(manCloth.getGoodsStyleCode());
        tvGoodsSize.setText(manCloth.getGoodsSize());
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
        srvGoodsImage.setAdapter(new WStyleAdapter(context, MyUtil.getTestString()));
//        clothImages = DataSupport.where("goodsStyleCode =?", manCloth.getGoodsStyleCode()).find(ClothImage.class);
//        if (clothImages != null && clothImages.size() > 0) {
//            colorAdapter = new ColorAdapter(context, clothImages);
//            srvGoodsImage.setAdapter(colorAdapter);
//            colorAdapter.setOnItemChildClickListener(InfoWomanDetailActivity.this);
//        }
    }

    public static void actionStart(Context context, Object obj) {
        Intent intent = new Intent(context, InfoWomanDetailActivity.class);
        intent.putExtra("goodsStyleCode", (String) obj);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_first, R.id.tv_change_first})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_first:
                if (!StringUtil.isEmpty(manCloth.getFirstImgPath())) {
                    ImageActivity.actionStart(context, manCloth.getFirstImgPath());
                }
                break;
            case R.id.tv_change_first:
                goCamera(REQUEST_TO_FIRST);
                break;

        }
    }

    private void goCamera(int code) {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofAll())//openCamera//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .setOutputCameraPath("/信天游/男装")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .forResult(code);//结果回调onActivityResult code
    }

    private void goGallery(int code) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//openCamera//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                .setOutputCameraPath("/信天游/男装")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .isCamera(false)// 是否显示拍照按钮 true or false
                .forResult(code);//结果回调onActivityResult code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectList = PictureSelector.obtainMultipleResult(data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TO_FIRST:
                    if (selectList.size() > 0) {
                        String path = Globle.womanPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + "首图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        renameFile(localMedia.getPath(), path);
                        manCloth.setFirstImgPath(path);
                        Glide.with(context)
                                .load(path)
                                .apply(options)
                                .into(ivFirst);
                        ContentValues values = new ContentValues();
                        values.put("firstImgPath", path);
                        DataSupport.updateAll(ManCloth.class, values, "goodsStyleCode = ?", manCloth.getGoodsStyleCode());
                    }
                    break;
                case REQUEST_TO_CAMERA_FRONT:
                    if (selectList.size() > 0) {
                        String path = Globle.womanPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + resultColor + "正面图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        renameFile(localMedia.getPath(), path);
                        ContentValues values = new ContentValues();
                        values.put("frontImgPath", path);
                        DataSupport.updateAll(ClothImage.class, values, "goodsStyleCode = ? and goodsColor =?", manCloth.getGoodsStyleCode(), resultColor);
                        updateList();
                    }
                    break;
                case REQUEST_TO_CAMERA_BACK:
                    if (selectList.size() > 0) {
                        String path = Globle.womanPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + resultColor + "背面图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        renameFile(localMedia.getPath(), path);
                        ContentValues values = new ContentValues();
                        values.put("backImgPath", path);
                        DataSupport.updateAll(ClothImage.class, values, "goodsStyleCode = ? and goodsColor =?", manCloth.getGoodsStyleCode(), resultColor);
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

    private void updateList() {
        initData();
    }

    @Override
    public void onItemClick(View view, Object item, int position) {
        EditImageActivity.actionStart(InfoWomanDetailActivity.this, (ClothImage) item);
    }

    @Override
    public void onItemChildClick(SuperBaseAdapter adapter, View view, int position) {
        ClothImage clothImage = clothImages.get(position);
        resultColor = clothImage.getGoodsColor();
        switch (view.getId()) {
            case R.id.iv_item_front:
                break;
        }
    }
}
