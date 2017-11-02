package com.dx.jxty.widget.pintu;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.dx.jxty.R;
import com.dx.jxty.adapter.PintuAdapter;
import com.dx.jxty.app.Globle;
import com.dx.jxty.base.CommonTitleActivity;
import com.dx.jxty.bean.ImagePath;
import com.dx.jxty.bean.WShow;
import com.dx.jxty.utils.BitmapUtils;
import com.dx.jxty.utils.HollowModelUtil;
import com.dx.jxty.utils.MyUtil;
import com.luck.picture.lib.adapter.PictureImageGridAdapter;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import net.bither.util.NativeUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dongxue on 2017/11/1.
 */

public class PintuActivity extends CommonTitleActivity implements SuperBaseAdapter.OnItemClickListener {
    @BindView(R.id.jigsaw_view)
    JigsawView mJigsawView;

    @BindView(R.id.srv_pintu)
    SuperRecyclerView superRecyclerView;

    private WShow manCloth;

    private ArrayList<PictureModel> mPictureModelList = new ArrayList<>();
    private PictureModel mSelectPictureModel;//被选择的拼图
    private List<ImagePath> imagePaths;
    private PintuAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pintu;
    }

    @Override
    protected void initView() {
        showCommonTitle("拼图");
        superRecyclerView.setRefreshEnabled(false);
        superRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));
    }

    @Override
    protected void initData() {
        manCloth = (WShow) getIntent().getSerializableExtra("WShow");
        imagePaths = manCloth.getwImagePathList();
        MyUtil.showToast(imagePaths.get(0).getFrontImgPath());
        List<Map> images = new ArrayList<>();
        for (int i = 0; i < imagePaths.size(); i++) {
            Map map = new HashMap();
            map.put("path", imagePaths.get(i).getFrontImgPath());
            map.put("isCheck", false);
            images.add(map);
        }
        adapter = new PintuAdapter(context, images);
        adapter.setOnItemClickListener(this);
        superRecyclerView.setAdapter(adapter);
        initJigsawView();
    }

    public static void actionStart(Context context, Object obj) {
        Intent intent = new Intent(context, PintuActivity.class);
        intent.putExtra("WShow", (WShow) obj);
        context.startActivity(intent);
    }

    private void initJigsawView() {
        tvRightTitle.setVisibility(View.VISIBLE);
        tvRightTitle.setText("保存");
        Bitmap mBitmapBackGround = BitmapFactory.decodeResource(getResources(), R.drawable.sky);
        mJigsawView.setBitmapBackGround(mBitmapBackGround).setPictureModels(getHollowModel());
    }

    public ArrayList<PictureModel> getHollowModel() {
        Bitmap picture0 = BitmapUtils.fileToBitmap(imagePaths.get(0).getFrontImgPath());
        Bitmap picture1 = BitmapUtils.fileToBitmap(imagePaths.get(1).getFrontImgPath());
        Bitmap picture2 = BitmapUtils.fileToBitmap(imagePaths.get(2).getFrontImgPath());
        Bitmap picture3 = BitmapUtils.fileToBitmap(imagePaths.get(3).getFrontImgPath());

        List<HollowModel> hmList = HollowModelUtil.getNineHM(context);
        PictureModel pictureModel0 = new PictureModel();
        pictureModel0.setBitmapPicture(picture0);
        pictureModel0.setHollowModel(hmList.get(0));

        PictureModel pictureModel1 = new PictureModel();
        pictureModel1.setBitmapPicture(picture1);
        pictureModel1.setHollowModel(hmList.get(1));

        PictureModel pictureModel2 = new PictureModel();
        pictureModel2.setBitmapPicture(picture2);
        pictureModel2.setHollowModel(hmList.get(2));

        PictureModel pictureModel3 = new PictureModel();
        pictureModel3.setBitmapPicture(picture3);
        pictureModel3.setHollowModel(hmList.get(3));

        mPictureModelList.add(pictureModel0);
        mPictureModelList.add(pictureModel1);
        mPictureModelList.add(pictureModel2);
        mPictureModelList.add(pictureModel3);
        return mPictureModelList;
    }


    @OnClick(R.id.tv_right_title)
    public void onViewClicked() {
        String path = Globle.womanPath + "/" + manCloth.getGoodsStyleCode() + manCloth.getGoodsName() + "首图.JPG";
        Bitmap bitmap = BitmapUtils.getBitmapFromView(mJigsawView, mJigsawView.getWidth(), mJigsawView.getHeight());
        NativeUtil.saveBitmap(bitmap, 100, path, false);
        ContentValues values = new ContentValues();
        values.put("firstImgPath", path);
        DataSupport.updateAll(WShow.class, values, "goodsStyleCode = ?", manCloth.getGoodsStyleCode());
        MyUtil.showToast("保存成功");
    }

    @Override
    public void onItemClick(View view, Object item, int position) {

    }
}
