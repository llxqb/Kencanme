package com.shushan.kencanme.mvp.ui.activity.rongCloud;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shushan.kencanme.R;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * 融云自定义消息接收
 */
@ProviderTag(messageContent = CustomizeMessage.class)
public class CustomizeMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomizeMessage> {
    RequestOptions options;
    RequestOptions options2;

    class ViewHolder {
        LinearLayout msgLayout;
        ImageView coverIv;
        TextView customizeMsgHintTv;
        TextView lookTv;
        TextView beansNum;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customize_message, null);
        ViewHolder holder = new ViewHolder();
        holder.msgLayout = view.findViewById(R.id.msg_layout);
        holder.coverIv = view.findViewById(R.id.cover_iv);
        holder.customizeMsgHintTv = view.findViewById(R.id.customize_msg_hint_tv);
        holder.lookTv = view.findViewById(R.id.look_tv);
        holder.beansNum = view.findViewById(R.id.beans_num);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, CustomizeMessage content, UIMessage message) {
        ViewHolder holder = (ViewHolder) v.getTag();
//        options = new RequestOptions()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(Constant.LOADING_SMALL)
//                .skipMemoryCache(true)
//                .placeholder(Constant.LOADING_SMALL)
//                .dontAnimate();
//
//        options2 = RequestOptions
//                .bitmapTransform(new MultiTransformation<>(new BlurTransformation(30, 3)))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(Constant.LOADING_SMALL)
//                .skipMemoryCache(true)
//                .placeholder(Constant.LOADING_SMALL)
//                .dontAnimate();

        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.msgLayout.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
            holder.customizeMsgHintTv.setTextColor(v.getResources().getColor(R.color.white));
            holder.lookTv.setVisibility(View.GONE);
            holder.beansNum.setVisibility(View.VISIBLE);
            holder.beansNum.setText(String.valueOf(content.beans));
//            Glide.with(v).load(content.cover_url).apply(options).into(holder.coverIv);
        } else {
            holder.msgLayout.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
            holder.customizeMsgHintTv.setTextColor(v.getResources().getColor(R.color.color_9b));
            holder.beansNum.setVisibility(View.GONE);
            holder.lookTv.setVisibility(View.VISIBLE);
            holder.lookTv.setText("view");
//            if (content.isLocked == 1) {
//                Glide.with(v).load(content.cover_url).apply(options2).into(holder.coverIv);
//            } else {
//                Glide.with(v).load(content.cover_url).apply(options).into(holder.coverIv);
//            }
        }
        Glide.with(v).load(content.cover_url).into(holder.coverIv);
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

}
