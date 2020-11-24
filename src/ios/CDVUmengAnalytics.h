#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>

@interface CDVUmengAnalytics : CDVPlugin

- (void)onEvent:(CDVInvokedUrlCommand*)command;
- (void)onEventWithLabel:(CDVInvokedUrlCommand*)command;
- (void)onEventWithParameters:(CDVInvokedUrlCommand*)command;
- (void)onEventWithCounter:(CDVInvokedUrlCommand*)command;
- (void)onPageBegin:(CDVInvokedUrlCommand*)command;
- (void)onPageEnd:(CDVInvokedUrlCommand*)command;
- (void)profileSignIn:(CDVInvokedUrlCommand*)command;
- (void)profileSignOff:(NSArray *)arguments;
- (void)getChannelId:(CDVInvokedUrlCommand*)command;
- (void)getDeviceId:(CDVInvokedUrlCommand*)command;
- (void)setLogEnabled:(CDVInvokedUrlCommand*)command;

@end
