package com.dx.jxty.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
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
import com.dx.jxty.base.BaseFragment;
import com.dx.jxty.bean.MShow;
import com.dx.jxty.db.ClothDBManager;
import com.dx.jxty.ui.InfoManDetailActivity;
import com.dx.jxty.ui.ScanActivity;
import com.dx.jxty.utils.MyUtil;
import com.dx.jxty.utils.StringUtil;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zxing.activity.CaptureActivity.RESULT_QRCODE_STRING;

/**
 * Created by dongxue on 2017/10/16.
 */

public class ManFragment extends BaseFragment implements SuperBaseAdapter.OnItemClickListener, SuperRecyclerView.LoadingListener {

    @BindView(R.id.super_recycle_view)
    SuperRecyclerView superRecycleView;

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

    private List<MShow> manClothList = new ArrayList<>();
    private ManClothAdapter manClothAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void initView() {
        superRecycleView.setLayoutManager(new LinearLayoutManager(context));
        superRecycleView.setLoadingListener(this);
        superRecycleView.setLoadMoreEnabled(false);
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
        manClothList = DataSupport.findAll(MShow.class);
        if (manClothList != null && manClothList.size() > 0) {
            updateData();
        } else {
            manClothList = new ArrayList<>();
            updateData();
        }
    }

    @Override
    public void onItemClick(View view, Object item, int position) {
        InfoManDetailActivity.actionStart(context, ((MShow) item).getGoodsStyleCode());
    }


    public void getFilePath() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_TO_EXTRA_FILE);
    }

    private final int REQUEST_TO_CAMERA_SCAN = 100;//扫描
    private final int REQUEST_TO_EXTRA_FILE = 101;//打开外部文件

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
                    MyUtil.i("---File_path---" + img_path);
                    ClothDBManager.INSTANCE.readMExtraExcelToDB(context, img_path);
                    manClothList = DataSupport.findAll(MShow.class);
                    updateData();
                }
                break;
        }
    }

    @OnClick({R.id.btn_get_file, R.id.iv_scan, R.id.iv_delete, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_file:
                getFilePath();
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
        if (StringUtil.isEmpty(MShow.getSearchCode(result))) {
            manClothList = new ArrayList<>();
            tvSearchResult.setText("没有搜索到编码为" + result + "的商品");
        } else {
            manClothList = DataSupport.where("goodsStyleCode like?", "%" + MShow.getSearchCode(result) + "%").find(MShow.class);
            if (manClothList != null && manClothList.size() > 0) {
                updateData();
                tvSearchResult.setText("共搜索到" + manClothList.size() + "款商品");
            } else {
                tvSearchResult.setText("没有搜索到编码为" + result + "的商品");
            }
        }
    }

    private void updateData() {
        manClothAdapter = new ManClothAdapter(context, manClothList);
        superRecycleView.setAdapter(manClothAdapter);
        manClothAdapter.setOnItemClickListener(this);
        tvSearchResult.setText("共有到" + manClothList.size() + "款商品");
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                superRecycleView.completeRefresh();
            }
        }, 1500);
    }

    @Override
    public void onLoadMore() {

    }
}
