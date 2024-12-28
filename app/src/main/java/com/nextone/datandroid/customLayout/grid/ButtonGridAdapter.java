package com.nextone.datandroid.customLayout.grid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.nextone.datandroid.R;

public class ButtonGridAdapter extends BaseAdapter {

    private Context context;
    private String[] buttonLabels;

    public ButtonGridAdapter(Context context, String[] buttonLabels) {
        this.context = context;
        this.buttonLabels = buttonLabels;
    }

    @Override
    public int getCount() {
        return buttonLabels.length;
    }

    @Override
    public Object getItem(int position) {
        return buttonLabels[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }
        Button button = view.findViewById(R.id.gridButton);
        button.setText(buttonLabels[position]);
        button.setOnClickListener(v ->
                Toast.makeText(context, "Clicked: " + buttonLabels[position], Toast.LENGTH_SHORT).show()
        );
        return view;
    }
}

