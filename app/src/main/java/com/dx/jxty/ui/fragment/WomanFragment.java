package com.dx.jxty.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dx.jxty.R;
import com.dx.jxty.adapter.WomanClothAdapter;
import com.dx.jxty.base.BaseFragment;
import com.dx.jxty.bean.ImagePath;
import com.dx.jxty.bean.WNewStytle;
import com.dx.jxty.bean.WShow;
import com.dx.jxty.bean.WomanCloth;
import com.dx.jxty.db.ClothDBManager;
import com.dx.jxty.ui.InfoWomanDetailActivity;
import com.dx.jxty.ui.ScanActivity;
import com.dx.jxty.utils.ImageCompressUtil;
import com.dx.jxty.utils.MyUtil;
import com.dx.jxty.utils.StringUtil;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zxing.activity.CaptureActivity.RESULT_QRCODE_STRING;

/**
 * Created by dongxue on 2017/10/16.
 */

public class WomanFragment extends BaseFragment implements SuperBaseAdapter.OnItemClickListener {

    @BindView(R.id.super_recycle_view)
    SuperRecyclerView superRecycleView;
    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.btn_get_file)
    TextView btnGetFile;
    @BindView(R.id.btn_compress)
    TextView btnGoSearch;
    @BindView(R.id.btn_delete)
    TextView btnDelete;

    @BindView(R.id.et_search_content)
    EditText etSearchContent;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_search_result)
    TextView tvSearchResult;

    private List<WShow> manClothList = new ArrayList<>();
    private WomanClothAdapter manClothAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void initView() {
        superRecycleView.setLayoutManager(new LinearLayoutManager(context));
        superRecycleView.setRefreshEnabled(false);
        etSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivDelete.setVisibility(View.VISIBLE);
                } else {
                    ivDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {
        manClothList = DataSupport.findAll(WShow.class);
        if (manClothList != null && manClothList.size() > 0) {
            updateData();
        }
    }

    @Override
    public void onItemClick(View view, Object item, int position) {
        InfoWomanDetailActivity.actionStart(context, ((WShow) item).getGoodsStyleCode());
    }


    public void getFilePath() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_TO_EXTRA_FILE);
    }

    public static final int REQUEST_TO_CAMERA_SCAN = 100;//扫描
    public static final int REQUEST_TO_EXTRA_FILE = 101;//打开外部文件

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TO_CAMERA_SCAN:
                if (data != null) {
                    String result = data.getStringExtra(RESULT_QRCODE_STRING);
                    searchGoods(result);
                }
                break;
            case REQUEST_TO_EXTRA_FILE:
                if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
                    Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor actualimagecursor = getActivity().managedQuery(uri, proj, null, null, null);
                    int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    actualimagecursor.moveToFirst();
                    String img_path = actualimagecursor.getString(actual_image_column_index);
                    File file = new File(img_path);
                    MyUtil.i("---File_path---" + img_path);
                    ClothDBManager.INSTANCE.readWExtraExcelToDB(context, file.toString());
                    manClothList = DataSupport.findAll(WShow.class);
                    updateData();
                }
                break;
        }
    }

    @OnClick({R.id.tv_test, R.id.btn_get_file, R.id.btn_compress, R.id.btn_delete,
            R.id.iv_scan, R.id.iv_delete, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_test:
                ClothDBManager.INSTANCE.readWLocalExcelToDB(context);
                manClothList = DataSupport.findAll(WShow.class);
                updateData();
                break;
            case R.id.btn_get_file:
                getFilePath();
                break;
            case R.id.btn_compress:
                ImageCompressUtil.compressImg();
                MyUtil.showToast("压缩成功");
                break;
            case R.id.btn_delete:
                DataSupport.deleteAll(WomanCloth.class);
                DataSupport.deleteAll(WShow.class);
                DataSupport.deleteAll(WNewStytle.class);
                DataSupport.deleteAll(ImagePath.class, "type = ?", "1");
                manClothList = DataSupport.findAll(WShow.class);
                updateData();
                tvSearchResult.setText("共有到" + manClothList.size() + "件商品");
                break;

            case R.id.iv_scan:
                Intent intent = new Intent(context, ScanActivity.class);
                startActivityForResult(intent, REQUEST_TO_CAMERA_SCAN);
                break;
            case R.id.iv_delete:
                etSearchContent.setText("");
                break;
            case R.id.iv_search:
                if (StringUtil.isEmpty(etSearchContent.getText().toString())) {
                    MyUtil.showToast("搜索内容不能为空");
                } else {
                    searchGoods(etSearchContent.getText().toString());
                }
                break;
        }
    }

    private void searchGoods(String result) {
        if (StringUtil.isEmpty(WShow.getSearchCode(result))) {
            manClothList = new ArrayList<>();
            tvSearchResult.setText("没有搜索到编码为" + result + "的商品");
        } else {
            manClothList = DataSupport.where("goodsStyleCode like?", "%" + WShow.getSearchCode(result) + "%").find(WShow.class);
            if (manClothList != null && manClothList.size() > 0) {
                updateData();
                tvSearchResult.setText("共搜索到" + manClothList.size() + "款商品");
            } else {
                tvSearchResult.setText("没有搜索到编码为" + result + "的商品");
            }
        }
    }

    private void updateData() {
        manClothAdapter = new WomanClothAdapter(context, manClothList);
        superRecycleView.setAdapter(manClothAdapter);
        manClothAdapter.setOnItemClickListener(this);
        tvSearchResult.setText("共有到" + manClothList.size() + "件商品");
    }
}
