package com.nextone.datandroid.fragment.compoment;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nextone.datandroid.R;
import com.nextone.datandroid.fragment.AbsFragment;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.input.socket.YardModelHandle;
import com.nextone.model.input.CarModel;
import com.nextone.model.modelView.ShareModelView;
import com.nextone.model.modelView.UserInfo;

public class LoginFragment extends AbsFragment {

    private TextView lbMess;
    private String mess;
    private final ShareModelView shareModelView;
    private UserInfo userInfo;

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
    private String getMessage() {
        MCUSerialHandler mcuSerialHandler = MCUSerialHandler.getInstance();
        if (!mcuSerialHandler.isConnect()) {
            userInvalide = true;
            return """
                    Vui lòng kiểm tra lại:
                    
                    - Bật cho phép kết nối OTG trong cài đặt máy.
                    - Kết nối điện thoại với thiết bị trên xe.
                    """;
        } else if (!YardModelHandle.getInstance().isConnect()) {
            String mess;
            if (this.userInfo != null
                    && (mess = this.userInfo.getMessage()) != null
                    && !mess.isBlank()) {
                return String.format("Thông báo:\r\n- %s", mess);
            } else {
                CarModel model = mcuSerialHandler.getModel();
                String yardUser = model.getYardUser();
                userInvalide = false;
                if (yardUser == null || yardUser.isBlank()) {
                    return """
                            Vui lòng tiến hành:
                            
                            - Đăng nhập tài khoản trên xe.
                            """;
                } else {
                    return """
                            Không thể kết nối tới server!
                            
                            Vui lòng kiểm tra lại:
                            
                            - kết nối wifi.
                            - Thông tin tài khoản.
                            """;
                }
            }
        }
        return "";
    }

    @Override
    public void onResume() {
        super.onResume();
        this.shareModelView.getCarModelMutableLiveData().observe(this, carModel ->
                this.setMess(getMessage()));
        this.shareModelView.getUserInfo().observe(this, userInfo -> {
            if (userInfo != null) {
                this.userInfo = userInfo;
                this.setMess(getMessage());
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        this.shareModelView.getCarModelMutableLiveData().removeObservers(this);
    }

    @Override
    protected void onInitCreateView(View view) {
        this.lbMess = view.findViewById(R.id.lbMess);
        setMess(mess);
        view.findViewById(R.id.btCheck).setOnClickListener(v -> {
            if (userInvalide) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            } else {
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