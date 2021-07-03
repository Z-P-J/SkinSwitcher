package com.zpj.skin.applicator;

import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;
import android.view.View;

import com.zpj.skin.Logger;
import com.zpj.skin.SkinEngine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * @Description: View 的换肤器，其他换肤器需要继承该类
 * @author: zhaoxuyang
 * @Date: 2019/1/31
 */
public class SkinViewApplicator {

    private static final String TAG = "SkinViewApplicator";

    private final HashMap<String, IAttributeApplicator<? extends View>> supportAttrs = new HashMap<>();

    private final HashMap<String, Integer> mAttrIndexMap = new HashMap<>();

    private int[] attrArr;

    private static final IAttributeApplicator<View> mBackgroundApplicator = new IAttributeApplicator<View>() {

        @Override
        public void onApply(View view, TypedArray typedArray, int typedArrayIndex) {
            view.setBackground(typedArray.getDrawable(typedArrayIndex));
        }
    };

    private static final IAttributeApplicator<View> mForegroundApplicator = new IAttributeApplicator<View>() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onApply(View view, TypedArray typedArray, int typedArrayIndex) {
            view.setForeground(typedArray.getDrawable(typedArrayIndex));
        }
    };

    public SkinViewApplicator() {
        addAttributeApplicator("background", mBackgroundApplicator);
        addAttributeApplicator("foreground", mForegroundApplicator);
    }

    protected void addAttributeApplicator(String attrName, IAttributeApplicator<? extends View> applicator) {
        supportAttrs.put(attrName, applicator);
    }

    /**
     * 换肤核心逻辑
     * @param view
     * @param attrsMap
     */
    public void apply(View view, ArrayMap<String, Integer> attrsMap) {

        if (view == null || attrsMap == null || attrsMap.size() == 0) {
            return;
        }
        view.getContext().setTheme(SkinEngine.getSkin());

        Logger.d(TAG, "------ " + view + " start apply skin");

        try {
            int index = 0;
            mAttrIndexMap.clear();
            if (attrArr == null) {
                attrArr = new int[supportAttrs.size()];
            } else {
                Arrays.fill(attrArr, 0);
            }

            Set<String> attrNameSet = supportAttrs.keySet();
            for (String attrName : attrNameSet) {
                Integer value = attrsMap.get(attrName);
                if (value != null) {
                    attrArr[index] = value;
                    mAttrIndexMap.put(attrName, index);
                    index++;
                }

            }

            //从主题中获取属性值，执行换肤
            TypedArray typedArray = view.getContext().getTheme().obtainStyledAttributes(attrArr);
            if (typedArray == null) {
                throw new Exception("typedArray is null!, view:" + view);
            }

            for (String attrName : attrNameSet) {
                Integer typedArrayIndex = mAttrIndexMap.get(attrName);
                IAttributeApplicator attributeApplicator = supportAttrs.get(attrName);
                if (typedArrayIndex != null && attributeApplicator != null) {
                    attributeApplicator.onApply(view, typedArray, typedArrayIndex);
                    Logger.d(TAG, "attrName: " + attrName);
                }
            }
            typedArray.recycle();

            Logger.d(TAG, "------ " + view + " apply skin success");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Logger.d(TAG, "------ " + view + " apply skin failed");
        }


    }


    /**
     * @Description: 每个属性的设置器
     * @author: zhaoxuyang
     * @Date: 2019/1/31
     */
    public interface IAttributeApplicator<T extends View> {

        void onApply(T view, TypedArray typedArray, int typedArrayIndex);
    }

}
