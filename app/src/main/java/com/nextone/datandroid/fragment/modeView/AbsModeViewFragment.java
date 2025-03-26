package com.nextone.datandroid.fragment.modeView;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.LayoutRes;

import com.nextone.datandroid.fragment.AbsFragment;
import com.nextone.datandroid.fragment.ShadowDialogFragment;
import com.nextone.datandroid.fragment.modeView.interfaces.IStart;
import com.nextone.model.modelView.ShareModelView;

public abstract class AbsModeViewFragment extends AbsFragment implements IStart {
    private final Handler handler;
    private int intervalMs = 1000;
    private boolean running;

    private Runnable runnable;

    protected AbsModeViewFragment(@LayoutRes int resource) {
        super(resource);
        this.handler = new Handler(Looper.getMainLooper());
        this.running = false;
        this.runnable = () -> {
            if (getView() != null) {
                this.updateUI();
            }
            this.handler.postDelayed(this.runnable, intervalMs);
        };
    }

    @Override
    protected void onInitCreateView(View view) {

    }

    @Override
    protected void onInitViewCreated(View view) {
        this.running = false;
    }

    public void setIntervalMs(int intervalMs) {
        this.intervalMs = Math.max(intervalMs, 100);
    }

    protected abstract void updateUI();

    @Override
    public void start() {
        if (this.running) return;
        this.handler.postDelayed(runnable, intervalMs);
        new Handler(Looper.getMainLooper()).post(() -> {
            ShareModelView shareModelView = ShareModelView.getInstance();
            shareModelView.getCarModelMutableLiveData().observe(this, carModel -> {
                if (getView() != null) {
                    this.updateUI();
                }
            });
            shareModelView.getUserInfo().observe(this, userInfo -> {
                if(userInfo != null){
                    String mess = userInfo.getMessage();
                    if(mess != null && !mess.isBlank()){
                        ShadowDialogFragment shadowDialogFragment = new ShadowDialogFragment();
                        shadowDialogFragment.showMessage(requireActivity().getSupportFragmentManager(),
                                mess);
                    }
                }
            });
        });
        this.running = true;
    }

    @Override
    public void stop() {
        new Handler(Looper.getMainLooper()).post(() -> {
            ShareModelView shareModelView = ShareModelView.getInstance();
            shareModelView.getCarModelMutableLiveData().removeObservers(this);
            shareModelView.getUserInfo().removeObservers(this);
        });
        this.handler.removeCallbacks(runnable);
        this.running = false;
    }

    @Override
    public boolean isStarted() {
        return this.running;
    }

}
