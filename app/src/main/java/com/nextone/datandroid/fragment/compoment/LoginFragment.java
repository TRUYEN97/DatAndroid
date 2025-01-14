package com.nextone.datandroid.fragment.compoment;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nextone.datandroid.R;
import com.nextone.datandroid.fragment.AbsFragment;
import com.nextone.model.input.CarModel;
import com.nextone.model.modelView.ShareModelView;

public class LoginFragment extends AbsFragment {

    private TextView lbMess;
    private String mess;
    private final ShareModelView shareModelView;

    private boolean userInvalide = false;

    public LoginFragment() {
        super(R.layout.fragment_login);
        this.shareModelView = ShareModelView.getInstance();
        // Required empty public constructor
    }

    @Override
    protected void onInitViewCreated(View view) {

    }

    @NonNull
    private String getMessage(CarModel carModel) {
        if (carModel == null) return "";
        String yardUser = carModel.getYardUser();
        String mess = """
                Không thể kết nối tới server!
                
                Vui lòng kiểm tra lại:
                
                - kết nối wifi.
                - Thông tin tài khoản.
                """;
        if (yardUser == null || yardUser.isBlank()) {
            userInvalide = true;
            mess = """
                    Vui lòng kiểm tra lại:
                    
                    - Kết nối usb.
                    - Thông tin tài khoản.
                    """;
        }else{
            userInvalide = false;
        }
        return mess;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.shareModelView.getCarModel().observe(this, carModel -> {
            if (carModel != null) {
                this.setMess(getMessage(carModel));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.shareModelView.getCarModel().removeObservers(this);
    }

    @Override
    protected void onInitCreateView(View view) {
        this.lbMess = view.findViewById(R.id.lbMess);
        setMess(mess);
        view.findViewById(R.id.btCheck).setOnClickListener(v -> {
            if (userInvalide){
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }else{
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
            }
        });
    }

    public void setMess(String mess) {
        this.mess = mess;
        if (this.lbMess != null) {
            this.lbMess.setText(mess);
        }
    }
}