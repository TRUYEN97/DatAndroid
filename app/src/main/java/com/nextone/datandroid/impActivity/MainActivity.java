package com.nextone.datandroid.impActivity;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.nextone.datandroid.MyActivity;
import com.nextone.controller.Core;
import com.nextone.datandroid.R;
import com.nextone.model.MyContextManagement;

public class MainActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContextManagement.getInstance().setAplicationContext(getApplicationContext());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MainLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Core.getInstance().start(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Main","Destroy");
    }
}