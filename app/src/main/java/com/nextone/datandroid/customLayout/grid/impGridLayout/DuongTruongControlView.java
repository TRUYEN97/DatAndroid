package com.nextone.datandroid.customLayout.grid.impGridLayout;

import android.content.Context;
import android.util.AttributeSet;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.grid.AbsGridLayoutView;
import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;
import com.nextone.model.modelTest.process.TestDataViewModel;
import com.nextone.controller.ProcessModelHandle;
import com.nextone.contest.AbsContest;
import com.nextone.common.ConstKey;

public class DuongTruongControlView extends AbsGridLayoutView {

    private MyImageLabel lbXp;
    private MyImageLabel lbTt;
    private MyImageLabel lbGt;
    private MyImageLabel lbKt;

    private TestDataViewModel dataViewModel;


    public DuongTruongControlView(Context context) {
        super(context);
        init();
    }

    public DuongTruongControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DuongTruongControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DuongTruongControlView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        super.init(4, 2,
                R.layout.view_mode_duong_truong_control_board,
                R.id.duongTruongControl,
                android.R.color.transparent);
        this.dataViewModel = ProcessModelHandle.getInstance().getTestDataModel();
        findViewById(R.id.btXp).setOnClickListener(v -> {
            if (carModel == null) {
                return;
            }
            carModel.setRemoteValue(ConstKey.KEY_BOARD.CONTEST.XP);
        });
        findViewById(R.id.btTT).setOnClickListener(v -> {
            if (carModel == null) {
                return;
            }
            carModel.setRemoteValue(ConstKey.KEY_BOARD.CONTEST.TS);
        });
        findViewById(R.id.btGT).setOnClickListener(v -> {
            if (carModel == null) {
                return;
            }
            carModel.setRemoteValue(ConstKey.KEY_BOARD.CONTEST.GS);
        });
        findViewById(R.id.btKT).setOnClickListener(v -> {
            if (carModel == null) {
                return;
            }
            carModel.setRemoteValue(ConstKey.KEY_BOARD.CONTEST.KT);
        });
        this.lbXp = findViewById(R.id.lbXp);
        this.lbXp.setTextLabel("Xuất phát");
        this.lbXp.setOnColorResource(android.R.color.holo_orange_dark);
        this.lbTt = findViewById(R.id.lbTt);
        this.lbTt.setTextLabel("Tăng tốc");
        this.lbTt.setOnColorResource(android.R.color.holo_orange_dark);
        this.lbGt = findViewById(R.id.lbGt);
        this.lbGt.setTextLabel("Giảm tốc");
        this.lbGt.setOnColorResource(android.R.color.holo_orange_dark);
        this.lbKt = findViewById(R.id.lbKt);
        this.lbKt.setTextLabel("Kết thúc");
        this.lbKt.setOnColorResource(android.R.color.holo_orange_dark);
    }

    public void update() {
        if (dataViewModel == null) {
            return;
        }
        AbsContest contest = dataViewModel.getContest();
        if (contest == null) {
            this.lbXp.setStatus(false);
            this.lbTt.setStatus(false);
            this.lbGt.setStatus(false);
            this.lbKt.setStatus(false);
            return;
        }
        switch (contest.getName()) {
            case ConstKey.CONTEST_NAME.XUAT_PHAT -> {
                this.lbXp.setStatus(true);
                this.lbTt.setStatus(false);
                this.lbGt.setStatus(false);
                this.lbKt.setStatus(false);
            }
            case ConstKey.CONTEST_NAME.TANG_TOC -> {
                this.lbXp.setStatus(false);
                this.lbTt.setStatus(true);
                this.lbGt.setStatus(false);
                this.lbKt.setStatus(false);
            }
            case ConstKey.CONTEST_NAME.GIAM_TOC -> {
                this.lbXp.setStatus(false);
                this.lbTt.setStatus(false);
                this.lbGt.setStatus(true);
                this.lbKt.setStatus(false);
            }
            case ConstKey.CONTEST_NAME.KET_THUC -> {
                this.lbXp.setStatus(false);
                this.lbTt.setStatus(false);
                this.lbGt.setStatus(false);
                this.lbKt.setStatus(true);
            }
        }
    }
}
