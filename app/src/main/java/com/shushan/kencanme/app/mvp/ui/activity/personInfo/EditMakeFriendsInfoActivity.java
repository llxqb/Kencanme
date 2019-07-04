package com.shushan.kencanme.app.mvp.ui.activity.personInfo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerPersonalInfoComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.PersonalInfoModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.mvp.utils.LoginUtils;
import com.shushan.kencanme.app.mvp.utils.PicUtils;
import com.shushan.kencanme.app.mvp.views.CircleImageView;
import com.shushan.kencanme.app.mvp.views.dialog.PhotoDialog;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 编辑交友资料
 */
public class EditMakeFriendsInfoActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener, PersonalInfoControl.PersonalInfoView, PhotoDialog.PhotoDialogListener {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.head_icon_iv)
    CircleImageView mHeadIconIv;
    @BindView(R.id.head_icon_rl)
    RelativeLayout mHeadIconRl;
    @BindView(R.id.user_name_ev)
    EditText mUserNameEv;
    @BindView(R.id.upload_hint_tv)
    TextView mUploadHintTv;
    @BindView(R.id.user_name_rl)
    RelativeLayout mUserNameRl;
    @BindView(R.id.declaration_ev)
    EditText mDeclarationEv;
    @BindView(R.id.declaration_world_limit_tv)
    TextView mDeclarationWorldLimitTv;
    @BindView(R.id.save_tv)
    TextView mSaveTv;
    @BindView(R.id.cover_iv)
    ImageView mCoverIv;
    @BindView(R.id.jz_video)
    JzvdStd mJzVideo;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Uri uri;
    //裁剪使用
    private CropOptions cropOptions;
    //成功取得照片
    Bitmap bitmap;
    private LoginUser mLoginUser = null;
    //选择背景图片还是头像图片弹框     /头像为 0   背景图为1
    private int photoOrVideo;

    @Inject
    PersonalInfoControl.PresenterPersonalInfo mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_make_friends_info);
        ButterKnife.bind(this);
        initializeInjector();
        setStatusBar();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.EditMakeFriendsInfoActivity_title));
        mDeclarationEv.addTextChangedListener(search_text_OnChange);
        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".png");
        uri = Uri.fromFile(file);
        int size = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        cropOptions = new CropOptions.Builder().setOutputX(size).setOutputX(size).setWithOwnCrop(false).create();
    }

    @Override
    public void initData() {
        mLoginUser = mBuProcessor.getLoginUser();
        if (mLoginUser.cover.trim().contains(".mp4")) {
            mJzVideo.setVisibility(View.VISIBLE);
            mCoverIv.setVisibility(View.GONE);
            mJzVideo.setUp(mLoginUser.cover, "");
            mUploadHintTv.setVisibility(View.VISIBLE);
            PicUtils.loadVideoScreenshot(this, mLoginUser.cover, mJzVideo.thumbImageView, 0);
        } else {
            mJzVideo.setVisibility(View.GONE);
            mCoverIv.setVisibility(View.VISIBLE);
            mUploadHintTv.setVisibility(View.GONE);
            mImageLoaderHelper.displayImage(this, mLoginUser.cover, mCoverIv, Constant.LOADING_MIDDLE);
        }
        mImageLoaderHelper.displayImage(this, mLoginUser.trait, mHeadIconIv, Constant.LOADING_SMALL);
        mUserNameEv.setText(mLoginUser.nickname);
        mDeclarationEv.setText(mLoginUser.declaration);
        //光标移到最后
        mUserNameEv.setSelection(mUserNameEv.getText().length());
        mDeclarationEv.setSelection(mDeclarationEv.getText().length());
    }

    @OnClick({R.id.common_back, R.id.cover_iv, R.id.head_icon_rl, R.id.save_tv, R.id.upload_hint_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.cover_iv:
                showVideoDialog();
                break;
            case R.id.head_icon_rl:
                showPhotoDialog();
                break;
            case R.id.upload_hint_tv:
                showVideoDialog();
                break;
            case R.id.save_tv:
                if (isValidEmpty()) {
                    UpdatePersonalInfoRequest personalInfoResponse = LoginUtils.tranPersonalInfoResponse(mLoginUser);
                    personalInfoResponse.nickname = mUserNameEv.getText().toString();
                    personalInfoResponse.declaration = mDeclarationEv.getText().toString();
                    mPresenter.onRequestPersonalInfo(personalInfoResponse);
                }
                break;
        }
    }

    /**
     * 验证界面是否为空
     */
    private boolean isValidEmpty() {
        if (TextUtils.isEmpty(mUserNameEv.getText())) {
            showToast(getResources().getString(R.string.CreatePersonalInfoActivity_nice_is_empty));
            return false;
        }
        if (TextUtils.isEmpty(mDeclarationEv.getText())) {
            showToast(getResources().getString(R.string.PersonalInfoUploadPhotoActivity_declaration_is_empty));
            return false;
        }
        return true;
    }


    /**
     * 显示照片弹框
     */
    private void showPhotoDialog() {
        photoOrVideo = 0;
        //弹出框框
        PhotoDialog photoDialog = PhotoDialog.newInstance();
        photoDialog.setListener(this);
        photoDialog.setData(getResources().getString(R.string.PhotoDialog_title), getResources().getString(R.string.PhotoDialog_photo), getResources().getString(R.string.PhotoDialog_album));
        DialogFactory.showDialogFragment(Objects.requireNonNull(this).getSupportFragmentManager(), photoDialog, PhotoDialog.TAG);
    }

    /**
     * 显示视频或者照片上传
     */
    private void showVideoDialog() {
        photoOrVideo = 1;
        //弹出框框
        PhotoDialog photoDialog = PhotoDialog.newInstance();
        photoDialog.setListener(this);
        photoDialog.setData("Select video or photo", "Video", "Photo");
        DialogFactory.showDialogFragment(Objects.requireNonNull(this).getSupportFragmentManager(), photoDialog, PhotoDialog.TAG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};
            assert selectedVideo != null;
            Cursor cursor = getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String videoPath = cursor.getString(columnIndex);
            cursor.close();
            //上传视频
            File file = new File(videoPath);
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("video", file.getName(), photoRequestBody);
            mPresenter.uploadVideo(photo);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void takeSuccess(TResult result) {
        bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());
        String path = PicUtils.convertIconToString(bitmap);
        uploadImage(path);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    private void uploadImage(String filename) {
        UploadImage uploadImage = new UploadImage();
        uploadImage.dir = String.valueOf(Constant.PIC_AVATOR);//1头像2封面3相册
        uploadImage.file = filename;
        mPresenter.uploadImage(uploadImage);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        //设置压缩规则，最大500kb
        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).setMaxPixel(800).create(), true);
        return takePhoto;
    }


    @Override
    public void updateSuccess(String response) {
        showToast(response);
        //更新用户信息
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_USER_INFO));
        mLoginUser.nickname = mUserNameEv.getText().toString();
        mLoginUser.declaration = mDeclarationEv.getText().toString();
        mBuProcessor.setLoginUser(mLoginUser);
        finish();
    }

    @Override
    public void uploadVideoSuccess(String videoPath) {
        mUploadHintTv.setVisibility(View.VISIBLE);
        mJzVideo.setVisibility(View.VISIBLE);
        mCoverIv.setVisibility(View.GONE);
        mJzVideo.setUp(videoPath, "");
        //获取视频第一帧
        PicUtils.loadVideoScreenshot(this, videoPath, mJzVideo.thumbImageView, 0);
        mLoginUser.cover = videoPath;
    }


    @Override
    public void uploadImageSuccess(String picPath) {
        if (photoOrVideo == 0) {
            mImageLoaderHelper.displayImage(this, picPath, mHeadIconIv, Constant.LOADING_SMALL);
            mLoginUser.trait = picPath;
        } else {
            mUploadHintTv.setVisibility(View.VISIBLE);
            mJzVideo.setVisibility(View.GONE);
            mCoverIv.setVisibility(View.VISIBLE);
            mImageLoaderHelper.displayImage(this, picPath, mCoverIv, Constant.LOADING_MIDDLE);
            mLoginUser.cover = picPath;
        }
    }

    @Override
    public void updateMyAlbumSuccess(String msg) {

    }


    @Override
    public void photoDialogBtnOkListener() {
        if (photoOrVideo == 0) {
            //拍照进行裁剪
            takePhoto.onPickFromCapture(uri);
        } else {
            //打开视频
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 100);
        }

    }

    @Override
    public void albumDialogBtnOkListener() {
        if (photoOrVideo == 0) {
            //从相册中选取进行裁剪
            takePhoto.onPickFromGalleryWithCrop(uri, cropOptions);
        } else {
            //从相册中选取
            takePhoto.onPickFromGallery();
        }
    }

    @Override
    public void photoDialogBtn3OkListener() {

    }

    public TextWatcher search_text_OnChange = new TextWatcher() {
        private int selectionStart;
        private int selectionEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            selectionStart = mDeclarationEv.getSelectionStart();
            selectionEnd = mDeclarationEv.getSelectionEnd();
            int worldTextNum = s.length();
            if (s.length() > 80) {
                showToast(getResources().getString(R.string.DataFraudActivity_only_100_word));
                s.delete(selectionStart - 1, selectionEnd);
                int tempSelection = selectionStart;
                mDeclarationWorldLimitTv.setText(worldTextNum + "/80");
                mDeclarationEv.setSelection(tempSelection);
            } else {
                mDeclarationWorldLimitTv.setText(worldTextNum + "/80");
            }
        }
    };

    private void initializeInjector() {
        DaggerPersonalInfoComponent.builder().appComponent(getAppComponent())
                .personalInfoModule(new PersonalInfoModule(EditMakeFriendsInfoActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

}
