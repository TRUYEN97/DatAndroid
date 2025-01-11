package com.nextone.datandroid.fragment.tabLayout.tabFragmant;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.nextone.common.CarConfig;
import com.nextone.datandroid.R;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.config.MCU_CONFIG_MODEL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SensorSettingFragment#} factory method to
 * create an instance of this fragment.
 */
public class SensorSettingFragment extends AbsTabFragment {

    private final CarConfig carConfig;
    private final String TAG = "SensorSettingFragment";
    private EditText inputEncoder;
    private EditText inputRpm;
    private EditText inputNtpDelayTime;

    public SensorSettingFragment() {
        // Required empty public constructor
        super(R.layout.setting_sensor);
        carConfig = CarConfig.getInstance();
    }

    @Override
    public void saveData() {
        MCU_CONFIG_MODEL mcu_config_model = carConfig.getMcuConfig();
        if (mcu_config_model == null || inputEncoder == null) {
            return;
        }
        mcu_config_model.setEncoder(Double.parseDouble(inputEncoder.getText().toString()));
        mcu_config_model.setRpm(Double.parseDouble(inputRpm.getText().toString()));
        mcu_config_model.setNp_time(Integer.parseInt(inputNtpDelayTime.getText().toString()));
        mcu_config_model.setNt_time(Integer.parseInt(inputNtpDelayTime.getText().toString()));
        if (MCUSerialHandler.getInstance().sendConfig(mcu_config_model)) {
            carConfig.updateMcuConfig(mcu_config_model);
//            SoundPlayer.getInstance().changeSuccess();
        } else {
//            SoundPlayer.getInstance().changeFault();
        }
        Log.i("SensorSettingFragment", "saveData");
    }

    @Override
    protected void onInitViewCreated(View view) {

    }

    @Override
    protected void onInitCreateView(View view) {
        inputEncoder = view.findViewById(R.id.inputEncoder);
        inputRpm = view.findViewById(R.id.inputRpm);
        inputNtpDelayTime = view.findViewById(R.id.inputNtpDelayTime);
        view.findViewById(R.id.btSave).setOnClickListener(v -> saveData());
    }

    @Override
    public void updateData() {
        MCU_CONFIG_MODEL mcu_config_model = carConfig.getMcuConfig();
        if (mcu_config_model == null || inputEncoder == null) {
            return;
        }
        this.inputEncoder.setText(String.valueOf(mcu_config_model.getEncoder()));
        this.inputRpm.setText(String.valueOf(mcu_config_model.getRpm()));
        this.inputNtpDelayTime.setText(String.valueOf(mcu_config_model.getNp_time()));
        Log.i("SensorSettingFragment", "updateData");
    }


}