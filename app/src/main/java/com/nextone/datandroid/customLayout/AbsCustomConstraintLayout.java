package com.nextone.datandroid.customLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.constraintlayout.widget.ConstraintLayout;

public abstract class AbsCustomConstraintLayout extends ConstraintLayout {
    public AbsCustomConstraintLayout(Context context) {
        super(context);
    }

    public AbsCustomConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsCustomConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void init(@LayoutRes int resource, boolean attachToRoot) {
        // Inflate layout
        LayoutInflater.from(getContext()).inflate(resource, this, attachToRoot);
    }

    protected void init(@LayoutRes int resource, ViewGroup root, boolean attachToRoot) {
        // Inflate layout
        LayoutInflater.from(getContext()).inflate(resource, root, attachToRoot);
    }
}
