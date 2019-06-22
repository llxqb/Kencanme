package com.shushan.kencanme.mvp.ui.activity.rongCloud;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.help.ImageLoaderHelper;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * 融云自定义消息接收
 */
@ProviderTag(messageContent = CustomizeMessage.class)
public class CustomizeMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomizeMessage> {
    private LookViewListener mLookViewListener;
    private Context mContext;


    public void setListener(LookViewListener lookViewListener) {
        this.mLookViewListener = lookViewListener;
    }

    /**
     * 刷新数据
     */
    public static void setData(int pos, CustomizeMessage customizeMessage) {
        customizeMessage.isLocked = 0;
    }


    class ViewHolder {
        LinearLayout msgLayout;
        ImageView coverIv;
        TextView customizeMsgHintTv;
        TextView customizeMsgTv;
        TextView lookTv;
        TextView beansNum;
        ImageView isLockedIv;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.item_customize_message, null);
        ViewHolder holder = new ViewHolder();
        holder.msgLayout = view.findViewById(R.id.msg_layout);
        holder.coverIv = view.findViewById(R.id.cover_iv);
        holder.customizeMsgTv = view.findViewById(R.id.customize_msg_tv);
        holder.customizeMsgHintTv = view.findViewById(R.id.customize_msg_hint_tv);
        holder.lookTv = view.findViewById(R.id.look_tv);
        holder.beansNum = view.findViewById(R.id.beans_num);
        holder.isLockedIv = view.findViewById(R.id.is_locked_iv);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, CustomizeMessage content, UIMessage message) {
        Log.e("ddd", "content:" + new Gson().toJson(content));
        ViewHolder holder = (ViewHolder) v.getTag();
        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.msgLayout.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
            holder.customizeMsgTv.setTextColor(v.getResources().getColor(R.color.white));
            holder.customizeMsgHintTv.setTextColor(v.getResources().getColor(R.color.white));
            holder.lookTv.setVisibility(View.GONE);
            holder.beansNum.setVisibility(View.VISIBLE);
            holder.beansNum.setText(String.valueOf(content.beans));
            ImageLoaderHelper.displayImage2(v, content.cover_url, holder.coverIv, Constant.LOADING_SMALL);
        } else {
//            Log.e("ddd", "content:" + new Gson().toJson(content));
            holder.msgLayout.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
            holder.customizeMsgTv.setTextColor(v.getResources().getColor(R.color.first_text_color));
            holder.customizeMsgHintTv.setTextColor(v.getResources().getColor(R.color.color_9b));
            holder.beansNum.setVisibility(View.GONE);
            holder.lookTv.setVisibility(View.VISIBLE);
            holder.lookTv.setText("view");
            if (content.isLocked == 1 && content.msgType == 1) {
                ImageLoaderHelper.displayGlassImage2(v, content.cover_url, holder.coverIv, Constant.LOADING_SMALL);
                holder.isLockedIv.setVisibility(View.VISIBLE);
            } else {
                ImageLoaderHelper.displayImage2(v, content.cover_url, holder.coverIv, Constant.LOADING_SMALL);
                holder.isLockedIv.setVisibility(View.GONE);
            }
        }

        holder.lookTv.setOnClickListener(v1 -> {
            if (mLookViewListener != null) {
                mLookViewListener.lookViewOnClickListener(v,position,content,message);
            }
        });
    }


    @Override
    public Spannable getContentSummary(CustomizeMessage data) {
        return new SpannableString("您收到一条私密信息");
    }


    @Override
    public void onItemClick(View view, int position, CustomizeMessage content, UIMessage message) {

    }

    @Override
    public void onItemLongClick(View view, int position, CustomizeMessage content, UIMessage message) {

    }

    public interface LookViewListener {
        void lookViewOnClickListener(View v, int position, CustomizeMessage content, UIMessage message);
    }

}
