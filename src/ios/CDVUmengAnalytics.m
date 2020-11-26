#import "CDVUmengAnalytics.h"
#import <UMCommon/UMCommon.h>
#import <UMCommon/MobClick.h>

@interface CDVUmengAnalytics ()

#if __has_feature(objc_arc)
@property (nonatomic, strong) NSString *currPageName;
#else
@property (nonatomic, retain) NSString *currPageName;
#endif

@end

@implementation CDVUmengAnalytics

#if __has_feature(objc_arc)
#else
- (void)dealloc {
    [super dealloc];
}
#endif

- (void)pluginInitialize {
    NSString* appKey = [self.commandDelegate.settings objectForKey:[@"CDVUmengAnalyticsAppKey" lowercaseString]];
    NSString* channelId = [self.commandDelegate.settings objectForKey:[@"ChannelId" lowercaseString]];
    [UMConfigure initWithAppkey: appKey channel: channelId];
}

- (void)onEvent:(CDVInvokedUrlCommand*)command {
    NSString *eventId = [command.arguments objectAtIndex:0];
    if (eventId == nil || [eventId isKindOfClass:[NSNull class]]) {
        return;
    }
    [MobClick event:eventId];
}

- (void)onEventWithLabel:(CDVInvokedUrlCommand*)command{
    NSString *eventId = [command.arguments objectAtIndex:0];
    if (eventId == nil || [eventId isKindOfClass:[NSNull class]]) {
        return;
    }
    NSString *eventLabel = [command.arguments objectAtIndex:1];
    if ([eventLabel isKindOfClass:[NSNull class]]) {
        eventLabel = nil;
    }
    [MobClick event:eventId label:eventLabel];
}

- (void)onEventWithParameters:(CDVInvokedUrlCommand*)command {
    NSString *eventId = [command.arguments objectAtIndex:0];
    if (eventId == nil || [eventId isKindOfClass:[NSNull class]]) {
        return;
    }
    NSDictionary *parameters = [command.arguments objectAtIndex:1];
    if (parameters == nil && [parameters isKindOfClass:[NSNull class]]) {
        parameters = nil;
    }
    [MobClick event:eventId attributes:parameters];
}

- (void)onEventWithCounter:(CDVInvokedUrlCommand*)command {
    NSString *eventId = [command.arguments objectAtIndex:0];
    if (eventId == nil || [eventId isKindOfClass:[NSNull class]]) {
        return;
    }
    NSDictionary *parameters = [command.arguments objectAtIndex:1];
    if (parameters == nil && [parameters isKindOfClass:[NSNull class]]) {
        parameters = nil;
    }
    NSString *eventNum = [command.arguments objectAtIndex:2];
    if (eventNum == nil && [eventNum isKindOfClass:[NSNull class]]) {
        eventNum = nil;
    }
    int num = [eventNum intValue];
    [MobClick event:eventId attributes:parameters counter:num];
}

- (void)onPageBegin:(CDVInvokedUrlCommand*)command {
    NSString *pageName = [command.arguments objectAtIndex:0];
    if (pageName == nil || [pageName isKindOfClass:[NSNull class]]) {
        return;
    }
    [MobClick beginLogPageView:pageName];
}

- (void)onPageEnd:(CDVInvokedUrlCommand*)command {
    NSString *pageName = [command.arguments objectAtIndex:0];
    if (pageName == nil || [pageName isKindOfClass:[NSNull class]]) {
        return;
    }
    [MobClick endLogPageView:pageName];
}

- (void)profileSignIn:(CDVInvokedUrlCommand*)command {
    NSString *puid = [command.arguments objectAtIndex:0];
    if (puid == nil || [puid isKindOfClass:[NSNull class]]) {
        return;
    }
    NSString *provider = [command.arguments objectAtIndex:1];
    if (provider == nil || [provider isKindOfClass:[NSNull class]] || provider.length == 0) {
        provider = nil;
    }

    if (provider == nil) {
        [MobClick profileSignInWithPUID:puid];
    } else {
        [MobClick profileSignInWithPUID:puid provider:provider];
    }
}

- (void)profileSignOff:(NSArray *)arguments {
    [MobClick profileSignOff];
}

- (void)getChannelId:(CDVInvokedUrlCommand*)command {
    NSString* channelId = [self.commandDelegate.settings objectForKey:[@"ChannelId" lowercaseString]];
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:channelId];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getDeviceId:(CDVInvokedUrlCommand*)command {
    NSString *deviceId = [[[UIDevice currentDevice] identifierForVendor] UUIDString];
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:deviceId];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)setLogEnabled:(CDVInvokedUrlCommand*)command {
    NSString *arg0 = [command.arguments objectAtIndex:0];
    if (arg0 == nil || [arg0 isKindOfClass:[NSNull class]]) {
        return;
    }
    BOOL enabled = [arg0 boolValue];
    [UMConfigure setLogEnabled:enabled];
}

@end
