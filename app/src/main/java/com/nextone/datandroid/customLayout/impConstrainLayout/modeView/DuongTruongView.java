package com.nextone.datandroid.customLayout.impConstrainLayout.modeView;

import android.content.Context;
import android.util.AttributeSet;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.grid.impGridLayout.CarStatusView;
import com.nextone.datandroid.customLayout.grid.impGridLayout.DuongTruongControlView;
import com.nextone.datandroid.customLayout.grid.impGridLayout.TestValueView;

public class DuongTruongView extends AbsModeView {
    private CarStatusView carStatusView;
    private TestValueView testValueView;
    private DuongTruongControlView duongTruongControlView;

    public DuongTruongView(Context context) {
        super(context, R.layout.duong_truong, true);
        init();
    }

    public DuongTruongView(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.duong_truong, true);
        init();
    }

    public DuongTruongView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, R.layout.duong_truong, true);
        init();
    }

    private void init() {
        this.carStatusView = findViewById(R.id.carStatusView);
        this.testValueView = findViewById(R.id.testValueView);
        this.duongTruongControlView = findViewById(R.id.duongTruongControlView);
    }


    @Override
    protected void updateUI() {
        this.carStatusView.update();
        this.testValueView.update();
        this.duongTruongControlView.update();
    }
}
