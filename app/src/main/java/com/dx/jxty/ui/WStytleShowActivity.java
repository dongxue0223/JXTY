package com.dx.jxty.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dx.jxty.R;
import com.dx.jxty.adapter.ColorAdapter;
import com.dx.jxty.app.Globle;
import com.dx.jxty.base.CommonTitleActivity;
import com.dx.jxty.bean.ImagePath;
import com.dx.jxty.bean.WNewStytle;
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

public class WStytleShowActivity extends CommonTitleActivity implements SuperBaseAdapter.OnRecyclerViewItemChildClickListener {

    @BindView(R.id.tv_new_code)
    TextView tvNewCode;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    private static final int REQUEST_TO_CAMERA_FRONT = 101;//拍照正面
    private static final int REQUEST_TO_CAMERA_BACK = 102;//拍照背面
    private static final int REQUEST_TO_GALLERY_FRONT = 201;//相册正面
    private static final int REQUEST_TO_GALLERY_BACK = 202;//相册背面
    @BindView(R.id.super_recycle_view)
    SuperRecyclerView view;
    private List<ImagePath> imagePathList;
    private ColorAdapter colorAdapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private WNewStytle wNewStytle;
    private String resultColor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wstytle_show;
    }

    @Override
    protected void initView() {
        view.setRefreshEnabled(false);
        view.setLayoutManager(new LinearLayoutManager(context));
        wNewStytle = (WNewStytle) getIntent().getSerializableExtra("wNewStytle");
        tvNewCode.setText("款号" + wNewStytle.getNewCode());
    }

    @Override
    protected void initData() {
        imagePathList = wNewStytle.getImagePaths();
//        if (imagePathList.size() == 0) {
//            ImagePath imagePath = new ImagePath();
//            imagePath.setType("1");
//            imagePath.setGoodsStyleCode(wNewStytle.getNewCode());
//            imagePath.setGoodsColor("白色");
//            imagePath.save();
//            imagePathList.add(imagePath);
//        }
        colorAdapter = new ColorAdapter(context, imagePathList);
        view.setAdapter(colorAdapter);
        colorAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void onItemChildClick(SuperBaseAdapter adapter, View view, int position) {
        ImagePath imagePath = imagePathList.get(position);
        resultColor = imagePath.getGoodsColor();
        switch (view.getId()) {
            case R.id.iv_item_front:
                MyUtil.goCamera(this, REQUEST_TO_CAMERA_FRONT);
                break;
            case R.id.iv_item_back:
                MyUtil.goCamera(this, REQUEST_TO_CAMERA_BACK);
                break;
            case R.id.tv_item_edit_front:
                MyUtil.goGallery(this, REQUEST_TO_GALLERY_FRONT);
                break;
            case R.id.tv_item_edit_back:
                MyUtil.goGallery(this, REQUEST_TO_GALLERY_BACK);
                break;
            case R.id.tv_item_pre_front:
                if (!StringUtil.isEmpty(imagePath.getFrontImgPath())) {
                    ImageActivity.actionStart(context, imagePath.getFrontImgPath());
                }
                break;
            case R.id.tv_item_pre_back:
                if (!StringUtil.isEmpty(imagePath.getBackImgPath())) {
                    ImageActivity.actionStart(context, imagePath.getBackImgPath());
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
                case REQUEST_TO_CAMERA_FRONT:
                    if (selectList.size() > 0) {
                        String path = Globle.womanPath + "/" + wNewStytle.getNewCode() + resultColor + "正面图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        MyUtil.renameFile(localMedia.getPath(), path);
                        ContentValues values = new ContentValues();
                        values.put("frontImgPath", path);
                        DataSupport.updateAll(ImagePath.class, values, "goodsStyleCode = ? and goodsColor =?", wNewStytle.getNewCode(), resultColor);
                        initData();
                    }
                    break;
                case REQUEST_TO_CAMERA_BACK:
                    if (selectList.size() > 0) {
                        String path = Globle.womanPath + "/" + wNewStytle.getNewCode() + resultColor + "背面图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        MyUtil.renameFile(localMedia.getPath(), path);
                        ContentValues values = new ContentValues();
                        values.put("backImgPath", path);
                        DataSupport.updateAll(ImagePath.class, values, "goodsStyleCode = ? and goodsColor =?", wNewStytle.getNewCode(), resultColor);
                        initData();
                    }
                    break;
                case REQUEST_TO_GALLERY_FRONT:
                    if (selectList.size() > 0) {
                        String path = Globle.womanPath + "/" + wNewStytle.getNewCode() + resultColor + "正面图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        MyUtil.copyFile(localMedia.getPath(), path);
                        ContentValues values = new ContentValues();
                        values.put("frontImgPath", path);
                        DataSupport.updateAll(ImagePath.class, values, "goodsStyleCode = ? and goodsColor =?", wNewStytle.getNewCode(), resultColor);
                        initData();
                    }
                    break;
                case REQUEST_TO_GALLERY_BACK:
                    if (selectList.size() > 0) {
                        String path = Globle.womanPath + "/" + wNewStytle.getNewCode() + resultColor + "背面图" + ".JPG";
                        LocalMedia localMedia = selectList.get(0);
                        MyUtil.copyFile(localMedia.getPath(), path);
                        ContentValues values = new ContentValues();
                        values.put("backImgPath", path);
                        DataSupport.updateAll(ImagePath.class, values, "goodsStyleCode = ? and goodsColor =?", wNewStytle.getNewCode(), resultColor);
                        initData();
                    }
                    break;
            }
        }
    }


    public static void actionStart(Context context, Object obj) {
        Intent intent = new Intent(context, WStytleShowActivity.class);
        intent.putExtra("wNewStytle", (WNewStytle) obj);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_new_code, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_new_code:

                break;
            case R.id.iv_add:
                inputTitleDialog();
                break;
        }
    }

    private void inputTitleDialog() {
        final EditText inputServer = new EditText(this);
        inputServer.setFocusable(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("输入添加的颜色").setView(inputServer).setNegativeButton("取消", null);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String inputName = inputServer.getText().toString();
                        ImagePath imagePath = new ImagePath();
                        imagePath.setGoodsStyleCode(wNewStytle.getNewCode());
                        imagePath.setGoodsColor(inputName);
                        imagePath.setType("1");
                        imagePath.save();
                        initData();
                    }
                });
        builder.show();
    }
}
