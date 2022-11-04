package com.zpj.skin;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.LayoutInflater;


/**
 * @Description: 使用方可以继承该Activity，或者将内部代码拷贝到自定义的Activity
 * @author: zhaoxuyang
 * @Date: 2019/1/31
 */
public class SkinActivity extends AppCompatActivity {

    private SkinLayoutInflater layoutInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater();
        layoutInflater.applyCurrentSkin();
        // AppCompatActivity 需要设置
        AppCompatDelegate delegate = this.getDelegate();
        if (delegate instanceof LayoutInflater.Factory2) {
            layoutInflater.setFactory2((LayoutInflater.Factory2) delegate);
        }
    }


    @NonNull
    @Override
    public final LayoutInflater getLayoutInflater() {
        if (layoutInflater == null) {
            layoutInflater = new SkinLayoutInflater(this);
        }
        return layoutInflater;
    }

    @Override
    public final Object getSystemService(@NonNull String name) {
        if (Context.LAYOUT_INFLATER_SERVICE.equals(name)) {
            return getLayoutInflater();
        }
        return super.getSystemService(name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        layoutInflater.destory();
    }
}
