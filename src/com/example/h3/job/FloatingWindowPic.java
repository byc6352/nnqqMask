/**
 * 
 */
package com.example.h3.job;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.example.h3.Config;
import com.example.h3.MainActivity;
import com.byc.nnqqMask.R;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @author byc
 *
 */
public class FloatingWindowPic {
	public static String TAG = "byc001";//���Ա�ʶ��
	private Context context;
	//-------------------------------------------------------------------------
	//���帡�����ڲ���
	private LinearLayout mFloatLayout;
	private WindowManager.LayoutParams wmParams;
    //���������������ò��ֲ����Ķ���
	private WindowManager mWindowManager;
	
	private boolean bShow=false;//�Ƿ���ʾ
	//��������
	private int i=0;

	//bTreadRun:��ֹ�̱߳�����
	private boolean bTreadRun=true;
	
	public FloatingWindowPic(Context context,int resource) {
		this.context = context;
		TAG=Config.TAG;

		createFloatViewPic(resource);
	}
    public void ShowFloatingWindow(){
    	if(!bShow){
    		
    		try{
       		 mWindowManager.addView(mFloatLayout, wmParams);
       		bShow=true;
       		}catch (Exception e) {
       			e.printStackTrace();
       		}
    	}
    }
    
    public void RemoveFloatingWindowPic(){
		if(mFloatLayout != null)
		{
			if(bShow)mWindowManager.removeView(mFloatLayout);
			bShow=false;
		}
    }
    public void SetFloatViewPara(int x,int y,int w,int h){
        // ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
    	if(wmParams==null)return;
        wmParams.x = x;
        wmParams.y = y;
        // �����������ڳ�������
        wmParams.width = w;
        wmParams.height =h;
        //�����������ڳ�������  
        //wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }
    private void createFloatViewPic(int resource)
 	{
 		wmParams = new WindowManager.LayoutParams();
 		//��ȡWindowManagerImpl.CompatModeWrapper
 		mWindowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
 		//����window type
 		wmParams.type = LayoutParams.TYPE_TOAST;  
 		//����ͼƬ��ʽ��Ч��Ϊ����͸��
         wmParams.format = PixelFormat.RGBA_8888; 
         //���ø������ڲ��ɾ۽���ʵ�ֲ���������������������ɼ����ڵĲ�����
         wmParams.flags = 
          // LayoutParams.FLAG_NOT_TOUCH_MODAL |
           LayoutParams.FLAG_NOT_FOCUSABLE	
         //  LayoutParams.FLAG_NOT_TOUCHABLE
           ;
         
         //������������ʾ��ͣ��λ��Ϊ����ö�
         wmParams.gravity = Gravity.LEFT | Gravity.TOP; 
         
         // ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
         wmParams.x = 0;
         wmParams.y = 0;

         // �����������ڳ�������
         //wmParams.width = w;
         //wmParams.height =h;
         
         //�����������ڳ�������  
         wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
         wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
         
         LayoutInflater inflater = LayoutInflater.from(context);
         //��ȡ����������ͼ���ڲ���
         //mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_bigpic, null);
         mFloatLayout = (LinearLayout) inflater.inflate(resource, null);
         //���mFloatLayout
         //mWindowManager.addView(mFloatLayout, wmParams);
         
         Log.i(TAG, "mFloatLayout-->left" + mFloatLayout.getLeft());
         Log.i(TAG, "mFloatLayout-->right" + mFloatLayout.getRight());
         Log.i(TAG, "mFloatLayout-->top" + mFloatLayout.getTop());
         Log.i(TAG, "mFloatLayout-->bottom" + mFloatLayout.getBottom());      
         
         
         mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
 				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
 				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        
 	}



    class PicThread extends Thread { 

    	 public PicThread() { 

    	 }
    	 @Override  
         public void run() {  

             
             while(bTreadRun){
                 //������Ϣ  
                 Message msg = new Message();  
                 msg.what = 0x15;
                 Bundle bundle = new Bundle();
                 bundle.clear(); 
            	 bundle.putString("msg", "01");  
            	 msg.setData(bundle);  //
            	 //������Ϣ �޸�UI�߳��е����  
            	 HandlerPic.sendMessage(msg); 
            	 try{
            	 Thread.sleep(100);
                 } catch (InterruptedException e) {
            		 e.printStackTrace();
            	 }
            	 //Log.i(TAG, i);
             }

             
    	 }
    }//class SockThread extends Thread { 
    public void StartSwitchPics(){
    	new PicThread().start();
    	return ;
    }
    public void StopSwitchPics(){
    	bTreadRun=false;
    	return ;
    }
    //������Ϣ��
    public Handler HandlerPic = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            if (msg.what == 0x15) {  
            	//Log.i(TAG, "handleMessage----------->"+i);
            	//switchPic(i);
            	i=i+1;
            	if(i>9)i=0;
            }  
        }  
  
    };  
	
}
