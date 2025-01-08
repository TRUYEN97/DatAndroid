package com.nextone.datandroid.impActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nextone.controller.Core;
import com.nextone.datandroid.MyActivity;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.impConstrainLayout.BaseModeLayout;
import com.nextone.model.MyContextManagement;

public class MainActivity extends MyActivity {

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
        BaseModeLayout baseModeLayout = new BaseModeLayout();
        getSupportFragmentManager().beginTransaction().replace(R.id.MainLayout, baseModeLayout).commit();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else {
            moveTaskToBack(true);
        }
    }
}