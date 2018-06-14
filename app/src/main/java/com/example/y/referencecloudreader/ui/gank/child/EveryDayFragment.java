package com.example.y.referencecloudreader.ui.gank.child;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.y.referencecloudreader.R;
import com.example.y.referencecloudreader.base.BaseFragment;
import com.example.y.referencecloudreader.databinding.FragmentEveryDayBinding;

/**
 * 每日推荐
 * A simple {@link Fragment} subclass.
 */
public class EveryDayFragment extends BaseFragment<FragmentEveryDayBinding> {


    public EveryDayFragment() {
        // Required empty public constructor
    }



    @Override
    protected int setContent() {
        return R.layout.fragment_every_day;
    }

}
