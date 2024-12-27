package com.nextone.datandroid.customLayout.impConstrainLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.nextone.datandroid.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.nextone.datandroid.customLayout.AbsCustomConstraintLayout;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.AbsModeView;

public class BaseModeLayout extends AbsCustomConstraintLayout {
    public BaseModeLayout(@NonNull Context context) {
        super(context);
        init(R.layout.mode, true);
    }

    public BaseModeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(R.layout.mode, true);
    }

    public BaseModeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(R.layout.mode, true);
    }

    public void setModeView(AbsModeView modeView){
        FrameLayout frameLayout = findViewById(R.id.boardMain);
        frameLayout.removeAllViews();
        frameLayout.addView(modeView);
    }

}
