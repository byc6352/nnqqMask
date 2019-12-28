/**
 * 
 */
package com.example.h3.job;

import java.util.Timer;
import java.util.TimerTask;

import com.example.h3.Config;
import com.example.h3.MainActivity;
import com.example.h3.QiangHongBaoService;
import com.byc.nnqqMask.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;
/**
 * @author byc
 *
 */
public class WechatAccessbilityJob extends BaseAccessbilityJob  {
	
	private static WechatAccessbilityJob current;
	private AccessibilityNodeInfo mRootNode; //��������
	//-------------------------------�����ʱ---------------------------------------------

	private SpeechUtil speeker ;
	//�����ࣺ���������˽���ͺ����ϸ��Ϣ���棺
	
	
	//private FloatingWindowPic fwb1;//��ʾͼƬ��������
	private FloatingWindowPic fws1;//��ʾͼƬ��������
	//private FloatingWindowPic fwb2;//��ʾͼƬ��������
	private FloatingWindowPic fws2;//��ʾͼƬ��������
	//private FloatingWindowPic fwb3;//��ʾͼƬ��������
	private FloatingWindowPic fws3;//��ʾͼƬ��������
	private int i=1;


    @Override
    public void onCreateJob(QiangHongBaoService service) {
        super.onCreateJob(service);
        speeker=SpeechUtil.getSpeechUtil(context);
        
        //fwb1=new FloatingWindowPic(context,R.layout.float_bigpic);
        fws1=new FloatingWindowPic(context,R.layout.float_smallpic);
        //fwb2=new FloatingWindowPic(context,R.layout.float_bigpic2);
        fws2=new FloatingWindowPic(context,R.layout.float_smallpic2);
        //fwb3=new FloatingWindowPic(context,R.layout.float_bigpic3);
        fws3=new FloatingWindowPic(context,R.layout.float_smallpic3);
    }
    @Override
    public void onStopJob() {

    }
    public static synchronized WechatAccessbilityJob getJob() {
        if(current == null) {
            current = new WechatAccessbilityJob();
        }
        return current;
    }
    
    //----------------------------------------------------------------------------------------
    @Override
    public void onReceiveJob(AccessibilityEvent event) {
    	
    	final int eventType = event.getEventType();
    	String sClassName=event.getClassName().toString();

		if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			mRootNode=event.getSource();
			if(mRootNode==null)return;
			ReplacePics(sClassName);

		}//if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) 

    }
    private void ReplacePics(String sClassName){
    	if(sClassName.equals(Config.WINDOW_LUCKYMONEY_DETAILUI)){
    		String sShow="";
    	switch(i){
    	case 1:

        	//fws1.SetFloatViewPara(596,509-50,94,43);
        	//fws1.ShowFloatingWindow();
        	sShow="��ϲ������ţţ��ֵΪ��3.00Ԫ";
        	break;
    	case 2:

        	fws1.SetFloatViewPara(596,509-50,94,43);
        	fws1.ShowFloatingWindow();
        	sShow="��ϲ������ţţ��ֵΪ��3.00Ԫ";
        	break;
    	case 3:

        	//fws2.SetFloatViewPara(596,509-50,94,43);
        	//fws2.ShowFloatingWindow();
        	sShow="��ϲ������ţţ��ֵΪ��1.00Ԫ";
        	break;
    	case 4:
        	fws2.SetFloatViewPara(596,509-50,94,43);
        	fws2.ShowFloatingWindow();
        	sShow="��ϲ������ţţ��ֵΪ��1.00Ԫ";
        	break;
    	case 5:
        	//fws3.SetFloatViewPara(596,509-50,94,43);
        	//fws3.ShowFloatingWindow();
        	sShow="��ϲ������ţţ��ֵΪ��2.00Ԫ";
        	break;
    	case 6:
        	//fwb3.SetFloatViewPara(243, 481-50,233,140);
        	///fwb3.ShowFloatingWindow();   
        	fws3.SetFloatViewPara(596,509-50,94,43);
        	fws3.ShowFloatingWindow();
        	sShow="��ϲ������ţţ��ֵΪ��2.00Ԫ";
        	break;
    	}
		Toast.makeText(context,sShow, Toast.LENGTH_LONG).show();
		speeker.speak(sShow);
    	i=i+1;
    	if(i>6)i=1;
		}else{
			//fw.DestroyFloatingWindow();
			//fwp1.RemoveFloatingWindowPic();
			//fwp2.RemoveFloatingWindowPic();
			//fwb1.RemoveFloatingWindowPic();
			fws1.RemoveFloatingWindowPic();
			//fwb2.RemoveFloatingWindowPic();
			fws2.RemoveFloatingWindowPic();
			//fwb3.RemoveFloatingWindowPic();
			fws3.RemoveFloatingWindowPic();
		}
    }  
    private void ReplacePics2(String sClassName){
    	if(sClassName.equals(Config.WINDOW_LUCKYMONEY_DETAILUI)){
    		String sShow="";
    	switch(i){
    	case 1:
        	//fwb1.SetFloatViewPara(243, 481-50,233,140);
        	//fwb1.ShowFloatingWindow();   [596,509][690,547]
        	fws1.SetFloatViewPara(596,509-50,94,43);
        	fws1.ShowFloatingWindow();
        	sShow="��ϲ������ţţ��ֵΪ��3.00Ԫ";
        	break;
    	case 2:
        	//fwb2.SetFloatViewPara(243, 481-50,233,140);
        	//fwb2.ShowFloatingWindow();   
        	fws2.SetFloatViewPara(596,509-50,94,43);
        	fws2.ShowFloatingWindow();
        	sShow="��ϲ������ţţ��ֵΪ��1.00Ԫ";
        	break;
    	case 3:
        	//fwb3.SetFloatViewPara(243, 481-50,233,140);
        	///fwb3.ShowFloatingWindow();   
        	fws3.SetFloatViewPara(596,509-50,94,43);
        	fws3.ShowFloatingWindow();
        	sShow="��ϲ������ţţ��ֵΪ��2.00Ԫ";
        	break;
    	}
		Toast.makeText(context,sShow, Toast.LENGTH_LONG).show();
		speeker.speak(sShow);
    	i=i+1;
    	if(i>3)i=1;
		}else{
			//fw.DestroyFloatingWindow();
			//fwp1.RemoveFloatingWindowPic();
			//fwp2.RemoveFloatingWindowPic();
			//fwb1.RemoveFloatingWindowPic();
			fws1.RemoveFloatingWindowPic();
			//fwb2.RemoveFloatingWindowPic();
			fws2.RemoveFloatingWindowPic();
			//fwb3.RemoveFloatingWindowPic();
			fws3.RemoveFloatingWindowPic();
		}
    }  
}
