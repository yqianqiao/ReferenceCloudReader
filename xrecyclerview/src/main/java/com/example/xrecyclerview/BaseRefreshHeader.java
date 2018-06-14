package com.example.xrecyclerview;

/**
 * Created by y on 2018/6/8.
 */

public interface BaseRefreshHeader {
    //下拉刷新
    int STATE_NORMAL = 0;
    //释放刷新
    int STATE_RELEASE_TO_REFRESH = 1;
    //正在刷新
    int STATE_REFRESHING = 2;
    //刷新完成
    int STATE_DONE = 3;

    /**
     * 移动距离
     *
     * @param delta
     */
    void onMove(float delta);

    /**
     * 释放动作
     *
     * @return
     */
    boolean releaseAction();

    /**
     * 刷新完成
     */
    void refreshComplete();

    /**
     * 获取可视高度
     *
     * @return
     */
    int getVisiableHeight();

}
