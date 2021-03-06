import React, { Component } from 'react'
import { TouchableHighlight, Text, View, TextInput, Platform, StyleSheet, AppState } from 'react-native'
import Paygilant, { Transaction, User, CurrencyCode, TransactionType, Address, PaymentMethodType } from 'react-native-paygilant'
import { NativeEventEmitter, NativeModules } from 'react-native';

export default class MainScreen extends Component {
    constructor(props) {
        super(props)
        this.state = {
            userId: this.props.navigation.state.params.userId,
            appState: AppState.currentState,
            paygilantListenerID: 100,
            requestID: null,
        }
        Paygilant.getScreenListner(Paygilant.ScreenListenerType.SCREEN_MAIN, this.state.paygilantListenerID,
            (id) => {
                console.log("startNewScreenListener success");
            },
            error => {
                console.log(error);
            })
    }

    componentDidMount() {
        AppState.addEventListener('change', this._handleAppStateChange);

        // receive event from native code for getRiskForCheckPoint
        const eventEmitter = new NativeEventEmitter(NativeModules.ToastExample)
        eventEmitter.addListener('RiskforCheckPointEvent', (event) => {
            var riskLevel = event.riskLevel
            var signedRisk = event.signedRisk
            var requestId = event.requestId

            alert("Receive event from " + requestId + ": riskLevel=" + riskLevel + ", signedRisk=" + signedRisk)
        })
    }

    componentWillUnmount() {
        AppState.removeEventListener('change', this._handleAppStateChange);
    }

    _handleAppStateChange = (nextAppState) => {
        if (this.state.appState.match(/inactive|background/) && nextAppState === 'active') {
            console.log('App has come to the foreground!');
            if (Platform.OS === "android") {
                Paygilant.resumeListen(this.state.paygilantListenerID)
            }
        } else {//if (this.state.appState.match(/active/) && (nextAppState === 'inactive' || nextAppState === 'background')) {
            console.log('App has come to the background!');
            if (Platform.OS === "android") {
                Paygilant.pauseListenToSensors(this.state.paygilantListenerID)
            }
        }
        this.setState({ appState: nextAppState });
    };


    sendMoney() {
        var transaction = Transaction
        transaction.timeStamp = new Date().getTime()
        transaction.user.userId = this.state.userId
        transaction.amount = 50
        transaction.curType = CurrencyCode.USD
        transaction.transactionType = TransactionType.PURCHASE
        transaction.destinationId ="DestinationID_1"
        transaction.payment.paymentMethod = PaymentMethodType.CREDIT_CARD
        transaction.payment.creditCardDetail.lastFourDigit = "8794"

        // alert(JSON.stringify(transaction))
        Paygilant.getRiskForCheckPoint(JSON.stringify(transaction), (requestID) => {
            this.setState({ requestID: requestID })
        })
    }

    myshop() {
        var transaction = Transaction
        transaction.timeStamp = new Date().getTime()
        transaction.user.userId = this.state.userId
        transaction.amount = 100
        transaction.curType = CurrencyCode.USD
        transaction.transactionType = TransactionType.PURCHASE
        transaction.destinationId ="DestinationID_2"
        transaction.payment.paymentMethod = PaymentMethodType.CREDIT_CARD
        transaction.payment.creditCardDetail.lastFourDigit = "8799"

        Paygilant.getRiskForCheckPoint(JSON.stringify(transaction), (requestID) => {
            this.setState({ requestID: requestID })
            this.props.navigation.push("Myshop", { requestID: requestID })
        })
    }

    render() {
        return (
            <View style={styles.container}>
                <TouchableHighlight style={styles.button} onPress={() => this.sendMoney()}>
                    <Text style={{ color: 'white', fontSize: 30 }}>{'Send Money'}</Text>
                </TouchableHighlight>
                <TouchableHighlight style={styles.button} onPress={() => this.myshop()}>
                    <Text style={{ color: 'white', fontSize: 30 }}>{'My Shop'}</Text>
                </TouchableHighlight>
            </View>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        flexDirection: 'column',
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center'
    },
    button: {
        width: '60%',
        height: 100,
        backgroundColor: 'gray',
        marginTop: 20,
        alignItems: 'center',
        justifyContent: 'center'
    }
});