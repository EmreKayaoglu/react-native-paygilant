package com.ptc.reactnative.paygilant;

import android.view.View;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.paygilant.PG_FraudDetection_SDK.Biometric.PaygilantScreenListener;
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

    private final ReactApplicationContext reactContext;
    private ArrayList<PaygilantScreenListener> screenListenerArray;

    public PaygilantModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        screenListenerArray = new ArrayList<>();

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
//    @ReactMethod
//    public void getRiskForCheckPoint(CheckPoint checkPoint, final PaygilantCommunication communicationObj, Callback callback) {
//        String requestID = PaygilantManager.getInstance(reactContext).getRiskForCheckPoint(checkPoint, communicationObj);
//        callback.invoke((requestID));
//    }

    /**
     * Function : arriveToCheckPoint
     */
    @ReactMethod
    public void arriveToCheckPoint(String checkPointType) {
        PaygilantManager.getInstance(reactContext).arriveToCheckPoint(new CheckPoint(CheckPointType.valueOf(checkPointType)) {
            @Override
            public JSONObject getJson() {
                return null;
            }
        });
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
            screenListenerArray.add(listener);
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
        for (PaygilantScreenListener listener:screenListenerArray) {
//            if (listener.id == listenerID) {
//                listener.resumeListen();
//                break;
//            }
        }
    }

    /**
     * Function : pauseListenToSensors
     * Should be called on the onPause() activity method. Can be called also during app interaction when tracking screen touches but not the sensors.
     */
//    @ReactMethod
//    public void pauseListenToSensors(PaygilantScreenListener listener) {
//        listener.pauseListenToSensors();
//    }

    /**
     * StartTouchListener
     * Should be called during Activity onCreate()
     */
//    @ReactMethod
//    public void StartTouchListener(PaygilantScreenListener listener) {
//        listener.StartTouchListener(getCurrentActivity());
//    }

    /**
     * Function : receiveRisk
     */
    @ReactMethod
    public void receiveRisk () {
//        PaygilantManager.init(reactContext,RNModu
//        leConst.SERVER_URL,null);
    }
}
