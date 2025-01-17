package com.nextone.datandroid.fragment.tabLayout.tabFragmant;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.gridlayout.widget.GridLayout;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;
import com.nextone.model.input.yard.TrafficLightModel;
import com.nextone.model.modelView.ShareModelView;

import java.util.List;

public class TabYardSignalStatusFragment extends AbsTabFragment {

    public static final int COLUMN_COUNT = 8;
    public static final int ROW_COUNT = 4;
    private GridLayout gridLayout;
    private MyImageLabel[] sensorStatus;

    private MyImageLabel traffic, traffic1;
    private final ShareModelView shareModelView;
    private final GradientDrawable background = new GradientDrawable();

    public TabYardSignalStatusFragment() {
        super(R.layout.fragment_view_yard_status);
        this.sensorStatus = new MyImageLabel[32];
        this.background.setCornerRadii(new float[]{
                34f, 34f,
                34f, 34f,
                25f, 25f,
                25f, 25f
        });
        this.shareModelView = ShareModelView.getInstance();
    }

    @Override
    public void saveData() {

    }

    @Override
    public void updateData() {

    }

    @Override
    protected void onInitViewCreated(View view) {


    }

    @Override
    protected void onInitCreateView(View view) {
        this.background.setColor(getResources().getColor(android.R.color.holo_purple, getContext().getTheme()));
        this.gridLayout = view.findViewById(R.id.gripViewSensorStatus);
        this.gridLayout.setColumnCount(COLUMN_COUNT);
        this.gridLayout.setRowCount(ROW_COUNT);
        this.gridLayout.setBackground(background);
        this.traffic = view.findViewById(R.id.lbTrafficLight);
        this.traffic1 = view.findViewById(R.id.lbTrafficLight1);
        int margin = 4;
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = 0;
        params.setMargins(margin, margin, margin, margin);
        MyImageLabel lb;
        for (int i = 0; i < 32; i++) {
            lb = new MyImageLabel(requireContext());
            sensorStatus[i] = lb;
            lb.setTextLabel("cb " + i);
            lb.setId(View.generateViewId());
            GridLayout.LayoutParams itemParams = new GridLayout.LayoutParams(params);
            itemParams.columnSpec = GridLayout.spec(i % COLUMN_COUNT, 1, 1f);
            itemParams.rowSpec = GridLayout.spec(i / COLUMN_COUNT, 1, 1f);
            lb.setLayoutParams(itemParams);
            this.gridLayout.addView(lb);
        }
        this.shareModelView.getYardModelMutableLiveData().observe(this, yardModel -> {
            if (yardModel == null) return;
            checkTrafficLight(this.traffic, yardModel.getTrafficLightModel());
            checkTrafficLight(this.traffic1, yardModel.getTrafficLightModel1());
            List<Boolean> inputs = yardModel.getInputs();
            int size = inputs.size();
            for (int i = 0; i < sensorStatus.length; i++) {
                if (i < size) {
                    sensorStatus[i].setStatus(inputs.get(i));
                }else{
                    sensorStatus[i].setStatus(false);
                }
            }
        });
    }

    private void checkTrafficLight(MyImageLabel lb, TrafficLightModel trafficLightModel) {
        lb.setStatus(true);
        lb.setOnColor(switch (trafficLightModel.getTrafficLight()) {
            case TrafficLightModel.GREEN:
                yield Color.GREEN;
            case TrafficLightModel.YELLOW:
                yield Color.YELLOW;
            default:
                yield Color.RED;
        });
        lb.setTextValue(String.valueOf(trafficLightModel.getTime()));
    }
}
