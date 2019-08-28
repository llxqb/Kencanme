package com.shushan.kencanme.app.mvp.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.shushan.kencanme.app.mvp.ui.activity.rongCloud.CustomizeMessage;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;

/**
 */
public class ConversationUtil {

    /**
     * 私密图片
     * 发送自定义消息
     * msgType: msgType =1 照片   2:视频
     * mBeansNum ;hi-bean数量
     */
    public static void sendCustomizeMesage(String mTargetId, Conversation.ConversationType mConversationType, String picLoad, int msgType, int mBeansNum) {
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
                Log.d("ConversationUtil", "onSuccess");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //错误时执行
                Log.d("ConversationUtil", "message " + new Gson().toJson(message));
            }
        });
    }


    /**
     * 图片图片
     * 发送图片消息
     */
    public static void sendImgMessage(String mTargetId, Conversation.ConversationType mConversationType, ImageMessage imgMsg) {
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
    /**
     * 发送文字消息
     */
    public static void sendTextMessage(String mTargetId, Conversation.ConversationType mConversationType, String text) {
        TextMessage myTextMessage = TextMessage.obtain(text);
        Message myMessage = Message.obtain(mTargetId, mConversationType, myTextMessage);
        RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //消息本地数据库存储成功的回调
            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
            }
        });
    }
}
