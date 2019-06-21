package com.shushan.kencanme.mvp.ui.activity.rongCloud;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerConversationComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.ConversationModule;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.entity.request.UseBeansRequest;
import com.shushan.kencanme.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.help.MyConversationClickListener;
import com.shushan.kencanme.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.mvp.utils.AppUtils;
import com.shushan.kencanme.mvp.utils.PicUtils;
import com.shushan.kencanme.mvp.utils.TranTools;
import com.shushan.kencanme.mvp.views.CommonDialog;
import com.shushan.kencanme.mvp.views.dialog.CommonChoiceDialog;
import com.shushan.kencanme.mvp.views.dialog.MessageUseBeansDialog;
import com.shushan.kencanme.mvp.views.dialog.SendPhotoTypeDialog;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;

/**
 * 打开消息界面
 */
public class ConversationActivity extends BaseActivity implements CommonChoiceDialog.commonChoiceDialogListener, RongIM.OnSendMessageListener,
        ConversationControl.ConversationView, CommonDialog.CommonDialogListener, SendPhotoTypeDialog.SendPhotoTypeDialogListener, CustomizeMessageItemProvider.LookViewListener, MessageUseBeansDialog.MessageUseBeansDialogListener {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_iv_right)
    ImageView mCommonRightIv;
    private LoginUser mLoginUser;
    String mTargetId;
    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    private String TAG = "ConversationActivity";

    @Inject
    ConversationControl.PresenterConversation mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        initializeInjector();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonRightIv.setVisibility(View.VISIBLE);
        //设置融云会话点击监听
        RongIM.setConversationClickListener(new MyConversationClickListener());
        //设置融云会话发送消息监听
        RongIM.getInstance().setSendMessageListener(this);
        //注册自定义消息接收
        KencanmeApp.mCustomizeMessageItemProvider.setListener(this);
        if (getIntent() != null) {
            initIntent(getIntent());
        }
    }

    @Override
    public void initData() {
        mLoginUser = mBuProcessor.getLoginUser();
    }

    private void initIntent(Intent intent) {
        mTargetId = intent.getData().getQueryParameter("targetId");
        mCommonTitleTv.setText(intent.getData().getQueryParameter("title"));
        mConversationType = Conversation.ConversationType.valueOf("PRIVATE");
    }


    @OnClick({R.id.common_back, R.id.common_iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.common_iv_right:
                CommonChoiceDialog commonChoiceDialog = CommonChoiceDialog.newInstance();
                commonChoiceDialog.setListener(this);
                DialogFactory.showDialogFragment(this.getSupportFragmentManager(), commonChoiceDialog, CommonChoiceDialog.TAG);
                break;
        }
    }

    @Override
    public void deleteUserListener() {
    }

    @Override
    public void blackUserListener() {
    }

    @Override
    public void reportUserListener() {
    }

    private boolean isSendPic = false;
    ImageMessage imgMsg = new ImageMessage();

    @Override
    public Message onSend(Message message) {
        //开发者根据自己需求自行处理逻辑
        if (AppUtils.isLimitMsg(AppUtils.userType(mLoginUser.svip, mLoginUser.vip, mLoginUser.sex), mLoginUser.today_chat)) {
            MessageContent messageContent = message.getContent();
            if (messageContent instanceof ImageMessage) {//图片消息
                if (!isSendPic) {
                    ImageMessage imageMessage = (ImageMessage) messageContent;
                    imgMsg = imageMessage;
                    sendImgMsgDialog();
                    Log.d(TAG, "onSent-ImageMessage:" + imageMessage.getRemoteUri());
                    return null;
                } else {
                    isSendPic = false;
                }
            }
            return message;
        } else {
            CommonDialog commonDialog = CommonDialog.newInstance();
            commonDialog.setListener(this);
            commonDialog.setContent("Open Super VIP Free Chat~");
            commonDialog.setStyle(Constant.DIALOG_TWO);
            DialogFactory.showDialogFragment(getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
        }
        return null;
    }

    /**
     * 显示选择照片弹框 弹框
     */
    private void sendImgMsgDialog() {
        SendPhotoTypeDialog sendPhotoTypeDialog = new SendPhotoTypeDialog();
        sendPhotoTypeDialog.setListener(this);
        DialogFactory.showDialogFragment(Objects.requireNonNull(this).getSupportFragmentManager(), sendPhotoTypeDialog, SendPhotoTypeDialog.TAG);
    }

    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
        mLoginUser.today_chat = mLoginUser.today_chat + 1;
        mBuProcessor.setLoginUser(mLoginUser);
        return false;
    }

    /**
     * 私密图片
     * 发送自定义消息
     */
    private void sendCustomizeMesage(String picLoad, int msgType) {
        CustomizeMessage customizeMessage = new CustomizeMessage();
        customizeMessage.beans = mBeansNum;
        customizeMessage.cover_url = picLoad;
        customizeMessage.isLocked = 1;
        customizeMessage.msgType = msgType;
        Message message = Message.obtain(mTargetId, mConversationType, customizeMessage);
        RongIM.getInstance().sendMessage(message, "have a private new message", null, new IRongCallback.ISendMessageCallback() {

            @Override
            public void onAttached(Message message) {
            }

            @Override
            public void onSuccess(Message message) {
                //发送成功后执行
                Log.e("dddd", "onSuccess");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //错误时执行
                Log.e("dddd", "message " + new Gson().toJson(message));
            }
        });
    }

    /**
     * 图片图片
     * 发送图片消息
     */
    private void sendImgMessage() {
        RongIM.getInstance().sendImageMessage(mConversationType, mTargetId, imgMsg, null, null, new RongIMClient.SendImageMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //保存数据库成功
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode code) {
                //发送失败
            }

            @Override
            public void onSuccess(Message message) {
                //发送成功
            }

            @Override
            public void onProgress(Message message, int progress) {
                //发送进度
            }
        });
    }


    private int mBeansNum;

    /**
     * 发送私密照片/视频
     */
    @Override
    public void sendPrivatePhotoBtnOKListener(int beansNum) {
        isSendPic = true;
        mBeansNum = beansNum;
        //上传图片到服务器
        UploadImage uploadImage = new UploadImage();
        uploadImage.dir = String.valueOf(Constant.PIC_ALBUM);//1头像2封面3相册
        Bitmap bitmap = BitmapFactory.decodeFile(imgMsg.getLocalUri().getEncodedPath());
        uploadImage.file = PicUtils.convertIconToString(PicUtils.ImageCompressL(bitmap));
        mPresenter.uploadImage(uploadImage);
    }

    /**
     * 发送普通照片/视频
     */
    @Override
    public void sendOrdinaryPhotoBtnOKListener() {
        isSendPic = true;
        sendImgMessage();
    }

    /**
     * 上传图片成功
     */
    @Override
    public void uploadImageSuccess(String picPath) {
//        Log.d("ddd", "picPath:" + picPath);
        if (TranTools.isVideo(picPath)) {
            sendCustomizeMesage(picPath, 2);
        } else {
            sendCustomizeMesage(picPath, 1);
        }
    }

    @Override
    public void uploadImageFail(String erorMsg) {
        isSendPic = false;
        showToast(erorMsg);
    }


    @Override
    public void commonDialogBtnOkListener() {
        startActivitys(OpenVipActivity.class);
    }

    CustomizeMessage mContent;

    /**
     * 点击view 展示
     */
    @Override
    public void lookViewOnClickListener(CustomizeMessage content) {
        mContent = content;
        MessageUseBeansDialog messageUseBeansDialog = MessageUseBeansDialog.newInstance();
        messageUseBeansDialog.setListener(this);
        messageUseBeansDialog.setTitle("To view private photos, you need to pay", content.beans);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), messageUseBeansDialog, MessageUseBeansDialog.TAG);
    }

    /**
     * 点击确定展示
     * 刷新自定义消息页面
     */
    @Override
    public void messageUseBeansDialogBtnOkListener() {
        UseBeansRequest useBeansRequest = new UseBeansRequest();
        useBeansRequest.token = mBuProcessor.getToken();
        useBeansRequest.beans = String.valueOf(mContent.beans);
        useBeansRequest.message_id = mTargetId;
        useBeansRequest.type = "3";
        mPresenter.onRequestUseBeans(useBeansRequest);
    }

    @Override
    public void UseBeansSuccess(String msg) {
        showToast(msg);
        //更新自定义消息view

        //TODO


        //更新个人信息
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestHomeUserInfo(tokenRequest);

    }

    @Override
    public void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse) {
        HomeUserInfoResponse.UserBean userBean = homeUserInfoResponse.getUser();
        //把另外几项LoginUser加入进来
        mLoginUser.exposure = userBean.getExposure();
        mLoginUser.beans = userBean.getBeans();
        mLoginUser.exposure_type = userBean.getExposure_type();
        mLoginUser.exposure_time = userBean.getExposure_time();
        mLoginUser.today_like = userBean.getToday_like();
        mLoginUser.today_chat = userBean.getToday_chat();
        mLoginUser.today_see_contact = userBean.getToday_see_contact();
        mBuProcessor.setLoginUser(mLoginUser);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initializeInjector() {
        DaggerConversationComponent.builder().appComponent(getAppComponent())
                .conversationModule(new ConversationModule(ConversationActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
