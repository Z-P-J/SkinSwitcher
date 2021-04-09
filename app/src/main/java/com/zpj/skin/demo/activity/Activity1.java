package com.zpj.skin.demo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zpj.skin.SkinActivity;
import com.zpj.skin.SkinEngine;
import com.zpj.skin.demo.R;

public class Activity1 extends SkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);

    }

    public void startActivity2(View view){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    public void setDaySkin(View view){
        SkinEngine.changeSkin(R.style.AppTheme);
    }

    public void setNightSkin(View view){
        SkinEngine.changeSkin(R.style.AppNightTheme);
    }
}
