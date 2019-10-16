package com.ptc.reactnative.paygilant;

import android.util.Log;

import com.paygilant.pgdata.CheckPoint.AddPaymentMethod;
import com.paygilant.pgdata.CheckPoint.CurrencyCode;
import com.paygilant.pgdata.CheckPoint.Login;
import com.paygilant.pgdata.CheckPoint.Registration;
import com.paygilant.pgdata.CheckPoint.Transaction;
import com.paygilant.pgdata.CheckPoint.TransactionType;
import com.paygilant.pgdata.CheckPoint.param.Address;
import com.paygilant.pgdata.CheckPoint.param.AuthorizationResponse;
import com.paygilant.pgdata.CheckPoint.param.BankAccountDetails;
import com.paygilant.pgdata.CheckPoint.param.CreditCardDetail;
import com.paygilant.pgdata.CheckPoint.param.Payment;
import com.paygilant.pgdata.CheckPoint.param.PaymentMethodType;
import com.paygilant.pgdata.CheckPoint.param.User;
import com.paygilant.pgdata.CheckPoint.param.VerificationType;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParseUtils {

    public static Registration getRegistrationFromJson(JSONObject object) {
        try {
            User user = getUserFromJson(object.getJSONObject("user"));
            Log.d("Json Parse", "Registration parse success");

            Registration registration = new Registration(user.getUserId(), user.getEmail(), user.getPhoneUser());
            return registration;
        } catch (JSONException e) {
            Log.d("Json Parse", "Registration parse error");
            return null;
        }
    }

    public static Login getLoginFromJson(JSONObject object) {
        try {
            User user = getUserFromJson(object.getJSONObject("user"));
            Log.d("Json Parse", "Login parse success");

            Login login = new Login(user.getUserId(), user.getEmail(), user.getPhoneUser());
            return login;
        } catch (JSONException e) {
            Log.d("Json Parse", "Login parse error");
            return null;
        }
    }

    public static AddPaymentMethod getAddPaymentMethodFromJson(JSONObject object) {
        try {
            User user = getUserFromJson(object.getJSONObject("user"));
            Payment payment = getPaymentFromJson(object.getJSONObject("payment"));
            Address billingAddress = getAddressFromJson(object.getJSONObject("billingAddress"));
            Log.d("Json Parse", "AddPaymentMethod parse success");

            AddPaymentMethod method = new AddPaymentMethod(user, payment, billingAddress);
            return method;
        } catch (JSONException e) {
            Log.d("Json Parse", "AddPaymentMethod parse error");
            return null;
        }
    }

    public static Transaction getTransactionFromJson(JSONObject object) {
        try {
            long timeStamp = Long.parseLong(object.getString("timeStamp"));
            User user = getUserFromJson(object.getJSONObject("user"));
            double amount = Double.parseDouble(object.getString("amount"));
            CurrencyCode curType = CurrencyCode.valueOf(object.getString("curType"));
            TransactionType actType = TransactionType.valueOf(object.getString("transactionType"));
            String destinationId = object.getString("destinationId");
            Payment payment = getPaymentFromJson(object.getJSONObject("payment"));
            Address billingAddress = getAddressFromJson(object.getJSONObject("billingAddress"));
            Address shippingAddress = getAddressFromJson(object.getJSONObject("shippingAddress"));
            AuthorizationResponse authorizationResponse = getAuthorizationResponseFromJson(object.getJSONObject("authorizationResponse"));
            Log.d("Json Parse", "Transaction parse success");

            Transaction transaction = new Transaction(timeStamp, actType,curType, destinationId,  amount, user, billingAddress, shippingAddress, payment, authorizationResponse);
            return transaction;
        } catch (JSONException e) {
            Log.d("Json Parse", "Transaction parse error");
            return null;
        }
    }

    private static User getUserFromJson(JSONObject object) {
        try {
            String userId = object.getString("userId");
            String email = object.getString("email");
            VerificationType isEmailVerified = VerificationType.valueOf(object.getString("isEmailVerified"));
            String phoneUser = object.getString("phoneUser");
            VerificationType isPhoneUserVerified = VerificationType.valueOf(object.getString("isPhoneUserVerified"));
            Log.d("Json Parse", "User parse success");

            User user = new User(email, isEmailVerified, phoneUser, isPhoneUserVerified);
            user.setUserId(userId);

            return user;
        } catch (JSONException e) {
            Log.d("Json Parse", "User parse error");
            return new User();
        }
    }

    private static CreditCardDetail getCreditCardDetailFromJson(JSONObject object) {
        try {
            String bin = object.getString("bin");
            String lastFourDigit = object.getString("lastFourDigit");
            int yearExpiryDate = Integer.parseInt(object.getString("yearExpiryDate"));
            int monthExpiryDate = Integer.parseInt(object.getString("monthExpiryDate"));
            String card_token = object.getString("card_token");
            String cardId = object.getString("cardId");
            Log.d("Json Parse", "CreditCardDetail parse success");

            CreditCardDetail cardDetail = new CreditCardDetail(card_token, cardId, bin, lastFourDigit, yearExpiryDate, monthExpiryDate);
            return cardDetail;
        } catch (JSONException e) {
            Log.d("Json Parse", "CreditCardDetail parse error");
            return new CreditCardDetail();
        }
    }

    private static BankAccountDetails getBankAccountDetailsFromJson(JSONObject object) {
        try {
            String bankAccountNumber = object.getString("bankAccountNumber");
            String bankAccountFullname = object.getString("bankAccountFullname");
            String bankAccountBsb = object.getString("bankAccountBsb");
            Log.d("Json Parse", "BankAccountDetails parse success");

            BankAccountDetails accountDetails = new BankAccountDetails(bankAccountNumber, bankAccountFullname, bankAccountBsb);
            return accountDetails;
        } catch (JSONException e) {
            Log.d("Json Parse", "BankAccountDetails parse error");
            return new BankAccountDetails();
        }
    }

    private static Address getAddressFromJson(JSONObject object) {
        try {
            String firstName = object.getString("firstName");
            String lastName = object.getString("lastName");
            String addressLine1 = object.getString("addressLine1");
            String addressLine2 = object.getString("addressLine2");
            String city = object.getString("city");
            String state = object.getString("state");
            String country = object.getString("country");
            String postalCode = object.getString("postalCode");
            String phoneNumber = object.getString("phoneNumber");
            Log.d("Json Parse", "Address parse success");

            Address address = new Address(firstName, lastName, addressLine1, addressLine2, city, state, country, postalCode, phoneNumber);
            return address;
        } catch (JSONException e) {
            Log.d("Json Parse", "Address parse error");
            return new Address();
        }
    }

    private static AuthorizationResponse getAuthorizationResponseFromJson(JSONObject object) {
        try {
            String verificationStatus = object.getString("verificationStatus");
            String aVSResultCode = object.getString("aVSResultCode");
            String declineReasonCode = object.getString("declineReasonCode");
            String declineReasonMessage = object.getString("declineReasonMessage");
            String acquirerResponseCode = object.getString("acquirerResponseCode");
            String acquirerResponseMessage = object.getString("acquirerResponseMessage");
            String threeDSResponseStatus = object.getString("threeDSResponseStatus");
            String threeDSResponseEnrolled = object.getString("threeDSResponseEnrolled");
            String threeDSResponseECI = object.getString("threeDSResponseECI");
            Log.d("Json Parse", "AuthorizationResponse parse success");

            AuthorizationResponse response = new AuthorizationResponse(verificationStatus, aVSResultCode, declineReasonCode, declineReasonMessage, acquirerResponseCode, acquirerResponseMessage, threeDSResponseStatus, threeDSResponseEnrolled, threeDSResponseECI);
            return response;
        } catch (JSONException e) {
            Log.d("Json Parse", "AuthorizationResponse parse error");
            return new AuthorizationResponse();
        }
    }


    private static Payment getPaymentFromJson(JSONObject object) {
        try {
            PaymentMethodType type = PaymentMethodType.valueOf(object.getString("paymentMethod"));
            String processor = object.getString("processor");
            String fullNameOnCard = object.getString("fullNameOnCard");
            CreditCardDetail cardDetail = getCreditCardDetailFromJson(object.getJSONObject("creditCardDetail"));
            BankAccountDetails accountDetails = getBankAccountDetailsFromJson(object.getJSONObject("bankAccountDetails"));
            Log.d("Json Parse", "Payment parse success");

            Payment payment = new Payment(type, processor, fullNameOnCard, cardDetail, accountDetails);
            return payment;
        } catch (JSONException e) {
            Log.d("Json Parse", "Payment parse error");
            return new Payment();
        }
    }

}
