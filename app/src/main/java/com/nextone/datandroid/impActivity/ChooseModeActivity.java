package com.nextone.datandroid.impActivity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nextone.datandroid.MyActivity;
import com.nextone.datandroid.R;
import com.nextone.datandroid.fragment.compoment.ModeChooserFragment;

public class ChooseModeActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_mode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ModeChooserFragment chooserFragment = (ModeChooserFragment) getSupportFragmentManager().
                findFragmentById(R.id.fragmentChooseMode);
        assert chooserFragment != null;
        chooserFragment.setSpanCount(3);
        chooserFragment.setChooseCallback(this::finish);
    }

}