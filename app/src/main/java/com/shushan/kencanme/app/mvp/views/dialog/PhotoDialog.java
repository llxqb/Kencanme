package com.shushan.kencanme.app.mvp.views.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.help.DialogFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 照片 dialog
 *
 * @author li.liu
 */
public class PhotoDialog extends BaseDialogFragment {
    public static final String TAG = PhotoDialog.class.getSimpleName();
    @BindView(R.id.dialog_photo_layout)
    RelativeLayout mDialogPhotoLayout;
    @BindView(R.id.dialog_title)
    TextView mDialogTitle;
    @BindView(R.id.dialog_photo)
    Button mDialogPhoto;
    @BindView(R.id.dialog_album)
    Button mDialogAlbum;
    @BindView(R.id.dialog_text3_btn)
    Button mDialogBtn3;
    Unbinder unbinder;
    private PhotoDialogListener dialogBtnListener;
    private String mTitle, mPhoto, mAlbum;
    private String mBtn3Text;

    public static PhotoDialog newInstance() {
        return new PhotoDialog();
    }

    public void setData(String title, String photo, String album) {
        mTitle = title;
        mPhoto = photo;
        mAlbum = album;
    }

    public void setData(String title, String photo, String album, String btn3Text) {
        mTitle = title;
        mPhoto = photo;
        mAlbum = album;
        mBtn3Text = btn3Text;
    }

    public void setListener(PhotoDialogListener photoBtnListener) {
        this.dialogBtnListener = photoBtnListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_photo, container, true);
        unbinder = ButterKnife.bind(this, view);
        mDialogTitle.setText(mTitle);
        mDialogPhoto.setText(mPhoto);
        mDialogAlbum.setText(mAlbum);
        if (!TextUtils.isEmpty(mBtn3Text)) {
            mDialogBtn3.setVisibility(View.VISIBLE);
            mDialogBtn3.setText(mBtn3Text);
        } else {
            mDialogBtn3.setVisibility(View.GONE);
        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.dialog_photo, R.id.dialog_album, R.id.dialog_photo_layout, R.id.dialog_text3_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_photo:
                if (dialogBtnListener != null) {
                    dialogBtnListener.photoDialogBtnOkListener();
                }
                closeCommonDialog();
                break;
            case R.id.dialog_album:
                if (dialogBtnListener != null) {
                    dialogBtnListener.albumDialogBtnOkListener();
                }
                closeCommonDialog();
                break;
            case R.id.dialog_text3_btn:
                if (dialogBtnListener != null) {
                    dialogBtnListener.photoDialogBtn3OkListener();
                }
                closeCommonDialog();
                break;
            case R.id.dialog_photo_layout:
                closeCommonDialog();
                break;
        }
    }


    public interface PhotoDialogListener {
        void photoDialogBtnOkListener();

        void albumDialogBtnOkListener();

        void photoDialogBtn3OkListener();
    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
