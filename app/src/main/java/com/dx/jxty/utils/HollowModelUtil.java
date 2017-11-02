package com.dx.jxty.utils;

import android.content.Context;

import com.dx.jxty.widget.pintu.HollowModel;
import com.luck.picture.lib.tools.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongxue on 2017/11/2.
 */

public class HollowModelUtil {

    public static List<HollowModel> getFourHM(Context context) {
        List<HollowModel> hollowModelList = new ArrayList<>();
        int width = ScreenUtils.getScreenWidth(context) / 2;
        int height = ScreenUtils.getScreenHeight(context) / 2;
        int a[][] = {{0, 0}, {width, 0}, {0, height}, {width, height}};
        for (int i = 0; i < a.length; i++) {
            HollowModel hollowModel = new HollowModel();
            hollowModel.setHollowX(a[i][0]);
            hollowModel.setHollowY(a[i][1]);
            hollowModel.setWidth(width);
            hollowModel.setHeight(height);
            hollowModelList.add(hollowModel);
        }
        return hollowModelList;
    }

    public static List<HollowModel> getSixHM(Context context) {
        List<HollowModel> hollowModelList = new ArrayList<>();
        int width = ScreenUtils.getScreenWidth(context) / 3;
        int height = ScreenUtils.getScreenHeight(context) / 2;
        int a[][] = {{0, 0}, {width, 0}, {width * 2, 0}, {0, height}, {width, height}, {width * 2, height}};
        for (int i = 0; i < a.length; i++) {
            HollowModel hollowModel = new HollowModel();
            hollowModel.setHollowX(a[i][0]);
            hollowModel.setHollowY(a[i][1]);
            hollowModel.setWidth(width);
            hollowModel.setHeight(height);
            hollowModelList.add(hollowModel);
        }
        return hollowModelList;
    }

    public static List<HollowModel> getNineHM(Context context) {
        List<HollowModel> hollowModelList = new ArrayList<>();
        int width = ScreenUtils.getScreenWidth(context) / 3;
        int height = ScreenUtils.getScreenHeight(context) / 3;
        int a[][] = {{0, 0}, {width, 0}, {width * 2, 0},
                {0, height}, {width, height}, {width * 2, height},
                {0, height * 2}, {width, height * 2}, {width * 2, height * 2}

        };
        for (int i = 0; i < a.length; i++) {
            HollowModel hollowModel = new HollowModel();
            hollowModel.setHollowX(a[i][0]);
            hollowModel.setHollowY(a[i][1]);
            hollowModel.setWidth(width);
            hollowModel.setHeight(height);
            hollowModelList.add(hollowModel);
        }
        return hollowModelList;
    }

}
