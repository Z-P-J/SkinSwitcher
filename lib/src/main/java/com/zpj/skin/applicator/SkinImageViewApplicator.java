package com.zpj.skin.applicator;


import android.content.res.TypedArray;
import android.graphics.Color;
import android.widget.ImageView;

public class SkinImageViewApplicator extends SkinViewApplicator {

    private static final String TAG = "SkinImageViewApplicator";

    public SkinImageViewApplicator() {
        super();
        addAttributeApplicator("tint", new IAttributeApplicator<ImageView>(){

            @Override
            public void onApply(ImageView view, TypedArray typedArray, int typedArrayIndex) {
                view.setColorFilter(typedArray.getColor(typedArrayIndex, Color.TRANSPARENT));
            }
        });

    }

}


