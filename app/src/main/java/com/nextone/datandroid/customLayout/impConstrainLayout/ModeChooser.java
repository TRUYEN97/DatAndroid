package com.nextone.datandroid.customLayout.impConstrainLayout;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.AbsCustomConstraintLayout;

public class ModeChooser extends AbsCustomConstraintLayout {

    public ModeChooser(@NonNull Context context) {
        super(context);
        init(R.layout.chosse_mode, true);
    }

    public ModeChooser(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(R.layout.chosse_mode, true);
    }

    public ModeChooser(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(R.layout.chosse_mode, true);
    }

    public void setDTOnClickListener(@Nullable OnClickListener l){
        findViewById(R.id.btDT).setOnClickListener(l);
    }
    public void setSHOnClickListener(@Nullable OnClickListener l){
        findViewById(R.id.btSh).setOnClickListener(l);
    }
}
