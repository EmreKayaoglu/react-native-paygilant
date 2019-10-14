import Paygilant from 'react-native-paygilant'

var LaunchCheckPoint = Object.freeze({
    checkPointType: Paygilant.CheckPointType.TYPE_LAUNCH,
})
var LoginCheckPoint = Object.freeze({
    checkPointType: Paygilant.CheckPointType.TYPE_LOGIN,
    // User detail
    userID: "",
    email: "",
    phoneNumber: "",
})
var RegisterCheckPoint = Object.freeze({
    checkPointType: Paygilant.CheckPointType.TYPE_REGISTER,
    // User detail
    userID: "",
    email: "",
    phoneNumber: "",
})
var TransactionCheckPoint = Object.freeze({
    checkPointType: Paygilant.CheckPointType.TYPE_TRANSACTION,
    transactionType: "",
    timeStamp: 0,
    curType: Paygilant.CurrencyCode.USD,
    userID: "",
    amount: 0,
    destinationId: "",
    paymentMethod: "",
})
var UpdateDetailsCheckPoint = Object.freeze({
    checkPointType: Paygilant.CheckPointType.TYPE_UPDATE_DETAILS,
    userID: "",
})
var AddPaymentMethodCheckPoint = Object.freeze({
    checkPointType: Paygilant.CheckPointType.TYPE_ADD_PAYMENT_METHOD,
    userID: "",

    // Payment details
    paymentMethod: Paygilant.PaymentMethodType.TYPE_UNKNOWN,
    processor: "",
    fullNameOnCard: "",
    // CreditCardDetail
    cardToken:"",
    cardId:"",
    bin:"",
    lastFourDigit:"",
    yearExpiryDate:0,
    monthExpiryDate:0,
    // BankAccountDetails
    number:"",
    fullName:"",
    bsb:"",
    // * Payment end

    // billingAddress
    firstName:"",
    lastName:"",
    addressLine1:"",
    addressLine2:"",
    city:"",
    state:"",
    country:"",
    postalCode:"",
    phoneNumber:"",
})

var GeneralCheckPoint = Object.freeze({
    checkPointType: Paygilant.CheckPointType.TYPE_GENERAL,
})

export function getNewLaunchCheckPoint() {
    var newInstance = Object.assign({}, LaunchCheckPoint)
    return newInstance
}
export function getNewLoginCheckPoint() {
    var newInstance = Object.assign({}, LoginCheckPoint)
    return newInstance
}
export function getRegisterCheckPoint() {
    var newInstance = Object.assign({}, RegisterCheckPoint)
    return newInstance
}
export function getNewTransactionCheckPoint() {
    var newInstance = Object.assign({}, TransactionCheckPoint)
    return newInstance
}
export function getNewUpdateDetailsCheckPoint() {
    var newInstance = Object.assign({}, UpdateDetailsCheckPoint)
    return newInstance
}
export function getNewAddPaymentMethodCheckPoint() {
    var newInstance = Object.assign({}, AddPaymentMethodCheckPoint)
    return newInstance
}
export function getNewGeneralCheckPoint() {
    var newInstance = Object.assign({}, GeneralCheckPoint)
    return newInstance
}

export function getJsonFromCheckPoint(checkpoint) {
    var formBody = [];
    for (var property in checkpoint) {
        var encodedKey = encodeURIComponent(property);
        var encodedValue = encodeURIComponent(checkpoint[property]);
        formBody.push('"' + encodedKey + '":"' + encodedValue + '"');
    }
    var jsonStr = "{" + formBody.join(",") + "}"
    return jsonStr
}