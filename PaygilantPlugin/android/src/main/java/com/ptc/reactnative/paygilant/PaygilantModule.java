package com.ptc.reactnative.paygilant;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.paygilant.PG_FraudDetection_SDK.Biometric.PaygilantScreenListener;
import com.paygilant.PG_FraudDetection_SDK.Communication.PaygilantCommunication;
import com.paygilant.PG_FraudDetection_SDK.PaygilantManager;
import com.paygilant.pgdata.CheckPoint.CheckPoint;
import com.paygilant.pgdata.CheckPoint.CheckPointStatus;
import com.paygilant.pgdata.CheckPoint.CheckPointType;
import com.paygilant.pgdata.CheckPoint.CurrencyCode;
import com.paygilant.pgdata.CheckPoint.ScreenListenerType;
import com.paygilant.pgdata.CheckPoint.Transaction;
import com.paygilant.pgdata.CheckPoint.TransactionType;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

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

        constants.put("TransactionType", Collections.unmodifiableMap(new HashMap<String, Object>(){{
            put("PURCHASE", "PURCHASE");
            put("MONEY_TRANSFER", "MONEY_TRANSFER");
            put("DEPOSIT", "DEPOSIT");
            put("WITHDRAW", "WITHDRAW");
            put("P2P", "P2P");
            put("IN_STORE", "IN_STORE");
            put("LOAD", "LOAD");
            put("AIRTIME", "AIRTIME");
            put("ONLINE_STORE", "ONLINE_STORE");
        }}));

        constants.put("CheckPointStatus", Collections.unmodifiableMap(new HashMap<String, Object>(){{
            put("STATUS_APPROVED", "APPROVED");
            put("STATUS_DENIED", "DENIED");
            put("STATUS_CANCELLED", "CANCELLED");
        }}));

        constants.put("CurrencyCode", Collections.unmodifiableMap(new HashMap<String, Object>(){{
            put("AFN", "AFN"); put("EUR", "EUR"); put("ALL", "ALL"); put("DZD", "DZD"); put("USD", "USD"); put("AOA", "AOA");
            put("XCD", "XCD"); put("ARS", "ARS"); put("AMD", "AMD"); put("AWG", "AWG"); put("AUD", "AUD"); put("AZN", "AZN");
            put("BSD", "BSD"); put("BHD", "BHD"); put("BDT", "BDT"); put("BBD", "BBD"); put("BYN", "BYN"); put("BZD", "BZD");
            put("XOF", "XOF"); put("BMD", "BMD"); put("INR", "INR"); put("BTN", "BTN"); put("BOB", "BOB"); put("BOV", "BOV");
            put("BAM", "BAM"); put("BWP", "BWP"); put("NOK", "NOK"); put("BRL", "BRL"); put("BND", "BND"); put("BGN", "BGN");
            put("BIF", "BIF"); put("CVE", "CVE"); put("KHR", "KHR"); put("XAF", "XAF"); put("CAD", "CAD"); put("KYD", "KYD");
            put("CLP", "CLP"); put("CLF", "CLF"); put("CNY", "CNY"); put("COP", "COP"); put("COU", "COU"); put("KMF", "KMF");
            put("CDF", "CDF"); put("NZD", "NZD"); put("CRC", "CRC"); put("HRK", "HRK"); put("CUP", "CUP"); put("CUC", "CUC");
            put("ANG", "ANG"); put("CZK", "CZK"); put("DKK", "DKK"); put("DJF", "DJF"); put("DOP", "DOP"); put("EGP", "EGP");
            put("SVC", "SVC"); put("ERN", "ERN"); put("ETB", "ETB"); put("FKP", "FKP"); put("FJD", "FJD"); put("XPF", "XPF");
            put("GMD", "GMD"); put("GEL", "GEL"); put("GHS", "GHS"); put("GIP", "GIP"); put("GTQ", "GTQ"); put("GBP", "GBP");
            put("GNF", "GNF"); put("GYD", "GYD"); put("HTG", "HTG"); put("HNL", "HNL"); put("HKD", "HKD"); put("HUF", "HUF");
            put("ISK", "ISK"); put("IDR", "IDR"); put("XDR", "XDR"); put("IRR", "IRR"); put("IQD", "IQD"); put("ILS", "ILS");
            put("JMD", "JMD"); put("JPY", "JPY"); put("JOD", "JOD"); put("KZT", "KZT"); put("KES", "KES"); put("KPW", "KPW");
            put("KRW", "KRW"); put("KWD", "KWD"); put("KGS", "KGS"); put("LAK", "LAK"); put("LBP", "LBP"); put("LSL", "LSL");
            put("ZAR", "ZAR"); put("LRD", "LRD"); put("LYD", "LYD"); put("CHF", "CHF"); put("MOP", "MOP"); put("MKD", "MKD");
            put("MGA", "MGA"); put("MWK", "MWK"); put("MYR", "MYR"); put("MVR", "MVR"); put("MRU", "MRU"); put("MUR", "MUR");
            put("XUA", "XUA"); put("MXN", "MXN"); put("MXV", "MXV"); put("MDL", "MDL"); put("MNT", "MNT"); put("MAD", "MAD");
            put("MZN", "MZN"); put("MMK", "MMK"); put("NAD", "NAD"); put("NPR", "NPR"); put("NIO", "NIO"); put("NGN", "NGN");
            put("OMR", "OMR"); put("PKR", "PKR"); put("PAB", "PAB"); put("PGK", "PGK"); put("PYG", "PYG"); put("PEN", "PEN");
            put("PHP", "PHP"); put("PLN", "PLN"); put("QAR", "QAR"); put("RON", "RON"); put("RUB", "RUB"); put("RWF", "RWF");
            put("SHP", "SHP"); put("WST", "WST"); put("STN", "STN"); put("SAR", "SAR"); put("RSD", "RSD"); put("SCR", "SCR");
            put("SLL", "SLL"); put("SGD", "SGD"); put("XSU", "XSU"); put("SBD", "SBD"); put("SOS", "SOS"); put("SSP", "SSP");
            put("LKR", "LKR"); put("SDG", "SDG"); put("SRD", "SRD"); put("SZL", "SZL"); put("SEK", "SEK"); put("CHE", "CHE");
            put("CHW", "CHW"); put("SYP", "SYP"); put("TWD", "TWD"); put("TJS", "TJS"); put("TZS", "TZS"); put("THB", "THB");
            put("TOP", "TOP"); put("TTD", "TTD"); put("TND", "TND"); put("TRY", "TRY"); put("TMT", "TMT"); put("UGX", "UGX");
            put("UAH", "UAH"); put("AED", "AED"); put("USN", "USN"); put("UYU", "UYU"); put("UYI", "UYI"); put("UYW", "UYW");
            put("UZS", "UZS"); put("VUV", "VUV"); put("VES", "VES"); put("VND", "VND"); put("YER", "YER"); put("ZMW", "ZMW");
            put("ZWL", "ZWL"); put("XBA", "XBA"); put("XBB", "XBB"); put("XBC", "XBC"); put("XBD", "XBD"); put("XTS", "XTS");
            put("XXX", "XXX"); put("XAU", "XAU"); put("XPD", "XPD"); put("XPT", "XPT"); put("XAG", "XAG");
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
    public void getRiskForCheckPoint(String transactionType, double time_stamp, String curType, String userID, double amount, String destinationId, String paymentMethod, final Callback callback) {
        Transaction transaction = getTransaction(transactionType, time_stamp, curType, userID, amount, destinationId, paymentMethod);
        final String requestID = PaygilantManager.getInstance(reactContext).getRiskForCheckPoint(transaction, new PaygilantCommunication() {
            @Override
            public void receiveRisk(int riskLevel, String signedRisk, String requestId) {
                // send event to javascript side
                WritableMap params = Arguments.createMap();
                params.putInt("riskLevel", riskLevel);
                params.putString("signedRisk", signedRisk);
                params.putString("requestId", requestId);
                sendEvent(reactContext, "RiskforCheckPointEvent", params);
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

    private Transaction getTransaction(String transactionType, double time_stamp, String curType, String userID, double amount, String destinationId, String paymentMethod) {
        Transaction tr = new Transaction(new Date((long)time_stamp), TransactionType.valueOf(transactionType), CurrencyCode.valueOf(curType), userID, amount, destinationId, paymentMethod);
        return  tr;
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

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
