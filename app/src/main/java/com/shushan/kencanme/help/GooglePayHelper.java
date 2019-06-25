package com.shushan.kencanme.help;

import android.content.Context;
import android.util.Log;

import com.shushan.kencanme.entity.Constants.ServerConstant;
import com.shushan.kencanme.mvp.utils.googlePayUtils.IabHelper;
import com.shushan.kencanme.mvp.utils.googlePayUtils.IabResult;
import com.shushan.kencanme.mvp.utils.googlePayUtils.Inventory;
import com.shushan.kencanme.mvp.utils.googlePayUtils.Purchase;

/**
 * google 支付 helper
 */
public class GooglePayHelper {
    private String TAG= "GooglePayHelper";
    private Context mContext;
    private IabHelper mHelper;
    public GooglePayHelper(Context context) {
        this.mContext = context;
    }

    /**
     * 初始化
     */
    public void initGooglePay(){
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
                if (mHelper == null){
                    Log.d(TAG, "Setup fail.");
                    return;
                }
                Log.d(TAG, "Setup success.");
            }
        });
    }


    /**
     * 查询库存
     * */
    private void queryInventory(){
        Log.e(TAG, "Query inventory start");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    /**查询库存的回调*/
    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.e(TAG, "Query inventory finished.");
            if (result.isFailure()) {
                Log.e(TAG, "Failed to query inventory: " + result);
                return;
            }
//            Log.e(TAG, "Query inventory was successful." + inventory.getPurchase(mPayInfo.getProductId()));
//            if (inventory.hasPurchase(mPayInfo.getProductId())){
//                //库存存在用户购买的产品，先去消耗
//            }else{
//                //库存不存在
//            }
        }
    };

    /**
     * 购买商品
     * google应用内支付调用购买接口的时候，应先确保用户没有存在这个商品的购买（买了但是没有消耗）
     * launchPurchaseFlow(Activity, String, int, OnIabPurchaseFinishedListener, String) 购买商品
     */
    public void buyGoods(){
        //在合适的地方调用购买
//        mHelper.launchPurchaseFlow(mContext, sku, RC_REQUEST, mPurchaseFinishedListener, extra);
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
                Log.d(TAG,"Error purchasing: " + result);
                return;
            }

            Log.d(TAG, "Purchase successful.");
            //模拟检测public key
            //购买成功后，应该将购买返回的信息发送到自己的服务端，自己的服务端再去利用public key去验签
//            checkPk(purchase);
        }
    };

    /**
     * 消耗掉商品
     */
    public void expendGoods(){
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
            }
            else {
                Log.d(TAG,"Error while consuming: " + result);
            }
        }
    };
}
