package com.shushan.kencanme.mvp.views.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.R;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.help.ImageLoaderHelper;
import com.shushan.kencanme.mvp.views.CircleImageView;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 匹配成功 dialog
 *
 * @author li.liu
 */
public class MatchSuccessDialog extends BaseDialogFragment {
    public static final String TAG = MatchSuccessDialog.class.getSimpleName();
    @BindView(R.id.match_success_iv)
    ImageView mMatchSuccessIv;
    @BindView(R.id.match_success_chat_tv)
    TextView mMatchSuccessChatTv;
    @BindView(R.id.dialog_match_success_layout)
    RelativeLayout mDialogMatchSuccessLayout;
    Unbinder unbinder;
    @BindView(R.id.match_mine_avatar_iv)
    CircleImageView mMatchMineAvatarIv;
    @BindView(R.id.match_friend_avatar_iv)
    CircleImageView mMatchFriendAvatarIv;
    @BindView(R.id.match_success_tv)
    TextView mMatchSuccessTv;
    private MatchSuccessListener matchSuccessListener;
    private String mMimeName, mMineAvatar, mFriendName, mFriendAvatar;
    @Inject
    protected ImageLoaderHelper mImageLoaderHelper;

    public static MatchSuccessDialog newInstance() {
        return new MatchSuccessDialog();
    }

    public void setListener(MatchSuccessListener dialogBtnListener) {
        this.matchSuccessListener = dialogBtnListener;
    }

    public void setContent(String mimeName, String mineAvatar, String friendName, String friendAvatar) {
        mMimeName = mimeName;
        mMineAvatar = mineAvatar;
        mFriendName = friendName;
        mFriendAvatar = friendAvatar;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_match_success, container, true);
        unbinder = ButterKnife.bind(this, view);
        ((KencanmeApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent().inject(this);
        initView();
        return view;
    }

    private void initView() {
        mImageLoaderHelper.displayImage(getActivity(), mMineAvatar, mMatchMineAvatarIv, R.mipmap.head_photo_loading);
        mImageLoaderHelper.displayImage(getActivity(), mFriendAvatar, mMatchFriendAvatarIv, R.mipmap.head_photo_loading);
        String successTv = mMimeName + " and " + mFriendName + getResources().getString(R.string.match_success_tv);
        mMatchSuccessTv.setText(successTv);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.match_success_iv, R.id.match_success_chat_tv, R.id.dialog_match_success_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.match_success_iv:
                break;
            case R.id.match_success_chat_tv:
                if (matchSuccessListener != null) {
                    matchSuccessListener.startChatBtnListener();
                }
                closeCommonDialog();
                break;
            case R.id.dialog_match_success_layout:
                break;
        }
    }


    public interface MatchSuccessListener {
        void startChatBtnListener();
    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }
}
