package com.ptc.reactnative.paygilant;

import com.paygilant.PG_FraudDetection_SDK.Biometric.PaygilantScreenListener;

public class PaygilantScreenListenerWrapper {
    public PaygilantScreenListener listener;
    public int _id;

    public PaygilantScreenListenerWrapper(PaygilantScreenListener listener, int _id) {
        this.listener = listener;
        this._id = _id;
    }
}
