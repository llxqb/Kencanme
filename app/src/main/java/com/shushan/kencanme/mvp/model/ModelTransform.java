package com.shushan.kencanme.mvp.model;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by li.liu on 2019/05/28.
 * ModelTransform
 */

public class ModelTransform {

    public ResponseData transformCommon(String response) {
        ResponseData responseData;
        try {
            JSONObject jsonObject = new JSONObject(response);
            responseData = new ResponseData(jsonObject);
        } catch (JSONException e) {
            responseData = new ResponseData();
        }
        return responseData;
    }

    //会员接口
    public ResponseData transformTypeTwo(String response) {
        ResponseData responseData;
        try {
            JSONObject jsonObject = new JSONObject(response);
            responseData = new ResponseData(jsonObject, 1);
        } catch (JSONException e) {
            responseData = new ResponseData();
        }
        return responseData;
    }
}
