package com.nextone.datandroid;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nextone.model.MyContextManagement;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContextManagement.getInstance().putActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyContextManagement.getInstance().removeActivity(this);
    }
}
