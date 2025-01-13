package com.nextone.datandroid.fragment.compoment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.nextone.controller.ProcessModelHandle;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;
import com.nextone.datandroid.fragment.AbsFragment;
import com.nextone.datandroid.impActivity.ChooseModeActivity;
import com.nextone.datandroid.impActivity.SettingActivity;
import com.nextone.input.camera.CameraModule;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.input.socket.YardModelHandle;
import com.nextone.model.modelView.ShareModelView;
import com.nextone.datandroid.fragment.modeView.AbsModeViewFragment;

public class BaseModeLayoutFragment extends AbsFragment {


    private MyImageLabel socketAlam;
    private MyImageLabel sensorAlam;

    private final Handler handler;
    private final CameraModule cameraModule;

    private Button btMode;


    public BaseModeLayoutFragment() {
        super(R.layout.view_frame_mode);
        this.handler = new Handler(Looper.getMainLooper());
        this.cameraModule = new CameraModule();
    }

    private void update() {
        this.socketAlam.setStatus(YardModelHandle.getInstance().isConnect());
        this.sensorAlam.setStatus(MCUSerialHandler.getInstance().isConnect());
        this.handler.postDelayed(this::update, 100);
    }

    private void initCamera(View view) {
        this.cameraModule.init(requireActivity(), this, view.findViewById(R.id.camera));
    }

    private void initBackground(View view) {
        GradientDrawable background = new GradientDrawable();
        background.setColor(getResources().getColor(android.R.color.darker_gray,
                getContext() == null ? null : getContext().getTheme()));
        background.setCornerRadii(new float[]{
                25f, 25f,
                25f, 25f,
                25f, 25f,
                25f, 25f
        });
        view.findViewById(R.id.informationView).setBackground(background);
    }

    public void setFragment(Fragment fragment, String tab, boolean addToBackStack) {
        if (getContext() == null) return;
        setChildFragment(R.id.boardModeView, fragment, tab, addToBackStack);
    }

    @Override
    public void onStart() {
        super.onStart();
//        this.cameraModule.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraModule.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        cameraModule.stop();
    }

    @Override
    protected void onInitViewCreated(View view) {
        view.findViewById(R.id.btSetting).setOnClickListener(v -> {
            showSetting();
        });
        this.handler.postDelayed(this::update, 100);
        ShareModelView.getInstance().getSubScreenFragment().observe(this, fragment -> {
            if (fragment != null) {
                if ( this.btMode != null && fragment instanceof AbsModeViewFragment viewFragment) {
                    setFragment(fragment, "Mode view fagment", false);
                    String currentModeName = viewFragment.getCurrentModeName();
                    this.btMode.setText(currentModeName == null ? "Chọn chế độ" : currentModeName);
                }
            } else {
                showModeChooser();
            }
        });
    }

    @Override
    protected void onInitCreateView(View view) {
        this.socketAlam = view.findViewById(R.id.socketAlam);
        this.socketAlam.setTextLabel("Server");
        this.socketAlam.setTextLabelColor(Color.BLACK);
        this.sensorAlam = view.findViewById(R.id.sensorAlam);
        this.sensorAlam.setTextLabel("Cảm biến");
        this.sensorAlam.setTextLabelColor(Color.BLACK);
        this.btMode = view.findViewById(R.id.btMode);
        this.btMode.setText("Chọn chế độ");
        this.btMode.setOnClickListener(v -> {
            this.showModeChooser();
        });
        /////////////////////////////
        initCamera(view);
        //////////////////////
        MyImageLabel lbSetting = view.findViewById(R.id.btSetting);
        lbSetting.setImage(R.drawable.setting_icon);
        lbSetting.setTextLabel("Cài đặt");
        lbSetting.setTextLabelColor(Color.BLACK);
        lbSetting.setOnColorResource(android.R.color.holo_blue_dark);
        lbSetting.setOffColorResource(android.R.color.holo_blue_light);
        lbSetting.setButtonMode(true);
        /////////////////////////////
        initBackground(view);
    }

    public void showModeChooser() {
        if (!ProcessModelHandle.getInstance().isTesting()) {
            Intent intent = new Intent(requireActivity(), ChooseModeActivity.class);
            startActivity(intent);
        }
    }

    public void showSetting() {
        Intent intent = new Intent(requireActivity(), SettingActivity.class);
        startActivity(intent);
    }
}
