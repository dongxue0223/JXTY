package com.dx.jxty.ui;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dx.jxty.R;
import com.dx.jxty.app.Globle;
import com.dx.jxty.base.BaseActivity;
import com.dx.jxty.bean.WShow;
import com.dx.jxty.utils.BitmapUtils;
import com.dx.jxty.utils.HollowModelUtil;
import com.dx.jxty.utils.MyUtil;
import com.dx.jxty.widget.pintu.HollowModel;
import com.dx.jxty.widget.pintu.JigsawView;
import com.dx.jxty.widget.pintu.PictureModel;

import net.bither.util.NativeUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dongxue on 2017/11/1.
 */

public class PintuActivity extends BaseActivity {
    @BindView(R.id.jigsaw_view)
    JigsawView mJigsawView;
    @BindView(R.id.layout_bottom_edit)
    LinearLayout mEditPicBottomBar;
    private WShow manCloth;
    private ArrayList<PictureModel> mPictureModelList = new ArrayList<>();
    private PictureModel mSelectPictureModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pintu;
    }

    @Override
    protected void initView() {
        mJigsawView.setPictureSelectListener(new JigsawView.PictureSelectListener() {
            @Override
            public void onPictureSelect(PictureModel pictureModel) {
                showPicEditBar(pictureModel);
            }
        });

        mJigsawView.setPictureNoSelectListener(new JigsawView.PictureNoSelectListener() {
            @Override
            public void onPictureNoSelect() {
                closePicEditBar();
            }
        });

        mJigsawView.setPictureCancelSelectListener(new JigsawView.PictureCancelSelectListener() {
            @Override
            public void onPictureCancelSelect() {
                closePicEditBar();
            }
        });
    }

    @Override
    protected void initData() {
        manCloth = (WShow) getIntent().getSerializableExtra("WShow");
        ArrayList<Map> selectList = (ArrayList<Map>) getIntent().getSerializableExtra("list");
        initJigsawView(selectList);

    }

    public static void actionStart(Context context, WShow wShow, ArrayList<Map> list) {
        Intent intent = new Intent(context, PintuActivity.class);
        intent.putExtra("WShow", wShow);
        intent.putExtra("list", list);
        context.startActivity(intent);
    }

    private void initJigsawView(ArrayList<Map> selectList) {
        Bitmap mBitmapBackGround = BitmapFactory.decodeResource(getResources(), R.drawable.sky);
        mJigsawView.setBitmapBackGround(mBitmapBackGround).setPictureModels(getHollowModel(selectList));
    }

    public ArrayList<PictureModel> getHollowModel(ArrayList<Map> selectList) {
        if (selectList.size() >= 4 && selectList.size() < 6) {
            List<HollowModel> hmList = HollowModelUtil.getFourHM(context);
            for (int i = 0; i < 4; i++) {
                Map map = selectList.get(i);
                Bitmap picture = BitmapUtils.getImageBitmap((String) map.get("path"));
                PictureModel pictureModel = new PictureModel();
                pictureModel.setBitmapPicture(picture);
                pictureModel.setHollowModel(hmList.get(i));
                mPictureModelList.add(pictureModel);
            }
        }
        if (selectList.size() >= 6 && selectList.size() < 9) {
            List<HollowModel> hmList = HollowModelUtil.getSixHM(context);
            for (int i = 0; i < 6; i++) {
                Map map = selectList.get(i);
                Bitmap picture = BitmapUtils.getImageBitmap((String) map.get("path"));
                PictureModel pictureModel = new PictureModel();
                pictureModel.setBitmapPicture(picture);
                pictureModel.setHollowModel(hmList.get(i));
                mPictureModelList.add(pictureModel);
            }
        }
        if (selectList.size() >= 9) {
            List<HollowModel> hmList = HollowModelUtil.getNineHM(context);
            for (int i = 0; i < 9; i++) {
                Map map = selectList.get(i);
                Bitmap picture = BitmapUtils.getImageBitmap((String) map.get("path"));
                PictureModel pictureModel = new PictureModel();
                pictureModel.setBitmapPicture(picture);
                pictureModel.setHollowModel(hmList.get(i));
                mPictureModelList.add(pictureModel);
            }
        }
        return mPictureModelList;
    }

    @OnClick({R.id.close_edit, R.id.tv_edit_rotate, R.id.tv_edit_overturn, R.id.tv_edit_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_edit:
                closePicEditBar();
                break;
            case R.id.tv_edit_rotate:
                if (mSelectPictureModel != null){
                    mSelectPictureModel.setRotate(mSelectPictureModel.getRotate() + 90);
                    mJigsawView.refreshView();
                }
                break;
            case R.id.tv_edit_overturn:
                if (mSelectPictureModel != null){
                    mSelectPictureModel.setScaleX(-mSelectPictureModel.getScaleX());
                    mJigsawView.refreshView();
                }
                break;
            case R.id.tv_edit_save:
                mJigsawView.setNeedHighlight(false);
                mJigsawView.refreshView();
                String path = Globle.womanPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + "首图.JPG";
                Bitmap bitmap = BitmapUtils.getBitmapFromView(mJigsawView, mJigsawView.getWidth(), mJigsawView.getHeight());
                NativeUtil.saveBitmap(bitmap, 100, path, false);
                ContentValues values = new ContentValues();
                values.put("firstImgPath", path);
                DataSupport.updateAll(WShow.class, values, "goodsStyleCode = ?", manCloth.getGoodsStyleCode());
                MyUtil.showToast("保存成功");
                finish();
                break;
        }
    }

    private void showPicEditBar(PictureModel pictureModel) {
        mSelectPictureModel = pictureModel;
        ObjectAnimator.ofFloat(mEditPicBottomBar, "translationY", 0).setDuration(200).start();
    }

    private void closePicEditBar() {
        if (mEditPicBottomBar.getTranslationY() == 0) {
            ObjectAnimator.ofFloat(mEditPicBottomBar, "translationY", 0, mEditPicBottomBar.getHeight()).setDuration(200).start();
        }
    }

}
