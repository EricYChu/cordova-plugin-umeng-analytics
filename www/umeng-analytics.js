var exec = require('cordova/exec');

module.exports = {
    onEvent: function(eventId) {
        exec(null, null, "UmengAnalytics", "onEvent", [eventId]);
    },
    onEventWithLabel: function(eventId, eventLabel) {
        exec(null, null, "UmengAnalytics", "onEventWithLabel", [eventId, eventLabel]);
    },
    onEventWithParameters: function(eventId, eventData) {
        exec(null, null, "UmengAnalytics", "onEventWithParameters", [eventId, eventData]);
    },
    onEventWithCounter: function(eventId, eventData, eventNum) {
        exec(null, null, "UmengAnalytics", "onEventWithCounter", [eventId, eventData, eventNum]);
    },
    onPageBegin: function(pageName) {
        exec(null, null, "UmengAnalytics", "onPageBegin", [pageName]);
    },
    onPageEnd: function(pageName) {
        exec(null, null, "UmengAnalytics", "onPageEnd", [pageName]);
    },
    profileSignIn: function(userId, provider) {
        exec(null, null, "UmengAnalytics", "profileSignIn", [userId, provider || ""]);
    },
    profileSignOff: function() {
        exec(null, null, "UmengAnalytics", "profileSignOff", []);
    },
    getChannelId: function(callBack) {
        exec(callBack, null, "UmengAnalytics", "getChannelId", []);
    },
    getDeviceId: function(callBack) {
        exec(callBack, null, "UmengAnalytics", "getDeviceId", []);
    },
    setLogEnabled: function(enabled) {
        exec(null, null, "UmengAnalytics", "setLogEnabled", [enabled]);
    }
};