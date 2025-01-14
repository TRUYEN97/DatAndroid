package com.nextone.datandroid.fragment.compoment;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.nextone.controller.modeController.ModeManagement;
import com.nextone.datandroid.R;
import com.nextone.datandroid.fragment.AbsFragment;
import com.nextone.datandroid.fragment.modeView.AbsModeViewFragment;
import com.nextone.datandroid.fragment.recyclerView.RecyclerViewFragment;
import com.nextone.datandroid.fragment.recyclerView.YardRecylerAdapter;
import com.nextone.mode.AbsTestMode;
import com.nextone.model.modelView.ShareModelView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Setter;

@Setter
public class ModeChooserFragment extends AbsFragment {

    private RecyclerViewFragment<YardRecylerAdapter.ViewHolder> recyclerViewFragment;
    private YardRecylerAdapter yardRecylerAdapter;
    private final ModeManagement modeManager;
    private final ExecutorService executorService;

    @Setter
    private Runnable chooseCallback;

    private int spanCount;

    public ModeChooserFragment() {
        this(2);
    }

    public ModeChooserFragment(int spanCount) {
        super(R.layout.chosse_mode);
        this.spanCount = Math.max(spanCount, 1);
        this.modeManager = ModeManagement.getInstance();
        this.executorService = Executors.newSingleThreadExecutor();
    }


    public void setSpanCount(int spanCount) {
        this.spanCount = Math.max(spanCount, 1);
    }

    @Override
    protected void onInitViewCreated(View view) {
        if (this.recyclerViewFragment != null) {
            this.recyclerViewFragment.setSpanCount(this.spanCount);
            this.recyclerViewFragment.setItemAdapter(yardRecylerAdapter);
        }
    }

    @Override
    protected void onInitCreateView(View view) {
        this.recyclerViewFragment = (RecyclerViewFragment<YardRecylerAdapter.ViewHolder>)
                getChildFragmentManager().findFragmentById(R.id.fragmentChooseMode);
        yardRecylerAdapter = new YardRecylerAdapter(getContext());
        ShareModelView shareModelView = ShareModelView.getInstance();
        var modeModel = this.modeManager.getModes();
        for (ModeManagement.ModeModel model : modeModel) {
            if (model == null) return;
            yardRecylerAdapter.addItem(new YardRecylerAdapter.YardRecycleModel(model.getName(), v -> {
                executorService.execute(() -> {
                    if (modeManager.startMode(model) && modeManager.getCurrentMode() != null) {
                        AbsModeViewFragment viewFragment = modeManager.getCurrentMode().getView();
                        shareModelView.postSubScreenFragment(viewFragment);
                        if (chooseCallback != null) {
                            new Handler(Looper.getMainLooper()).postDelayed(chooseCallback, 100);
                        }
                    }
                });
            }));
        }

    }

    public boolean hasChoose() {
        return this.modeManager.getCurrentMode() != null;
    }

    public boolean isChangeModeAble() {
        AbsTestMode<? extends AbsModeViewFragment> currentMode = this.modeManager.getCurrentMode();
        return currentMode == null || !currentMode.getModeHandle().isTesting();
    }


}
