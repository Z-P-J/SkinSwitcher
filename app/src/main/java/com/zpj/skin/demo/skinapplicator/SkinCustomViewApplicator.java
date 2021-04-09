package com.zpj.skin.demo.skinapplicator;

import android.content.res.TypedArray;
import android.graphics.Color;

import com.zpj.skin.applicator.SkinViewApplicator;
import com.zpj.skin.demo.widget.CustomView;

public class SkinCustomViewApplicator extends SkinViewApplicator {

    public SkinCustomViewApplicator() {
        super();
        addAttributeApplicator("lineColor", new IAttributeApplicator<CustomView>() {
            @Override
            public void onApply(CustomView view, TypedArray typedArray, int typedArrayIndex) {
                view.setLineColor(typedArray.getColor(typedArrayIndex, Color.BLACK));
            }
        });
    }
}
