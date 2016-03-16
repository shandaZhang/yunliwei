package com.example.yunliwei.global;

import android.util.SparseArray;

import com.zjrc.client.socket.socketAction;

public class constData {
	static final public String textCoding = "utf-8";
	static final public int iMaxListViewItem = 50;

	static final public int iSocketTimeOut = 15 * 1000;
	static final public int iConnectTimeOut = 1500;

	// 正式环境

	// static public String SERVER_IP = "218.205.67.175";
	// static public String SERVER_IP =
	// "https://10.73.152.91:9600/RCMeetingServer2.0/ClientRequestDeal";
	// static public String SERVER_IP =
	// "https://192.168.1.104:9600/RCMeetingServer2.0/ClientRequestDeal";
	// static public String SERVER_IP =
	// "https://218.205.67.175:9601/ClientRequestDeal";
	// static public String SERVER_IP =
	// "https://218.205.67.175:9603/ClientRequestDeal";
	static public String SERVER_IP = "https://20.21.1.91:9601/ClientRequestDeal";
	// 测试环境
	// static public String SERVER_IP = "192.168.31.208";

	// static public String SERVER_IP = "10.73.152.91";
	// static public String SERVER_IP = "120.199.26.226";
	// static public String SERVER_IP = "10.73.152.30";
	// 正式端口
	static public int SERVER_PORT = 9600;
	// 测试端口
	// static public int SERVER_PORT = 9601;

	// static public String SERVER_IP = "20.21.1.91";
	// 正式环境
	// static public String SERVER_IP = "111.1.45.62";
	// static public int SERVER_PORT = 9600;
	// static public String UPDATE_URL =
	// "http://211.138.125.209:84/RCMeetingServer/android";

	static public String UPDATE_URL = "http://111.1.45.62/android";

	static private SparseArray<String> errInfo = new SparseArray<String>();

	static public void init() {
		errInfo.put(socketAction.CONNECT_ERR, "连接服务器失败");
		errInfo.put(socketAction.SEND_ERR, "发送数据失败");
		errInfo.put(socketAction.RECV_ERR, "接收数据失败");
	}

	static public String getErrInfo(int i) {
		return errInfo.get(i);
	}

//	static public String getShowMessage() {
//		String sMsg = "当前版本不提供";
//		String userid = saveDataGlobal.getString(saveDataGlobal.USER_ID, null);
//		String tmpuserid = saveDataGlobal.getString(saveDataGlobal.TMP_USER_ID,
//				null);
//		if (userid != null && tmpuserid != null
//				&& userid.compareToIgnoreCase(tmpuserid) == 0) {
//			sMsg = "演示会议不提供";
//		}
//		return sMsg;
//	}
}
