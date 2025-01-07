package com.nextone.datandroid.customLayout.recyclerView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;

public class ViewHolder extends RecyclerView.ViewHolder {
    MyImageLabel itemImage;

    public ViewHolder(@NonNull MyImageLabel itemImage) {
        super(itemImage);
        this.itemImage = itemImage;
    }
}