package com.nextone.datandroid.customLayout.impConstrainLayout.modeView;

import android.view.View;

import com.nextone.common.ConstKey;
import com.nextone.contest.AbsContest;
import com.nextone.controller.ProcessModelHandle;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.grid.impGridLayout.CarStatusView;
import com.nextone.datandroid.customLayout.grid.impGridLayout.TestValueView;
import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.input.CarModel;
import com.nextone.model.modelTest.process.TestDataViewModel;

public class SaHinhView extends AbsModeView{

    private TestDataViewModel dataViewModel;

    private CarModel carModel;
    private CarStatusView carStatusView;
    private TestValueView testValueView;
    private MyImageLabel lbContestName;
    private MyImageLabel lbCb1;
    private MyImageLabel lbCb2;
    private MyImageLabel lbCb3;
    public SaHinhView() {
        super(R.layout.view_mode_sa_hinh);
    }


    @Override
    protected void onInitCreateView(View view) {
        view.findViewById(R.id.btStart).setOnClickListener(v -> {
            this.getCarModel().setRemoteValue(ConstKey.KEY_BOARD.CONTEST.XP);
        });
        view.findViewById(R.id.btEndTest).setOnClickListener(v -> {
            this.getCarModel().setRemoteValue(ConstKey.KEY_BOARD.CONTEST.KT);
        });
        this.lbContestName = view.findViewById(R.id.myImageLabel);
        this.lbContestName.setTextLabel("Bài thi hiện tại");
        this.carStatusView = view.findViewById(R.id.carStatusView);
        this.testValueView = view.findViewById(R.id.testValueView);
        this.lbCb1 = view.findViewById(R.id.lbT1);
        this.lbCb1.setTextLabel("Cảm biến 1");
        this.lbCb2 = view.findViewById(R.id.lbT2);
        this.lbCb2.setTextLabel("Cảm biến 2");
        this.lbCb3 = view.findViewById(R.id.lbT3);
        this.lbCb3.setTextLabel("Cảm biến 3");

    }

    private CarModel getCarModel() {
        if (this.carModel == null) {
            this.carModel = MCUSerialHandler.getInstance().getModel();
        }
        return this.carModel;
    }

    private TestDataViewModel getDataViewModel() {
        if (this.dataViewModel == null) {
            this.dataViewModel = ProcessModelHandle.getInstance().getTestDataModel();
        }
        return this.dataViewModel;
    }

    @Override
    protected void updateUI() {
        TestDataViewModel testDataViewModel = getDataViewModel();
        if (testDataViewModel != null) {
            AbsContest contest = testDataViewModel.getContest();
            if(contest != null){
                this.lbContestName.setTextValue(contest.getName());
            }else{
                this.lbContestName.setTextValue("");
            }
            this.carStatusView.update();
            this.testValueView.update();
        }
        CarModel carModel = getCarModel();
        if (carModel != null) {
            this.lbCb1.setStatus(carModel.isT1());
            this.lbCb2.setStatus(carModel.isT2());
            this.lbCb3.setStatus(carModel.isT3());
        }

    }

    @Override
    public void showModeName(String fullName) {

    }
}
