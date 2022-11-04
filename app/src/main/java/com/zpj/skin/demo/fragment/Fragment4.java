package com.zpj.skin.demo.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zpj.skin.SkinFragment;
import com.zpj.skin.demo.R;

public class Fragment4 extends SkinFragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView==null){
            mView = inflater.inflate(R.layout.fragment4, container, false);
        }
        return mView;
    }




}
