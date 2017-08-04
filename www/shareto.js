var exec = require('cordova/exec');

exports.shared = function(success, error) {
    exec(success, error, "shareto", "shared");
};
