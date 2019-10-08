import React, { Component } from 'react'
import { TouchableHighlight, Text, View, TextInput, Platform, StyleSheet, AppState } from 'react-native'
import Paygilant from 'react-native-paygilant'

export default class MainScreen extends Component {
    constructor(props) {
        super(props)
        this.state = {
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
        Paygilant.getRiskForCheckPoint(Paygilant.CheckPointType.TYPE_GENERAL, (requestID) => { this.setState({ requestID: requestID }) })
    }

    myshop() {
        Paygilant.getRiskForCheckPoint(Paygilant.CheckPointType.TYPE_GENERAL, (requestID) => {
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