package com.dx.jxty.ui;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dx.jxty.R;
import com.dx.jxty.base.BaseActivity;
import com.dx.jxty.ui.fragment.ManFragment;
import com.dx.jxty.ui.fragment.SettingFragment;
import com.dx.jxty.ui.fragment.WomanFragment;
import com.dx.jxty.utils.PermissionHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dongxue on 2017/10/16.
 */

public class HomeActivity extends BaseActivity {
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_content, new ManFragment());
        ft.commit();
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.icon_boy, "男装").setActiveColor(R.color.bluedodger))
                .addItem(new BottomNavigationItem(R.mipmap.icon_girl, "女装").setActiveColor(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.mipmap.icon_setting, "设置").setActiveColor(R.color.green_blackish))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (position) {
                    case 0:
                        ft.replace(R.id.fl_content, new ManFragment());
                        break;
                    case 1:
                        ft.replace(R.id.fl_content, new WomanFragment());
                        break;
                    case 2:
                        ft.replace(R.id.fl_content, new SettingFragment());
                        break;
                }
                ft.commit();
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    @Override
    protected void initData() {

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
