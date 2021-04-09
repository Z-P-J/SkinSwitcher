# SkinSwitcher
Android Change Skin,  Android Night Mode, 安卓换肤，安卓夜间模式

![screenshot](static/demo.gif)

## Install

````gradle
implementation 'com.github.Z-P-J:SkinSwitcher:1.0.0'
````

## 如何使用

### 1、皮肤资源组织形式

框架中的皮肤是以主题的形式存在的，在 values 目录下建立 skins.xml，文件内容如下样例：

```<resources>

        <!--定义皮肤的全部属性，例如全局的背景色，全局的字体色等-->
       <declare-styleable name="skin_attr">
           <attr name="main_bg" format="color|reference"></attr>
           <attr name="button_bg" format="color|reference"></attr>
           <attr name="button_text_color" format="color|reference"></attr>
           <attr name="text_color" format="color|reference"></attr>
           <attr name="line_color" format="color|reference"></attr>
       </declare-styleable>

       <!--定义皮肤主题，给皮肤的属性赋值-->
       <!--白天皮肤-->
       <style name="AppTheme" parent="android:Theme.Light.NoTitleBar">
           <item name="main_bg">@color/main_bg</item>
           <item name="button_bg">@color/button_bg</item>
           <item name="button_text_color">@color/button_text_color</item>
           <item name="text_color">@color/text_color</item>
           <item name="line_color">@color/line_color</item>
       </style>

       <!--夜间皮肤-->
       <style name="AppNightTheme" parent="android:Theme.Light.NoTitleBar">
           <item name="main_bg">@color/night_main_bg</item>
           <item name="button_bg">@color/night_button_bg</item>
           <item name="button_text_color">@color/night_button_text_color</item>
           <item name="text_color">@color/night_text_color</item>
           <item name="line_color">@color/night_line_color</item>
       </style>

   </resources>
```

如果某个控件需要换肤，则该控件的一些属性的值必须引用皮肤属性，例如：

```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:orientation="vertical"
       android:background="?attr/main_bg">
       <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:textColor="?attr/text_color" />
</LinearLayout>

```

### 2、接入 SDK

将 lib 项目的代码引入到自己的项目中。

Application 初始化：

```
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //设置当前皮肤
        SkinEngine.getInstance().changeSkin(R.style.AppTheme);
    }
}
```

Activity 改造：

目的是替换 LayoutInflater 的获取，可以将自己项目的 Activity 统一继承框架的 SkinActivity。
如果不能以继承的形式，则需将 SkinActivity 内部的代码拷贝到自己项目相应的类中。

```
package com.zxy.skin.sdk;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;


/**
 * @Description: 使用方可以继承该Activity，或者将内部代码拷贝到自定义的Activity
 * @author: zhaoxuyang
 * @Date: 2019/1/31
 */
public class SkinActivity extends AppCompatActivity {

    private SkinLayoutInflater mLayoutInfalter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater();
        mLayoutInfalter.applyCurrentSkin();
        // AppCompatActivity 需要设置
        AppCompatDelegate delegate = this.getDelegate();
        if (delegate instanceof LayoutInflater.Factory2) {
            mLayoutInfalter.setFactory2((LayoutInflater.Factory2) delegate);
        }
        
        // 自己的逻辑
    }



    @Override
    public final LayoutInflater getLayoutInflater() {
        if (mLayoutInfalter == null) {
            mLayoutInfalter = new SkinLayoutInflater(this);
        }
        return mLayoutInfalter;
    }

    @Override
    public final Object getSystemService(String name) {
        if (Context.LAYOUT_INFLATER_SERVICE.equals(name)) {
            return getLayoutInflater();
        }
        return super.getSystemService(name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLayoutInfalter.destory();
    }
}

```
类似Activity，Fragment也需要做同样的操作

```
package com.zxy.skin.sdk;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;


/**
 * @Description: 使用方可以继承该Fragment，或者将内部代码拷贝到自定义的Fragment
 * @author: zhaoxuyang
 * @Date: 2019/1/31
 */
public class SkinFragment extends Fragment {


    @Override
    public void onDetach() {
        super.onDetach();
        LayoutInflater layoutInflater = getLayoutInflater();
        if(layoutInflater instanceof  SkinLayoutInflater){
            SkinLayoutInflater skinLayoutInflater = (SkinLayoutInflater) layoutInflater;
            skinLayoutInflater.destory();
        }
    }
}

```
### 3、扩展 SkinApplicator

lib 项目中只写了 SkinViewApplicator 和 SkinTextViewApplicator ，支持 background， textColor
等属性的换肤操作，其他属性的 Applicator 编写可以参照这两个以及样例中的 SkinCustomViewApplicator。
新增的 Applicator 需要注册到框架中。

```
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinEngine.changeSkin(R.style.AppTheme);
        SkinEngine.registerSkinApplicator(CustomView.class, new SkinCustomViewApplicator());

    }
}
```

### 4、代码创建的 view 如何换肤

有时候会需要在代码中动态创建view，故也提供了针对动态创建view的方案，使用如下：

```
public class MyActivity extends SkinActivity {

    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayout = new LinearLayout(this);
        setContentView(mLinearLayout);
        SkinEngine.setBackgroud(mLinearLayout, R.attr.main_bg);
        //或者 SkinEngine.applyViewAttr(mLinearLayout, "background", R.attr.main_bg);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinEngine.unRegisterSkinObserver(mLinearLayout);
    }
}
```
## TODO

1、Applicator annotation processer
