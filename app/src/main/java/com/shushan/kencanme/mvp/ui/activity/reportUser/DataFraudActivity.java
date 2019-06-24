package com.shushan.kencanme.mvp.ui.activity.reportUser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerReportUserComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.ReportUserModule;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.ReportUserRequest;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.adapter.FraudPhotoAdapter;
import com.shushan.kencanme.mvp.utils.LogUtils;
import com.shushan.kencanme.mvp.utils.PicUtils;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.views.dialog.PhotoDialog;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 举报头像资料作假
 */
public class DataFraudActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener, PhotoDialog.PhotoDialogListener, ReportUserControl.ReportUserView {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.data_fraud_content_ev)
    EditText mDataFraudContentEv;
    @BindView(R.id.world_limit_text)
    TextView mWorldLimitText;//最大限制100字
    @BindView(R.id.submit_btn)
    Button mSubmitBtn;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Uri uri;
    //裁剪使用
    private CropOptions cropOptions;

    //选择照片的路径集合
    private ArrayList<TImage> photoList = new ArrayList<>();
    FraudPhotoAdapter photoAdapter;
    //默认传8张
    private int maxPicNum = 8;
    private String uid;
    /**
     * 上传成功后图片集合
     */
    private List<String> mPicList = new ArrayList<>();
    @Inject
    ReportUserControl.PresenterReportUser mPresenter;


    public static void start(Context context, String uid) {
        Intent intent = new Intent(context, DataFraudActivity.class);
        intent.putExtra("uid", uid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fraud);
        ButterKnife.bind(this);
        StatusBarUtil.setTransparentForImageView(this, null);
        initializeInjector();
        initView();
        initData();
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            uid = getIntent().getStringExtra("uid");
        }
        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".png");
        uri = Uri.fromFile(file);
        int size = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        cropOptions = new CropOptions.Builder().setOutputX(size).setOutputX(size).setWithOwnCrop(false).create();
        mDataFraudContentEv.addTextChangedListener(search_text_OnChange);
        //设置RecyclerView第一张图片为默认图片
        photoList.add(null);
        photoAdapter = new FraudPhotoAdapter(this, photoList, mImageLoaderHelper);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(photoAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.photo_delete:
                        photoAdapter.remove(position);
                        photoAdapter.notifyDataSetChanged();
                        break;
                    case R.id.photo_item_rl:
                        if (position == 0) {
                            if (photoAdapter.getItemCount() >= 9) {
                                showToast(getResources().getString(R.string.fraud_album_max_num));
                            } else {
                                showDialog();
                            }
                        } else {
                            showToast("" + position);
                            //查看大图
//                TImage bean = photoAdapter.getItem(position);
//                if (bean != null) {
//                }
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
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

    @OnClick({R.id.back, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit_btn:
                //上传图片
                if (TextUtils.isEmpty(mDataFraudContentEv.getText())) {
                    showToast(getResources().getString(R.string.DataFraudActivity_desc_is_null));
                    return;
                }
                for (int i = 0; i < photoList.size(); i++) {
                    TImage tImage = photoList.get(i);
                    Bitmap bitmap = BitmapFactory.decodeFile(tImage.getCompressPath());
                    String path = PicUtils.convertIconToString(bitmap);
                    uploadImage(path);
                }
                break;
        }
    }


    /**
     * 显示照片弹框
     */
    private void showDialog() {
        //弹出框框
        PhotoDialog photoDialog = PhotoDialog.newInstance();
        photoDialog.setListener(this);
        photoDialog.setData(getResources().getString(R.string.PhotoDialog_title), getResources().getString(R.string.PhotoDialog_photo), getResources().getString(R.string.PhotoDialog_album));
        DialogFactory.showDialogFragment(Objects.requireNonNull(this).getSupportFragmentManager(), photoDialog, PhotoDialog.TAG);
    }

    /**
     * 上传图片
     */
    private void uploadImage(String filename) {
        UploadImage uploadImage = new UploadImage();
        uploadImage.dir = String.valueOf(Constant.PIC_REPORT);//1头像2封面3相册4举报5消息
        uploadImage.file = filename;
        mPresenter.uploadImage(uploadImage);
    }

    @Override
    public void takeSuccess(TResult result) {
        //成功取得照片
        photoList = result.getImages();
        maxPicNum = maxPicNum - photoList.size();
        photoAdapter.addData(photoList);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        //取得失败
        showToast("fail");
    }

    @Override
    public void takeCancel() {

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
            selectionStart = mDataFraudContentEv.getSelectionStart();
            selectionEnd = mDataFraudContentEv.getSelectionEnd();
            if (s.length() > 100) {
                showToast(getResources().getString(R.string.DataFraudActivity_only_word));
                s.delete(selectionStart - 1, selectionEnd);
                int tempSelection = selectionStart;
                int worldTextNum = s.length();
                mWorldLimitText.setText(worldTextNum + "/100");
                mDataFraudContentEv.setSelection(tempSelection);
            }
        }
    };

    @Override
    public void photoDialogBtnOkListener() {
        takePhoto.onPickFromCapture(uri);
    }

    @Override
    public void albumDialogBtnOkListener() {
        if (maxPicNum > 0) {
            takePhoto.onPickMultiple(maxPicNum);
        }
    }

    @Override
    public void photoDialogBtn3OkListener() {

    }

    /**
     * 上传图片成功
     */
    @Override
    public void uploadImageSuccess(String picPath) {
        mPicList.add(picPath);
        if (mPicList != null && mPicList.size() == photoList.size()) {
            //上传完最后一张图片 进行举报
            ReportUserRequest reportUserRequest = new ReportUserRequest();
            reportUserRequest.token = mBuProcessor.getToken();
            reportUserRequest.uid = uid;
            reportUserRequest.reason = "1";
            LogUtils.e("mPicList:" + new Gson().toJson(mPicList));
            reportUserRequest.pics = new Gson().toJson(mPicList);
            if (!TextUtils.isEmpty(mDataFraudContentEv.getText())) {
                reportUserRequest.describe = mDataFraudContentEv.getText().toString();
            }
            mPresenter.onRequestReportUser(reportUserRequest);
        }
    }

    /**
     * 举报用户成功
     */
    @Override
    public void reportUserSuccess(String msg) {
        showToast(msg);
        finish();
    }


    private void initializeInjector() {
        DaggerReportUserComponent.builder().appComponent(getAppComponent())
                .reportUserModule(new ReportUserModule(DataFraudActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

}
