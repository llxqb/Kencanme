package com.shushan.kencanme.app.mvp.ui.activity.setting;

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
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerSettingComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.SettingModule;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.FeedbackProblemRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.mvp.ui.adapter.FraudPhotoAdapter;
import com.shushan.kencanme.app.mvp.utils.LogUtils;
import com.shushan.kencanme.app.mvp.utils.PicUtils;
import com.shushan.kencanme.app.mvp.views.dialog.PhotoDialog;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
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
 * 问题反馈
 */
public class FeedbackProblemActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener, PhotoDialog.PhotoDialogListener, SettingControl.SettingView {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.question_desc_ev)
    EditText mQuestionDescEv;
    @BindView(R.id.world_limit_tv)
    TextView mWorldLimitTv;
    @BindView(R.id.photo_select_num)
    TextView mPhotoSelectNum;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.email_ev)
    EditText mEmailEv;
    @BindView(R.id.save_btn)
    Button mSaveBtn;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Uri uri;
    //选择照片的路径集合
    private ArrayList<TImage> photoList = new ArrayList<>();
    FraudPhotoAdapter photoAdapter;
    //默认传8张
    private int maxPicNum = 6;
    /**
     * 上传成功后图片集合
     */
    private List<String> mPicList = new ArrayList<>();
    @Inject
    SettingControl.PresenterSetting mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_problem);
        ButterKnife.bind(this);
        initializeInjector();
        setStatusBar();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.FeedbackProblemActivity_title));
        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".png");
        uri = Uri.fromFile(file);
        mQuestionDescEv.addTextChangedListener(search_text_OnChange);
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
                        String photoNum = (photoAdapter.getItemCount() - 1) + "/6";
                        maxPicNum = maxPicNum + 1;
                        mPhotoSelectNum.setText(photoNum);
                        break;
                    case R.id.photo_item_rl:
                        if (position == 0) {
                            if (photoAdapter.getItemCount() >= 7) {
                                showToast(getResources().getString(R.string.album_num_max_6));
                            } else {
                                showDialog();
                            }
                        } else {
                            //查看大图
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

    @OnClick({R.id.common_back, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.save_btn:
                //上传图片
                if (isValidEmpty()) {
                    for (int i = 0; i < photoList.size(); i++) {
                        TImage tImage = photoList.get(i);
                        Bitmap bitmap = BitmapFactory.decodeFile(tImage.getCompressPath());
                        String path = PicUtils.convertIconToString(bitmap);
                        uploadImage(path);
                    }
                }
                break;
        }
    }

    private boolean isValidEmpty() {
        if (TextUtils.isEmpty(mQuestionDescEv.getText())) {
            showToast(getResources().getString(R.string.FeedbackProblemActivity_problem_desc_null));
            return false;
        }
        return true;
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
        String photoNum = photoAdapter.getItemCount() + "/6";
        mPhotoSelectNum.setText(photoNum);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        //取得失败
        showToast("fail");
    }

    @Override
    public void takeCancel() {

    }

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
            //上传完最后一张图片 问题反馈
            FeedbackProblemRequest feedbackProblemRequest = new FeedbackProblemRequest();
            feedbackProblemRequest.token = mBuProcessor.getToken();
            feedbackProblemRequest.problem = mQuestionDescEv.getText().toString();
            feedbackProblemRequest.email = mEmailEv.getText().toString();
            LogUtils.e("mPicList:" + new Gson().toJson(mPicList));
            feedbackProblemRequest.pics = new Gson().toJson(mPicList);
            mPresenter.onRequestFeedbackProblem(feedbackProblemRequest);
        }
    }

    @Override
    public void feedbackProblemSuccess(String msg) {
        showToast(msg);
        finish();
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
            selectionStart = mQuestionDescEv.getSelectionStart();
            selectionEnd = mQuestionDescEv.getSelectionEnd();
            int worldTextNum = s.length();
            if (s.length() > 400) {
                showToast(getResources().getString(R.string.FeedbackProblemActivity_only_400_word));
                s.delete(selectionStart - 1, selectionEnd);
                int tempSelection = selectionStart;
                mWorldLimitTv.setText(worldTextNum + "/400");
                mQuestionDescEv.setSelection(tempSelection);
            } else {
                mWorldLimitTv.setText(worldTextNum + "/400");
            }
        }
    };


    private void initializeInjector() {
        DaggerSettingComponent.builder().appComponent(getAppComponent())
                .settingModule(new SettingModule(FeedbackProblemActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    public void updateSuccess(String msg) {
    }

    @Override
    public void updateFail(String errorMsg) {
    }
}
