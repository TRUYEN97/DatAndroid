package com.nextone.datandroid.customLayout.impConstrainLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.AbsCustomConstraintLayout;

public class ModeChooserView extends AbsCustomConstraintLayout {


    public ModeChooserView(@NonNull Context context) {
        super(context);
        init(R.layout.chosse_mode, true);
    }

    public ModeChooserView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(R.layout.chosse_mode, true);
    }

    public ModeChooserView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(R.layout.chosse_mode, true);
    }


    public void setBtDtOnclick(View.OnClickListener listener){
        findViewById(R.id.btDT).setOnClickListener(listener);
    }

    public void setBtShOnclick(View.OnClickListener listener){
        findViewById(R.id.btSh).setOnClickListener(listener);
    }
}
