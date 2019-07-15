package com.shushan.kencanme.app.help;

import android.app.Activity;
import android.util.Log;

import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.Constants.ServerConstant;
import com.shushan.kencanme.app.mvp.utils.googlePayUtils.IabHelper;
import com.shushan.kencanme.app.mvp.utils.googlePayUtils.IabResult;
import com.shushan.kencanme.app.mvp.utils.googlePayUtils.Inventory;
import com.shushan.kencanme.app.mvp.utils.googlePayUtils.Purchase;


/**
 * google 支付 helper
 */
public class GooglePayHelper {
    private String TAG = "GooglePayHelper";
    private Activity mContext;
    private IabHelper mHelper;
    private String sku;
    private String mOrderId;
    private BuyFinishListener mBuyFinishListener;

    public GooglePayHelper(Activity context, BuyFinishListener buyFinishListener) {
        this.mContext = context;
        mBuyFinishListener = buyFinishListener;
    }

    /**
     * 初始化
     */
    public IabHelper initGooglePay() {
        //设置自己从google控制台得到的公钥
        mHelper = new IabHelper(mContext, ServerConstant.GOOGLE_PAY_PUBLIC_KEY);
        //调试模式
        mHelper.enableDebugLogging(false);
        mHelper.startSetup(result -> {
            if (!result.isSuccess()) {
                Log.d(TAG, "Setup fail.");
                return;
            }
            if (mHelper == null) {
                Log.d(TAG, "Setup fail.");
                return;
            }
            Log.d(TAG, "Setup success.");
        });
        return mHelper;
    }


    /**
     * 查询商品
     */
    public void queryGoods(String sku, String orderId) {
        this.sku = sku;
        this.mOrderId = orderId;
        queryInventory();
    }

    /**
     * 查询库存
     * 1、查询库存    存在-->消耗     不存在-->购买
     * 2、消耗
     * 3、购买      成功--->消耗
     */
    private void queryInventory() {
        Log.e(TAG, "Query inventory start");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    /**
     * 购买商品
     * google应用内支付调用购买接口的时候，应先确保用户没有存在这个商品的购买（买了但是没有消耗）
     * launchPurchaseFlow(Activity, String, int, OnIabPurchaseFinishedListener, String) 购买商品
     */
    private void buyGoods() {
        try {
            // 这个payload是要给Google发送的备注信息，自定义参数，购买完成之后的订单中也有该字段
            mHelper.launchPurchaseFlow(mContext, sku, Constant.GOOGLE_PAY_REQ, mPurchaseFinishedListener, mOrderId);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
            if (mBuyFinishListener != null) {
                mBuyFinishListener.buyFinishFail();
            }
            Log.e(TAG, "IabHelper:" + e.toString());
        }
    }

    /**
     * 查询库存的回调
     */
    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            if (result.isFailure()) {
                Log.e(TAG, "查询商品信息失败: " + result);
                return;
            }
            Purchase purchase = inventory.getPurchase(sku);
            Log.e(TAG, "查询商品信息成功 purchase:" + purchase);
//            Log.e(TAG, "getSkuDetails" + inventory.getSkuDetails(sku));
            if (purchase != null) {
                //库存存在用户购买的产品，先去消耗
                expendGoods(inventory.getPurchase(sku));
            } else {
                //库存不存在
                //进行购买
                //在合适的地方调用购买
                buyGoods();
            }
        }
    };

    /**
     * 购买的回调
     */
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.e(TAG, "购买的回调 result: " + result.isSuccess());
//            Log.e(TAG, "购买的回调 purchase:" + new Gson().toJson(purchase));
            if (result.isSuccess()) {
                //支付成功
                expendGoods(purchase);
                //模拟检测public key
                //购买成功后，应该将购买返回的信息发送到自己的服务端，自己的服务端再去利用public key去验签
                if (mBuyFinishListener != null) {
                    mBuyFinishListener.buyFinishSuccess(purchase);
                }
            } else {
                // Oh noes! pay fail
                Log.e(TAG, "购买的回调 失败: " + result);
                if (mBuyFinishListener != null) {
                    mBuyFinishListener.buyFinishFail();
                }
            }
        }
    };

    /**
     * 消耗商品
     * 用户购买成功后，如果是可重复购买的商品，应该立刻将这个商品消耗掉，以及在购买之前应确保用户不存在这个商品，如果存在就调用消耗商品的接口去将商品消耗掉
     */
    private void expendGoods(Purchase purchase) {
        try {
            mHelper.consumeAsync(purchase, mConsumeFinishedListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消耗商品回调
     */
    private IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = (purchase, result) -> {
        Log.e(TAG, "消耗商品回调 ");
        if (result.isSuccess()) {
            Log.e(TAG, "消耗商品回调 --成功");
        } else {
            Log.e(TAG, "消耗商品回调 --失败" + result);
        }
    };


    /**
     * 销毁mHelper
     */
    public void dispose() {
        if (mHelper != null) {
            try {
                mHelper.dispose();
                mHelper = null;
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        }
    }

    public interface BuyFinishListener {
        void buyFinishSuccess(Purchase purchase);

        //支付失败或取消
        void buyFinishFail();

    }
}
