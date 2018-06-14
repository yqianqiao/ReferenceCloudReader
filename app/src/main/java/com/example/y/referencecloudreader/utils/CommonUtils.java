package com.example.y.referencecloudreader.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.example.y.referencecloudreader.app.ReferenceCloudReaderApplication;

import java.util.Random;

/**
 * Created by y on 2018/5/29.
 */

public class CommonUtils {
    /**
     * 随机颜色
     */
    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(150) + 50;
        int green = random.nextInt(150) + 50;
        int blue = random.nextInt(150) + 50;
        return Color.rgb(red, green, blue);
    }

    public static Drawable getDrawable(int resId){
        return getResources().getDrawable(resId);
    }

    public static Resources getResources(){
        return ReferenceCloudReaderApplication.getInstance().getResources();
    }
    public static int getColor(int resid) {
        return getResources().getColor(resid);
    }
}
