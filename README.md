# 友盟移动统计 Cordova 插件

## 平台

- iOS
- Android

## 安装

```shell script
cordova plugin add cordova-plugin-umeng-analytics
```

## 配置 App ID

在 `config.xml` 中配置以下内容。

```xml
<!-- iOS -->
<platform name="ios">
    <preference name="CDVUmengAnalyticsAppKey" value="YOUR_UMENG_IOS_APP_ID"/>
    <preference name="ChannelId" value="YOUR_UMENG_IOS_CHANNEL_ID"/>
</platform>

<!-- Android -->
<platform name="android">
    <preference name="CDVUmengAnalyticsAppKey" value="YOUR_UMENG_ANDROID_APP_ID"/>
    <preference name="ChannelId" value="YOUR_UMENG_ANDROID_CHANNEL_ID"/>
</platform>
```

## 使用

当成功安装和配置完成后，您可以通过全局变量 `UmengAnalytics` 来调用友盟统计相关方法。

### 自定义事件统计

```javascript
/**
 * @param {string} eventId - 自定义事件 ID。
 */
UmengAnalytics.onEvent(eventId)

/**
 * @param {string} eventId - 自定义事件 ID。
 * @param {string} label - 分类标签。不同的标签会分别进行统计，方便同一事件的不同标签的对比，为 `nil` 或空字符串时后台会生成和 `eventId` 同的标签。
 */
UmengAnalytics.onEventWithLabel(eventId, label)

/**
 * @param {string} eventId - 自定义事件 ID。
 * @param {Object<string, string>} attributes - 属性中的 key/value 必须为 `String` 类型, 每个应用至多添加 500 个自定义事件，key 不能超过100个 。
 */
UmengAnalytics.onEventWithParameters(eventId, attributes)

/**
 * @param {string} eventId - 自定义事件 ID。
 * @param {Object<string, string>} attributes - 属性中的 key/value 必须为String类型, 每个应用至多添加 500 个自定义事件，key 不能超过 100 个。
 * @param {Number} counter - 自定义数值。
 */
UmengAnalytics.onEventWithCounter(eventId, attributes, counter)
 ```

### 页面访问统计

必须配对调用 `onPageBegin` 和 `onPageEnd` 两个方法来完成自动统计，若只调用某一个方法不会生成有效数据；在该页面展示时调用 `onPageBegin`，当退出该页面时调用 `onPageEnd`。

```javascript
/**
 * @param {string} pageName - 统计的页面名称。
 */
UmengAnalytics.onPageBegin(pageName)

/**
 * @param {string} pageName - 统计的页面名称。
 */
UmengAnalytics.onPageEnd(pageName)
 ```

### 账号统计

统计用户时以设备为标准，如果需要统计应用自身的账号，可以使用此功能。

```javascript
/**
 * @param {string} userId - 用户 ID。
 * @param {string=} provider - 可选。不能以下划线 `_` 开头，使用大写字母和数字标识; 如果是上市公司，建议使用股票代码。
 */
UmengAnalytics.profileSignIn(userId, provider)

/**
 * 调用后，不再发送账号内容。
 */
UmengAnalytics.profileSignOff()
 ```

### 调试模式

```javascript
/**
 * @param {boolean} enabled - 是否打开调试模式。
 */
UmengAnalytics.setLogEnabled(enabled);
 ```

### 获取渠道 ID

```javascript
/**
 * @param {Function<string>} callback
 */
UmengAnalytics.getChannelId(callback);
 ```

### 获取设备 ID

```javascript
/**
 * @param {Function<string>} callback
 */
UmengAnalytics.getDeviceId(callback);
 ```
