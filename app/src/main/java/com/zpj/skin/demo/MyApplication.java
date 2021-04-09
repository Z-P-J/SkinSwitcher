package com.zpj.skin.demo;

import android.app.Application;

import com.zpj.skin.SkinEngine;
import com.zpj.skin.demo.skinapplicator.SkinCustomViewApplicator;
import com.zpj.skin.demo.widget.CustomView;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinEngine.changeSkin(R.style.AppTheme);
        SkinEngine.registerSkinApplicator(CustomView.class, new SkinCustomViewApplicator());
    }
}
