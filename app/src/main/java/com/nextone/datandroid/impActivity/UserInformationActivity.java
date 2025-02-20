package com.nextone.datandroid.impActivity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nextone.datandroid.MyActivity;
import com.nextone.datandroid.R;
import com.nextone.input.serial.MCUSerialHandler;

public class UserInformationActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView txtId = findViewById(R.id.txtId);
        TextView txtName = findViewById(R.id.txtName);
        txtId.setEnabled(false);
        txtName.setEnabled(false);
        MCUSerialHandler mcuSerialHandler = MCUSerialHandler.getInstance();
        txtId.setText(mcuSerialHandler.getModel().getYardUser());
    }
}