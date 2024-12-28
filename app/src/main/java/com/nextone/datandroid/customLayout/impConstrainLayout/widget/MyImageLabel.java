package com.nextone.datandroid.customLayout.impConstrainLayout.widget;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.AbsCustomConstraintLayout;

import lombok.Setter;


@Setter
public class MyImageLabel extends AbsCustomConstraintLayout {
    private int onColor = android.R.color.holo_orange_light;
    private int offColor = android.R.color.white;
    private RelativeLayout iconLayout;
    private TextView textValue;
    private TextView textLabel;
    private ImageView imageView;

    public MyImageLabel(Context context) {
        super(context);
        init(R.layout.my_image_item, true);
        initView();
    }

    public MyImageLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(R.layout.my_image_item, true);
        initView();
    }

    public MyImageLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(R.layout.my_image_item, true);
        initView();
    }

    private void initView() {
        this.iconLayout = findViewById(R.id.relativeLayout);
        this.imageView = findViewById(R.id.imageView);
        this.textValue = findViewById(R.id.textValue);
        this.textValue.setTextSize(22);
        this.textLabel = findViewById(R.id.textLabel);
        this.textLabel.setTextSize(8);
        setStatus(false);
        setStrokeColor(android.R.color.holo_blue_light);
    }

    public void setStrokeColor(int color) {
        if (color < 0) return;
        GradientDrawable background = (GradientDrawable) iconLayout.getBackground();
        background.setStroke(2, getResources().getColor(color));
    }

    public void setStatus(boolean status) {
        GradientDrawable background = (GradientDrawable) iconLayout.getBackground();
        if (status) {
            background.setColor(getResources().getColor(onColor));
        } else {
            background.setColor(getResources().getColor(offColor));
        }
    }

    public void setTextLabel(String text) {
        this.textLabel.setText(text);
    }

    public void setTextLabelColor(int color) {
        this.textLabel.setTextColor(color);
    }

    public void setTextLabelSize(int size) {
        this.textLabel.setTextSize(size);
    }

    public void setTextValue(String text) {
        this.textValue.setText(text);
    }

    public void setTextValueColor(int color) {
        this.textValue.setTextColor(color);
    }

    public void setTextValueSize(int size) {
        this.textValue.setTextSize(size);
    }

    public void setImage(int resource) {
        this.imageView.setImageResource(resource);
    }
}
