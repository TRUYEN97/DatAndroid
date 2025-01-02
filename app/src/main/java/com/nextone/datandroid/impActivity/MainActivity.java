package com.nextone.datandroid.impActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nextone.controller.Core;
import com.nextone.controller.ModeChooser;
import com.nextone.datandroid.MyActivity;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.impConstrainLayout.BaseModeLayout;
import com.nextone.input.camera.CameraModule;
import com.nextone.model.MyContextManagement;
public class MainActivity extends MyActivity {
    private CameraModule cameraModule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MainLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        MyContextManagement.getInstance().setAplicationContext(getApplicationContext());
        Core.getInstance().start();
        BaseModeLayout baseModeLayout = findViewById(R.id.ModeView);
        baseModeLayout.start();
        ModeChooser modeChooser = new ModeChooser();
        modeChooser.setFrameLayout(baseModeLayout.getFrameLayout());
        modeChooser.setBtMode(baseModeLayout.getBtMode());
        modeChooser.show();
        cameraModule = new CameraModule();
        cameraModule.init(this, this, baseModeLayout.getPreviewView());
        cameraModule.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraModule.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraModule.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}