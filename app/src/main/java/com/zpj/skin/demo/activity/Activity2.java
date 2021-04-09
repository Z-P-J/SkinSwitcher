package com.zpj.skin.demo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zpj.skin.SkinActivity;
import com.zpj.skin.SkinEngine;
import com.zpj.skin.demo.R;

public class Activity2 extends SkinActivity {

    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        mLinearLayout = findViewById(R.id.container);
        SkinEngine.setBackground(mLinearLayout, R.attr.main_bg);
    }

    public void startActivity3(View view) {
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }

    public void setDaySkin(View view) {
        SkinEngine.changeSkin(R.style.AppTheme);
    }

    public void setNightSkin(View view) {
        SkinEngine.changeSkin(R.style.AppNightTheme);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinEngine.unRegisterSkinObserver(mLinearLayout);
    }
}
