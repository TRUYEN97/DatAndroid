package com.nextone.datandroid.fragment.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextone.datandroid.R;

import java.util.ArrayList;
import java.util.List;

public class YardRecylerAdapter extends RecyclerView.Adapter<YardRecylerAdapter.ViewHolder> {

    private final Context context;
    private final List<YardRecycleModel> itemList;

    public YardRecylerAdapter(Context context) {
        this.context = context;
        this.itemList = new ArrayList<>();
    }

    public void addItem(YardRecycleModel item) {
        if (item == null) return;
        itemList.add(item);
        notifyItemInserted(itemList.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
//        MyImageLabel view = new MyImageLabel(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YardRecycleModel yardRecycleModel = itemList.get(position);
        if (yardRecycleModel.onclickListener != null) {
            holder.button.setOnClickListener(yardRecycleModel.onclickListener);
        }
        if (yardRecycleModel.name != null) {
            holder.button.setText(yardRecycleModel.name);
//            holder.button.setTextLabel(yardRecycleModel.name);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void removeItem(int index) {
        if (index < 0 || index >= itemList.size()) return;
        itemList.remove(index);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final Button button;

        //        final MyImageLabel button;
        public ViewHolder(@NonNull View view) {
//        public ViewHolder(@NonNull MyImageLabel view) {
            super(view);
            this.button = view.findViewById(R.id.btViewHolder);
//            this.button = view;
//            this.button.setButtonMode(true);
        }
    }

    public static class YardRecycleModel {
        String name;
        View.OnClickListener onclickListener;

        public YardRecycleModel(String name, View.OnClickListener onclickListener) {
            this.name = name;
            this.onclickListener = onclickListener;
        }
    }
}
