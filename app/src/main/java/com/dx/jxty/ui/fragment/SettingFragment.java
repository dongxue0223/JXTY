package com.dx.jxty.ui.fragment;

import android.view.View;

import com.dx.jxty.R;
import com.dx.jxty.base.BaseFragment;
import com.dx.jxty.bean.ImagePath;
import com.dx.jxty.bean.MShow;
import com.dx.jxty.bean.ManCloth;
import com.dx.jxty.bean.WNewStytle;
import com.dx.jxty.bean.WShow;
import com.dx.jxty.bean.WomanCloth;
import com.dx.jxty.db.ClothDBManager;
import com.dx.jxty.utils.ImageCompressUtil;
import com.dx.jxty.utils.MyUtil;

import org.litepal.crud.DataSupport;

import butterknife.OnClick;

/**
 * Created by dongxue on 2017/10/31.
 */

public class SettingFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_1, R.id.ll_2, R.id.ll_3, R.id.ll_4, R.id.ll_5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_1:
                DataSupport.deleteAll(ManCloth.class);
                DataSupport.deleteAll(MShow.class);
                DataSupport.deleteAll(ImagePath.class, "type = ?", "0");
                MyUtil.showToast("已清除");
                break;
            case R.id.ll_2:
                DataSupport.deleteAll(WomanCloth.class);
                DataSupport.deleteAll(WShow.class);
                DataSupport.deleteAll(WNewStytle.class);
                DataSupport.deleteAll(ImagePath.class, "type = ?", "1");
                MyUtil.showToast("已清除");
                break;
            case R.id.ll_3:
                ImageCompressUtil.compressImg(0);
                ImageCompressUtil.compressImg(1);
                MyUtil.showToast("压缩成功");
                break;
            case R.id.ll_4:
                MyUtil.showToast("压缩成功");
                break;
            case R.id.ll_5:
                ClothDBManager.INSTANCE.readLocalExcelToDB(context);
                ClothDBManager.INSTANCE.readWLocalExcelToDB(context);
                MyUtil.showToast("导入完成");
                break;
        }
    }
}
