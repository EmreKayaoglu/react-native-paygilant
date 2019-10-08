import React, { Component } from 'react'
import { TouchableHighlight, Text, View, Image, Platform, StyleSheet } from 'react-native'
import Paygilant from 'react-native-paygilant'

export default class MyshopScreen extends Component {
    constructor(props) {
        super(props)
        this.state = {
            requestID: this.props.navigation.state.params.requestID,
        }
    }

    componentWillMount() {
        Paygilant.updateCheckPointStatus(Paygilant.CheckPointType.TYPE_GENERAL, this.state.requestID, Paygilant.CheckPointStatus.STATUS_APPROVED, "000")
    }

    componentWillUnmount() {
    }

    logout() {
        this.props.navigation.navigate("Login")
    }

    render() {
        return (
            <View style={styles.container}>
                <Image style={styles.shopImage} source={require('../assets/img/1.png')} />
                <Image style={styles.shopImage} source={require('../assets/img/2.png')} />
                <TouchableHighlight style={styles.button} onPress={() => this.logout()}>
                    <Text style={{ color: 'white', fontSize: 20 }}>{'Logout'}</Text>
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
    shopImage: {
        width: '80%',
        height: 150,
        backgroundColor: 'gray',
        marginTop: 20,
    },
    button: {
        width: '60%',
        height: 42,
        backgroundColor: 'gray',
        marginTop: 20,
        alignItems: 'center',
        justifyContent: 'center'
    }
});