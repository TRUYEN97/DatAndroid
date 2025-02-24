package com.nextone.datandroid.fragment;

import android.app.Dialog;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.nextone.datandroid.R;

public class ShadowDialogFragment extends DialogFragment {
    private String mess;
    private GradientDrawable background;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_shadow_message);

        if (dialog.getWindow() != null && background == null) {
            this.background = new GradientDrawable();
            this.background.setColor(0x8fD0CDCD);
            this.background.setCornerRadii(new float[]{
                    40f, 40f,
                    40f, 40f,
                    40f, 40f,
                    40f, 40f
            });
            dialog.getWindow().setBackgroundDrawable(this.background);
        }
        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        tvMessage.setText(mess);
        return dialog;
    }

    public void showUpdateSuccess(@NonNull FragmentManager manager) {
        showMessage(manager, "Cập nhật thành công");
    }

    public void showUpdatefailure(@NonNull FragmentManager manager) {
        showMessage(manager, "Cập nhật thất bại!!");
    }
    public void showMessage(@NonNull FragmentManager manager, String mess) {
        showMessage(manager, mess, 1000);
    }
    public void showMessage(@NonNull FragmentManager manager, String mess, int time) {
        this.mess = mess;
        new Handler(Looper.getMainLooper()).postDelayed(()->{
            if(isAdded()){
                dismiss();
            }
        }, time);
        show(manager, "ShadowDialog");
    }
}

