package com.zlzkj.cordova;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

public class ActivityPlugin extends CordovaPlugin {

	// 可接收的方法
	private static final String ACTION_1 = "start";

    private CallbackContext myCallbackContext;

	@Override
	public boolean execute(String action, JSONArray args,
			final CallbackContext callbackContext) throws JSONException {

		if (ACTION_1.equals(action)) {

            this.myCallbackContext = callbackContext;

            try {
                // 接收的参数
                final String activityClassName = args.getString(0);
                final JSONObject jsonData = args.getJSONObject(1);

                //启动新的原生Activity
                Intent intent = new Intent();
                intent.setClass(cordova.getActivity(), Class.forName(activityClassName));
                intent.putExtras(JSONObjectToBunble(jsonData)); //传值
                cordova.startActivityForResult(this, intent, 1);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        } else {
			callbackContext.error(action + " is not a supported function.");
			return false;
		}

	}

    //onActivityResult为第二个Activity执行完后的回调接收方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        switch (resultCode) { //resultCode为回传的标记，约定在新的原生Activity中回传的是RESULT_OK
            case Activity.RESULT_OK:
                Bundle bundle=intent.getExtras();
                //新的原生Activity把值传回Cordova
                PluginResult mPlugin = new PluginResult(PluginResult.Status.OK,BundleToJSONObject(bundle));
                mPlugin.setKeepCallback(false);
                myCallbackContext.sendPluginResult(mPlugin);
                myCallbackContext.success("success");
                break;
            default:
                myCallbackContext.error("resultCode should be 'RESULT_OK'");
                break;
        }
    }

    /**
     * JSON对象转成Bundle，用于Activity之间传值；
     * 键和值都被转成String类型
     * @param jsonObject
     * @return
     */
    private static Bundle JSONObjectToBunble(JSONObject jsonObject){

        Bundle bundle = new Bundle();
        Iterator<?> it = jsonObject.keys();
        while(it.hasNext()){
            String key = it.next().toString();
            try {
                bundle.putString(key,jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return bundle;
    }

    /**
     * Bundle转成JSON对象，用于Activity之间传值；
     * 键被转成String类型,值被转成Object类型
     * @param bundle
     * @return
     */
    private static JSONObject BundleToJSONObject(Bundle bundle){
        JSONObject jsonObject = new JSONObject();
        Set<String> keySet = bundle.keySet();
        for(String key : keySet) {
            try {
                jsonObject.put(key,bundle.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

}
