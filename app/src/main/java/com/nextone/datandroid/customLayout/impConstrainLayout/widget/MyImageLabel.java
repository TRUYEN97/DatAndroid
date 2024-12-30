package com.nextone.datandroid.customLayout.impConstrainLayout.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.AbsCustomConstraintLayout;

import lombok.Setter;


@Setter
public class MyImageLabel extends AbsCustomConstraintLayout {
    private int onColor = android.R.color.holo_orange_light;
    private int offColor = android.R.color.white;

    private ConstraintLayout backGroundLayout;
    private ConstraintLayout backGroundLayout1;
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

    private final GradientDrawable imgLabelBackground = new GradientDrawable();
    private final GradientDrawable background = new GradientDrawable();
    private final GradientDrawable background1 = new GradientDrawable();


    private void initView() {
        //////////////////////////
        this.background.setColor(0x3fD0CDCD);
        this.background.setCornerRadii(new float[]{
                34f, 34f,
                34f, 34f,
                25f, 25f,
                25f, 25f
        });
        ///////////////////////
        this.background1.setColor(0x3fD0CDCD);
        this.background1.setCornerRadii(new float[]{
                34f, 34f,
                34f, 34f,
                25f, 25f,
                25f, 25f
        });
        ///////////////////////
        this.imgLabelBackground.setColor(getResources().getColor(offColor));
        this.imgLabelBackground.setCornerRadius(25f);
        this.imgLabelBackground.setStroke(2, getResources().getColor(android.R.color.holo_blue_light));
        ///////////////////////
        this.backGroundLayout = findViewById(R.id.background);
        this.backGroundLayout.setBackground(background);
        this.backGroundLayout1 = findViewById(R.id.background1);
        this.backGroundLayout1.setBackground(background1);
        this.iconLayout = findViewById(R.id.relativeLayout);
        this.iconLayout.setBackground(imgLabelBackground);
        this.imageView = findViewById(R.id.imageView);
        this.textValue = findViewById(R.id.textValue);
        this.textValue.setTextSize(24);
        this.textLabel = findViewById(R.id.textLabel);
        this.textLabel.setTextSize(8);
        this.textLabel.setTextColor(Color.WHITE);
        setStatus(false);
    }

    public void setStrokeColor(int color) {
        if (color < 0) return;
        imgLabelBackground.setStroke(2, getResources().getColor(color));
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
