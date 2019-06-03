package com.shushan.kencanme.mvp.ui.activity.reportUser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.mvp.ui.adapter.PhotoAdapter;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 举报头像资料作假
 */
public class DataFraudActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
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
    PhotoAdapter photoAdapter;
    //默认传8张
    private int maxPicNum = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fraud);
        ButterKnife.bind(this);
        StatusBarUtil.setTransparentForImageView(this, null);
        initView();
        initData();
    }

    @Override
    public void initView() {
        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".png");
        uri = Uri.fromFile(file);
        int size = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        cropOptions = new CropOptions.Builder().setOutputX(size).setOutputX(size).setWithOwnCrop(false).create();
        mDataFraudContentEv.addTextChangedListener(search_text_OnChange);
        //设置RecyclerView第一张图片为默认图片
        photoList.add(null);
        photoAdapter = new PhotoAdapter(this, photoList, mImageLoaderHelper);
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
                                showToast("最多传8张");
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

    @OnClick({R.id.back, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit_btn:
                break;
        }
    }


    /**
     * 显示照片弹框
     */
    private void showDialog() {
        //弹出框框
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);
        builder.setTitle("选择照片");
        String[] choices = {"拍照", "从相机里选择"};
        builder.setItems(choices, (dialog, which) -> {
            switch (which) {
                case 0:
                    //拍照
                    takePhoto.onPickFromCapture(uri);
                    break;
                case 1:
                    if (maxPicNum > 0) {
                        takePhoto.onPickMultiple(maxPicNum);
                    }
                    break;
                default:
                    break;
            }
        });
        builder.show();
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
        showToast("设置失败");
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
                showToast("限制100字以内");
                s.delete(selectionStart - 1, selectionEnd);
                int tempSelection = selectionStart;
                int worldTextNum = s.length();
                mWorldLimitText.setText(worldTextNum + "/100");
                mDataFraudContentEv.setSelection(tempSelection);
            }
        }
    };

}
