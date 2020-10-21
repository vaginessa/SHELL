# SHELL for Android
Shell Scripting Android app with Terminal emulator to get control over Android CLI Interface.
<p align="center">
  <img src="https://github.com/abhinandanarya06/SHELL/blob/main/.idea/res/main.jpg?raw=true">
</p>
<p align="center">
  <img src="https://github.com/abhinandanarya06/SHELL/blob/main/.idea/res/demo1.jpg?raw=true" width="33%">
  <img src="https://github.com/abhinandanarya06/SHELL/blob/main/.idea/res/demo2.jpg?raw=true" width="33%">
  <img src="https://github.com/abhinandanarya06/SHELL/blob/main/.idea/res/demo3.jpg?raw=true" width="33%">
</p>


## Quickstart
1. Clone this repo
```
  git clone https://github.com/abhinandanarya06/SHELL.git
```
2. Create a file ```local.properties``` and add content as follows
```
sdk.dir=<ANDROID SDK PATH>
storeFile=<YOUR KEYSTORE FILE>
keyAlias=<YOUR KEYSTORE KEY ALIAS>
storePassword=<YOUR KEYSTORE PASSWORD>
keyPassword=<YOUR KEY PASSWORD>
```

2. Run the following for producing and installing release version of the app
```
  ./gradlew assembleRelease # FOR PRODUCING APK FILE IN app/build/outputs/apk/debug 
  
  ./gradlew installRelease # FOR INSTALLING APP DIRECTLY. MAKE SURE PHONE IS CONNECTED
```

## Issues
1. Terminal UI is not fully interactive
2. Input command (read) doesn't work 
3. Infinite loop script crashes the app

## Contribute ?
You are very welcome to pull request and make contributions.
Here are few ideas for contribution : 
1. Create a CLI Operating System like design by contributing in developing BASH's and other commands in C++.
2. Improving Terminal UI by implementing custom views. (Refer to Custom View Android documentation)
3. Adding Some learning materials for shell scripting.
4. Solving above mentioned issues.

### *Happy Learning !*
