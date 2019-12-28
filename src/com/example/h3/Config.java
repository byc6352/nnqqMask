/**
 * 
 */

package com.example.h3;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
/**
 * @author byc
 *
 */
public class Config {
	
	public static final String PREFERENCE_NAME = "byc_nnqqMask_config";//�����ļ�����
	
	public static final String TAG = "byc001";//���Ա�ʶ��
	public static final boolean DEBUG = true;//���Ա�ʶ��
	//΢�ŵİ���
	public static final String WECHAT_PACKAGENAME = "com.tencent.mobileqq";

    //--------------------------------------------------------------------------------------
    //����������û���������

	//΢�Ŷ��壺
    public static final String WINDOW_LUCKYMONEY_RECEIVEUI="cooperation.qwallet.plugin.QWalletPluginProxyActivity";
    public static final String WINDOW_LUCKYMONEY_DETAILUI="cooperation.qwallet.plugin.QWalletPluginProxyActivity";

    //�㲥��Ϣ����
    public static final String ACTION_QIANGHONGBAO_SERVICE_DISCONNECT = "com.byc.qianghongbao.ACCESSBILITY_DISCONNECT";
    public static final String ACTION_QIANGHONGBAO_SERVICE_CONNECT = "com.byc.qianghongbao.ACCESSBILITY_CONNECT";

    //ţţ�淨���壺
    public static final int NN_SINGLE=1;
    public static final int NN_DOUBLE=2;
    public static final int NN_THREE=3;
    public static final int NN_HT=4;//��β��
    public static final int NN_PG=5;//�ƾţ�
    
    private static final String NN_WANG_FA="NN_WangFa";//--�������ں���е�λ��

	   private static Config current;
	    private SharedPreferences preferences;
	    private Context context;
	    SharedPreferences.Editor editor;
	    
	    private Config(Context context) {
	        this.context = context;
	        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
	        editor = preferences.edit(); 

	    }
	   
	    public static synchronized Config getConfig(Context context) {
	        if(current == null) {
	            current = new Config(context.getApplicationContext());
	        }
	        return current;
	    }
	 	    //NN �淨������
	    public int getNnWangFa() {
	        return preferences.getInt(NN_WANG_FA, 1);
	    }
	    public void setNnWangFa(int iWangFa) {
	        editor.putInt(NN_WANG_FA, iWangFa).apply();
	    }	
	    
}
