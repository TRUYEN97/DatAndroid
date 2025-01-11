package com.nextone.datandroid.fragment.tabLayout.tabFragmant;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.nextone.common.YardConfig;
import com.nextone.datandroid.R;
import com.nextone.datandroid.fragment.recyclerView.RecyclerViewFragment;
import com.nextone.datandroid.fragment.recyclerView.YardRecylerAdapter;
import com.nextone.datandroid.impActivity.YardContestSettingActivity;
import com.nextone.datandroid.impActivity.YardListContestSettingActivity;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YardSettingFragment} factory method to
 * create an instance of this fragment.
 */
public class YardSettingFragment extends AbsTabFragment {


    public static final String NDCNDB_NAME = "Nhường đường cho người đi bộ";
    public static final String DXND_NAME = "Dừng xe ngang dốc";
    public static final String QVBX_NAME = "Qua vệt bánh xe";
    public static final String DHVG_NAME = "Đường hẹp vuông góc";
    public static final String NT1_NAME = "Ngã tư 1";
    public static final String NT2_NAME = "Ngã tư 2";
    public static final String GXD_NAME = "Ghép xe dọc";
    public static final String NT3_NAME = "Ngã tư 3";
    public static final String DUONG_TAU_NAME = "Đường tàu";
    public static final String TANG_TOC_NAME = "Tăng tốc trên đường thẳng";
    public static final String GXN_NAME = "Ghép xe ngang";
    public static final String NT4_NAME = "Ngã tư 4";
    public static final String DTQC_NAME = "Đường vòng quanh co";
    private final RecyclerViewFragment<YardRecylerAdapter.ViewHolder> recyclerViewFragment;
    private final int rank;

    public YardSettingFragment(int rank) {
        // Required empty public constructor
        super(R.layout.setting_yard);
        this.rank = rank;
        this.recyclerViewFragment = new RecyclerViewFragment<>(3);
    }


    @Override
    public void saveData() {
        YardConfig.getInstance().update();
        Log.i("YardSettingFragment", "saveData");
    }

    @Override
    public void updateData() {
        YardRecylerAdapter yardRecylerAdapter = new YardRecylerAdapter(getContext());
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(NDCNDB_NAME, v -> {
                    showContestSettingActivity(YardContestSettingActivity.DXCNDB, NDCNDB_NAME);
                }));
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(DXND_NAME, v -> {
                    showContestSettingActivity(YardContestSettingActivity.DXND, DXND_NAME);
        }));
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(QVBX_NAME, v -> {
                    showListContestSettingActivity(YardListContestSettingActivity.VBX, QVBX_NAME);
        }));
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(DHVG_NAME, v -> {
                    showListContestSettingActivity(YardListContestSettingActivity.DVG, DHVG_NAME);
                }));
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(NT1_NAME, v -> {
                    showContestSettingActivity(YardContestSettingActivity.NGTU1, NT1_NAME);
                }));
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(DTQC_NAME, v -> {
                    showListContestSettingActivity(YardListContestSettingActivity.DS, DTQC_NAME);
                }));
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(NT2_NAME, v -> {
                    showContestSettingActivity(YardContestSettingActivity.NGTU2, NT2_NAME);
                }));
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(GXD_NAME, v -> {
                    showListContestSettingActivity(YardListContestSettingActivity.GXD, GXD_NAME);
                }));
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(NT3_NAME, v -> {
                    showContestSettingActivity(YardContestSettingActivity.NGTU3, NT3_NAME);
                }));
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(DUONG_TAU_NAME, v -> {
                    showContestSettingActivity(YardContestSettingActivity.DUONG_TAU, DUONG_TAU_NAME);
                }));
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(TANG_TOC_NAME, v -> {
                    showContestSettingActivity(YardContestSettingActivity.TANG_TOC, TANG_TOC_NAME);
                }));
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(GXN_NAME, v -> {
                    showListContestSettingActivity(YardListContestSettingActivity.GXN, GXN_NAME);
                }));
        yardRecylerAdapter.addItem(
                new YardRecylerAdapter.YardRecycleModel(NT4_NAME, v -> {
                    showContestSettingActivity(YardContestSettingActivity.NGTU4, NT4_NAME);
                }));
        this.recyclerViewFragment.setItemAdapter(yardRecylerAdapter);
        Log.i("YardSettingFragment", "updateData");
    }

    private void showContestSettingActivity(int index, String name) {
        Intent intent = new Intent(getContext(), YardContestSettingActivity.class);
        intent.putExtra(YardContestSettingActivity.RANK_KEY, rank);
        intent.putExtra(YardContestSettingActivity.CONTEST_INDEX_KEY, index);
        intent.putExtra(YardContestSettingActivity.CONTEST_NAME_KEY, name);
        startActivity(intent);
    }
    private void showListContestSettingActivity(int index, String name) {
        Intent intent = new Intent(getContext(), YardListContestSettingActivity.class);
        intent.putExtra(YardListContestSettingActivity.RANK_KEY, rank);
        intent.putExtra(YardListContestSettingActivity.CONTEST_INDEX_KEY, index);
        intent.putExtra(YardListContestSettingActivity.CONTEST_NAME_KEY, name);
        startActivity(intent);
    }

    @Override
    protected void onInitViewCreated(View view) {
        setChildFragment(R.id.settingYardContainer, recyclerViewFragment,
                "YardRankSetting"+rank, false);
    }

    @Override
    protected void onInitCreateView(View view) {

    }
}