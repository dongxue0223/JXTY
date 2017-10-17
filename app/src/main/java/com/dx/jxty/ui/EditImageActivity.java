package com.dx.jxty.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dx.jxty.R;
import com.dx.jxty.base.CommonTitleActivity;
import com.dx.jxty.bean.ClothImage;
import com.dx.jxty.utils.StringUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EditImageActivity extends CommonTitleActivity {

    @BindView(R.id.iv_font)
    ImageView ivFont;
    @BindView(R.id.tv_change_font)
    ImageView tvChangeFont;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_change_back)
    ImageView tvChangeBack;

    private RequestOptions options;
    private List<LocalMedia> selectList = new ArrayList<>();
    private ClothImage manCloth;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_image;
    }

    public static void actionStart(Context context, Object obj) {
        Intent intent = new Intent(context, EditImageActivity.class);
        intent.putExtra("manCloth", (ClothImage) obj);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        options = new RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
    }

    @Override
    protected void initData() {
        ClothImage temp = (ClothImage) getIntent().getSerializableExtra("manCloth");
//        manCloth = temp;

        List<ClothImage> clothImages = DataSupport.where("goodsStyleCode =? and goodsColor=?", temp.getGoodsStyleCode(), temp.getGoodsColor()).find(ClothImage.class);
        if (clothImages != null && clothImages.size() > 0) {
            manCloth = clothImages.get(0);
            if (!StringUtil.isEmpty(manCloth.getFrontImgPath())) {
                Glide.with(context)
                        .load(manCloth.getFrontImgPath())
                        .apply(options)
                        .into(ivFont);
            }

            if (!StringUtil.isEmpty(manCloth.getBackImgPath())) {
                Glide.with(context)
                        .load(manCloth.getBackImgPath())
                        .apply(options)
                        .into(ivBack);
            }
        } else {
            manCloth = temp;
        }
    }

    private void goPhoto(int code) {
        PictureSelector.create(EditImageActivity.this)
                .openCamera(PictureMimeType.ofAll())
                .setOutputCameraPath("/信天游")// 自定义拍照保存路径,可不填
                .compress(true)// 是否压缩 true or false
                .forResult(code);//结果回调onActivityResult code
    }

    @OnClick({R.id.iv_font, R.id.tv_change_font, R.id.iv_back, R.id.tv_change_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_font:
                if (!StringUtil.isEmpty(manCloth.getFrontImgPath())) {
                    ImageActivity.actionStart(context,manCloth.getFrontImgPath());
                }
                break;
            case R.id.iv_back:
                if (!StringUtil.isEmpty(manCloth.getBackImgPath())) {
                    ImageActivity.actionStart(context,manCloth.getBackImgPath());
                }
                break;
            case R.id.tv_change_font:
                goPhoto(200);
                break;
            case R.id.tv_change_back:
                goPhoto(201);
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case 200:
//                    // 图片选择
//                    selectList = PictureSelector.obtainMultipleResult(data);
//                    if (selectList.size() > 0) {
//                        String path = parentPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName()+ manCloth.getGoodsColor() + "正面图" + ".JPG";
//                        LocalMedia localMedia = selectList.get(0);
//                        File file = new File(localMedia.getPath());
//
//                        File fontImg = new File(path);
//                        if (fontImg.exists()) {
//                            fontImg.delete();
//                        }
//                        file.renameTo(fontImg);
//                        localMedia.setPath(path);
//                        manCloth.setFrontImgPath(path);
//                        Glide.with(context)
//                                .load(localMedia.getPath())
//                                .apply(options)
//                                .into(ivFont);
//
//                        ContentValues values = new ContentValues();
//                        values.put("fontImgPath", localMedia.getPath());
//                        DataSupport.updateAll(ClothImage.class, values, "goodsStyleCode = ? and goodsColor =?", manCloth.getGoodsStyleCode(), manCloth.getGoodsColor());
//                    }
//                    break;
//                case 201:
//                    // 图片选择
//                    selectList = PictureSelector.obtainMultipleResult(data);
//                    if (selectList.size() > 0) {
//                        String path = parentPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + manCloth.getGoodsColor() + "背面图" + ".JPG";
//                        LocalMedia localMedia = selectList.get(0);
//                        File file = new File(localMedia.getPath());
//                        File backImg = new File(path);
//                        if (backImg.exists()) {
//                            backImg.delete();
//                        }
//                        file.renameTo(backImg);
//                        localMedia.setPath(path);
//                        manCloth.setBackImgPath(path);
//                        Glide.with(context)
//                                .load(localMedia.getPath())
//                                .apply(options)
//                                .into(ivBack);
//
//                        ContentValues values = new ContentValues();
//                        values.put("backImgPath", localMedia.getPath());
//                        DataSupport.updateAll(ClothImage.class, values, "goodsStyleCode = ? and goodsColor =?", manCloth.getGoodsStyleCode(), manCloth.getGoodsColor());
//                    }
//                    break;
//            }
//        }
//    }
}
