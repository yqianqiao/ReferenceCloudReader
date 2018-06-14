package com.example.y.referencecloudreader;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.y.referencecloudreader.databinding.ActivityMainBinding;
import com.example.y.referencecloudreader.http.rx.RxBus;
import com.example.y.referencecloudreader.http.rx.RxBusBaseMessage;
import com.example.y.referencecloudreader.http.rx.RxCodeConstants;
import com.example.y.referencecloudreader.ui.Book.BookFragment;
import com.example.y.referencecloudreader.ui.gank.GankFragment;
import com.example.y.referencecloudreader.ui.menu.NavHomePageActivity;
import com.example.y.referencecloudreader.ui.one.OneFragment;
import com.example.y.referencecloudreader.view.MyFragmentPagerAdapter;
import com.example.y.referencecloudreader.view.statusbar.StatusBarUtil;

import java.util.ArrayList;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding mBinding;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ViewPager vpContent;
    private ImageView ivTitleGank;
    private ImageView ivTitleOne;
    private ImageView ivTitleDou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initStatusView();
        initId();
        initRxBus();
        StatusBarUtil.setColorNoTranslucent(MainActivity.this
                , ContextCompat.getColor(this, R.color.colorTheme));
        initContentFragment();
        initDrawerLayout();
        initListener();
    }

    private void initListener() {
        ivTitleOne.setOnClickListener(this);
        ivTitleGank.setOnClickListener(this);
        ivTitleDou.setOnClickListener(this);
    }

    private void initDrawerLayout() {
        mBinding.navView.setNavigationItemSelectedListener(this);

    }

    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new GankFragment());
        mFragmentList.add(new OneFragment());
        mFragmentList.add(new BookFragment());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        vpContent.setAdapter(adapter);
        vpContent.setOffscreenPageLimit(2);
        vpContent.addOnPageChangeListener(this);

        mBinding.include.ivTitleGank.setSelected(true);
        vpContent.setCurrentItem(0);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);

            actionBar.setHomeButtonEnabled(true); //设置返回键可用
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void initRxBus() {
        RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE_TO_ONE, RxBusBaseMessage.class)
                .subscribe(new Action1<RxBusBaseMessage>() {
                    @Override
                    public void call(RxBusBaseMessage rxBusBaseMessage) {
                        mBinding.vpContent.setCurrentItem(1);
                    }
                });
    }

    /**
     * 初始化id
     */
    private void initId() {
        drawerLayout = mBinding.drawerLayout;

        toolbar = mBinding.include.toolbar;

        vpContent = mBinding.vpContent;

        ivTitleGank = mBinding.include.ivTitleGank;
        ivTitleOne = mBinding.include.ivTitleOne;
        ivTitleDou = mBinding.include.ivTitleDou;


    }

    /**
     * 设置状态栏高度
     */
    private void initStatusView() {
        ViewGroup.LayoutParams layoutParams = mBinding.include.viewStatus.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
        mBinding.include.viewStatus.setLayoutParams(layoutParams);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e("aaaaaa", "onPageSelected: " + position);
        switch (position) {
            case 0:
                ivTitleGank.setSelected(true);
                ivTitleOne.setSelected(false);
                ivTitleDou.setSelected(false);
                break;
            case 1:
                ivTitleOne.setSelected(true);
                ivTitleGank.setSelected(false);
                ivTitleDou.setSelected(false);
                break;
            case 2:
                ivTitleDou.setSelected(true);
                ivTitleGank.setSelected(false);
                ivTitleOne.setSelected(false);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_gank:
                if (vpContent.getCurrentItem() != 0) {
                    ivTitleGank.setSelected(true);
                    ivTitleOne.setSelected(false);
                    ivTitleDou.setSelected(false);
                    vpContent.setCurrentItem(0);
                }
                break;
            case R.id.iv_title_one:
                if (vpContent.getCurrentItem() != 1) {
                    ivTitleOne.setSelected(true);
                    ivTitleGank.setSelected(false);
                    ivTitleDou.setSelected(false);
                    vpContent.setCurrentItem(1);
                }
                break;
            case R.id.iv_title_dou:
                if (vpContent.getCurrentItem() != 2) {
                    ivTitleDou.setSelected(true);
                    ivTitleGank.setSelected(false);
                    ivTitleOne.setSelected(false);
                    vpContent.setCurrentItem(2);
                }
                break;

        }
    }

    /**
     * 侧滑菜单item
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ll_nav_homepage:
                startActivity(new Intent(this, NavHomePageActivity.class));
                mBinding.drawerLayout.closeDrawers();
                break;
        }
        return false;
    }
}
