package com.ptc.reactnative.paygilant;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.paygilant.PG_FraudDetection_SDK.Biometric.PaygilantScreenListener;
import com.paygilant.PG_FraudDetection_SDK.Communication.PaygilantCommunication;
import com.paygilant.PG_FraudDetection_SDK.PaygilantManager;
import com.paygilant.pgdata.CheckPoint.CheckPoint;
import com.paygilant.pgdata.CheckPoint.CheckPointStatus;
import com.paygilant.pgdata.CheckPoint.CheckPointType;
import com.paygilant.pgdata.CheckPoint.ScreenListenerType;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PaygilantModule extends ReactContextBaseJavaModule {

    private static final String TAG = "PaygilantModule";
    private final ReactApplicationContext reactContext;
    private ArrayList<PaygilantScreenListenerWrapper> screenListenerArray;
    private ArrayList<CheckPoint> checkPointArray;

    public PaygilantModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        screenListenerArray = new ArrayList<>();
        checkPointArray = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "Paygilant";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("ScreenListenerType", Collections.unmodifiableMap(new HashMap<String, Object>(){{
            put("SCREEN_REGISTRATION_FORM", "REGISTRATION_FORM");
            put("SCREEN_LOGIN_FORM", "LOGIN_FORM");
            put("SCREEN_MAIN", "MAIN_SCREEN");
            put("SCREEN_CATALOG", "CATALOG_SCREEN");
            put("SCREEN_SEARCH", "SEARCH_SCREEN");
            put("SCREEN_PRODUCT", "PRODUCT_SCREEN");
            put("SCREEN_GENERAL_FORM", "GENERAL_FORM");
            put("SCREEN_PINCODE", "PINCODE");
            put("SCREEN_TRANSACTION_DETAILS", "TRANSACTION_DETAILS");
            put("SCREEN_CONTACTS", "CONTACTS");
            put("SCREEN_PAYMENT", "PAYMENT_SCREEN");
            put("SCREEN_GENERAL", "GENERAL");
            put("SCREEN_ADD_PAYMENT", "ADD_PAYMENT_SCREEN");
            put("SCREEN_WITHDRAW", "WITHDRAW_SCREEN");
        }}));

        constants.put("CheckPointType", Collections.unmodifiableMap(new HashMap<String, Object>(){{
            put("TYPE_LAUNCH", "LAUNCH");
            put("TYPE_REGISTER", "REGISTER");
            put("TYPE_LOGIN", "LOGIN");
            put("TYPE_TRANSACTION", "TRANSACTION");
            put("TYPE_UPDATE_DETAILS", "UPDATE_DETAILS");
            put("TYPE_ADD_PAYMENT_METHOD", "ADD_PAYMENT_METHOD");
            put("TYPE_GENERAL", "GENERAL");
        }}));

        constants.put("CheckPointStatus", Collections.unmodifiableMap(new HashMap<String, Object>(){{
            put("STATUS_APPROVED", "APPROVED");
            put("STATUS_DENIED", "DENIED");
            put("STATUS_CANCELLED", "CANCELLED");
        }}));
        return constants;
    }

    /**
     * Function : init
     */
    @ReactMethod
    public void init() {
        PaygilantManager.init(reactContext, AppConstant.SERVER_URL,null);
    }

    /**
     * Function : getRiskForCheckPoint
     * Called when arriving at the predefined checkpoints, and the application needs to get Paygilant’s Risk Score.
     */
    @ReactMethod
    public void getRiskForCheckPoint(String checkPointType, final Callback callback) {
        CheckPoint checkPoint = getCheckPoint(checkPointType);
        final String requestID = PaygilantManager.getInstance(reactContext).getRiskForCheckPoint(checkPoint, new PaygilantCommunication() {
            @Override
            public void receiveRisk(int i, String s, String s1) {

            }
        });
        callback.invoke((requestID));
    }

    /**
     * Function : arriveToCheckPoint
     */
    @ReactMethod
    public void arriveToCheckPoint(String checkPointType) {
        CheckPoint checkPoint = getCheckPoint(checkPointType);
        PaygilantManager.getInstance(reactContext).arriveToCheckPoint(checkPoint);
    }

    /**
     * Function : updateCheckPointStatus
     * Called when arriving at the predefined checkpoints, and the application needs to get Paygilant’s Risk Score.
     */
    @ReactMethod
    public void updateCheckPointStatus(String type, String requestID, String status, String transactionID) {
        PaygilantManager.getInstance(reactContext).updateCheckPointStatus(CheckPointType.valueOf(type), requestID, CheckPointStatus.valueOf(status), transactionID);
    }

    /**
     * Function : setUserId
     */
    @ReactMethod
    public void setUserId(String userId) {
        PaygilantManager.getInstance(reactContext).setUserId(userId);
    }

    /**
     * Function : getScreenListner
     * When starting to track a screen, related to application interactions or behavioral biometrics. This is the only way that the application should initialize PaygilantScreenListener object.
     */
    @ReactMethod
    public void getScreenListner(String type, int actionID, Callback successCallback, Callback errorCallback) {
        View rootView = getCurrentActivity().findViewById(android.R.id.content);
        if (rootView == null) {
            rootView = getCurrentActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        }

        if (rootView != null) {
            PaygilantScreenListener listener = PaygilantManager.getInstance(reactContext).startNewScreenListener(ScreenListenerType.valueOf(type), actionID, getCurrentActivity(), rootView);
            PaygilantScreenListenerWrapper wrapper = new PaygilantScreenListenerWrapper(listener, actionID);
            screenListenerArray.add(wrapper);
            if (successCallback != null)
                successCallback.invoke(actionID);
        } else {
            if (errorCallback != null)
                errorCallback.invoke("Can't find root View");
        }
    }

    /**
     * Function : resumeListen
     * Call resumeListen inside onResume() activity method, in order to listen for sensors’ events which occurred during the activity lifetime
     */
    @ReactMethod
    public void resumeListen(int listenerID) {
        for (PaygilantScreenListenerWrapper wrapper:screenListenerArray) {
            if (wrapper._id == listenerID) {
                Log.i(TAG, "PaygilantScreenListener("+ listenerID + ") resumeListen");
                wrapper.listener.resumeListen();
                break;
            }
        }
    }

    /**
     * Function : pauseListenToSensors
     * Should be called on the onPause() activity method. Can be called also during app interaction when tracking screen touches but not the sensors.
     */
    @ReactMethod
    public void pauseListenToSensors(int listenerID) {
        for (PaygilantScreenListenerWrapper wrapper:screenListenerArray) {
            if (wrapper._id == listenerID) {
                Log.i(TAG, "PaygilantScreenListener("+ listenerID + ") pauseListenToSensors");
                wrapper.listener.pauseListenToSensors();
                break;
            }
        }
    }

    /**
     * StartTouchListener
     * Should be called during Activity onCreate()
     */
    @ReactMethod
    public void StartTouchListener(int listenerID) {
        for (PaygilantScreenListenerWrapper wrapper:screenListenerArray) {
            if (wrapper._id == listenerID) {
                Log.i(TAG, "PaygilantScreenListener("+ listenerID + ") pauseListenToSensors");
                wrapper.listener.StartTouchListener(getCurrentActivity());
                break;
            }
        }
    }

    /**
     * Function : onRequestPermissionsResult
     */
    @ReactMethod
    public void onRequestPermissionsResult(int requestCode, ReadableArray permissions, ReadableArray grantResults) {
        int p1 = (permissions != null) ? permissions.size() : 0;
        int p2 = (grantResults != null) ? grantResults.size() : 0;
        String[] strPermissions = new String[p1];
        int[] intGrantResults = new int[p2];

        if (permissions != null && permissions.size() > 0) {
            for (int i = 0; i < permissions.size(); i++) {
                strPermissions[i] = permissions.getString(i);
            }
        }

        if (grantResults != null && grantResults.size() > 0) {
            for (int i = 0; i < grantResults.size(); i++) {
                intGrantResults[i] = grantResults.getInt(i);
            }
        }

        PaygilantManager.getInstance(reactContext).onRequestPermissionsResult(requestCode, strPermissions, intGrantResults);
    }

    private CheckPoint getCheckPoint(String checkPointType) {
        CheckPoint checkPoint = null;
        for (CheckPoint cp:checkPointArray) {
            if (cp.getType() == CheckPointType.valueOf(checkPointType)) {
                checkPoint = cp;
                break;
            }
        }
        if (checkPoint == null) {
            checkPoint = new CheckPoint(CheckPointType.valueOf(checkPointType)) {
                @Override
                public JSONObject getJson() {
                    return null;
                }
            };
            checkPointArray.add(checkPoint);
        }

        return checkPoint;
    }
}
