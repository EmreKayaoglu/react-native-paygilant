import React, { Component } from 'react'
import { TouchableHighlight, Text, View, TextInput, Platform, StyleSheet } from 'react-native'
import Paygilant, { CheckpointType } from 'react-native-paygilant'

export default class LoginScreen extends Component {
    constructor(props) {
        super(props)
        this.state = {
            username: ''
        }
        Paygilant.initialize()
    }

    componentWillMount() {
    }

    componentWillUnmount() {
    }

    login() {
        if (this.state.username != '') {
            Paygilant.setUserId(this.state.username)
            Paygilant.arriveToCheckPoint(CheckpointType.LOGIN)
            this.props.navigation.push("Main", { userId: this.state.username })
        }
    }

    render() {
        return (
            <View style={styles.container}>
                <TextInput
                    onChangeText={(text) => this.setState({ username: text })}
                    value={this.state.username}
                    style={styles.input}
                    placeholder={"Username"}
                    placeholderTextColor='#000000d0'
                    underlineColorAndroid='transparent' />

                <TouchableHighlight style={styles.button} onPress={() => this.login()}>
                    <Text style={{ color: 'white', fontSize: 20 }}>{'Login'}</Text>
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
    input: {
        width: '60%',
        height: 42,
        borderColor: 'black',
        borderWidth: 1,
        fontSize: 20
    },
    button: {
        width: '60%',
        height: 42,
        backgroundColor: 'gray',
        marginTop: 40,
        alignItems: 'center',
        justifyContent: 'center'
    }
});