# cordova plugin shareto

Cordova plugin to get shared Single or Multiple images for Android platform. 

## Installation

The plugin conforms to the Cordova plugin specification, it can be installed using the Cordova command line interface.


```
cordova plugin add "https://github.com/mitaljoshi/shareto.git"
```

## Supported Platform

* Android

## Usage
The plugin is exposed via the cordova.plugins.shareto object and provides the following function:

### shared(SuccessCallback, ErrorCallback)
add the following code inside `onDeviceReady` : 

```
cordova.plugins.shareto.shared(success, failure);
```

#### Example of Get Single shared image String:


```javaScript
var success = function (imgString) {
        // Set base64 Image string to your image container
}

var failure = function (e) {
        //Handle Error
}

cordova.plugins.shareto.shared(success, failure);
```

### For Multiple shared images

For Multiple shared images, shared() returns Array of base64 Image Strings.

#### Example to get Multiple Image Strings: 
```javaScript
var success = function (imageString) {
        //check if imageString is array.
        if (Array.isArray(imageString)) {
                for (var i = 0; i < imageString.length ; i++) {
                        // set image
                }
        }
        else {
                //set image
        }
}
var failure = function (e) {
        //Handle error
}

cordova.plugins.shareto.shared(success, failure);
```
### Note
The image is passed to the success callback as a Base64-encoded String.





