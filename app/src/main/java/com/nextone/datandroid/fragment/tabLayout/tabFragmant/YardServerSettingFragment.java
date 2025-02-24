package com.nextone.datandroid.fragment.tabLayout.tabFragmant;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.nextone.common.CarConfig;
import com.nextone.datandroid.R;
import com.nextone.datandroid.fragment.ShadowDialogFragment;
import com.nextone.datandroid.fragment.EditPasswordDialogFragment;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YardServerSettingFragment#} factory method to
 * create an instance of this fragment.
 */
public class YardServerSettingFragment extends AbsTabFragment {

    private final CarConfig carConfig;
    private final String TAG = "YardServerSettingFragment";
    private EditText inputIp;
    private EditText inputPort;

    public YardServerSettingFragment() {
        // Required empty public constructor
        super(R.layout.setting_yard_server);
        carConfig = CarConfig.getInstance();
    }

    @Override
    public void saveData() {
        if (carConfig == null || inputIp == null) return;
        ShadowDialogFragment shadowDialogFragment = new ShadowDialogFragment();
        String ip = inputIp.getText().toString();
        if (ip.isBlank() || !ip.matches("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}")) {
            shadowDialogFragment.showMessage(requireActivity().getSupportFragmentManager(),
                    "Vui lòng nhập địa chỉ IP");
            return;
        }
        boolean hasChange = false;
        String port = inputPort.getText().toString();
        if (port.isBlank() || !port.matches("\\d+")) {
            shadowDialogFragment.showMessage(requireActivity().getSupportFragmentManager(),
                    "Vui lòng nhập địa chỉ PORT");
            return;
        }
        if (!carConfig.getYardIp().equals(ip)){
            carConfig.setYardIp(ip);
            hasChange = true;
        }
        if (carConfig.getYardPort() != Integer.parseInt(port)){
            carConfig.setYardPort(Integer.parseInt(port));
            hasChange = true;
        }
        if (hasChange){
            shadowDialogFragment.showUpdateSuccess(requireActivity().getSupportFragmentManager());
            Log.i(TAG, "saveData");
        }
    }

    @Override
    protected void onInitViewCreated(View view) {

    }

    @Override
    protected void onInitCreateView(View view) {
        inputIp = view.findViewById(R.id.inputServerIP);
        inputPort = view.findViewById(R.id.inputServerPort);
        view.findViewById(R.id.btSave).setOnClickListener(v -> saveData());
        view.findViewById(R.id.txtChangePw).setOnClickListener(v -> {
            EditPasswordDialogFragment editPasswordDialogFragment = new EditPasswordDialogFragment();
            editPasswordDialogFragment.setAcceptActionCallback(() -> {
                carConfig.setPassword(editPasswordDialogFragment.getPassword());
                ShadowDialogFragment shadowDialogFragment = new ShadowDialogFragment();
                shadowDialogFragment.showUpdateSuccess(requireActivity().getSupportFragmentManager());
            });
            editPasswordDialogFragment.show(requireActivity().getSupportFragmentManager(), "editPassword");
        });
    }

    @Override
    public void updateData() {
        if (carConfig == null || inputIp == null) return;
        inputIp.setText(carConfig.getYardIp());
        inputPort.setText(String.valueOf(carConfig.getYardPort()));
        Log.i(TAG, "updateData");
    }


}