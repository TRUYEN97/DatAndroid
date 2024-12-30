package com.nextone.datandroid.customLayout.impConstrainLayout;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nextone.controller.modeController.ModeManagement;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.AbsCustomConstraintLayout;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.DuongTruongView;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.SaHinhView;
import com.nextone.mode.imp.DT_B_MODE;
import com.nextone.mode.imp.SH_B_MODE;

import lombok.Setter;

public class ModeChooser extends AbsCustomConstraintLayout {

    private ModeManagement modeManager;

    @Setter
    private BaseModeLayout baseModeLayout;

    public ModeChooser(@NonNull Context context) {
        super(context);
        init(R.layout.chosse_mode, true);
    }

    public ModeChooser(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(R.layout.chosse_mode, true);
    }

    public ModeChooser(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(R.layout.chosse_mode, true);
    }

    @Override
    protected void init(int resource, boolean attachToRoot) {
        super.init(resource, attachToRoot);
        this.modeManager = ModeManagement.getInstance();
        Context context = getContext();
        findViewById(R.id.btDT).setOnClickListener(v -> {
            DuongTruongView view = new DuongTruongView(context);
            this.modeManager.updateMode(new DT_B_MODE<DuongTruongView>(view, false));
            if (this.baseModeLayout != null) {
                this.baseModeLayout.updateUI(view);
            }
        });
        findViewById(R.id.btSh).setOnClickListener(v -> {
            SaHinhView view = new SaHinhView(context);
            this.modeManager.updateMode(new SH_B_MODE<SaHinhView>(view, false));
            if (this.baseModeLayout != null) {
                this.baseModeLayout.updateUI(view);
            }
        });
    }
}
