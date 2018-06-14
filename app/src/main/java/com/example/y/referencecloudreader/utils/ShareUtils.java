package com.example.y.referencecloudreader.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by y on 2018/6/8.
 */

public class ShareUtils {


    public static void share(Context context,String extraText){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"分享");
        intent.putExtra(Intent.EXTRA_TEXT,extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent,"分享"));
    }
}
