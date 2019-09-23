# react-native-paygilant

## Getting started

`$ npm install react-native-paygilant --save`

### Mostly automatic installation

`$ react-native link react-native-paygilant`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-paygilant` and add `Paygilant.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libPaygilant.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.reactlibrary.PaygilantPackage;` to the imports at the top of the file
  - Add `new PaygilantPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-paygilant'
  	project(':react-native-paygilant').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-paygilant/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-paygilant')
  	```


## Usage
```javascript
import Paygilant from 'react-native-paygilant';

// TODO: What to do with the module?
Paygilant;
```
