package com.shushan.kencanme.app.help;

import android.app.Activity;
import android.util.Log;

import com.shushan.kencanme.app.entity.Constants.ServerConstant;
import com.shushan.kencanme.app.mvp.utils.LogUtils;
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
    public void initGooglePay() {
        //设置自己从google控制台得到的公钥
        mHelper = new IabHelper(mContext, ServerConstant.GOOGLE_PAY_PUBLIC_KEY);
        //调试模式
        mHelper.enableDebugLogging(true);
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");
                if (!result.isSuccess()) {
                    Log.d(TAG, "Setup fail.");
                    return;
                }
                if (mHelper == null) {
                    Log.d(TAG, "Setup fail.");
                    return;
                }
                Log.d(TAG, "Setup success.");
            }
        });


    }


    /**
     * 查询库存
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
     * 查询库存的回调
     */
    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.e(TAG, "Query inventory finished." + "sku：" + sku);
            if (result.isFailure()) {
                Log.e(TAG, "Failed to query inventory: " + result);
                return;
            }
            Log.e(TAG, "Query inventory was successful:" + inventory.getPurchase(sku));
            Log.e(TAG, "getSkuDetails" + inventory.getSkuDetails(sku));

            if (inventory.hasPurchase(sku)) {
                //库存存在用户购买的产品，先去消耗

            } else {
                //库存不存在
            }

//             Check for gas delivery -- if we own gas, we should fill up the tank immediately
            //查询你的产品是否存在没有消耗的，要是没有消耗，先去消耗，再购买
//            Purchase gasPurchase = inventory.getPurchase(purchaseId);
//            if (gasPurchase != null && verifyDeveloperPayload(gasPurchase))
//            {
//                try {
//                    mHelper.consumeAsync(inventory.getPurchase(purchaseId), mConsumeFinishedListener);
//                } catch (IabHelper.IabAsyncInProgressException e) {
//                    complain("Error consuming gas. Another async operation in progress.");
//                }
//                return;
//            }
//            Log.i(TAG, "初始库存查询完成；启用主用户界面.");

        }
    };

    /**
     * 购买商品
     * google应用内支付调用购买接口的时候，应先确保用户没有存在这个商品的购买（买了但是没有消耗）
     * launchPurchaseFlow(Activity, String, int, OnIabPurchaseFinishedListener, String) 购买商品
     */
    public void buyGoods(String sku, String orderId) {
        this.sku = sku;
        this.mOrderId = orderId;
//        queryInventory();

        //在合适的地方调用购买
        try {
            // 这个payload是要给Google发送的备注信息，自定义参数，购买完成之后的订单中也有该字段
            mHelper.launchPurchaseFlow(mContext, sku, 100, mPurchaseFinishedListener, mOrderId);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
            if (mBuyFinishListener != null) {
                mBuyFinishListener.buyFinishFail();
            }
            LogUtils.e("IabHelper--e:" + e.toString());
        }

    }


    /**
     * 购买的回调
     */
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
            if (result.isFailure()) {
                // Oh noes! pay fail
                Log.d(TAG, "Error purchasing: " + result);
                if (mBuyFinishListener != null) {
                    mBuyFinishListener.buyFinishFail();
                }
                return;
            }


            //INAPP_PURCHASE_DATA,INAPP_DATA_SIGNATURE,order_no
            Log.d(TAG, "Purchase successful.");
            //模拟检测public key
            //购买成功后，应该将购买返回的信息发送到自己的服务端，自己的服务端再去利用public key去验签
//            checkPk(purchase);

            if (mBuyFinishListener != null) {
                mBuyFinishListener.buyFinishSuccess(purchase);
            }
        }
    };

    /**
     * 消耗商品
     * 用户购买成功后，如果是可重复购买的商品，应该立刻将这个商品消耗掉，以及在购买之前应确保用户不存在这个商品，如果存在就调用消耗商品的接口去将商品消耗掉
     */
    public void expendGoods() {
//        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
    }


    /**
     * 消耗商品回调
     */
    private IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        @Override
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
            if (result.isSuccess()) {
                Log.d(TAG, "Consumption successful. Provisioning.");
            } else {
                Log.d(TAG, "Error while consuming: " + result);
            }
        }
    };


    public interface BuyFinishListener {
        void buyFinishSuccess(Purchase purchase);

        void buyFinishFail();
    }
}
