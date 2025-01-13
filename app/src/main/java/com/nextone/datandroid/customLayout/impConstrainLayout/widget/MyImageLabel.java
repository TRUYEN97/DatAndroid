package com.nextone.datandroid.customLayout.impConstrainLayout.widget;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.AbsCustomConstraintLayout;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;


@Setter
public class MyImageLabel extends AbsCustomConstraintLayout {

    public static int ON_COLOR = android.R.color.holo_orange_light;
    public static int OFF_COLOR = android.R.color.white;

    @Setter
    private Integer onColor = null;

    private Integer offColor = null;
    private TextView textValue;
    private TextView textLabel;

    private ImageView imageView;

    @Getter
    private boolean status = false;

    @Setter
    private boolean buttonMode = false;

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

    public MyImageLabel(Context context, ViewGroup parent, boolean attachToRoot) {
        super(context);
        init(R.layout.my_image_item, parent, attachToRoot);
        initView();
    }

    private final GradientDrawable imgLabelBackground = new GradientDrawable();
    private final GradientDrawable background = new GradientDrawable();
    private final GradientDrawable background1 = new GradientDrawable();

    private int getColor(@ColorRes int onColor) {
        return getResources().getColor(onColor, getContext().getTheme());
    }

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
        this.imgLabelBackground.setShape(GradientDrawable.RECTANGLE);
        this.imgLabelBackground.setColor(getColor(OFF_COLOR));
        this.imgLabelBackground.setCornerRadius(30f);
        this.imgLabelBackground.setStroke(2, getColor(android.R.color.holo_blue_light));
        ///////////////////////
        findViewById(R.id.background).setBackground(background);
        findViewById(R.id.background1).setBackground(background1);
        findViewById(R.id.relativeLayout).setBackground(imgLabelBackground);
        this.imageView = findViewById(R.id.imageView);
        this.textValue = findViewById(R.id.textValue);
        this.textValue.setTextSize(24);
        this.textLabel = findViewById(R.id.textLabel);
        this.textLabel.setTextSize(8);
        this.textLabel.setTextColor(getColor(android.R.color.white));
        setStatus(false);
    }

    public void setShape(int shape) {
        this.imgLabelBackground.setShape(shape);
    }

    public void setBGColorResource(int color) {
        background.setColor(getColor(color));
    }

    public void setBGColor(int color) {
        background.setColor(color);
    }

    public void setBG1ColorResource(int color) {
        background1.setColor(getColor(color));
    }

    public void setBG1Color(int color) {
        background1.setColor(color);
    }

    public void blink(int blinkTime){
        setStatus(!status);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> setStatus(!status), blinkTime);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(v -> {
            if (buttonMode) {
                blink(200);
            }
            if (l != null) {
                l.onClick(v);
            }
        });
    }

    public void setBGsColorResource(int color) {
        background.setColor(getColor(color));
        background.setColor(getColor(color));
    }

    public void setOnColorResource(Integer color) {
        if (color == null) {
            this.onColor = null;
        } else {
            this.onColor = getColor(color);
        }
    }

    public void setOffColor(Integer offColor) {
        this.offColor = offColor;
        if (!status)
            setStatus(false);
    }

    public void setOffColorResource(Integer color) {
        if (color == null) {
            this.offColor = null;
        } else {
            this.offColor = getColor(color);
        }
        if (!status)
            setStatus(false);
    }

    public void setBGsColor(int color) {
        background.setColor(color);
        background.setColor(color);
    }

    public void setStrokeColor(int color) {
        imgLabelBackground.setStroke(2, getColor(color));
    }

    public void setStatus(boolean status) {
        this.status = status;
        if (status) {
            imgLabelBackground.setColor(Objects.requireNonNullElseGet(onColor, () -> getColor(ON_COLOR)));
        } else {
            imgLabelBackground.setColor(Objects.requireNonNullElseGet(offColor, () -> getColor(OFF_COLOR)));
        }
    }

    public void setTextLabel(String text) {
        this.textLabel.setText(text);
    }

    public void setTextLabelColor(int color) {
        this.textLabel.setTextColor(color);
    }

    public void setTextLabelColorResource(int color) {
        this.textLabel.setTextColor(getColor(color));
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

    public void setTextValueColorResource(int color) {
        this.textValue.setTextColor(getColor(color));
    }

    public void setTextValueSize(int size) {
        this.textValue.setTextSize(size);
    }

    public void setImage(int resource) {
        this.imageView.setImageResource(resource);
    }
}
