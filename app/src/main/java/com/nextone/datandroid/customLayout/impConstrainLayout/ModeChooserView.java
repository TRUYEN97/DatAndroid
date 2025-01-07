package com.nextone.datandroid.customLayout.impConstrainLayout;

import android.view.View;

import com.nextone.datandroid.AbsFragment;
import com.nextone.datandroid.R;

import lombok.Setter;

@Setter
public class ModeChooserView extends AbsFragment {

    private View.OnClickListener btDtOnclick;
    private View.OnClickListener btDTB1Onclick;
    private View.OnClickListener btShOnclick;
    private View.OnClickListener btShB1Onclick;
    private View.OnClickListener btShCOnclick;
    private View.OnClickListener btShDOnclick;
    private View.OnClickListener btShEOnclick;

    public ModeChooserView() {
        super(R.layout.chosse_mode);
    }

    @Override
    protected void onInitViewCreated(View view) {

    }

    @Override
    protected void onInitCreateView(View view) {
        view.findViewById(R.id.btDT).setOnClickListener(this.btDtOnclick);
        view.findViewById(R.id.btDTB1).setOnClickListener(this.btDTB1Onclick);
        view.findViewById(R.id.btSh).setOnClickListener(this.btShOnclick);
        view.findViewById(R.id.btShB1).setOnClickListener(this.btShB1Onclick);
        view.findViewById(R.id.btShC).setOnClickListener(this.btShCOnclick);
        view.findViewById(R.id.btShD).setOnClickListener(this.btShDOnclick);
        view.findViewById(R.id.btShE).setOnClickListener(this.btShEOnclick);
    }
}
