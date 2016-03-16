package com.example.yunliwei.activity;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.example.yunliwei.global.constData;
import com.example.yunliwei.http.AsyncTaskSocketManager;
import com.zjrc.client.global.logGlobal;
import com.zjrc.client.layout.processDlgAction;
import com.zjrc.client.layout.processDlgAction.onProcessDialogListener;
import com.zjrc.client.layout.showDlgAction;
import com.zjrc.client.socket.asyncTaskSocket.onDataRecvListener;
import com.zjrc.client.socket.socketAction;

public abstract class BaseActivity extends Activity {
	protected AsyncTaskSocketManager sockMngObj = new AsyncTaskSocketManager();
	private processDlgAction processObj = new processDlgAction();

	abstract void onRecvData(JSONObject obj, int iCode);

	static final protected String sMsgSending = "正在请求数据，请等待......";
	static final protected String sMsgUnknown = "系统异常";
	static final protected String sMsgConnectErr = "连接服务器失败";
	static final protected String sMsgSendErr = "发送数据失败";
	static final protected String sMsgRecvErr = "接收数据失败";
	static final protected String TOKEN_FAIL = "9"; // 口令失效

	private onDataRecvListener oLsner = new onDataRecvListener() {
		@Override
		public void onDataRecv(String strRecv, int iCode) {
			boolean bDone = false;
			if (strRecv != null) {
				JSONObject obj = null;
				try {
					obj = new JSONObject(strRecv);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (obj != null) {
					String result = obj.optString("result");
					if (result != null && result.compareToIgnoreCase("ok") == 0) {

					} else {

					}
				}
			}
			if (!bDone) {
				showDlgAction.showAlertDialog(BaseActivity.this, sMsgUnknown);
			}
			processObj.dismissDialog();
		}

		@Override
		public void onError(int iErrType, int iCode) {
			processObj.dismissDialog();
			BaseActivity.this.hidePushHead();

			String sMsg = null;
			if (iErrType == socketAction.CONNECT_ERR) {
				sMsg = sMsgConnectErr;
			} else if (iErrType == socketAction.SEND_ERR) {
				sMsg = sMsgSendErr;
			} else if (iErrType == socketAction.RECV_ERR) {
				sMsg = sMsgRecvErr;
			}
			if (sMsg != null) {
				showDlgAction.showAlertDialog(BaseActivity.this, sMsg);
			}
			logGlobal.log("onError:" + iErrType);
		}
	};

	/**
	 * 隐藏下拉头
	 */
	public void hidePushHead() {

	}

	protected void sendData(JSONObject obj) {
		sendData(obj, 0);
	}

	protected void sendData(JSONObject obj, int iCode) {
		if (obj != null) {
			String strSend = obj.toString();
			processObj.showDialog(this, sMsgSending, cLsner);
			sockMngObj.startAsyncTask(strSend, oLsner, iCode);
		}
	}

	protected void sendData(JSONObject obj, int iCode, String msg) {
		if (obj != null) {
			String strSend = obj.toString();
			if (!"".equals(msg)) {
				processObj.showDialog(this, msg, cLsner);
			}
			sockMngObj.startAsyncTask(strSend, oLsner, iCode);
		}
	}

	protected void sendDataNoProcess(JSONObject obj) {
		sendDataNoProcess(obj, 0);
	}

	protected void sendDataNoProcess(JSONObject obj, int iCode) {
		if (obj != null) {
			String strSend = obj.toString();
			sockMngObj.startAsyncTask(strSend, oLsner, iCode);
		}
	}

	private onProcessDialogListener cLsner = new onProcessDialogListener() {
		@Override
		public void onCancelled() {
			sockMngObj.cancelAsyncTask();
			processObj.dismissDialog();
		}
	};

	@Override
	protected void onDestroy() {
		sockMngObj.cancelAsyncTask();
		processObj.dismissDialog();
		super.onDestroy();
	}

	private DialogInterface.OnClickListener dlgCancel = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};

	protected void showAlertDialog(String msg) {
		if (msg != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setTitle(android.R.string.dialog_alert_title);
			builder.setMessage(msg);
			builder.setPositiveButton(android.R.string.ok, dlgCancel);
			builder.create();
			builder.show();
		}
	}

	protected void showInfoDialog(String title, String msg) {
		showInfoDialog(title, msg, dlgCancel);
	}

	protected void showInfoDialog(String title, String msg,
			DialogInterface.OnClickListener dlgLsn) {
		if (title != null && msg != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setTitle(title);
			builder.setMessage(msg);
			builder.setPositiveButton(android.R.string.ok, dlgLsn);
			builder.create();
			builder.show();
		}
	}

	protected void showInfoDialogConfirm(String title, String msg,
			DialogInterface.OnClickListener dlgLsn) {
		showInfoDialogConfirmOrCancel(title, msg, dlgLsn, dlgCancel);
	}

	protected void showInfoDialogConfirmOrCancel(String title, String msg,
			DialogInterface.OnClickListener dlgLsn,
			DialogInterface.OnClickListener cancelgLsn) {
		if (title != null && msg != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setTitle(title);
			builder.setCancelable(false);
			builder.setMessage(msg);
			builder.setPositiveButton(android.R.string.ok, dlgLsn);
			builder.setNegativeButton(android.R.string.cancel, cancelgLsn);
			builder.create();
			builder.show();
		}
	}

	protected void showImage(String sFile) {
		File obj = new File(sFile);
		if (obj.isFile()) {
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(obj), "image/*");
			startActivity(intent);
		}
	}

	public void returnToLogin() {
		// YLWApplication ylw = (YLWApplication) this.getApplication();
		// Intent intent = new Intent();
		// intent.putExtra("bAutoLogin", false);
		// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// intent.setClass(this, LogActivity.class);
		// startActivityForResult(intent, activityType.loginActivity);
		// ylw.deinitResource();
		// finish();
	}

	protected void showProcessDialog(String sMsg) {
		processObj.showDialog(this, sMsg);
	}

	protected void dimissProcessDialog() {
		processObj.dismissDialog();
	}

	private void showAlertErrorRelogin(String msg) {
		if (msg != null) {
			try {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setIcon(android.R.drawable.ic_dialog_alert);
				builder.setTitle(android.R.string.dialog_alert_title);
				builder.setMessage(msg);
				builder.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								returnToLogin();
							}
						});
				builder.create();
				builder.show();
			} catch (Exception e) {
			}
		}
	}
}
