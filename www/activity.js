//引入依赖
var exec = require('cordova/exec');

/**
 * 主类
 */
function Activity() {
}
;


/**
 * 调用原生activity;
 */
Activity.prototype.start = function(activityClassName,jsonData,successCallback) {
	exec(successCallback, errorCallback, "ActivityPlugin", "start", [activityClassName,jsonData]);
};

var errorCallback = function(message) {
    alert("Error:" + message);
};


/**
 * 安装插件
 */
module.exports = new Activity();
