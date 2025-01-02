package com.nextone.datandroid.customLayout.tabLayout.tabFragmant;

import android.util.Log;
import android.widget.Button;
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

    private CarConfig carConfig;
    private final String TAG = "SensorSettingFragment";
    private EditText inputEncoder;
    private EditText inputRpm;
    private EditText inputNtpDelayTime;
    private Button btSave;

    public SensorSettingFragment() {
        // Required empty public constructor
        super(R.layout.fragment_sensor_setting);
    }

    @Override
    public void saveData() {
        if (carConfig == null) {
            carConfig = CarConfig.getInstance();
        }
        MCU_CONFIG_MODEL mcu_config_model = carConfig.getMcuConfig();
        if(mcu_config_model == null) {
            return;
        }
        if (inputEncoder == null) {
            inputEncoder = getView().findViewById(R.id.inputEncoder);
        }
        if (inputRpm == null) {
            inputRpm = getView().findViewById(R.id.inputRpm);
        }
        if (inputNtpDelayTime == null) {
            inputNtpDelayTime = getView().findViewById(R.id.inputNtpDelayTime);
        }
        if (btSave == null) {
            btSave = getView().findViewById(R.id.btSave);
            btSave.setOnClickListener(v -> saveData());
        }
        mcu_config_model.setEncoder(Double.parseDouble(inputEncoder.getText().toString()));
        mcu_config_model.setRpm(Double.parseDouble(inputRpm.getText().toString()));
        mcu_config_model.setNp_time(Integer.parseInt(inputNtpDelayTime.getText().toString()));
        mcu_config_model.setNt_time(Integer.parseInt(inputNtpDelayTime.getText().toString()));
        if(MCUSerialHandler.getInstance().sendConfig(mcu_config_model)){
            Log.i("SensorSettingFragment", "saveData");
            carConfig.updateMcuConfig(mcu_config_model);
//            SoundPlayer.getInstance().changeSuccess();
        }else{
//            SoundPlayer.getInstance().changeFault();
        }
    }

    @Override
    public void updateData() {
        if (carConfig == null) {
            carConfig = CarConfig.getInstance();
        }
        MCU_CONFIG_MODEL mcu_config_model = carConfig.getMcuConfig();
        if(mcu_config_model == null) {
            return;
        }
        if (inputEncoder == null) {
            inputEncoder = getView().findViewById(R.id.inputEncoder);
        }
        if (inputRpm == null) {
            inputRpm = getView().findViewById(R.id.inputRpm);
        }
        if (inputNtpDelayTime == null) {
            inputNtpDelayTime = getView().findViewById(R.id.inputNtpDelayTime);
        }
        if (btSave == null) {
            btSave = getView().findViewById(R.id.btSave);
            btSave.setOnClickListener(v -> saveData());
        }
        this.inputEncoder.setText(String.valueOf(mcu_config_model.getEncoder()));
        this.inputRpm.setText(String.valueOf(mcu_config_model.getRpm()));
        this.inputNtpDelayTime.setText(String.valueOf(mcu_config_model.getNp_time()));
        Log.i("SensorSettingFragment", "updateData");
    }



}