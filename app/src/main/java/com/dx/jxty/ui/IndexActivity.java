package com.dx.jxty.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dx.jxty.R;
import com.dx.jxty.adapter.ManClothAdapter;
import com.dx.jxty.base.BaseActivity;
import com.dx.jxty.bean.ClothSingleStytle;
import com.dx.jxty.bean.ManCloth;
import com.dx.jxty.db.ExtraDBManager;
import com.dx.jxty.utils.ImageCompressUtil;
import com.dx.jxty.utils.MyUtil;
import com.dx.jxty.utils.PermissionHelper;
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

public class IndexActivity extends BaseActivity implements SuperBaseAdapter.OnItemClickListener {

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

    private List<ClothSingleStytle> manClothList = new ArrayList<>();
    private ManClothAdapter manClothAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    manClothAdapter = new ManClothAdapter(context, manClothList);
                    superRecycleView.setAdapter(manClothAdapter);
                    manClothAdapter.setOnItemClickListener(IndexActivity.this);
                    tvSearchResult.setText("共有到" + manClothList.size() + "件商品");
                    break;
            }
        }
    };

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
//        manClothList = DataSupport.findAll(ManCloth.class);
//        if (manClothList != null && manClothList.size() > 0) {
//            handler.sendEmptyMessage(100);
//        }
    }

    @Override
    public void onItemClick(View view, Object item, int position) {
        InfoManDetailActivity.actionStart(context, ((ManCloth) item).getGoodsStyleCode());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
                    int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    actualimagecursor.moveToFirst();
                    String img_path = actualimagecursor.getString(actual_image_column_index);
                    File file = new File(img_path);
                    MyUtil.i("---File_path---" + img_path);
                    ExtraDBManager.INSTANCE.readExtraExcelToDB(context, file.toString());
//                    manClothList = DataSupport.findAll(ManCloth.class);
                    handler.sendEmptyMessage(100);
                }
                break;
        }
    }

    @OnClick({R.id.tv_test, R.id.btn_get_file, R.id.btn_compress, R.id.btn_delete,
            R.id.iv_scan, R.id.iv_delete, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_test:
                ExtraDBManager.INSTANCE.readLocalExcelToDB(context);
//                manClothList = DataSupport.findAll(ManCloth.class);
                handler.sendEmptyMessage(100);
                break;
            case R.id.btn_get_file:
                getFilePath();
                break;
            case R.id.btn_compress:
                ImageCompressUtil.compressImg();
                MyUtil.showToast("压缩成功");
                break;
            case R.id.btn_delete:
                DataSupport.deleteAll(ManCloth.class);
//                manClothList = DataSupport.findAll(ManCloth.class);
                handler.sendEmptyMessage(100);
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
//        manClothList = DataSupport.where("goodsStyleCode like?", "%" + result + "%").find(ManCloth.class);
        if (manClothList != null && manClothList.size() > 0) {
            handler.sendEmptyMessage(100);
            tvSearchResult.setText("共搜索到" + manClothList.size() + "件商品");
        } else {
            tvSearchResult.setText("没有搜索到款号为" + result + "的商品");
        }
    }

    String[] permissionsCheck = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    @Override
    protected void onResume() {
        super.onResume();
        PermissionHelper permissionHelper = new PermissionHelper(this, permissionsCheck, 100);
        boolean b = permissionHelper.checkSelfPermission(permissionsCheck);//判断权限授权状态
        //如果没有获取到权限,则尝试获取权限
        if (!b) {
            permissionHelper.request(new PermissionHelper.PermissionCallback() {
                @Override
                public void onPermissionGranted() {
                }

                @Override
                public void onPermissionDenied() {
                }

                @Override
                public void onPermissionDeniedBySystem() {

                }
            });
        } else {

        }
    }
}
