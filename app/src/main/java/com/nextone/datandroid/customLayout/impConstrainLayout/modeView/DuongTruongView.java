package com.nextone.datandroid.customLayout.impConstrainLayout.modeView;

import android.view.View;

import androidx.annotation.NonNull;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.grid.impGridLayout.CarStatusView;
import com.nextone.datandroid.customLayout.grid.impGridLayout.DuongTruongControlView;
import com.nextone.datandroid.customLayout.grid.impGridLayout.TestValueView;

public class DuongTruongView extends AbsModeView {
    private CarStatusView carStatusView;
    private TestValueView testValueView;
    private DuongTruongControlView duongTruongControlView;

    public DuongTruongView() {
        super(R.layout.view_mode_duong_truong);
    }

    protected void onInitCreateView(@NonNull View view) {
        this.carStatusView = view.findViewById(R.id.carStatusView);
        this.testValueView = view.findViewById(R.id.testValueView);
        this.duongTruongControlView = view.findViewById(R.id.duongTruongControlView);
    }


    protected void updateUI() {
        this.carStatusView.update();
        this.testValueView.update();
        this.duongTruongControlView.update();
    }

    @Override
    public void showModeName(String fullName) {

    }
}
