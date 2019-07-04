package com.shushan.kencanme.app.mvp.ui.activity.personInfo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerPersonalInfoComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.PersonalInfoModule;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.mvp.ui.activity.main.MainActivity;
import com.shushan.kencanme.app.mvp.utils.PicUtils;
import com.shushan.kencanme.app.mvp.views.dialog.PhotoDialog;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
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
 * 上传照片
 */
public class PersonalInfoUploadPhotoActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener, PersonalInfoControl.PersonalInfoView, PhotoDialog.PhotoDialogListener {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.photo_iv_rl)
    ImageView mPhotoIvRl;
    @BindView(R.id.jz_video)
    JzvdStd mJzVideo;
    @BindView(R.id.declaration_ev)
    EditText mdeclarationEv;
    @BindView(R.id.upload_photo_world_limit_text)
    TextView mUploadPhotoWorldLimitText;
    @BindView(R.id.complete_btn)
    Button mCompleteBtn;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    String mToken;
    //成功取得照片
    Bitmap bitmap;
    private LoginUser mLoginUser;

    private UpdatePersonalInfoRequest mPersonalInfoRequest;
    @Inject
    PersonalInfoControl.PresenterPersonalInfo mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_upload_photo);
        initializeInjector();
        ButterKnife.bind(this);
        setStatusBar();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mToken = mBuProcessor.getToken();
        if (getIntent() != null) {
            mPersonalInfoRequest = getIntent().getParcelableExtra("personalInfoRequest");
        }
    }

    @Override
    public void initData() {
        mLoginUser = mBuProcessor.getLoginUser();
    }

    @OnClick({R.id.common_back, R.id.photo_iv_rl, R.id.complete_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.photo_iv_rl:
                showVideoDialog();
                break;
            case R.id.complete_btn:
//                startActivitys(AgreementActivity.class);
//                finish();
                if (isValidEmpty()) {
                    mPersonalInfoRequest.declaration = mdeclarationEv.getText().toString();
                    mPresenter.onRequestPersonalInfo(mPersonalInfoRequest);
                }
                break;
        }
    }

    private boolean isValidEmpty() {
        if (TextUtils.isEmpty(mdeclarationEv.getText())) {
            showToast(getResources().getString(R.string.PersonalInfoUploadPhotoActivity_declaration_is_empty));
            return false;
        }
        if(mPersonalInfoRequest.cover==null){
            showToast(getResources().getString(R.string.CreatePersonalInfoActivity_cover_is_empty));
            return false;
        }
        return true;
    }

    /**
     * 显示视频或者照片上传
     */
    private void showVideoDialog() {
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


    private void uploadImage(String filename) {
        UploadImage uploadImage = new UploadImage();
        uploadImage.dir = String.valueOf(Constant.PIC_COVER);//1头像2封面3相册
        uploadImage.file = filename;
        mPresenter.uploadImage(uploadImage);
    }


    @Override
    public void takeFail(TResult result, String msg) {
        //取得失败
        showToast(msg);
    }

    @Override
    public void takeCancel() {

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
        updateUserInfo();
        startActivitys(MainActivity.class);
        finish();
    }

    private void updateUserInfo() {
        mLoginUser.nickname = mPersonalInfoRequest.nickname;
        mLoginUser.sex = mPersonalInfoRequest.sex;
        mLoginUser.birthday = mPersonalInfoRequest.birthday;
        mLoginUser.city = mPersonalInfoRequest.city;
        mLoginUser.cover = mPersonalInfoRequest.cover;
        mLoginUser.declaration = mPersonalInfoRequest.declaration;
        mBuProcessor.setLoginUser(mLoginUser);
    }

    @Override
    public void uploadVideoSuccess(String videoPath) {
        mJzVideo.setVisibility(View.VISIBLE);
        mPhotoIvRl.setVisibility(View.GONE);
        mJzVideo.setUp(videoPath, "");
        //获取视频第一帧
        PicUtils.loadVideoScreenshot(this, videoPath, mJzVideo.thumbImageView, 0);
        mPersonalInfoRequest.cover = videoPath;
    }


    @Override
    public void uploadImageSuccess(String picPath) {
//        Log.e("ddd", "picPath:" + picPath);
        mJzVideo.setVisibility(View.GONE);
        mPhotoIvRl.setVisibility(View.VISIBLE);
        mPhotoIvRl.setImageBitmap(bitmap);
        mPersonalInfoRequest.cover = picPath;
    }


    @Override
    public void updateMyAlbumSuccess(String msg) {

    }

    public static void start(Context context, UpdatePersonalInfoRequest personalInfoRequest) {
        Intent intent = new Intent(context, PersonalInfoUploadPhotoActivity.class);
        intent.putExtra("personalInfoRequest", personalInfoRequest);
        context.startActivity(intent);
    }


    @Override
    public void photoDialogBtnOkListener() {
        //打开视频
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    public void albumDialogBtnOkListener() {
        takePhoto.onPickFromGallery();
    }

    @Override
    public void photoDialogBtn3OkListener() {

    }

    private void initializeInjector() {
        DaggerPersonalInfoComponent.builder().appComponent(getAppComponent())
                .personalInfoModule(new PersonalInfoModule(PersonalInfoUploadPhotoActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
