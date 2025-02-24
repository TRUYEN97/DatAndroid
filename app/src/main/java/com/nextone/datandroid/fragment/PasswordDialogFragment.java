package com.nextone.datandroid.fragment;

import android.app.Dialog;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.nextone.common.Util;
import com.nextone.datandroid.R;

import lombok.Setter;

public class PasswordDialogFragment extends DialogFragment {
    private GradientDrawable background;

    @Setter
    private Runnable acceptActionCallback;
    @Setter
    private Runnable deniedActionCallback;

    private TextView editPass;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_password);

        if (dialog.getWindow() != null && background == null) {
            this.background = new GradientDrawable();
            this.background.setColor(dialog.getWindow().getColorMode());
            this.background.setCornerRadii(new float[]{
                    60f, 60f,
                    60f, 60f,
                    40f, 40f,
                    40f, 40f
            });
//            dialog.getWindow().setBackgroundDrawable(this.background);
        }
        editPass = dialog.findViewById(R.id.editTextPassword);
        Button btAccept = dialog.findViewById(R.id.btAccept);
        btAccept.setOnClickListener(v -> {
            if (acceptActionCallback != null) {
                acceptActionCallback.run();
            }
            if(isAdded()){
                dismiss();
            }
        });
        Button btCancel = dialog.findViewById(R.id.btCancel);
        btCancel.setOnClickListener(v -> {
            if (deniedActionCallback != null) {
                deniedActionCallback.run();
            }
            if(isAdded()){
                dismiss();
            }
        });
        return dialog;
    }

    public String getPassword(){
        return Util.stringToMd5(editPass.getText().toString());
    }
}
