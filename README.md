# single-engine-multi-fragments

This is an [add to app](https://flutter.dev/docs/development/add-to-app/android/project-setup) scenario, you need to build aar first, and it work on android only.
## Build
1. Switch to flutter master channel
2. `cd flutter_module/`
3. `flutter build aar -v`
4. `cd ../android`
5. `./gradlew assembleDebug installDebug`

After all command done, the apk should install to your device.