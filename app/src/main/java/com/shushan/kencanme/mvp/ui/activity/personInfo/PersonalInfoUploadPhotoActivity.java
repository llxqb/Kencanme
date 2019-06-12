package com.shushan.kencanme.mvp.ui.activity.personInfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerPersonalInfoComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.PersonalInfoModule;
import com.shushan.kencanme.entity.Constant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.entity.response.UpdatePersonalInfoResponse;
import com.shushan.kencanme.mvp.ui.activity.main.MainActivity;
import com.shushan.kencanme.mvp.utils.PicUtils;

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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 上传照片
 */
public class PersonalInfoUploadPhotoActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener, PersonalInfoControl.PersonalInfoView {

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
        initView();
        initData();
    }

    @Override
    public void initView() {
        mToken = mBuProcessor.getToken();
        Log.e("ddd", "mToken:" + mToken);
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);
        if (getIntent() != null) {
            mPersonalInfoRequest = getIntent().getParcelableExtra("personalInfoRequest");
        }
    }

    @Override
    public void initData() {

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
        return true;
    }

    /**
     * 显示视频或者照片上传
     */
    private void showVideoDialog() {
        //弹出框框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select video or photo");
        String[] choices = {"Video", "Photo"};
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        //打开视频
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 100);
                        break;
                    case 1:
                        takePhoto.onPickFromGallery();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
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
        showToast("设置失败");
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
    public void updateSuccess(UpdatePersonalInfoResponse response) {
        Log.e("ddd", "response:" + new Gson().toJson(response));
        showToast("创建成功");
        startActivitys(MainActivity.class);
        finish();
    }

    @Override
    public void updateFail(String errorMsg) {
        showToast("保存失败");
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
    public void uploadVideoFail(String msg) {
        showToast(msg);
    }


    @Override
    public void uploadImageSuccess(String picPath) {
        Log.e("ddd", "picPath:" + picPath);
        mJzVideo.setVisibility(View.GONE);
        mPhotoIvRl.setVisibility(View.VISIBLE);
        mPhotoIvRl.setImageBitmap(bitmap);
        mPersonalInfoRequest.cover = picPath;
    }

    @Override
    public void uploadImageFail(String msg) {

    }

    public static void start(Context context, UpdatePersonalInfoRequest personalInfoRequest) {
        Intent intent = new Intent(context, PersonalInfoUploadPhotoActivity.class);
        intent.putExtra("personalInfoRequest", personalInfoRequest);
        context.startActivity(intent);
    }

    private void initializeInjector() {
        DaggerPersonalInfoComponent.builder().appComponent(getAppComponent())
                .personalInfoModule(new PersonalInfoModule(PersonalInfoUploadPhotoActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
