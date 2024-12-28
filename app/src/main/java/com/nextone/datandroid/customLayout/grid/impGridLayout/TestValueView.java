package com.nextone.datandroid.customLayout.grid.impGridLayout;

import android.content.Context;
import android.util.AttributeSet;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.grid.AbsGridLayoutView;
import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;

public class TestValueView extends AbsGridLayoutView {
    private MyImageLabel s;
    private MyImageLabel v;
    private MyImageLabel tt;
    private MyImageLabel t;
    private MyImageLabel score;
    private MyImageLabel rpm;

    public TestValueView(Context context) {
        super(context);
        init();
    }

    public TestValueView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestValueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TestValueView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        super.init(2, 3,
                R.layout.test_value_view,
                R.id.testValue,
                android.R.color.holo_purple);
        this.s = findViewById(R.id.s);
        this.s.setTextLabel("S");
        this.v = findViewById(R.id.v);
        this.v.setTextLabel("V");
        this.tt = findViewById(R.id.tt);
        this.tt.setTextLabel("TT");
        this.t = findViewById(R.id.t);
        this.t.setTextLabel("T");
        this.score = findViewById(R.id.score);
        this.score.setTextLabel("Điểm");
        this.rpm = findViewById(R.id.rpm);
        this.rpm.setTextLabel("RPM");
    }

    public void update() {
        if (carModel == null) {
            return;
        }
        this.s.setTextValue(String.valueOf(carModel.getDistance()));
        this.v.setTextValue(String.valueOf(carModel.getSpeed()));
        this.rpm.setTextValue(String.valueOf(carModel.getRpm()));
    }
}
