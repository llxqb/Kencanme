package com.shushan.kencanme.mvp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;


/**
 * 底部自定义view
 * Created by yyg on 2016/4/22.
 */
public class MyBottomLayout extends LinearLayout implements View.OnClickListener{
    private Context context;
    private RelativeLayout homeLayout;
    private RelativeLayout messageLayout;
    private RelativeLayout settingLayout;
    private ICallbackListener iCallbackListener = null;

    private ImageView mCartImageView = null;
    private TextView mUnreadNumber = null;



    public MyBottomLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public MyBottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public MyBottomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 初始化
     */
    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottom, this);
        findView(view);
        initData();
        initListener();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        setResidAndColor(0);
    }

    /**
     * 把所有的数据整合一起进行抽取
     */
    private void changeDataItem(int[] resid, int[] color) {
        initDataItem(homeLayout, resid[0], "Home", color[0]);
        initDataItem(messageLayout, resid[1], "Message", color[1]);
        initDataItem(settingLayout, resid[3], "我的", color[3]);
    }

    /**
     * 初始化数据的抽取方法
     * @param resid
     * @param name
     * @param color
     */
    private void initDataItem(View view, int resid, String name, int color) {
        view.findViewById(R.id.tabImg).setBackgroundResource(resid);
        TextView tv = (TextView) view.findViewById(R.id.tabText);
        tv.setText(name);
        int colorSelected = context.getResources().getColor(R.color.colorSelected);
        int colorNormal = context.getResources().getColor(R.color.colorNormal);
        tv.setTextColor( (color == 1) ? colorSelected : colorNormal);
    }

    /**
     * 找到控件的方法
     *
     * @param view
     */
    private void findView(View view) {
        homeLayout =  view.findViewById(R.id.homeLayout);
        messageLayout =  view.findViewById(R.id.messageLayout);
        settingLayout = view.findViewById(R.id.settingLayout);
    }

    /**
     * 控件的监听事件
     */
    private void initListener() {
        homeLayout.setOnClickListener(this);
        messageLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
    }

    /**
     * 控件的点击事件
     * 点击后改变显示的图标和文字的颜色
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeLayout:
                setResidAndColor(0);
                break;
            case R.id.messageLayout:
                setResidAndColor(1);
                break;
            case R.id.settingLayout:
                setResidAndColor(2);
                break;
        }

        //这里加入了一个接口方法，留给ViewPager去实现
        //功能是点击item后viewPager也会跟着变
        iCallbackListener.click(v.getId());
    }

    public void setResidAndColor(int i) {
        switch (i) {
            case 0:
                changeDataItem(setResid(new int[] {1, 0, 0, 0, 0}),
                        new int[] {1, 0, 0, 0, 0});
                break;
            case 1:
                changeDataItem(setResid(new int[] {0, 1, 0, 0, 0}),
                        new int[] {0, 1, 0, 0, 0});
                break;
            case 2:
                changeDataItem(setResid(new int[] {0, 0, 1, 0, 0}),
                        new int[] {0, 0, 1, 0, 0});
                break;
            case 3:
                changeDataItem(setResid(new int[] {0, 0, 0, 1, 0}),
                        new int[] {0, 0, 0, 1, 0});
                break;
        }
    }

    public int[] setResid(int[] resid) {
       int resHome =  (resid[0] == 1) ?  R.mipmap.tab_home_selected : R.mipmap.tab_home_normal;
       int resMessage =  (resid[1] == 1) ?  R.mipmap.tab_cart_selected : R.mipmap.tab_cart_normal;
       int resSetting =  (resid[2] == 1) ?  R.mipmap.tab_setting_selected : R.mipmap.tab_setting_normal;
       return new int[] { resHome, resMessage, resSetting };
    }

    //初始化接口，由需要实现activity（MainActivity）调用
    //通过findviewbyid获取MyBottomLayout，进行调用
    public void setOnCallbackListener(ICallbackListener iCallbackListener) {
        this.iCallbackListener = iCallbackListener;
    }
    //自定义接口文件，click方法由调用处实现，功能是完成viewpager的滑动
    public interface ICallbackListener {
        public void click(int id);
    }

}