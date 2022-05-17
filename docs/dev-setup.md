# iOS and Android deployment
This documentation is a work in progress. There can be wrong ways and missing steps.
## Links
### iOS
[iOS ionic](https://ionicframework.com/docs/developing/ios) with
```
xcode-select --install
```
Also use
```
sudo gem install cocoapods
```
and followed again the instructions - in this project Cordova is used.
```
ionic capacitor add ios
ionic capacitor open ios
```
Set a package ID e.g. "de.piphi.biokiste". Follow the instructions
Starting the app with
```
ionic capacitor run ios -l --external
```
or using the play button in XCode worked.
### Solutions for problems
- [Possible Problem with node 18](https://github.com/ionic-team/ionic-cli/issues/4839)
- [How to run cocoapos on Apple M1](https://stackoverflow.com/questions/64901180/how-to-run-cocoapods-on-apple-silicon-m1/64901980#64901980)
- [bug: npx cap update crashes on Apple Silicon](https://github.com/ionic-team/capacitor/issues/4876#issuecomment-890914265) and [Capacitor fails building on MacOS Big Sur (suspecting Apple M1 ARM processor)](https://github.com/ionic-team/capacitor/issues/3897#issuecomment-1003215909) for message
``LoadError - dlsym(0x7fe291e235f0, Init_ffi_c): symbol not found - /Library/Ruby/Gems/2.6.0/gems/ffi-1.13.1/lib/ffi_c.bundle``
- [Tried in between](https://stackoverflow.com/questions/64901180/how-to-run-cocoapods-on-apple-silicon-m1/64901980#64901980) for message:
## Terminal history dump 05.2022
This is an adhoc documentation of used terminal commands:
```
ionic capacitor add ios
sudo gem install cocoapods
ionic capacitor add ios
npm run build
arch -x86_64 pod install
ionic capacitor add ios
ionic capacitor open ios
git status
ionic capacitor add android
ionic cap open android
mkdir misc
cd misc
vi cp-build-to-backend.sh
cd ..
npm run build
npm run build
npm install @mui/material @emotion/react @emotion/styled
npm run build

```