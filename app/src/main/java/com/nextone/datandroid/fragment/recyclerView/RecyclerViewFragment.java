package com.nextone.datandroid.fragment.recyclerView;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nextone.datandroid.fragment.AbsFragment;
import com.nextone.datandroid.R;

public class RecyclerViewFragment<VH extends RecyclerView.ViewHolder> extends AbsFragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<VH> itemAdapter;

    private final GridLayoutManager gridLayoutManager;


    public RecyclerViewFragment() {
        this( 2);
    }

    public RecyclerViewFragment(int spanCount) {
        this(null, spanCount);
    }


    public RecyclerViewFragment(RecyclerView.Adapter<VH> itemAdapter, int spanCount) {
        super(R.layout.fragment_recycler_view);
        this.itemAdapter = itemAdapter;
        this.gridLayoutManager = new GridLayoutManager(getContext(), Math.max(spanCount, 1));

    }

    public void setSpanCount(int spanCount) {
        this.gridLayoutManager.setSpanCount(Math.max(spanCount, 1));
    }

    public void setItemAdapter(RecyclerView.Adapter<VH> itemAdapter) {
        this.itemAdapter = itemAdapter;
        if (this.recyclerView != null) {
            this.recyclerView.setAdapter(this.itemAdapter);
        }
    }

    @Override
    protected void onInitViewCreated(View view) {

    }

    @Override
    protected void onInitCreateView(View view) {
        this.recyclerView = view.findViewById(R.id.recyclerView);
        if (this.recyclerView != null) {
            this.recyclerView.setAdapter(this.itemAdapter);
            this.recyclerView.setLayoutManager(this.gridLayoutManager);
        }
    }

}
