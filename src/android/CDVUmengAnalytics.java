package cordova.plugins.umenganalytics;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.umeng.commonsdk.UMConfigure;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CDVUmengAnalytics extends CordovaPlugin {

    private Context mContext = null;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        String appKey = webView.getPreferences().getString("CDVUmengAnalyticsAppKey", "");
        String pushSecret = webView.getPreferences().getString("CDVUmengAnalyticsPushSecret", null);
        String channelId = getChannelId();

        this.mContext = cordova.getActivity().getApplicationContext();

        UMConfigure.preInit(mContext, appKey, channelId);
        UMConfigure.init(mContext, appKey, channelId, UMConfigure.DEVICE_TYPE_PHONE, pushSecret);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        MobclickAgent.setScenarioType(mContext, EScenarioType.E_UM_NORMAL);
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        MobclickAgent.onResume(mContext);
    }

    @Override
    public void onPause(boolean multitasking) {
        super.onPause(multitasking);
        MobclickAgent.onPause(mContext);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        switch (action) {
            case "onEvent":
                return onEvent(args, mContext);

            case "onEventWithLabel":
                return onEventWithLabel(args, mContext);

            case "onEventWithParameters":
                return onEventWithParameters(args, mContext);

            case "onEventWithCounter":
                return onEventWithCounter(args, mContext);

            case "onPageBegin":
                return onPageBegin(args);

            case "onPageEnd":
                return onPageEnd(args);

            case "profileSignIn":
                return profileSignIn(args);

            case "profileSignOff":
                return profileSignOff();

            case "getChannelId":
                String channelId = getChannelId();
                callbackContext.success(channelId);
                return true;

            case "getDeviceId":
                try {
                    String deviceId = getDeviceId(mContext);
                    callbackContext.success(deviceId);
                } catch (Exception e) {
                    e.printStackTrace();
                    callbackContext.error(0);
                }
                return true;

            case "setLogEnabled":
                return setLogEnabled(args);
        }

        return false;
    }

    private boolean onEvent(JSONArray args, Context context) {
        try {
            String eventId = args.getString(0);
            MobclickAgent.onEvent(context, eventId);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    private boolean onEventWithLabel(JSONArray args, Context context) {
        try {
            String eventId = args.getString(0);
            String label = args.getString(1);
            MobclickAgent.onEvent(context, eventId, label);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    private boolean onEventWithParameters(JSONArray args, Context context) {
        try {
            String eventId = args.getString(0);
            JSONObject obj = args.getJSONObject(1);
            Map<String, String> map = new HashMap<String, String>();
            Iterator<String> it = obj.keys();
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                Object o = obj.get(key);
                if (o instanceof Integer) {
                    String value = String.valueOf(o);
                    map.put(key, value);
                } else if (o instanceof String) {
                    String strValue = (String) o;
                    map.put(key, strValue);
                }
            }
            MobclickAgent.onEvent(context, eventId, map);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    private boolean onEventWithCounter(JSONArray args, Context context) {
        try {
            String eventId = args.getString(0);
            JSONObject obj = args.getJSONObject(1);
            Map<String, String> map = new HashMap<String, String>();
            Iterator<String> it = obj.keys();
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                Object o = obj.get(key);
                if (o instanceof Integer) {
                    String value = String.valueOf(o);
                    map.put(key, value);
                } else if (o instanceof String) {
                    String strValue = (String) o;
                    map.put(key, strValue);
                }
            }
            int value = args.getInt(2);
            MobclickAgent.onEventValue(context, eventId, map, value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    private boolean onPageBegin(JSONArray args) {
        try {
            String pageName = args.getString(0);
            MobclickAgent.onPageStart(pageName);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    private boolean onPageEnd(JSONArray args) {
        try {
            String pageName = args.getString(0);
            MobclickAgent.onPageEnd(pageName);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    private boolean profileSignIn(JSONArray args) {
        try {
            String userId = args.getString(0);
            String provider = args.getString(1);
            if (provider.length() == 0) {
                MobclickAgent.onProfileSignIn(userId);
            } else {
                MobclickAgent.onProfileSignIn(userId, provider);
            }
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    private boolean profileSignOff() {
        try {
            MobclickAgent.onProfileSignOff();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private String getChannelId() {
        return webView.getPreferences().getString("ChannelId", "");
    }

    private String getDeviceId(Context context) {
        try {
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean setLogEnabled(JSONArray args) {
        try {
            boolean enabled = args.getBoolean(0);
            UMConfigure.setLogEnabled(enabled);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
}
