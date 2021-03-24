package com.ecommerce.demo.utils;


import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class ResponseJson {
    private static final String Message = "msg";
    private static final String Info = "info";
    private static final String Flag = "flag";

    public static JSONObject buildSuccess(String msg, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Message, msg);
        jsonObject.put(Flag,"true");
        jsonObject.put(Info, data);
        return jsonObject;
    }

    public static JSONObject buildSuccess(String msg, Map<String,String> map) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Message, msg);
        jsonObject.put(Flag,"true");
        jsonObject.put(Info, map);
        return jsonObject;
    }

    public static JSONObject buildSuccess(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Message, msg);
        jsonObject.put(Flag,"true");
        return jsonObject;
    }

    public static JSONObject buildFail(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Message, msg);
        jsonObject.put(Flag,"false");
        return jsonObject;
    }
}
