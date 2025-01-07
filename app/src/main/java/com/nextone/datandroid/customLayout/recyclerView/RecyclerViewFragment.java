package com.nextone.datandroid.customLayout.recyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nextone.datandroid.R;

public class RecyclerViewFragment<VH extends RecyclerView.ViewHolder> extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<VH> itemAdapter;

    private final GridLayoutManager gridLayoutManager;


    public RecyclerViewFragment() {
        this(null, 2);
    }

    public RecyclerViewFragment(RecyclerView.Adapter<VH> itemAdapter, int spanCount) {
        this.itemAdapter = itemAdapter;
        this.gridLayoutManager = new GridLayoutManager(getContext(), Math.max(spanCount, 1));

    }

    public void setSpanCount(int spanCount) {
        this.gridLayoutManager.setSpanCount(Math.max(spanCount, 1));
    }

    public void setItemAdapter(RecyclerView.Adapter<VH> itemAdapter) {
        this.itemAdapter = itemAdapter;
        this.recyclerView.setLayoutManager(this.gridLayoutManager);
        this.recyclerView.setAdapter(itemAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        this.recyclerView = view.findViewById(R.id.recyclerView);
        if (this.recyclerView != null) {
            this.recyclerView.setLayoutManager(this.gridLayoutManager);
            this.recyclerView.setAdapter(this.itemAdapter);
        }
        return view;
    }
}
