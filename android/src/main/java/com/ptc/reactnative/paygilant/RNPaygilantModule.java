package com.ptc.reactnative.paygilant;

import android.app.Activity;
import android.view.View;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.paygilant.PG_FraudDetection_SDK.Biometric.PaygilantScreenListener;
import com.paygilant.PG_FraudDetection_SDK.Communication.PaygilantCommunication;
import com.paygilant.PG_FraudDetection_SDK.PaygilantManager;
import com.paygilant.pgdata.CheckPoint.CheckPoint;
import com.paygilant.pgdata.CheckPoint.CheckPointStatus;
import com.paygilant.pgdata.CheckPoint.CheckPointType;
import com.paygilant.pgdata.CheckPoint.ScreenListenerType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * RNPaygilantModule
 *
 * Author: Emre
 * GitHub: https://github.com/EmreKayaoglu
 * Email: emrefurkankayao@gmail.com
 */

public class RNPaygilantModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;

    public RNPaygilantModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNPaygilant";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("ScreenListenerType", Collections.unmodifiableMap(new HashMap<String, Object>(){{
            put("SCREEN_REGISTRATION_FORM", ScreenListenerType.REGISTRATION_FORM);
            put("SCREEN_LOGIN_FORM", ScreenListenerType.LOGIN_FORM);
            put("SCREEN_MAIN", ScreenListenerType.MAIN_SCREEN);
            put("SCREEN_CATALOG", ScreenListenerType.CATALOG_SCREEN);
            put("SCREEN_SEARCH", ScreenListenerType.SEARCH_SCREEN);
            put("SCREEN_PRODUCT", ScreenListenerType.PRODUCT_SCREEN);
            put("SCREEN_GENERAL_FORM", ScreenListenerType.GENERAL_FORM);
            put("SCREEN_PINCODE", ScreenListenerType.PINCODE);
            put("SCREEN_TRANSACTION_DETAILS", ScreenListenerType.TRANSACTION_DETAILS);
            put("SCREEN_CONTACTS", ScreenListenerType.CONTACTS);
            put("SCREEN_PAYMENT", ScreenListenerType.PAYMENT_SCREEN);
            put("SCREEN_GENERAL", ScreenListenerType.GENERAL);
            put("SCREEN_ADD_PAYMENT", ScreenListenerType.ADD_PAYMENT_SCREEN);
            put("SCREEN_WITHDRAW", ScreenListenerType.WITHDRAW_SCREEN);
        }}));

        constants.put("CheckPointType", Collections.unmodifiableMap(new HashMap<String, Object>(){{
            put("TYPE_LAUNCH", CheckPointType.LAUNCH);
            put("TYPE_REGISTER", CheckPointType.REGISTER);
            put("TYPE_LOGIN", CheckPointType.LOGIN);
            put("TYPE_TRANSACTION", CheckPointType.TRANSACTION);
            put("TYPE_UPDATE_DETAILS", CheckPointType.UPDATE_DETAILS);
            put("TYPE_ADD_PAYMENT_METHOD", CheckPointType.ADD_PAYMENT_METHOD);
            put("TYPE_GENERAL", CheckPointType.GENERAL);
        }}));

        constants.put("CheckPointStatus", Collections.unmodifiableMap(new HashMap<String, Object>(){{
            put("STATUS_APPROVED", CheckPointStatus.APPROVED);
            put("STATUS_DENIED", CheckPointStatus.DENIED);
            put("STATUS_CANCELLED", CheckPointStatus.CANCELLED);
        }}));
        return constants;
    }

    /**
     * Function : init
     */
    @ReactMethod
    public void init() {
        PaygilantManager.init(reactContext,RNModuleConst.SERVER_URL,null);
    }

    /**
     * Function : getRiskForCheckPoint
     * Called when arriving at the predefined checkpoints, and the application needs to get Paygilant’s Risk Score.
     */
    @ReactMethod
    public void getRiskForCheckPoint(CheckPoint checkPoint, final PaygilantCommunication communicationObj, Callback callback) {
        String requestID = PaygilantManager.getInstance(reactContext).getRiskForCheckPoint(checkPoint, communicationObj);
        callback.invoke((requestID));
    }

    /**
     * Function : updateCheckPointStatus
     * Called when arriving at the predefined checkpoints, and the application needs to get Paygilant’s Risk Score.
     */
    @ReactMethod
    public void updateCheckPointStatus(CheckPointType type, String requestID, CheckPointStatus status, String transactionID) {
        PaygilantManager.getInstance(reactContext).updateCheckPointStatus(type, requestID, status, transactionID);
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
    public void getScreenListner(ScreenListenerType type, int actionID, View currentView, Callback callback) {
        PaygilantScreenListener listener = PaygilantManager.getInstance(reactContext).startNewScreenListener(type, actionID, getCurrentActivity(), currentView);
        callback.invoke(listener);
    }
    /**
     * Function : resumeListen
     * Call resumeListen inside onResume() activity method, in order to listen for sensors’ events which occurred during the activity lifetime
     */
    @ReactMethod
    public void resumeListen(PaygilantScreenListener listener) {
        listener.resumeListen();
    }

    /**
     * Function : pauseListenToSensors
     * Should be called on the onPause() activity method. Can be called also during app interaction when tracking screen touches but not the sensors.
     */
    @ReactMethod
    public void pauseListenToSensors(PaygilantScreenListener listener) {
        listener.pauseListenToSensors();
    }

    /**
     * StartTouchListener
     * Should be called during Activity onCreate()
     */
    @ReactMethod
    public void StartTouchListener(PaygilantScreenListener listener) {
        listener.StartTouchListener(getCurrentActivity());
    }

    /**
     * Function : receiveRisk
     */
    @ReactMethod
    public void receiveRisk () {
//        PaygilantManager.init(reactContext,RNModuleConst.SERVER_URL,null);
    }

}
