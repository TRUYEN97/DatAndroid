package com.nextone.datandroid.fragment.compoment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.camera.view.PreviewView;
import androidx.fragment.app.Fragment;

import com.nextone.controller.ProcessModelHandle;
import com.nextone.controller.modeController.ModeManagement;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;
import com.nextone.datandroid.fragment.AbsFragment;
import com.nextone.datandroid.fragment.modeView.AbsModeViewFragment;
import com.nextone.datandroid.impActivity.ChooseModeActivity;
import com.nextone.datandroid.impActivity.SettingActivity;
import com.nextone.input.camera.CameraModule;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.input.socket.YardModelHandle;
import com.nextone.model.modelView.ShareModelView;
import com.nextone.datandroid.impActivity.UserInformationActivity;

public class BaseModeLayoutFragment extends AbsFragment {


    private MyImageLabel socketAlam;
    private MyImageLabel sensorAlam;

    private final Handler handler;
    private final CameraModule cameraModule;

    private Button btMode;
    private final ShareModelView shareModelView;

    private final LoginFragment loginFragment;

    private ModeManagement modeManagement;
    private AbsModeViewFragment currentViewFragment;

    private boolean hasShowLoginFragment = false;


    public BaseModeLayoutFragment() {
        super(R.layout.view_frame_mode);
        this.handler = new Handler(Looper.getMainLooper());
        this.cameraModule = new CameraModule();
        this.shareModelView = ShareModelView.getInstance();
        this.loginFragment = new LoginFragment();
    }

    private void update() {
        boolean skSt = YardModelHandle.getInstance().isConnect();
        boolean ssSt = MCUSerialHandler.getInstance().isConnect();
        if (!skSt || !ssSt) {
            if (!this.modeManagement.hasPauseCurrentMode()) {
                this.modeManagement.pauseCurrentMode();
                this.shareModelView.setSubScreenFragment(this.loginFragment);
            }
        } else if (this.modeManagement.hasPauseCurrentMode()) {
            this.modeManagement.resumeCurrentMode();
            this.shareModelView.setSubScreenFragment(this.currentViewFragment);
        }
        this.socketAlam.setStatus(skSt);
        this.sensorAlam.setStatus(ssSt);
        this.handler.postDelayed(this::update, 100);
    }

    private void initCamera(PreviewView pv) {
        this.cameraModule.init(requireActivity(), this, pv);
        pv.setOnClickListener(v -> {
            if (this.cameraModule.isStreaming()) {
                this.cameraModule.stop();
            } else {
                this.cameraModule.start();
            }
        });
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
    public void onResume() {
        super.onResume();
        this.cameraModule.start();
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
    protected void onInitViewCreated(View view) {
        this.modeManagement = ModeManagement.getInstance();
        view.findViewById(R.id.btSetting).setOnClickListener(v -> {
            showSetting();
        });
        view.findViewById(R.id.imgViewLogo).setOnClickListener(v -> {
            showUerInfo();
        });
        this.shareModelView.getSubScreenFragment().observe(this, fragment -> {
            if (fragment != null) {
                if (fragment instanceof AbsModeViewFragment viewFragment) {
                    this.currentViewFragment = viewFragment;
                    if (this.btMode != null) {
                        String currentModeName = viewFragment.getCurrentModeName();
                        this.btMode.setText(currentModeName == null ? "Chọn chế độ" : currentModeName);
                    }
                    if (!this.modeManagement.hasPauseCurrentMode()) {
                        hasShowLoginFragment = false;
                        setFragment(fragment, "Mode mode view fragment", false);
                    }
                } else if (fragment instanceof LoginFragment && !hasShowLoginFragment) {
                    hasShowLoginFragment = true;
                    setFragment(fragment, "login fragment", false);
                } else {
                    hasShowLoginFragment = false;
                    setFragment(fragment, "view fragment", false);
                }
            } else {
                showModeChooser();
            }
        });
        this.handler.postDelayed(this::update, 100);
    }

    @SuppressLint("SetTextI18n")
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
        initCamera(view.findViewById(R.id.camera));
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

    public void showUerInfo(){
        Intent intent = new Intent(requireActivity(), UserInformationActivity.class);
        startActivity(intent);
    }
}
