package com.shushan.kencanme.mvp.ui.activity.rongCloud;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * 融云自定义消息
 */
@SuppressLint("ParcelCreator")
@MessageTag(value = "RC:CustomMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CustomizeMessage extends MessageContent {
    /**
     *  beans = 10;
     * "cover_url" = "https://menggoda.oss-ap-southeast-5.aliyuncs.com/video/20190618/4ef2c12c536c05ded397ff65dd379bd2.mp4";
     * isLocked = 1;   0 未锁   1锁
     * msgType = 2;    msgType =1 照片   2:视频
     */
    public int beans;
    public String cover_url;
    public int isLocked;
    public int msgType;

    public CustomizeMessage() {
    }

    public CustomizeMessage(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("user"))
                setUserInfo(parseJsonToUserInfo(jsonObj.getJSONObject("user")));
            if (jsonObj.has("beans"))
                beans = jsonObj.optInt("beans");
            if (jsonObj.has("cover_url"))
                cover_url = jsonObj.optString("cover_url");
            if (jsonObj.has("isLocked"))
                isLocked = jsonObj.optInt("isLocked");
            if (jsonObj.has("msgType"))
                msgType = jsonObj.optInt("msgType");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
//            if (getJSONUserInfo() != null) {
//                jsonObj.putOpt("user", getJSONUserInfo());
//            }
            jsonObj.put("beans", beans);
            jsonObj.put("cover_url", cover_url);
            jsonObj.put("isLocked", isLocked);
            jsonObj.put("msgType", msgType);
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }


    //给消息赋值。
    public CustomizeMessage(Parcel in) {
        beans = ParcelUtils.readIntFromParcel(in);//该类为工具类，消息属性
        cover_url = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
        isLocked = ParcelUtils.readIntFromParcel(in);//该类为工具类，消息属性
        msgType = ParcelUtils.readIntFromParcel(in);//该类为工具类，消息属性
        //这里可继续增加你消息的属性
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<CustomizeMessage> CREATOR = new Creator<CustomizeMessage>() {

        @Override
        public CustomizeMessage createFromParcel(Parcel source) {
            return new CustomizeMessage(source);
        }

        @Override
        public CustomizeMessage[] newArray(int size) {
            return new CustomizeMessage[size];
        }
    };

    /**
     * 描述了包含在 Parcelable 对象排列信息中的特殊对象的类型。
     *
     * @return 一个标志位，表明Parcelable对象特殊对象类型集合的排列。
     */
    public int describeContents() {
        return 0;
    }

    /**
     * 将类的数据写入外部提供的 Parcel 中。
     *
     * @param dest  对象被写入的 Parcel。
     * @param flags 对象如何被写入的附加标志。
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, beans);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, cover_url);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, isLocked);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, msgType);//该类为工具类，对消息中属性进行序列化
        //这里可继续增加你消息的属性
    }
}
