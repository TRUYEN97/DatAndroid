package com.nextone.datandroid.customLayout.recyclerView;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;

import java.util.ArrayList;
import java.util.List;

public class YardRecylerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<MyImageLabel> itemList;

    public YardRecylerAdapter(Context context) {
        this.context= context;
        this.itemList = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new MyImageLabel(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
