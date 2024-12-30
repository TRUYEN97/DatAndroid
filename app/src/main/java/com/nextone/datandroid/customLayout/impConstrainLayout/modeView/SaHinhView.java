package com.nextone.datandroid.customLayout.impConstrainLayout.modeView;

import android.content.Context;
import android.util.AttributeSet;

import com.nextone.controller.ProcessModelHandle;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.grid.impGridLayout.CarStatusView;
import com.nextone.datandroid.customLayout.grid.impGridLayout.TestValueView;
import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.modelTest.process.TestDataViewModel;
import com.nextone.contest.AbsContest;
import com.nextone.common.ConstKey;

public class SaHinhView extends AbsModeView{

    private TestDataViewModel dataViewModel;
    private CarStatusView carStatusView;
    private TestValueView testValueView;
    private MyImageLabel myImageLabel;
    public SaHinhView(Context context) {
        super(context, R.layout.sa_hinh, true);
    }

    public SaHinhView(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.sa_hinh, true);
    }

    public SaHinhView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, R.layout.sa_hinh, true);
    }

    @Override
    protected void init(int resource, boolean attachToRoot) {
        super.init(resource, attachToRoot);
        findViewById(R.id.btStart).setOnClickListener(v -> {
            MCUSerialHandler.getInstance().getModel().setRemoteValue(ConstKey.KEY_BOARD.CONTEST.XP);
        });
        findViewById(R.id.btEndTest).setOnClickListener(v -> {
            MCUSerialHandler.getInstance().getModel().setRemoteValue(ConstKey.KEY_BOARD.CONTEST.KT);
        });
        this.dataViewModel = ProcessModelHandle.getInstance().getTestDataModel();
        this.myImageLabel = findViewById(R.id.myImageLabel);
        this.carStatusView = findViewById(R.id.carStatusView);
        this.testValueView = findViewById(R.id.testValueView);
    }

    @Override
    protected void updateUI() {
        if (this.dataViewModel == null) {
            return;
        }
        AbsContest contest = this.dataViewModel.getContest();
        if(contest != null){
            this.myImageLabel.setTextValue(contest.getName());
        }else{
            this.myImageLabel.setTextValue("");
        }
        this.carStatusView.update();
        this.testValueView.update();
    }
}
