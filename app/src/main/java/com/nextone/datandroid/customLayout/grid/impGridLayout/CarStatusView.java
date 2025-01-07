package com.nextone.datandroid.customLayout.grid.impGridLayout;

import android.content.Context;
import android.util.AttributeSet;

import com.nextone.common.ConstKey;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.grid.AbsGridLayoutView;
import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;

public class CarStatusView extends AbsGridLayoutView {
    public static final String STOP = "Dừng";
    public static final String FORWARD = "Tiến";
    public static final String BACKWARD = "Lùi";
    private MyImageLabel nt;
    private MyImageLabel np;
    private MyImageLabel cm;
    private MyImageLabel pt;
    private MyImageLabel at;
    private MyImageLabel s1;
    private MyImageLabel s2;
    private MyImageLabel s3;
    private MyImageLabel s4;
    private MyImageLabel grear;
    public CarStatusView(Context context) {
        super(context);
        init();
    }

    public CarStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CarStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CarStatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        super.init(2,5,R.layout.view_car_status,R.id.carStatus,android.R.color.transparent);
        this.nt = findViewById(R.id.nt);
        this.nt.setImage(R.drawable.nt);
        this.nt.setTextLabel("Xi nhan trái");
        this.np = findViewById(R.id.np);
        this.np.setImage(R.drawable.np);
        this.np.setTextLabel("Xi nhan phải");
        this.cm = findViewById(R.id.cm);
        this.cm.setImage(R.drawable.cm);
        this.cm.setTextLabel("Chết máy");
        this.pt = findViewById(R.id.pt);
        this.pt.setImage(R.drawable.pt);
        this.pt.setTextLabel("Phanh tay");
        this.at = findViewById(R.id.at);
        this.at.setImage(R.drawable.at);
        this.at.setTextLabel("Dây an toàn");
        this.s1 = findViewById(R.id.s1);
        this.s1.setImage(R.drawable.point);
        this.s1.setTextLabel("S1");
        this.s2 = findViewById(R.id.s2);
        this.s2.setImage(R.drawable.point);
        this.s2.setTextLabel("S2");
        this.s3 = findViewById(R.id.s3);
        this.s3.setImage(R.drawable.point);
        this.s3.setTextLabel("S3");
        this.s4 = findViewById(R.id.s4);
        this.s4.setImage(R.drawable.point);
        this.s4.setTextLabel("S4");
        this.grear = findViewById(R.id.grear);
        this.grear.setTextLabel(STOP);
    }

    public void update(){
        if(carModel == null){
            return;
        }
        this.nt.setStatus(carModel.isNt());
        this.np.setStatus(carModel.isNp());
        this.cm.setStatus(carModel.isCm());
        this.pt.setStatus(carModel.isPt());
        this.at.setStatus(carModel.isAt());
        this.s1.setStatus(carModel.isS1());
        this.s2.setStatus(carModel.isS2());
        this.s3.setStatus(carModel.isS3());
        this.s4.setStatus(carModel.isS4());
        this.grear.setTextValue(String.valueOf(carModel.getGearBoxValue()));
        switch (carModel.getStatus()) {
            case ConstKey.CAR_ST.STOP:
                this.grear.setTextLabel(STOP);
                break;
            case ConstKey.CAR_ST.FORWARD:
                this.grear.setTextLabel(FORWARD);
                break;
            case ConstKey.CAR_ST.BACKWARD:
                this.grear.setTextLabel(BACKWARD);
                break;
        }
    }
}
