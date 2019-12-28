package com.example.h3;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityManager;
import android.util.Log;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.os.Build;
import android.content.Context;
import android.content.Intent;

import java.util.List;
import java.util.Iterator;

import com.example.h3.job.WechatAccessbilityJob;
/**
 * <p>Created by byc</p>
 * <p/>
 * �������ҷ���
 */
public class QiangHongBaoService extends AccessibilityService {
	//�����ʶ
	private static String TAG = "byc001";
	//��ʵ������
	 private static QiangHongBaoService service;
	 //job����
	 private WechatAccessbilityJob job;
	 @Override
	 public void onCreate() {
		 super.onCreate();
	     service = this;
	     TAG=Config.TAG;
	     job=WechatAccessbilityJob.getJob();
	}
	 public Config getConfig() {
	        return Config.getConfig(this);
	 }

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		
		job.onReceiveJob(event);
	}//public
    @Override
    public void onInterrupt() {
    	 //Log.d(TAG, "qianghongbao service interrupt");
        Toast.makeText(this, "�ж�ţţ����", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        service = this;
        TAG=Config.TAG;
        job=WechatAccessbilityJob.getJob();
        job.onCreateJob(service);
        Toast.makeText(this, "������ţţ����", Toast.LENGTH_SHORT).show();
        //���͹㲥���Ѿ���������
        //Intent intent = new Intent(Config.ACTION_QIANGHONGBAO_SERVICE_CONNECT);
       // sendBroadcast(intent);
    }
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    Log.d(TAG, "qianghongbao service destory");
	    job.onStopJob();

	    service = null;
	    job = null;
        //���͹㲥���Ѿ��Ͽ���������
	    Toast.makeText(this, "���ж�ţţ����", Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(Config.ACTION_QIANGHONGBAO_SERVICE_DISCONNECT);
        //sendBroadcast(intent);
	}
    
    /**
     * �жϵ�ǰ�����Ƿ���������
     * */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isRunning() {
        if(service == null) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) service.getSystemService(Context.ACCESSIBILITY_SERVICE);
        AccessibilityServiceInfo info = service.getServiceInfo();
        if(info == null) {
            return false;
        }
        List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        Iterator<AccessibilityServiceInfo> iterator = list.iterator();

        boolean isConnect = false;
        while (iterator.hasNext()) {
            AccessibilityServiceInfo i = iterator.next();
            if(i.getId().equals(info.getId())) {
                isConnect = true;
                break;
            }
        }
        if(!isConnect) {
            return false;
        }
        return true;
    }
}
