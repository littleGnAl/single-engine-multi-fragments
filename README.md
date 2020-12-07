# single-engine-multi-fragments

This is an [add to app](https://flutter.dev/docs/development/add-to-app/android/project-setup) scenario, you need to build aar first, and it work on android only.
## Build
1. Switch to flutter master channel
2. `cd flutter_module/`
3. `flutter packages get`
4. `flutter build aar -v`
5. `cd ../android`
6. `./gradlew assembleDebug installDebug`

After all command done, the apk should install to your device.


研究看源代码后，我发现只有detach逻辑，没有reattach逻辑，这样会导致之前的FlutterActivity/FlutterFragment delegate为空，所以只要回到之前的页面就会报错，例如，FlutterActivity1 -> FlutterActivity2，finish FlutterActivity2后回到FlutterActivity1就会报错，我害怕这会影响到1.22之前使用FlutterActivity的逻辑，会导致crash。