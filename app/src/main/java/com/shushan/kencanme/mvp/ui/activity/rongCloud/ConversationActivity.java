package com.shushan.kencanme.mvp.ui.activity.rongCloud;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.shushan.kencanme.mvp.ui.activity.pay.RechargeActivity;
import com.shushan.kencanme.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.mvp.utils.AppUtils;
import com.shushan.kencanme.mvp.utils.ConversationUtil;
import com.shushan.kencanme.mvp.utils.PicUtils;
import com.shushan.kencanme.mvp.utils.TranTools;
import com.shushan.kencanme.mvp.views.dialog.CommonChoiceDialog;
import com.shushan.kencanme.mvp.views.dialog.MessageUseBeansDialog;
import com.shushan.kencanme.mvp.views.dialog.RechargeBeansDialog;
import com.shushan.kencanme.mvp.views.dialog.SendPhotoTypeDialog;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIMessage;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;

/**
 * 打开消息界面
 */
public class ConversationActivity extends BaseActivity implements CommonChoiceDialog.commonChoiceDialogListener, RongIM.OnSendMessageListener, ConversationControl.ConversationView,
        SendPhotoTypeDialog.SendPhotoTypeDialogListener, CustomizeMessageItemProvider.LookViewListener, MessageUseBeansDialog.MessageUseBeansDialogListener, RechargeBeansDialog.RechargeDialogListener {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_iv_right)
    ImageView mCommonRightIv;
    @BindView(R.id.chat_top_hint_btn)
    TextView mChatTopHintBtn;
    @BindView(R.id.chat_top_hint_rl)
    RelativeLayout mChatTopHintRl;
    private LoginUser mLoginUser;
    String mTargetId;
    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    /**
     * 已用嗨豆查看图片集合
     */
    private List<String> mMessageIdList;
    /**
     * 1、使用1嗨豆聊天
     * 2、使用嗨豆查看私有照片
     */
    private int UseBeansDialogFlag;
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
//        mCommonRightIv.setVisibility(View.VISIBLE);
        //设置融云会话点击监听
        RongIM.setConversationClickListener(new MyConversationClickListener());
        //设置融云会话发送消息监听
        RongIM.getInstance().setSendMessageListener(this);
        //注册自定义消息接收
        CustomizeMessageItemProvider.setListener(this);
        if (getIntent() != null) {
            initIntent(getIntent());
        }
    }

    @Override
    public void initData() {
        mLoginUser = mBuProcessor.getLoginUser();
        mMessageIdList = (List<String>) mSharePreferenceUtil.readObjData("messageIdList");
        CustomizeMessageItemProvider.setMessageList(mMessageIdList);
        String chatTopHintRl = mSharePreferenceUtil.getData("chat_top_rl");
        if (chatTopHintRl.equals("true")) {
            mChatTopHintRl.setVisibility(View.GONE);
        }
//        ConversationUtil.sendCustomizeMesage("Kencanme6", Conversation.ConversationType.PRIVATE,"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3071844911,2905106883&fm=26&gp=0.jpg",1,5);
    }

    private void initIntent(Intent intent) {
        mTargetId = Objects.requireNonNull(intent.getData()).getQueryParameter("targetId");
        mCommonTitleTv.setText(intent.getData().getQueryParameter("title"));
        mConversationType = Conversation.ConversationType.valueOf("PRIVATE");
    }

    @OnClick({R.id.common_back, R.id.chat_top_hint_btn, R.id.common_iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.chat_top_hint_btn:
                mSharePreferenceUtil.setData("chat_top_rl", "true");
                mChatTopHintRl.setVisibility(View.GONE);
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

    /**
     * 获取消息id
     */
    int messageId;

    @Override
    public Message onSend(Message message) {
        //开发者根据自己需求自行处理逻辑
        if (AppUtils.isLimitMsg(mLoginUser.userType, mLoginUser.today_chat) && mLoginUser.beans >= 1) {
            MessageContent messageContent = message.getContent();
            if (messageContent instanceof ImageMessage) {//图片消息
                if (!isSendPic) {
                    messageId = message.getMessageId();
                    imgMsg = (ImageMessage) messageContent;
                    sendImgMsgDialog();
                    return null;
                } else {
                    isSendPic = false;
                }
            }
            isSendPic = false;
            return message;
        } else {
            if (mLoginUser.beans > 0) {
                UseBeansDialogFlag = 1;
                DialogFactory.showUseBeansDialog(this, getResources().getString(R.string.dialog_use_beans_chat_num), 1);
            } else {
                DialogFactory.showRechargeBeansDialog2(this);
            }
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
        ConversationUtil.sendImgMessage(mTargetId, mConversationType, imgMsg);
    }

    /**
     * 上传图片成功
     */
    @Override
    public void uploadImageSuccess(String picPath) {
//        Log.d("ddd", "picPath:" + picPath);
        if (TranTools.isVideo(picPath)) {
            ConversationUtil.sendCustomizeMesage(mTargetId, mConversationType, picPath, 2, mBeansNum);
        } else {
            ConversationUtil.sendCustomizeMesage(mTargetId, mConversationType, picPath, 1, mBeansNum);
        }
    }

    @Override
    public void uploadImageFail(String erorMsg) {
        isSendPic = false;
        showToast(erorMsg);
    }

    View customizeView;
    int customizePos;
    CustomizeMessage mContent;
    UIMessage mCustomizeMessage;

    /**
     * 点击view 展示
     */
    @Override
    public void lookViewOnClickListener(View v, int position, CustomizeMessage content, UIMessage message) {
        customizeView = v;
        customizePos = position;
        mContent = content;
        mCustomizeMessage = message;
        UseBeansDialogFlag = 2;
        DialogFactory.showUseBeansDialog(this, getResources().getString(R.string.ConversationActivity_private_photo_hint), content.beans);
    }

    /**
     * 点击确定展示
     * 刷新自定义消息页面
     */
    @Override
    public void messageUseBeansDialogBtnOkListener() {
        if (UseBeansDialogFlag == 1) {
            UseBeansRequest useBeansRequest = new UseBeansRequest();
            useBeansRequest.token = mBuProcessor.getToken();
            useBeansRequest.beans = String.valueOf(mContent.beans);
            useBeansRequest.message_id = String.valueOf(mCustomizeMessage.getMessageId());
            useBeansRequest.type = "4";
            mPresenter.onRequestUseBeans(useBeansRequest);
        } else if (UseBeansDialogFlag == 2) {
            UseBeansRequest useBeansRequest = new UseBeansRequest();
            useBeansRequest.token = mBuProcessor.getToken();
            useBeansRequest.beans = String.valueOf(mContent.beans);
            useBeansRequest.message_id = String.valueOf(mCustomizeMessage.getMessageId());
            useBeansRequest.type = "3";
            mPresenter.onRequestUseBeans(useBeansRequest);
        }

    }

    /**
     * 使用beans
     */
    @Override
    public void UseBeansSuccess(String msg) {
        showToast(msg);
        //TODO更新自定义消息view
        mMessageIdList.add(String.valueOf(mCustomizeMessage.getMessageId()));
        mSharePreferenceUtil.saveObjData("messageIdList", mMessageIdList);
        mContent.isLocked = 0;
        KencanmeApp.mCustomizeMessageItemProvider.bindView(customizeView, customizePos, mContent, mCustomizeMessage);
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

    /**
     * 成为VIP
     */
    @Override
    public void earnBeansDialogBtnListener() {
        startActivitys(OpenVipActivity.class);
    }

    /**
     * 充值
     */
    @Override
    public void rechargeBeansDialogBtnListener() {
        startActivitys(RechargeActivity.class);
    }

    @Override
    protected void onDestroy() {
        KencanmeApp.mCustomizeMessageItemProvider.setListenerNoll();
        super.onDestroy();
    }

    private void initializeInjector() {
        DaggerConversationComponent.builder().appComponent(getAppComponent())
                .conversationModule(new ConversationModule(ConversationActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
