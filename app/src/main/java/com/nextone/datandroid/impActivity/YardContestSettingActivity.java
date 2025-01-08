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

import com.nextone.common.YardConfig;
import com.nextone.datandroid.MyActivity;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.tabLayout.tabFragmant.ContestConfigFragment;
import com.nextone.model.yardConfigMode.ContestConfig;
import com.nextone.model.yardConfigMode.YardConfigModel;
import com.nextone.model.yardConfigMode.YardRankConfig;

public class YardContestSettingActivity extends MyActivity {

    public static final int DXCNDB = 0;
    public static final int DXND = 1;
    public static final int NGTU1 = 2;
    public static final int NGTU2 = 3;
    public static final int NGTU3 = 4;
    public static final int NGTU4 = 5;
    public static final int TANG_TOC = 6;
    public static final int DUONG_TAU = 7;
    public static final String RANK_KEY = "rank";
    public static final String CONTEST_INDEX_KEY = "contestIndex";
    public static final String CONTEST_NAME_KEY = "contestName";

    private ContestConfigFragment contestConfigFragment;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_yard_contest_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        int rank = getIntent().getIntExtra(RANK_KEY, 1);
        int index = getIntent().getIntExtra(CONTEST_INDEX_KEY, 0);
        String name = getIntent().getStringExtra(CONTEST_NAME_KEY);
        TextView textName = findViewById(R.id.textName);
        textName.setText(name);
        contestConfigFragment = (ContestConfigFragment) getSupportFragmentManager().
                findFragmentById(R.id.fragmentContainerView);
        if (contestConfigFragment != null) {
            contestConfigFragment.setContestConfig(getContestConfig(rank, index));
            contestConfigFragment.setSaveCallback(config -> {
                YardConfig.getInstance().update();
            });
        }

    }

    @Override
    protected void onDestroy() {
        if (contestConfigFragment != null) {
            contestConfigFragment.saveData();
        }
        super.onDestroy();
    }


    @NonNull
    private static ContestConfig getContestConfig(int rank, int index) {
        YardConfigModel configModel = YardConfig.getInstance().getYardConfigModel();
        YardRankConfig rankModel = switch (rank) {
            case 2 -> configModel.getC();
            case 3 -> configModel.getD();
            case 4 -> configModel.getE();
            default -> configModel.getB();
        };
        return switch (index) {
            case DXND -> rankModel.getDungXeNgangDoc();
            case NGTU1 -> rankModel.getNgaTu1();
            case NGTU2 -> rankModel.getNgaTu2();
            case NGTU3 -> rankModel.getNgaTu3();
            case NGTU4 -> rankModel.getNgaTu4();
            case TANG_TOC -> rankModel.getTangToc();
            case DUONG_TAU -> rankModel.getDuongTau();
            default -> rankModel.getDungXeChoNg();
        };
    }
}