import { NativeModules } from 'react-native';

const { Paygilant } = NativeModules;

//consts

//checkpoint type enum
const CheckpointType = {
    LAUNCH: "LAUNCH",
    REGISTER: "REGISTER",
    LOGIN: "LOGIN",
    TRANSACTION: "TRANSACTION",
    UPDATE_DETAILS: "UPDATE_DETAILS",
    ADD_PAYMENT_METHOD: "ADD_PAYMENT_METHOD",
    GENERAL: "GENERAL"
}

//verification type enum
const VerificationType = {
    VERIFIED: "VERIFIED",
    NOT_VERIFIED: "NOT_VERIFIED",
    UNKNOWN: "UNKNOWN"
}

//payment method type enum
const PaymentMethodType = {
    CREDIT_CARD: "CREDIT_CARD",
    BANK_ACCOUNT: "BANK_ACCOUNT",
    ECHECK: "ECHECK",
    UNKNOWN: "UNKNOWN"
}

//checkpoint update status enum - usually for updating transaction 
const CheckPointStatus = {

    /**
    * checkpoint was approved by the application / application server
    */
    APPROVED: "APPROVED",

    /**
     * checkpoint was denied / blocked by the application / application server
     */
    DENIED: "DENIED",

    /**
     * checkpoint was canceled by the user
     */
    CANCELLED: "CANCELLED"
}

//transaction type enum
const TransactionType = {

    /**
     * payment for goods
     */
    PURCHASE: "PURCHASE",

    /**
     * money transfer to another payee
     */
    MONEY_TRANSFER: "MONEY_TRANSFER",

    /**
     * money deposit to store
     */
    DEPOSIT: "DEPOSIT",

    /**
     * money withdraw from store
     */
    WITHDRAW: "WITHDRAW",
}

//all supported currencty code enum
const CurrencyCode = {
    AFN: "AFN",
    EUR: "EUR",
    ALL: "ALL",
    DZD: "DZD",
    USD: "USD",
    AOA: "AOA",
    XCD: "XCD",
    ARS: "ARS",
    AMD: "AMD",
    AWG: "AWG",
    AUD: "AUD",
    AZN: "AZN",
    BSD: "BSD",
    BHD: "BHD",
    BDT: "BDT",
    BBD: "BBD",
    BYN: "BYN",
    BZD: "BZD",
    XOF: "XOF",
    BMD: "BMD",
    INR: "INR",
    BTN: "BTN",
    BOB: "BOB",
    BOV: "BOV",
    BAM: "BAM",
    BWP: "BWP",
    NOK: "NOK",
    BRL: "BRL",
    BND: "BND",
    BGN: "BGN",
    BIF: "BIF",
    CVE: "CVE",
    KHR: "KHR",
    XAF: "XAF",
    CAD: "CAD",
    KYD: "KYD",
    CLP: "CLP",
    CLF: "CLF",
    CNY: "CNY",
    COP: "COP",
    COU: "COU",
    KMF: "KMF",
    CDF: "CDF",
    NZD: "NZD",
    CRC: "CRC",
    HRK: "HRK",
    CUP: "CUP",
    CUC: "CUC",
    ANG: "ANG",
    CZK: "CZK",
    DKK: "DKK",
    DJF: "DJF",
    DOP: "DOP",
    EGP: "EGP",
    SVC: "SVC",
    ERN: "ERN",
    ETB: "ETB",
    FKP: "FKP",
    FJD: "FJD",
    XPF: "XPF",
    GMD: "GMD",
    GEL: "GEL",
    GHS: "GHS",
    GIP: "GIP",
    GTQ: "GTQ",
    GBP: "GBP",
    GNF: "GNF",
    GYD: "GYD",
    HTG: "HTG",
    HNL: "HNL",
    HKD: "HKD",
    HUF: "HUF",
    ISK: "ISK",
    IDR: "IDR",
    XDR: "XDR",
    IRR: "IRR",
    IQD: "IQD",
    ILS: "ILS",
    JMD: "JMD",
    JPY: "JPY",
    JOD: "JOD",
    KZT: "KZT",
    KES: "KES",
    KPW: "KPW",
    KRW: "KRW",
    KWD: "KWD",
    KGS: "KGS",
    LAK: "LAK",
    LBP: "LBP",
    LSL: "LSL",
    ZAR: "ZAR",
    LRD: "LRD",
    LYD: "LYD",
    CHF: "CHF",
    MOP: "MOP",
    MKD: "MKD",
    MGA: "MGA",
    MWK: "MWK",
    MYR: "MYR",
    MVR: "MVR",
    MRU: "MRU",
    MUR: "MUR",
    XUA: "XUA",
    MXN: "MXN",
    MXV: "MXV",
    MDL: "MDL",
    MNT: "MNT",
    MAD: "MAD",
    MZN: "MZN",
    MMK: "MMK",
    NAD: "NAD",
    NPR: "NPR",
    NIO: "NIO",
    NGN: "NGN",
    OMR: "OMR",
    PKR: "PKR",
    PAB: "PAB",
    PGK: "PGK",
    PYG: "PYG",
    PEN: "PEN",
    PHP: "PHP",
    PLN: "PLN",
    QAR: "QAR",
    RON: "RON",
    RUB: "RUB",
    RWF: "RWF",
    SHP: "SHP",
    WST: "WST",
    STN: "STN",
    SAR: "SAR",
    RSD: "RSD",
    SCR: "SCR",
    SLL: "SLL",
    SGD: "SGD",
    XSU: "XSU",
    SBD: "SBD",
    SOS: "SOS",
    SSP: "SSP",
    LKR: "LKR",
    SDG: "SDG",
    SRD: "SRD",
    SZL: "SZL",
    SEK: "SEK",
    CHE: "CHE",
    CHW: "CHW",
    SYP: "SYP",
    TWD: "TWD",
    TJS: "TJS",
    TZS: "TZS",
    THB: "THB",
    TOP: "TOP",
    TTD: "TTD",
    TND: "TND",
    TRY: "TRY",
    TMT: "TMT",
    UGX: "UGX",
    UAH: "UAH",
    AED: "AED",
    USN: "USN",
    UYU: "UYU",
    UYI: "UYI",
    UYW: "UYW",
    UZS: "UZS",
    VUV: "VUV",
    VES: "VES",
    VND: "VND",
    YER: "YER",
    ZMW: "ZMW",
    ZWL: "ZWL",
    XBA: "XBA",
    XBB: "XBB",
    XBC: "XBC",
    XBD: "XBD",
    XTS: "XTS",
    XXX: "XXX",
    XAU: "XAU",
    XPD: "XPD",
    XPT: "XPT",
    XAG: "XAG",
}

//objects

//User object holds end user information
var User = Object({
    //user id
    userId: "",

    //user email
    email: "",

    //is email verified: choose type from VerificationType enum
    isEmailVerified: VerificationType.UNKNOWN,

    //user phone
    phoneUser: "",

    //is phone verified: choose type from VerificationType enum
    isPhoneUserVerified: VerificationType.UNKNOWN
})

//Credit card detail information
var CreditCardDetail = Object({
    //bin number
    bin: "",

    //last four digits of card
    lastFourDigit: "",

    //year expiry
    yearExpiryDate: 0,

    //month expiry
    monthExpiryDate: 0,

    //card token
    card_token: "",

    //card id
    cardId: "",
})

//Bank account detail information
var BankAccountDetails = Object({

    //bank account number
    bankAccountNumber: "",

    //bank account owner full name
    bankAccountFullname: "",

    //bank account state branch
    bankAccountBsb: "",
})

//Payment object obtains payment options information
var Payment = Object({
    paymentMethod: PaymentMethodType.UNKNOWN,
    processor: "",
    fullNameOnCard: "",
    creditCardDetail: CreditCardDetail,
    bankAccountDetails: BankAccountDetails,
})

//Address object information
var Address = Object({
    firstName: "",
    lastName: "",
    addressLine1: "",
    addressLine2: "",
    city: "",
    state: "",
    country: "",
    postalCode: "",
    phoneNumber: "",
})

//AuthorizationResponse object holds clearing/bank response for transaction
var AuthorizationResponse = Object({
    verificationStatus: "",
    aVSResultCode: "",
    declineReasonCode: "",
    declineReasonMessage: "",
    acquirerResponseCode: "",
    acquirerResponseMessage: "",
    threeDSResponseStatus: "",
    threeDSResponseEnrolled: "",
    threeDSResponseECI: ""
})

//checkpoints

/**
 * General checkpoint, can be used on any place in the app
 */
var General = Object({
    type: CheckpointType.GENERAL,
    additionalData: {},
})

/**
 * Login checkpoint, to be used during login attempt
 */
var Login = Object({
    //User object holds end user information
    user: User,

    //checkpoint type login, remains unchanged
    type: CheckpointType.LOGIN,

    //additional data that can be added as a json format
    additionalData: {},
})

/**
 * Registration checkpoint. to be used after registration of new user.
 */
var Register = Object({
    //User object holds end user information
    user: User,

    //checkpoint type register, remains unchanged
    type: CheckpointType.REGISTER,

    //additional data that can be added as a json format
    additionalData: {},
})

/**
 * Add Payment Method checkpoint
 * use when user add / change his payment method
 * e.g add credit card details or bank account details
 */
var AddPaymentMethod = Object({
    //User object holds end user information
    user: User,

    //Payment object obtains payment options information
    payment: Payment,

    //Billing Address object obtains with address related data information
    billingAddress: Address,

    //checkpoint type register, remains unchanged
    type: CheckpointType.ADD_PAYMENT_METHOD,

    //additional data that can be added as a json format
    additionalData: {},
})

/**
 * Transaction checkpoint. to be used on transaction attempt.
 * There are few types of transactions, see transactionType enum
 */
var Transaction = Object({
	// timestamp
	timeStamp: 0,

    //User object holds end user information
    user: User,

    //amount of transaction
    amount: 0,

    //currency type, choose from CurrencyCode enum
    curType: CurrencyCode.USD,

    //transaction type, choose from TransactionType enum
    transactionType: TransactionType.PURCHASE,

    //end user that transaction is going to
    destinationId: "",

    //Payment object obtains payment options information
    payment: Payment,

    //Billing Address object obtains with address related data information
    billingAddress: Address,

    //Shipping Address object obtains with address related data information
    shippingAddress: Address,

    //XXXXXXXXXX
    authorizationResponse: AuthorizationResponse,

    //checkpoint type register, remains unchanged
    type: CheckpointType.TRANSACTION,

    //additional data that can be added as a json format
    additionalData: {},
})


export default Paygilant

export {
    General, Login, Register, AddPaymentMethod, Transaction,
    User, Payment, CreditCardDetail, BankAccountDetails, Address, AuthorizationResponse,
    CheckpointType, VerificationType, PaymentMethodType, CheckPointStatus, TransactionType, CurrencyCode
}
