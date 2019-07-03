package com.shushan.kencanme.mvp.ui.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.utils.DataCleanManager;
import com.shushan.kencanme.mvp.views.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * desc:清除缓存
 */
public class ClearCacheActivity extends BaseActivity implements CommonDialog.CommonDialogListener {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.clear_tv)
    TextView mClearTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);
        ButterKnife.bind(this);
        setStatusBar();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.ClearCacheActivity_title));
    }

    @Override
    public void initData() {
        try {
            mClearTv.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.common_back, R.id.clear_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.clear_tv:
                showClearCacheDialog();
                break;
        }
    }

    private void showClearCacheDialog() {
        DialogFactory.showCommonDialog(this, getResources().getString(R.string.ClearCacheActivity_clear_cache_hint), Constant.DIALOG_ONE);
    }

    /**
     * 清除缓存
     */
    @Override
    public void commonDialogBtnOkListener() {
        DataCleanManager.clearAllCache(this);
        try {
            mClearTv.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
