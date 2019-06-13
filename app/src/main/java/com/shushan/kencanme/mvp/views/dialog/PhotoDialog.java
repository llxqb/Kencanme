package com.shushan.kencanme.mvp.views.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.help.DialogFactory;

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
    Unbinder unbinder;
    private PhotoDialogListener dialogBtnListener;
    private String mTitle, mPhoto, mAlbum;

    public static PhotoDialog newInstance() {
        return new PhotoDialog();
    }

    public void setData(String title, String photo, String album) {
        mTitle = title;
        mPhoto = photo;
        mAlbum = album;
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
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.dialog_photo, R.id.dialog_album, R.id.dialog_photo_layout})
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
            case R.id.dialog_photo_layout:
                closeCommonDialog();
                break;
        }
    }


    public interface PhotoDialogListener {
        void photoDialogBtnOkListener();

        void albumDialogBtnOkListener();
    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
