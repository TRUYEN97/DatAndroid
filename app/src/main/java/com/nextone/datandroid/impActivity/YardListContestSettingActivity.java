package com.nextone.datandroid.impActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.nextone.common.YardConfig;
import com.nextone.datandroid.MyActivity;
import com.nextone.datandroid.R;
import com.nextone.datandroid.fragment.tabLayout.CustomPagerAdapter;
import com.nextone.datandroid.fragment.tabLayout.TabLayoutCustomFragment;
import com.nextone.datandroid.fragment.tabLayout.tabFragmant.ContestConfigFragment;
import com.nextone.model.yardConfigMode.ContestConfig;
import com.nextone.model.yardConfigMode.YardConfigModel;
import com.nextone.model.yardConfigMode.YardRankConfig;

import java.util.List;

public class YardListContestSettingActivity extends MyActivity {
    public static final String RANK_KEY = "rank";
    public static final String CONTEST_INDEX_KEY = "contestIndex";
    public static final String CONTEST_NAME_KEY = "contestName";
    public static final int VBX = 0;
    public static final int DVG = 1;
    public static final int DS = 2;
    public static final int GXD = 3;
    public static final int GXN = 4;

    private int lineCount = 0;

    private List<ContestConfig> contestConfigs;

    @SuppressLint({"MissingInflatedId", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_yard_list_contest_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        int rank = getIntent().getIntExtra(RANK_KEY, 1);
        int contestIndex = getIntent().getIntExtra(CONTEST_INDEX_KEY, 0);
        String name = getIntent().getStringExtra(CONTEST_NAME_KEY);
        TextView textName = findViewById(R.id.txtName);
        textName.setText(name);
        TabLayoutCustomFragment tabLayoutCustomView = new TabLayoutCustomFragment();
        CustomPagerAdapter settingPagerAdapter = new CustomPagerAdapter(this);
        contestConfigs = getContestConfigs(rank, contestIndex);
        findViewById(R.id.btAdd).setOnClickListener(v -> {
            addNewTab(new ContestConfig(), settingPagerAdapter);
        });
        findViewById(R.id.btDelete).setOnClickListener(v -> {
            tabLayoutCustomView.removeCurrentItem();
        });
        tabLayoutCustomView.setCallback(pager -> {
            return settingPagerAdapter;
        });
        for (ContestConfig contestConfig : contestConfigs) {
            addNewTab(contestConfig, settingPagerAdapter);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, tabLayoutCustomView);
        fragmentTransaction.commit();

    }

    private void addNewTab(ContestConfig contestConfig, CustomPagerAdapter settingPagerAdapter) {
        ContestConfigFragment fragment = new ContestConfigFragment(contestConfig);
        fragment.setSaveCallback(config -> {
            if (settingPagerAdapter != null) {
                contestConfigs.clear();
                for(Fragment frag : settingPagerAdapter.getFragmentList()){
                    if (frag instanceof ContestConfigFragment configFragment) {
                        contestConfigs.add(configFragment.getContestConfig());
                    }
                }
                YardConfig.getInstance().update();
            }
        });
        settingPagerAdapter.addFragment(fragment,
                String.format("Line %d", ++lineCount));
    }

    @NonNull
    private static List<ContestConfig> getContestConfigs(int rank, int index) {
        YardConfigModel configModel = YardConfig.getInstance().getYardConfigModel();
        YardRankConfig rankModel = switch (rank) {
            case 2 -> configModel.getC();
            case 3 -> configModel.getD();
            case 4 -> configModel.getE();
            default -> configModel.getB();
        };
        return switch (index) {
            case DVG -> rankModel.getDuongVuongGoc();
            case DS -> rankModel.getDuongS();
            case GXD -> rankModel.getDoXeDoc();
            case GXN -> rankModel.getDoXeNgang();
            default -> rankModel.getVetBanhXe();
        };
    }
}