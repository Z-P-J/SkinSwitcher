package com.zpj.skin.demo.activity;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.zpj.skin.SkinActivity;
import com.zpj.skin.SkinEngine;
import com.zpj.skin.demo.R;
import com.zpj.skin.demo.fragment.Fragment1;
import com.zpj.skin.demo.fragment.Fragment2;
import com.zpj.skin.demo.fragment.Fragment4;

import java.util.ArrayList;


public class Activity3 extends SkinActivity {

    private ArrayList<Fragment> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        mData.add(new Fragment1());
        mData.add(new Fragment2());
        mData.add(new Fragment4());
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
    }

    public void setDaySkin(View view){
        SkinEngine.changeSkin(R.style.AppTheme);
    }

    public void setNightSkin(View view){
        SkinEngine.changeSkin(R.style.AppNightTheme);
    }


    public class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getCount() {
            return mData.size();
        }


    }

}
