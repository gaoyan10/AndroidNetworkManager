package me.yan.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.os.AsyncTask;
import android.util.Log;

public class NetStatusManager {
	private static NetStatusManager instance = new NetStatusManager();
	private String url;
	private int second = 20;
	private NetStatus status;
	private NetStatusManager() {
		url = "www.baidu.com";
		status = NetStatus.UNKNOW;
		Ping ping = new Ping();
		ping.execute();
	}
	public static NetStatusManager getInstance() {
		return instance;
	}
	public void config(String targetUrl) {
		this.url = targetUrl;
	}
	public void configTimeOut(int second) {
		this.second = second;
	}
	private class Ping extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			String s = "";
			s = ping();
			Log.i("ping", s);
			return s;
		}
	}

	private String ping() {
		String resault = "";
		Process p;
		try {
			// ping -c 3 -w 100 中 ，-c 是指ping的次数 3是指ping 3次 ，-w 100
			// 以秒为单位指定超时间隔，是指超时时间为100秒
			this.status = NetStatus.WAIT;
			p = Runtime.getRuntime().exec("ping -c 3 -w " + second + " " + url);
			int status = p.waitFor();
			InputStream input = p.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			System.out.println("Return ============" + buffer.toString());
			if (status == 0) {
				//resault = "success";
				this.status = NetStatus.GOOD;
			} else {
				//resault = "faild";
				this.status = NetStatus.BREAK;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return resault;
	}
	public void refreshStatus() {
		Ping ping = new Ping();
		ping.execute();
	}
	protected void changeStatus(NetStatus status) {
		this.status = status;
	}
	public NetStatus getNetStatus() {
		return status;
	}
}
