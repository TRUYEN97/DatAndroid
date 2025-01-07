package com.nextone.datandroid.customLayout.impConstrainLayout;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nextone.controller.ModeChooser;
import com.nextone.controller.ProcessModelHandle;
import com.nextone.datandroid.AbsFragment;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;
import com.nextone.datandroid.customLayout.tabLayout.CustomPagerAdapter;
import com.nextone.datandroid.customLayout.tabLayout.TabLayoutCustomView;
import com.nextone.datandroid.customLayout.tabLayout.tabFragmant.SensorSettingFragment;
import com.nextone.datandroid.customLayout.tabLayout.tabFragmant.TabYardRankSetting;
import com.nextone.input.camera.CameraModule;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.input.socket.YardModelHandle;

public class BaseModeLayout extends AbsFragment {


    private MyImageLabel socketAlam;
    private MyImageLabel sensorAlam;

    private final Handler handler;

    private final ModeChooser modeChooser;


    private final CameraModule cameraModule;

    private final TabLayoutCustomView tabLayoutCustomView;



    public BaseModeLayout() {
        super(R.layout.view_frame_mode);
        this.modeChooser = new ModeChooser();
        this.handler = new Handler(Looper.getMainLooper());
        this.cameraModule = new CameraModule();
        this.tabLayoutCustomView = new TabLayoutCustomView();
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
        background.setColor(getResources().getColor(android.R.color.darker_gray, getContext().getTheme()));
        background.setCornerRadii(new float[]{
                25f, 25f,
                25f, 25f,
                25f, 25f,
                25f, 25f
        });
        view.findViewById(R.id.informationView).setBackground(background);
    }

    public void addFragment(Fragment fragment, String tab, boolean addToBackStack) {
        addChildFragment(R.id.boardModeView, fragment, tab, addToBackStack);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.cameraModule.start();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraModule.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onInitViewCreated(View view) {
        view.findViewById(R.id.btSetting).setOnClickListener(v -> {
            if (!ProcessModelHandle.getInstance().isTesting()) {
                tabLayoutCustomView.setCallback(pager -> {
                    CustomPagerAdapter settingPagerAdapter = new CustomPagerAdapter(requireActivity());
                    settingPagerAdapter.addFragment(new SensorSettingFragment(), "Cảm biến");
                    settingPagerAdapter.addFragment(new TabYardRankSetting(), "Thông tin sân");
                    return settingPagerAdapter;
                });
                addFragment(tabLayoutCustomView, "tab_setting", false);
            }
        });
        this.modeChooser.setFrameLayout(this);
        this.modeChooser.show();
        this.handler.postDelayed(this::update, 100);
    }

    @Override
    protected void onInitCreateView(View view) {
        this.socketAlam = view.findViewById(R.id.socketAlam);
        this.socketAlam.setTextLabel("Server");
        this.socketAlam.setTextLabelColor(Color.BLACK);
        this.sensorAlam = view.findViewById(R.id.sensorAlam);
        this.sensorAlam.setTextLabel("Cảm biến");
        this.sensorAlam.setTextLabelColor(Color.BLACK);
        Button btMode = view.findViewById(R.id.btMode);
        btMode.setText("Chọn chế độ");
        btMode.setOnClickListener(v -> {
            this.modeChooser.show();
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
}
