package com.nextone.datandroid.customLayout.grid.impGridLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.grid.AbsGridLayoutView;
import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;
import com.nextone.model.modelTest.process.TestDataViewModel;
import com.nextone.controller.ProcessModelHandle;
import com.nextone.contest.AbsContest;

public class TestValueView extends AbsGridLayoutView {
    private MyImageLabel s;
    private MyImageLabel v;
    private MyImageLabel tt;
    private MyImageLabel t;
    private MyImageLabel score;
    private MyImageLabel rpm;

    private TestDataViewModel dataViewModel;

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
                android.R.color.transparent);
        this.dataViewModel = ProcessModelHandle.getInstance().getTestDataModel();
        this.s = findViewById(R.id.s);
        this.s.setTextLabel("Quãng đường");
        this.v = findViewById(R.id.v);
        this.v.setTextLabel("Vận tốc");
        this.tt = findViewById(R.id.tt);
        this.tt.setTextLabel("Tổng thời gian");
        this.t = findViewById(R.id.t);
        this.t.setTextLabel("Thời gian");
        this.score = findViewById(R.id.score);
        this.score.setTextLabel("Điểm");
        this.rpm = findViewById(R.id.rpm);
        this.rpm.setTextLabel("Vòng tua");
    }

    @SuppressLint("DefaultLocale")
    public void update() {
        if (carModel == null) {
            return;
        }
        this.s.setTextValue(String.format("%.1f", carModel.getDistance()));
        this.v.setTextValue(String.format("%.1f", carModel.getSpeed()));
        this.rpm.setTextValue(String.valueOf(carModel.getRpm()));
        if(this.dataViewModel == null) {
            return;
        }
        this.score.setTextValue(String.valueOf(this.dataViewModel.getScore()));
        if (this.dataViewModel.getTestTime() == 0){
            this.tt.setTextValue("0");
        }else{
            long t = this.dataViewModel.getTestTime() / 1000;
            long m = t / 60;
            long s = t - (m * 60);
            this.tt.setTextValue(String.format("%d:%d", m, s));
        }
        AbsContest contest = this.dataViewModel.getContest();
        if (contest != null) {
            long t = contest.getTestTime() / 1000;
            long m = t / 60;
            long s = t - (m * 60);
            this.t.setTextValue(String.format("%d:%d", m, s));
        }else{
            this.t.setTextValue("0");
        }
    }
}
