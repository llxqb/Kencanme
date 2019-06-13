package com.shushan.kencanme.mvp.ui.activity.photo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerPersonalInfoComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.PersonalInfoModule;
import com.shushan.kencanme.entity.Constants.ActivityConstant;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.UpdateAlbumRequest;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.entity.response.UpdatePersonalInfoResponse;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.activity.personInfo.PersonalInfoControl;
import com.shushan.kencanme.mvp.utils.PicUtils;
import com.shushan.kencanme.mvp.utils.TranTools;
import com.shushan.kencanme.mvp.views.dialog.PhotoDialog;

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
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * desc:上传照片或者视频
 */
public class UploadPhotoActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener, PersonalInfoControl.PersonalInfoView, PhotoDialog.PhotoDialogListener {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.photo_iv)
    ImageView mPhotoIv;
    @BindView(R.id.jz_video)
    JzvdStd mJzVideo;
    @BindView(R.id.ordinary_photo_check_iv)
    ImageView mOrdinaryPhotoCheckIv;
    @BindView(R.id.ordinary_photo_tv)
    TextView mOrdinaryPhotoTv;
    @BindView(R.id.vip_photo_check_iv)
    ImageView mVipPhotoCheckIv;
    @BindView(R.id.vip_photo_tv)
    TextView mVipPhotoTv;
    @BindView(R.id.private_photo_check_iv)
    ImageView mPrivatePhotoCheckIv;
    @BindView(R.id.private_photo_tv)
    TextView mPrivatePhotoTv;
    @BindView(R.id.save_btn)
    Button mSaveBtn;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    //成功照片路径  用于点击完成时候判断资源是否为Empty
    private String photoUrl;
    //选择上传的图片类型  1 普通 2 VIP 3 私密
    private int picType = 0;
    private UpdateAlbumRequest updateAlbumRequest;

    @Inject
    PersonalInfoControl.PresenterPersonalInfo mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        initializeInjector();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.UploadPhotoActivity_title));
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);//播放填充满背景，不带黑色背景
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.common_back, R.id.photo_iv, R.id.jz_video, R.id.ordinary_photo_check_iv, R.id.vip_photo_check_iv, R.id.private_photo_check_iv, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.photo_iv:
                showVideoDialog();
                break;
            case R.id.jz_video:
                break;
            case R.id.ordinary_photo_check_iv:
                //普通照片
                setIvTypeBg();
                mOrdinaryPhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_choose);
                picType = 1;
                break;
            case R.id.vip_photo_check_iv:
                //VIP照片
                setIvTypeBg();
                mVipPhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_choose);
                picType = 2;
                break;
            case R.id.private_photo_check_iv:
                //私密照片
                setIvTypeBg();
                mPrivatePhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_choose);
                picType = 3;
                break;
            case R.id.save_btn:
                if (isValidEmpty()) {
                    updateAlbumRequest = new UpdateAlbumRequest();
                    updateAlbumRequest.token = mBuProcessor.getToken();
                    updateAlbumRequest.album_type = String.valueOf(picType);
                    updateAlbumRequest.album_url = photoUrl;
                    updateAlbumRequest.isVideo = TranTools.isVideo(photoUrl);
                    //todo
                    mPresenter.updateMyAlbum(updateAlbumRequest);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean isValidEmpty() {
        if (TextUtils.isEmpty(photoUrl)) {
            showToast("请先上传图片或者视频");
            return false;
        }
        if (picType == 0) {
            showToast("请选择照片类型");
            return false;
        }
        return true;
    }


    /**
     * 重置图片背景资源 check
     */
    private void setIvTypeBg() {
        mOrdinaryPhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_no_choose);
        mVipPhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_no_choose);
        mPrivatePhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_no_choose);
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
        Bitmap bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());
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
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

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
    public void updateSuccess(UpdatePersonalInfoResponse response) {
    }

    @Override
    public void updateFail(String errorMsg) {
    }

    @Override
    public void uploadVideoSuccess(String videoPath) {
        Log.e("ddd", "videoPath:" + videoPath);
        photoUrl = videoPath;
        mJzVideo.setVisibility(View.VISIBLE);
        mPhotoIv.setVisibility(View.GONE);
        mJzVideo.setUp(videoPath, "");
        PicUtils.loadVideoScreenshot(this, videoPath, mJzVideo.thumbImageView, 0);  //获取视频第一帧显示
//        mPersonalInfoRequest.cover = videoPath;
    }

    @Override
    public void uploadVideoFail(String msg) {
        showToast(msg);
    }

    @Override
    public void uploadImageSuccess(String picPath) {
        Log.e("ddd", "picPath:" + picPath);
        photoUrl = picPath;
        mJzVideo.setVisibility(View.GONE);
        mPhotoIv.setVisibility(View.VISIBLE);
        mImageLoaderHelper.displayMatchImage(this, picPath, mPhotoIv);
//        mPersonalInfoRequest.cover = picPath;
    }

    @Override
    public void uploadImageFail(String msg) {
        showToast(msg);
    }

    /**
     * 图片增加/修改成功
     * @param msg
     */
    @Override
    public void updateMyAlbumSuccess(String msg) {
        showToast(msg);
        Intent intent = new Intent(ActivityConstant.UPDATE_MY_ALBUM);
        intent.putExtra("updateAlbumRequest", updateAlbumRequest);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }


    private void initializeInjector() {
        DaggerPersonalInfoComponent.builder().appComponent(getAppComponent())
                .personalInfoModule(new PersonalInfoModule(UploadPhotoActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
