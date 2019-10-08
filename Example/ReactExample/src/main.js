import React, { Component } from 'react'
import { TouchableHighlight, Text, View, TextInput, Platform, StyleSheet, AppState } from 'react-native'

export default class MainScreen extends Component {
    constructor(props) {
        super(props)
        this.state = {
            appState: AppState.currentState,
            paygilantListenerID: 100,
            requestID: "",
        }
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
        } else {//if (this.state.appState.match(/active/) && (nextAppState === 'inactive' || nextAppState === 'background')) {
            console.log('App has come to the background!');
        }
        this.setState({ appState: nextAppState });
    };


    sendMoney() {
    }

    myshop() {
        this.props.navigation.push("Myshop")
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