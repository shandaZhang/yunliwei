package com.example.yunliwei.activity;

import java.io.File;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;

public class BaiduMapActivity extends BaseActivity {

	private static final String APP_FOLDER_NAME = "yunliwei";
	private String mSDCardPath = null;
	private String authinfo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initDirs();
		initNavi();
	}

	@Override
	void onRecvData(JSONObject obj, int iCode) {

	}

	private void initNavi() {
		BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME,
				new NaviInitListener() {
					@Override
					public void onAuthResult(int status, String msg) {
						if (0 == status) {
							authinfo = "key校验成功!";
						} else {
							authinfo = "key校验失败, " + msg;
						}
						BaiduMapActivity.this.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								Toast.makeText(BaiduMapActivity.this, authinfo,
										Toast.LENGTH_LONG).show();
							}
						});
					}

					public void initSuccess() {
						Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化成功",
								Toast.LENGTH_SHORT).show();
					}

					public void initStart() {
						Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化开始",
								Toast.LENGTH_SHORT).show();
					}

					public void initFailed() {
						Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化失败",
								Toast.LENGTH_SHORT).show();
					}

				}, null);

	}

	private boolean initDirs() {
		mSDCardPath = getSdcardDir();
		if (mSDCardPath == null) {
			return false;
		}
		File f = new File(mSDCardPath, APP_FOLDER_NAME);
		if (!f.exists()) {
			try {
				f.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

}
