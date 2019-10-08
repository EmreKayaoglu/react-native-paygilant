/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';

import LoginScreen from './src/login'
import MainScreen from './src/main'
import MyshopScreen from './src/myshop'

const AppNavigator = createStackNavigator(
  {
    Login: {
      screen: LoginScreen,
      navigationOptions: () => ({
        title: `LOGIN`,
      })
    },
    Main: {
      screen: MainScreen,
      navigationOptions: () => ({
        title: `MAIN`,
      })
    },
    Myshop: {
      screen: MyshopScreen,
      navigationOptions: () => ({
        title: `MY SHOP`,
      })
    },
  },
  {
    initialRouteName: "Login",
    animationEnabled: true,
  }
);

const AppContainer = createAppContainer(AppNavigator);

export default class App extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <AppContainer />
    );
  }
}