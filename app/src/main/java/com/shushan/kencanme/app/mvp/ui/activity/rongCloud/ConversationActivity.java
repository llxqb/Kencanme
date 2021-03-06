package com.shushan.kencanme.app.mvp.ui.activity.rongCloud;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.app.KencanmeApp;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerConversationComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.ConversationModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.HiNumRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.entity.request.UseBeansRequest;
import com.shushan.kencanme.app.entity.request.UserInfoByRidRequest;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.UserInfoByRidResponse;
import com.shushan.kencanme.app.entity.response.UserRelationResponse;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.help.MyConversationClickListener;
import com.shushan.kencanme.app.mvp.ui.activity.pay.RechargeActivity;
import com.shushan.kencanme.app.mvp.ui.activity.photo.LookPhotoActivity;
import com.shushan.kencanme.app.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.app.mvp.utils.ConversationUtil;
import com.shushan.kencanme.app.mvp.utils.LoginUtils;
import com.shushan.kencanme.app.mvp.utils.PicUtils;
import com.shushan.kencanme.app.mvp.utils.TranTools;
import com.shushan.kencanme.app.mvp.views.dialog.CommonChoiceDialog;
import com.shushan.kencanme.app.mvp.views.dialog.MessageUseBeansDialog;
import com.shushan.kencanme.app.mvp.views.dialog.RechargeBeansDialog;
import com.shushan.kencanme.app.mvp.views.dialog.SendPhotoTypeDialog;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
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
    /**
     * 默认进来显示打招呼hi布局
     */
    @BindView(R.id.hi_layout_rl)
    RelativeLayout mHiLayoutRl;
    @BindView(R.id.hi_iv)
    ImageView mHiIv;
    @BindView(R.id.hi_message_tv)
    TextView mHiMessageTv;
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
    /**
     * 被聊天用户id
     */
    private String chatUid;
    private UserRelationResponse mUserRelationResponse;
    /**
     * 用户聊天还是与客服聊天
     * 0:用户聊天  1:客服聊天
     */
    private int chatType;
    /**
     * hiLayoutIsShow 是否显示hiLayout 布局
     * true : 免费打招呼
     * false: 使用嗨豆
     */
    boolean hiLayoutIsShow;
    String hiLayoutKey;//记录hiLayout 布局key
    boolean isClickHiBtn;
    /**
     * 1: 赚嗨豆
     * 2：使用嗨豆
     */
    private int rechargeBeansDialogType;
    @Inject
    ConversationControl.PresenterConversation mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        setStatusBar();
        initializeInjector();
        initView();
        initData();
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ActivityConstant.PAY_SUCCESS_UPDATE_INFO)) {
            // 充值后更新
            mLoginUser = mBuProcessor.getLoginUser();
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.PAY_SUCCESS_UPDATE_INFO);
    }

    @Override
    public void initView() {
        chatType = mSharePreferenceUtil.getIntData("chatType");//在线客服
//        mCommonRightIv.setVisibility(View.VISIBLE);
        //设置融云会话发送消息监听
        RongIM.getInstance().setSendMessageListener(this);
        //注册自定义消息接收
        CustomizeMessageItemProvider.setListener(this);
        if (getIntent() != null) {
            mTargetId = Objects.requireNonNull(getIntent().getData()).getQueryParameter("targetId");
            mCommonTitleTv.setText(getIntent().getData().getQueryParameter("title"));
            mConversationType = Conversation.ConversationType.valueOf("PRIVATE");
            if (chatType != 1) {
                onRequestUserInfoByRid();
            }
        }
        hiLayoutKey = mSharePreferenceUtil.getData("hi_layout_key");
//        Log.e("ddd", "mTargetId:" + mTargetId + "  hi_layout_key:" + hiLayoutKey);
        if (!TextUtils.isEmpty(hiLayoutKey)) {
            hiLayoutIsShow = !hiLayoutKey.contains(mTargetId);
        } else {
            hiLayoutIsShow = true;
        }

        if (!hiLayoutIsShow || chatType == 1) {
            mHiLayoutRl.setVisibility(View.GONE);
        } else {
            mHiLayoutRl.setVisibility(View.VISIBLE);
            mHiMessageTv.setText(getResources().getString(R.string.message_hi_content));
        }
    }

    @Override
    public void initData() {
        mLoginUser = mBuProcessor.getLoginUser();
        mMessageIdList = (List<String>) mSharePreferenceUtil.readObjData("messageIdList");
        CustomizeMessageItemProvider.setMessageList(mMessageIdList);
        boolean chatTopHintRl = mSharePreferenceUtil.getBooleanData("chat_top_rl");
        if (chatType == 1 || chatTopHintRl) {
            mChatTopHintRl.setVisibility(View.GONE);
        }else {
            mChatTopHintRl.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.common_back, R.id.chat_top_hint_btn, R.id.common_iv_right, R.id.hi_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.chat_top_hint_btn:
                mSharePreferenceUtil.setData("chat_top_rl", true);
                mChatTopHintRl.setVisibility(View.GONE);
                break;
            case R.id.common_iv_right:
                CommonChoiceDialog commonChoiceDialog = CommonChoiceDialog.newInstance();
                commonChoiceDialog.setListener(this);
                DialogFactory.showDialogFragment(this.getSupportFragmentManager(), commonChoiceDialog, CommonChoiceDialog.TAG);
                break;
            case R.id.hi_iv:
                if (mLoginUser.userType == 1) {
                    isClickHiBtn = true;
                    onRequestHiNum();
                } else {
                    //发送打招呼消息 hi
                    ConversationUtil.sendTextMessage(mTargetId, mConversationType, mHiMessageTv.getText().toString());
                }
                break;
        }
    }

    /**
     * 免费打招呼
     */
    private void onRequestHiNum() {
        HiNumRequest hiNumRequest = new HiNumRequest();
        hiNumRequest.token = mLoginUser.token;
        hiNumRequest.call_id = chatUid;
        mPresenter.onRequestHiNum(hiNumRequest);
    }

    /**
     * 根据融云第三方id获取用户头像和昵称
     */
    private void onRequestUserInfoByRid() {
        UserInfoByRidRequest userInfoByRidRequest = new UserInfoByRidRequest();
        userInfoByRidRequest.token = mBuProcessor.getToken();
        userInfoByRidRequest.rongyun_third_id = mTargetId;
        mPresenter.onRequestUserInfoByRid(userInfoByRidRequest);
    }

    /**
     * 根据融云第三方id获取关系
     */
    private void onRequestUserReleate() {
        UserInfoByRidRequest userInfoByRidRequest = new UserInfoByRidRequest();
        userInfoByRidRequest.token = mBuProcessor.getToken();
        userInfoByRidRequest.rongyun_third_id = mTargetId;
        mPresenter.onRequestUserRelation(userInfoByRidRequest);
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
    @Override
    public Message onSend(Message message) {
        //开发者根据自己需求自行处理逻辑
        if (chatType == 1) {//客服
            return message;
        } else {
            if (mLoginUser.userType == 1 && !hiIsShow() && mLoginUser.beans == 0 && mUserRelationResponse != null && mUserRelationResponse.getState() != 2) {
                //男非VIP 和beans=0  和 不是好友关系
                rechargeBeansDialogType = 1;
                DialogFactory.showRechargeBeansDialog2(this);
            } else {
                MessageContent messageContent = message.getContent();
                if (messageContent instanceof ImageMessage) {//图片消息
                    if (!isSendPic) {
                        imgMsg = (ImageMessage) messageContent;
                        sendImgMsgDialog();
                        return null;
                    } else {
                        isSendPic = false;
                    }
                }
                isSendPic = false;
                return message;
            }
        }
        return null;
    }

    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
        mHiLayoutRl.setVisibility(View.GONE);
        if (chatType != 1) {
            if (hiLayoutIsShow && isClickHiBtn) {//免费打招呼
                hiLayoutIsShow = false;
            } else {
                if (mLoginUser.userType == 1 && mUserRelationResponse != null && mUserRelationResponse.getState() != 2) {
                    UseBeansDialogFlag = 1;
                    useBeansToChat("4", 1);//嗨豆聊天
                }
            }
            //保存会话id
            if (!hiLayoutKey.contains(mTargetId)) {
                mSharePreferenceUtil.setData("hi_layout_key", hiLayoutKey + "/" + mTargetId);//记录会话id
            }
        }
        return false;
    }

    /**
     * 判断hiLayout是否显示 和 hiLayout显示是否是点击hi发送按钮
     */
    private boolean hiIsShow() {
        if (!hiLayoutIsShow) {
            return false;
        } else {
            return isClickHiBtn;
        }
    }

    /**
     * 使用嗨豆 聊天
     * 3查看私密照片4男性回复女性消息
     */
    private void useBeansToChat(String type, int beans) {
        UseBeansRequest useBeansRequest = new UseBeansRequest();
        useBeansRequest.token = mBuProcessor.getToken();
        useBeansRequest.beans = String.valueOf(beans);
        if (mCustomizeMessage != null) {
            useBeansRequest.message_id = mCustomizeMessage.getUId();
        }
        useBeansRequest.type = type;
        useBeansRequest.see_id = chatUid;
        mPresenter.onRequestUseBeans(useBeansRequest);
    }

    /**
     * 显示选择照片弹框 弹框
     */
    private void sendImgMsgDialog() {
        SendPhotoTypeDialog sendPhotoTypeDialog = new SendPhotoTypeDialog();
        sendPhotoTypeDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), sendPhotoTypeDialog, SendPhotoTypeDialog.TAG);
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
//        //上传图片到服务器
//        UploadImage uploadImage = new UploadImage();
//        uploadImage.dir = String.valueOf(Constant.PIC_MESSAGE);//1头像2封面3相册
//        Bitmap bitmap = BitmapFactory.decodeFile(imgMsg.getLocalUri().getEncodedPath());
//        uploadImage.file = PicUtils.convertIconToString(PicUtils.ImageCompressL(bitmap));
//        mPresenter.uploadImage(uploadImage);
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
        DialogFactory.showUseBeansDialog(this, getResources().getString(R.string.ConversationActivity_private_photo_hint), content.beans);
    }

    /**
     * 查看已用hai-bean查看的图片
     */
    @Override
    public void lookPictureOnClickListener(String coverUrl) {
        LookPhotoActivity.start(this, coverUrl);
    }

    /**
     * 点击确定展示
     * 刷新自定义消息页面
     */
    @Override
    public void messageUseBeansDialogBtnOkListener() {
        UseBeansDialogFlag = 2;
        useBeansToChat("3", mContent.beans);
    }

    /**
     * 使用beans
     */
    @Override
    public void UseBeansSuccess(String msg) {
        //更新自定义消息view
        if (UseBeansDialogFlag == 2) {
            mMessageIdList.add(mCustomizeMessage.getUId());
            mSharePreferenceUtil.saveObjData("messageIdList", mMessageIdList);
            mContent.isLocked = 0;
            KencanmeApp.mCustomizeMessageItemProvider.bindView(customizeView, customizePos, mContent, mCustomizeMessage);
        }
        //更新个人信息
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestHomeUserInfo(tokenRequest);
    }

    @Override
    public void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse) {
        HomeUserInfoResponse.UserBean userBean = homeUserInfoResponse.getUser();
        mBuProcessor.setLoginUser(LoginUtils.upDateLoginUser(mLoginUser, userBean));
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_USER_INFO));
    }

    /**
     * 根据融云第三方id获取用户头像和昵称 成功
     */
    @Override
    public void getUserInfoSuccess(UserInfoByRidResponse userInfoByRidResponse) {
        onRequestUserReleate();
        chatUid = String.valueOf(userInfoByRidResponse.getUid());
        //设置融云会话点击监听
        String rUserId = mSharePreferenceUtil.getData("rUserId");
        RongIM.setConversationClickListener(new MyConversationClickListener(userInfoByRidResponse.getUid(), rUserId));
    }

    /**
     * 根据融云第三方id获取关系 成功
     */
    @Override
    public void getUserRelationSuccess(UserRelationResponse userRelationResponse) {
        mUserRelationResponse = userRelationResponse;
    }

    /**
     * 聊天打招呼接口
     */
    @Override
    public void getHiNumSuccess() {
        //发送打招呼消息 hi
        hiLayoutIsShow = true;
        ConversationUtil.sendTextMessage(mTargetId, mConversationType, mHiMessageTv.getText().toString());
    }

    @Override
    public void getHiNumFail(String msg) {
        showToast(msg);
        if (mLoginUser.beans > 0) {
            rechargeBeansDialogType = 2;
            DialogFactory.showRechargeBeansDialog3(this);
        } else {
            rechargeBeansDialogType = 1;
            DialogFactory.showRechargeBeansDialog2(this);
        }
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
        if (rechargeBeansDialogType == 2) {
            //发送打招呼消息 hi
            hiLayoutIsShow = false;
            ConversationUtil.sendTextMessage(mTargetId, mConversationType, mHiMessageTv.getText().toString());
        } else {
            startActivitys(RechargeActivity.class);
        }
    }

    @Override
    protected void onDestroy() {
        mSharePreferenceUtil.setData("chatType", 0);//设为默认聊天类型
        if (KencanmeApp.mCustomizeMessageItemProvider != null) {
            KencanmeApp.mCustomizeMessageItemProvider.setListenerNoll();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        assert fragment != null;
        if (!fragment.onBackPressed()) {
            finish();
        }
    }

    private void initializeInjector() {
        DaggerConversationComponent.builder().appComponent(getAppComponent())
                .conversationModule(new ConversationModule(ConversationActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
