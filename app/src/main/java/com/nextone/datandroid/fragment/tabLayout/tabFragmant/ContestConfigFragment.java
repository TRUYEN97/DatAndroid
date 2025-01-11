package com.nextone.datandroid.fragment.tabLayout.tabFragmant;

import android.view.View;
import android.widget.EditText;

import com.nextone.datandroid.R;
import com.nextone.model.yardConfigMode.ContestConfig;

import lombok.Getter;
import lombok.Setter;

public class ContestConfigFragment extends AbsTabFragment{
    @Getter
    @Setter
    private ContestConfig contestConfig;
    private EditText disLine;
    private EditText disOut;
    private EditText lower;
    private EditText upper;
    private EditText sensorIndex;

    @Setter
    private SaveCallback saveCallback;

    public ContestConfigFragment() {
        super(R.layout.fragment_contest_config);
    }
    public ContestConfigFragment(ContestConfig contestConfig) {
        super(R.layout.fragment_contest_config);
        this.contestConfig = contestConfig;
    }

    @Override
    public void saveData() {
        if(contestConfig == null || disLine == null) return;
        String val;
        if (!(val = disLine.getText().toString()).isEmpty()) {
            contestConfig.setDistanceLine(Double.parseDouble(val));
        }
        if (!(val = disOut.getText().toString()).isEmpty()) {
            contestConfig.setDistanceOut(Double.parseDouble(val));
        }
        if (!(val = lower.getText().toString()).isEmpty()) {
            contestConfig.setDistanceLowerLimit(Double.parseDouble(val));
        }
        if (!(val = upper.getText().toString()).isEmpty()) {
            contestConfig.setDistanceUpperLimit(Double.parseDouble(val));
        }
        if (!(val = sensorIndex.getText().toString()).isEmpty()) {
            contestConfig.setIndexOfYardInput(Integer.parseInt(val));
        }
        if(saveCallback != null){
            saveCallback.save(contestConfig);
        }
    }

    public static interface SaveCallback{
        void save(ContestConfig config);
    }

    @Override
    public void updateData() {
        this.disLine.setText(String.valueOf(contestConfig.getDistanceLine()));
        this.disOut.setText(String.valueOf(contestConfig.getDistanceOut()));
        this.lower.setText(String.valueOf(contestConfig.getDistanceLowerLimit()));
        this.upper.setText(String.valueOf(contestConfig.getDistanceUpperLimit()));
        this.sensorIndex.setText(String.valueOf(contestConfig.getIndexOfYardInput()));
    }

    @Override
    protected void onInitViewCreated(View view) {

    }

    @Override
    protected void onInitCreateView(View view) {
        this.disLine = view.findViewById(R.id.input1);
        this.disOut = view.findViewById(R.id.input2);
        this.lower = view.findViewById(R.id.inputLower);
        this.upper = view.findViewById(R.id.inputUpper);
        this.sensorIndex = view.findViewById(R.id.input3);
        view.findViewById(R.id.btSave).setOnClickListener(v -> {
            saveData();
        });
    }
}
