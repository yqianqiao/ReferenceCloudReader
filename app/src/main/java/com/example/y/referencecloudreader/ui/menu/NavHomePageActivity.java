package com.example.y.referencecloudreader.ui.menu;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.y.referencecloudreader.R;
import com.example.y.referencecloudreader.base.BaseActivity;
import com.example.y.referencecloudreader.databinding.ActivityNavHomePageBinding;
import com.example.y.referencecloudreader.utils.ShareUtils;
import com.example.y.referencecloudreader.view.statusbar.StatusBarUtil;

public class NavHomePageActivity extends BaseActivity<ActivityNavHomePageBinding> {

    private Toolbar toolbar;

    @Override
    protected void initView() {
        showContentView();
        mBinding.toolBar.setVisibility(View.GONE);
        StatusBarUtil.setTranslucentForImageView(this, 0, bindingView.toolbar);
        bindingView.fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.share(NavHomePageActivity.this, getString(R.string.string_share_text));
            }
        });

//        toolbar = bindingView.toolbar;
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.mipmap.icon_back);
//        }
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("aaa", "onClick: ");
//                onBackPressed();
//            }
//        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nav_home_page;
    }
}
