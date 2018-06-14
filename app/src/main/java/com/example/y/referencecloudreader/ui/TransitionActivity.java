package com.example.y.referencecloudreader.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import com.bumptech.glide.Glide;
import com.example.y.referencecloudreader.MainActivity;
import com.example.y.referencecloudreader.R;
import com.example.y.referencecloudreader.app.ConstantsImageUrl;
import com.example.y.referencecloudreader.databinding.ActivityTransitionBinding;
import com.example.y.referencecloudreader.utils.CommonUtils;
import com.example.y.referencecloudreader.view.statusbar.StatusBarUtil;

import java.lang.annotation.Annotation;
import java.util.Random;

/**
 * 欢迎页
 */
public class TransitionActivity extends AppCompatActivity {
    private ActivityTransitionBinding mBinding;
    private boolean animationEnd;
    private boolean isIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transition);
        int i = new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
        //  mBinding.ivDefaultPic.setImageDrawable(CommonUtils.getDrawable(R.mipmap.img_transition_default));
        //StatusBarUtil.setTranslucentDiff(this);
        mBinding.ivDefaultPic.setImageResource(R.mipmap.img_transition_default);
        Glide.with(this)
                .load(ConstantsImageUrl.TRANSITION_URLS[i])
                .placeholder(R.mipmap.img_transition_default)
                .error(R.mipmap.img_transition_default)
                .into(mBinding.ivPic);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.ivDefaultPic.setVisibility(View.GONE);
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 3500);

        mBinding.tvJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainActivity();
            }
        });
    }

//    /**
//     * 实现监听跳转效果
//     */
//    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
//        @Override
//        public void onAnimationStart(Animation animation) {
//              animationEnd();
//        }
//
//        @Override
//        public void onAnimationEnd(Animation animation) {
//
//        }
//
//        @Override
//        public void onAnimationRepeat(Animation animation) {
//
//        }
//    };
//
//    private void animationEnd() {
//        synchronized (TransitionActivity.this) {
//            if (!animationEnd) {
//                animationEnd = true;
//                mBinding.ivPic.clearAnimation();
//                toMainActivity();
//            }
//        }
//    }

    private void toMainActivity() {
        if (isIn) {
            return;
        }
        startActivity(new Intent(this, MainActivity.class));

        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }


}
