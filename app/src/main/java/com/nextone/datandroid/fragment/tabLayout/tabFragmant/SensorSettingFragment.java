package com.nextone.datandroid.fragment.tabLayout.tabFragmant;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.nextone.common.CarConfig;
import com.nextone.datandroid.R;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.config.MCU_CONFIG_MODEL;
import com.nextone.datandroid.fragment.ShadowDialogFragment;
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
        if (carConfig == null || inputEncoder == null) return;
        MCU_CONFIG_MODEL mcu_config_model = carConfig.getMcuConfig();
        if (mcu_config_model == null) {
            return;
        }
        boolean hasChange = false;
        String value = inputEncoder.getText().toString();
        if (mcu_config_model.getEncoder() != Double.parseDouble(value)) {
            mcu_config_model.setEncoder(Double.parseDouble(value));
            hasChange = true;
        }
        value = inputRpm.getText().toString();
        if (mcu_config_model.getRpm() != Double.parseDouble(value)) {
            mcu_config_model.setRpm(Double.parseDouble(value));
            hasChange = true;
        }
        value = inputNtpDelayTime.getText().toString();
        int nt_time = Integer.parseInt(value);
        if (mcu_config_model.getNt_time() != nt_time) {
            mcu_config_model.setNt_time(nt_time);
            mcu_config_model.setNp_time(nt_time);
            hasChange = true;
        }
        if (!hasChange) {
            return;
        }
        ShadowDialogFragment shadowDialogFragment = new ShadowDialogFragment();
        if (MCUSerialHandler.getInstance().sendConfig(mcu_config_model)) {
            carConfig.updateMcuConfig(mcu_config_model);
            shadowDialogFragment.showUpdateSuccess(requireActivity().getSupportFragmentManager());
        } else {
            shadowDialogFragment.showUpdatefailure(requireActivity().getSupportFragmentManager());
        }
        Log.i(TAG, "saveData");
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
        if(carConfig == null || inputEncoder == null) return;
        MCU_CONFIG_MODEL mcu_config_model = carConfig.getMcuConfig();
        if (mcu_config_model == null) {
            return;
        }
        this.inputEncoder.setText(String.valueOf(mcu_config_model.getEncoder()));
        this.inputRpm.setText(String.valueOf(mcu_config_model.getRpm()));
        this.inputNtpDelayTime.setText(String.valueOf(mcu_config_model.getNp_time()));
        Log.i(TAG, "updateData");
    }


}