package com.dx.jxty.ui;

import android.Manifest;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dx.jxty.R;
import com.dx.jxty.base.BaseActivity;
import com.dx.jxty.ui.fragment.ManFragment;
import com.dx.jxty.ui.fragment.WomanFragment;
import com.dx.jxty.utils.PermissionHelper;

import butterknife.BindView;

/**
 * Created by dongxue on 2017/10/16.
 */

public class HomeActivity extends BaseActivity {
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private FragmentManager fm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_content, new ManFragment());
        ft.commit();
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.back, "男").setActiveColor(R.color.bluedodger))
                .addItem(new BottomNavigationItem(R.drawable.back, "女").setActiveColor(R.color.bluedodger))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentTransaction ft = fm.beginTransaction();
                switch (position) {
                    case 0:
                        ft.replace(R.id.fl_content, new ManFragment());
                        ft.commit();
                        break;
                    case 1:
                        ft.replace(R.id.fl_content, new WomanFragment());
                        ft.commit();
                        break;
                }
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
