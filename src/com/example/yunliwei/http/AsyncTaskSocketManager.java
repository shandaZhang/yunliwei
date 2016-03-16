package com.example.yunliwei.http;

import com.example.yunliwei.global.constData;
import com.zjrc.client.socket.asyncTaskSocket;
import com.zjrc.client.socket.asyncTaskSocket.onDataRecvListener;

//import com.zjrc.meeting_hlwdh.global.constData;

public class AsyncTaskSocketManager {
	static private int iSendRecvTimeOut = 20 * 1000;
	static private int iSendRecvRetry = 3;

	static private String strIP = null;
	static private int iPort = 0;

	static public String getServerIP() {
		return strIP;
	}

	static public int getServerPort() {
		return iPort;
	}

	static public void setTimeOut(int iTimeOut, int iRetry) {
		iSendRecvTimeOut = iTimeOut;
		iSendRecvRetry = iRetry;
	}

	static public void setServer(String ip, int port) {
		strIP = ip;
		iPort = port;
	}

	static public void setServerIp(String ip) {
		strIP = ip;
	}

	static public void setServerPort(int port) {
		iPort = port;
	}

	static public void setTimeOut(int iTimeOut) {
		iSendRecvTimeOut = iTimeOut;
	}

	private asyncTaskSocket sockObj = null;

	public void startAsyncTask(String strSend, onDataRecvListener oDRLsner,
			int iCode) {
		if (sockObj != null) {
			sockObj.cancelAsyncTask(true);
		}
		sockObj = new asyncTaskSocket();
		sockObj.setTimeOut(iSendRecvTimeOut, iSendRecvRetry);
		sockObj.setServer(constData.SERVER_IP, iPort);
		sockObj.startAsyncTask(strSend, oDRLsner, iCode);

	}

	public void cancelAsyncTask() {
		if (sockObj != null) {
			sockObj.cancelAsyncTask(true);
			sockObj = null;
		}
	}

	public boolean isRunning() {
		if (sockObj != null) {
			return sockObj.isRunning();
		}
		return false;
	}
}
