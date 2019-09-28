package com.shushan.kencanme.app.mvp.ui.activity.photo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerPersonalInfoComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.PersonalInfoModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.UpdateAlbumRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.entity.response.MyAlbumResponse;
import com.shushan.kencanme.app.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.app.entity.response.UploadVideoResponse;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.mvp.ui.activity.personInfo.PersonalInfoControl;
import com.shushan.kencanme.app.mvp.utils.PicUtils;
import com.shushan.kencanme.app.mvp.utils.TranTools;
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
    @BindView(R.id.beans_one)
    TextView mBeansOne;
    @BindView(R.id.beans_five)
    TextView mBeansFive;
    @BindView(R.id.beans_custom_ev)
    EditText mBeansCustomEv;
    @BindView(R.id.ordinary_photo_ll)
    LinearLayout mOrdinaryPhotoLl;
    @BindView(R.id.vip_photo_ll)
    LinearLayout mVipPhotoLl;
    @BindView(R.id.private_photo_ll)
    LinearLayout mPrivatePhotoLl;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    //成功照片路径  用于点击完成时候判断资源是否为Empty
    private String photoUrl;
    //选择上传的图片类型  1 普通 2 VIP 3 私密
    private int picType = 0;
    //如果是私密照片 需支付嗨豆数量
    private int beansNumber = 0;
    @Inject
    PersonalInfoControl.PresenterPersonalInfo mPresenter;
    private MyAlbumResponse.DataBean dataBean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        initializeInjector();
        setStatusBar();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    public static void start(Context context, MyAlbumResponse.DataBean dataBean) {
        Intent intent = new Intent(context, UploadPhotoActivity.class);
        intent.putExtra("dataBean", dataBean);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.UploadPhotoActivity_title));
        mBeansCustomEv.addTextChangedListener(textWatcher);
        if (getIntent() != null) {
            dataBean = getIntent().getParcelableExtra("dataBean");
        }
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.common_back, R.id.photo_iv, R.id.jz_video, R.id.save_btn, R.id.beans_one, R.id.beans_five, R.id.beans_custom_ev,
            R.id.ordinary_photo_ll, R.id.vip_photo_ll, R.id.private_photo_ll})
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
            case R.id.ordinary_photo_ll:
                //普通照片
                setIvTypeBg();
                picType = 1;
                mOrdinaryPhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_choose);
                mOrdinaryPhotoTv.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                break;
            case R.id.vip_photo_ll:
                //VIP照片
                setIvTypeBg();
                picType = 2;
                mVipPhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_choose);
                mVipPhotoTv.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                break;
            case R.id.private_photo_ll:
                //私密照片
                setIvTypeBg();
                picType = 3;
                mPrivatePhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_choose);
                mPrivatePhotoTv.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                break;
            case R.id.save_btn:
                if (isValidEmpty()) {
                    UpdateAlbumRequest updateAlbumRequest = new UpdateAlbumRequest();
                    updateAlbumRequest.token = mBuProcessor.getToken();
                    updateAlbumRequest.album_type = picType;
                    updateAlbumRequest.album_url = photoUrl;
                    updateAlbumRequest.isVideo = TranTools.isVideo(photoUrl);
                    if(!TextUtils.isEmpty(taskId)){
                        updateAlbumRequest.taskId = taskId;
                    }
                    if (dataBean != null) {
                        updateAlbumRequest.id = dataBean.getId(); //从我的--相册跳转过来   进行图片修改更新   否则是增加图片
                    }
                    if (picType == 3) {
                        updateAlbumRequest.cost = beansNumber;  //嗨豆数量
                    }
                    mPresenter.updateMyAlbum(updateAlbumRequest);
                }
                break;
            case R.id.beans_one:
                initBeansBg();
                mBeansOne.setBackgroundResource(R.drawable.bg_beans_selectored_5);
                mBeansOne.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                beansNumber = 1;
                break;
            case R.id.beans_five:
                initBeansBg();
                mBeansFive.setBackgroundResource(R.drawable.bg_beans_selectored_5);
                mBeansFive.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                beansNumber = 5;
                break;
            case R.id.beans_custom_ev:
                initBeansBg();
                mBeansCustomEv.setBackgroundResource(R.drawable.bg_beans_selectored_5);
                mBeansCustomEv.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                break;
        }
    }

    private void initBeansBg() {
        mBeansOne.setBackgroundResource(R.drawable.bg_beans_selector_5);
        mBeansFive.setBackgroundResource(R.drawable.bg_beans_selector_5);
        mBeansCustomEv.setBackgroundResource(R.drawable.bg_beans_selector_5);
        mBeansOne.setTextColor(getResources().getColor(R.color.third_text_color));
        mBeansFive.setTextColor(getResources().getColor(R.color.third_text_color));
        mBeansCustomEv.setTextColor(getResources().getColor(R.color.third_text_color));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean isValidEmpty() {
        if (TextUtils.isEmpty(photoUrl)) {
            showToast(getResources().getString(R.string.UploadPhotoActivity_upload_pic_or_video_hint));
            return false;
        }
        if (picType == 0) {
            showToast(getResources().getString(R.string.UploadPhotoActivity_upload_pic_hint));
            return false;
        }
        if (picType == 3) {
            if (beansNumber == 0) {
                showToast(getResources().getString(R.string.UploadPhotoActivity_upload_beans_hint));
                return false;
            }
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
        mOrdinaryPhotoTv.setTextColor(getResources().getColor(R.color.color_80));
        mVipPhotoTv.setTextColor(getResources().getColor(R.color.color_80));
        mPrivatePhotoTv.setTextColor(getResources().getColor(R.color.color_80));
    }

    /**
     * 显示视频或者照片上传
     */
    private void showVideoDialog() {
        //弹出框框
        PhotoDialog photoDialog = PhotoDialog.newInstance();
        photoDialog.setListener(this);
        photoDialog.setData(getResources().getString(R.string.PhotoDialog_title_hint), getResources().getString(R.string.Video), getResources().getString(R.string.Photo));
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
        Bitmap bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());
        String path = PicUtils.convertIconToString(PicUtils.ImageCompressL(bitmap));
        uploadImage(path);
    }


    private void uploadImage(String filename) {
        UploadImage uploadImage = new UploadImage();
        uploadImage.dir = String.valueOf(Constant.PIC_COVER);//1头像2封面3相册
        uploadImage.file = filename;
        mPresenter.uploadImage(uploadImage);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    public void albumDialogBtnOkListener() {
        takePhoto.onPickFromGallery();
    }

    @Override
    public void photoDialogBtn3OkListener() {

    }

    @Override
    public void updateSuccess(String response) {
    }

    /**
     * 鉴黄追踪第三方taskId
     */
    String taskId;
    @Override
    public void uploadVideoSuccess(UploadVideoResponse uploadVideoResponse) {
        photoUrl = uploadVideoResponse.getUrl();
        mJzVideo.setVisibility(View.VISIBLE);
        mPhotoIv.setVisibility(View.GONE);
        mJzVideo.setUp(uploadVideoResponse.getUrl(), "");
        PicUtils.loadVideoScreenshot(this, uploadVideoResponse.getUrl(), mJzVideo.thumbImageView, 0,true);  //获取视频第一帧显示
        taskId = uploadVideoResponse.getTaskId();
//        mPersonalInfoRequest.cover = videoPath;
    }

    @Override
    public void uploadImageSuccess(String picPath) {
//        Log.e("ddd", "picPath:" + picPath);
        photoUrl = picPath;
        mJzVideo.setVisibility(View.GONE);
        mPhotoIv.setVisibility(View.VISIBLE);
        mImageLoaderHelper.displayImage(this, picPath, mPhotoIv, Constant.LOADING_MIDDLE);
//        mPersonalInfoRequest.cover = picPath;
    }

    /**
     * 图片增加/修改成功
     */
    @Override
    public void updateMyAlbumSuccess(String msg) {
        showToast(msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_MY_ALBUM));
        finish();
    }

    @Override
    public void personalInfoSuccess(PersonalInfoResponse response) {

    }


    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s)) {
                initBeansBg();
                mBeansCustomEv.setBackgroundResource(R.drawable.bg_beans_selectored_5);
                mBeansCustomEv.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                beansNumber = Integer.parseInt(s.toString());
            }
        }
    };

    private void initializeInjector() {
        DaggerPersonalInfoComponent.builder().appComponent(getAppComponent())
                .personalInfoModule(new PersonalInfoModule(UploadPhotoActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
