/**
 * 
 */
package com.example.h3.job;

import android.content.Context;
import android.media.AudioManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.example.h3.Config;
/**
 * @author byc
 *
 */
public class SpeechUtil implements SpeechSynthesizerListener {
	
	private static SpeechUtil current;
	private Context context;
	private  String TAG = "byc001";//���Ա�ʶ��
	private SpeechSynthesizer mSpeechSynthesizer;
	
	private static final String MY_APP_ID = "8685795";
	private static final String MY_API_KEY = "7kqPsc6Lzi0VlYowSLvLwv4T";
	private static final String MY_SECRET_KEY= "ade9db698491cea9bb81508210d61dcf";
	
    private String mSampleDirPath;
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";

    private static final int PRINT = 0;
    
	
	

	public SpeechUtil(Context context) {
		this.context = context;
		TAG=Config.TAG;
		initialEnv();
		initialTts();
	}
    public static synchronized SpeechUtil getSpeechUtil(Context context) {
        if(current == null) {
            current = new SpeechUtil(context);
        }
        return current;
    }
    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
        toPrint(mSampleDirPath+"_______________________________");
        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        //copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        //copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        /*
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
                */
    }
    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    /**
     * ��sample������Ҫ����Դ�ļ�������SD����ʹ�ã���Ȩ�ļ�Ϊ��ʱ��Ȩ�ļ�����ע����ʽ��Ȩ��
     * 
     * @param isCover �Ƿ񸲸��Ѵ��ڵ�Ŀ���ļ�
     * @param source
     * @param dest
     */
    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = context.getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void initialTts() {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        this.mSpeechSynthesizer.setContext(context);
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // �ı�ģ���ļ�·�� (��������ʹ��)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        // ��ѧģ���ļ�·�� (��������ʹ��)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        // ������Ȩ�ļ�·��,��δ���ý�ʹ��Ĭ��·��.������ʱ��Ȩ�ļ�·����LICENCE_FILE_NAME���滻����ʱ��Ȩ�ļ���ʵ��·��������ʹ����ʱlicense�ļ�ʱ��Ҫ�������ã������[Ӧ�ù���]�п�ͨ����ʽ������Ȩ������Ҫ���øò��������齫���д���ɾ�����������棩
        // ����ϳɽ��������ʱ��Ȩ�ļ���Ҫ���ڵ���ʾ��˵��ʹ������ʱ��Ȩ�ļ�����ɾ����ʱ��Ȩ���ɡ�
        //this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
        //        + LICENSE_FILE_NAME);
        // ���滻Ϊ����������ƽ̨��ע��Ӧ�õõ���App ID (������Ȩ)
        this.mSpeechSynthesizer.setAppId(MY_APP_ID);
        // ���滻Ϊ����������ƽ̨ע��Ӧ�õõ���apikey��secretkey (������Ȩ)
        this.mSpeechSynthesizer.setApiKey(MY_API_KEY, MY_SECRET_KEY);
        // �����ˣ��������棩�����ò���Ϊ0,1,2,3���������������˻ᶯ̬���ӣ���ֵ����ο��ĵ������ĵ�˵��Ϊ׼��0--��ͨŮ����1--��ͨ������2--�ر�������3--���������������
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // ����Mixģʽ�ĺϳɲ���
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        /*
        // ��Ȩ���ӿ�(ֻ��ͨ��AuthInfo���м�����Ȩ�Ƿ�ɹ���)
        // AuthInfo�ӿ����ڲ��Կ������Ƿ�ɹ����������߻���������Ȩ�����������Ȩ�ɹ��ˣ�����ɾ��AuthInfo���ֵĴ��루�ýӿ��״���֤ʱ�ȽϺ�ʱ��������Ӱ������ʹ�ã��ϳ�ʹ��ʱSDK�ڲ����Զ���֤��Ȩ��
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);

        if (authInfo.isSuccess()) {
            toPrint("auth success");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            toPrint("auth failed errorMsg=" + errorMsg);
        }
		*/
        // ��ʼ��tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        /*
        // ��������Ӣ����Դ���ṩ����Ӣ�ĺϳɹ��ܣ�
        int result =
                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        toPrint("loadEnglishModel result=" + result);
        */
    }
    //@Override
    private void onDestroy() {
        this.mSpeechSynthesizer.release();
        //super.onDestroy();
    }

    public void speak(String text) {
        //��Ҫ�ϳɵ��ı�text�ĳ��Ȳ��ܳ���1024��GBK�ֽڡ�
        int result = this.mSpeechSynthesizer.speak(text);
        if (result < 0) {
            toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    private void pause() {
        this.mSpeechSynthesizer.pause();
    }

    private void resume() {
        this.mSpeechSynthesizer.resume();
    }

    private void stop() {
        this.mSpeechSynthesizer.stop();
    }
    private void synthesize(String text) {
        //��Ҫ�ϳɵ��ı�text�ĳ��Ȳ��ܳ���1024��GBK�ֽڡ�
        int result = this.mSpeechSynthesizer.synthesize(text);
        if (result < 0) {
            toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }
    private void batchSpeak() {
        List<SpeechSynthesizeBag> bags = new ArrayList<SpeechSynthesizeBag>();
        bags.add(getSpeechSynthesizeBag("123456", "0"));
        bags.add(getSpeechSynthesizeBag("���", "1"));
        bags.add(getSpeechSynthesizeBag("ʹ�ðٶ������ϳ�SDK", "2"));
        bags.add(getSpeechSynthesizeBag("hello", "3"));
        bags.add(getSpeechSynthesizeBag("����һ��demo����", "4"));
        int result = this.mSpeechSynthesizer.batchSpeak(bags);
        if (result < 0) {
            toPrint("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    private SpeechSynthesizeBag getSpeechSynthesizeBag(String text, String utteranceId) {
        SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
        //��Ҫ�ϳɵ��ı�text�ĳ��Ȳ��ܳ���1024��GBK�ֽڡ�
        speechSynthesizeBag.setText(text);
        speechSynthesizeBag.setUtteranceId(utteranceId);
        return speechSynthesizeBag;
    }
    /*
     * @param arg0
     */
    @Override
    public void onSynthesizeStart(String utteranceId) {
        toPrint("onSynthesizeStart utteranceId=" + utteranceId);
    }
    /*
     * @param arg0
     * 
     * @param arg1
     * 
     * @param arg2
     */
    @Override
    public void onSynthesizeDataArrived(String utteranceId, byte[] data, int progress) {
        // toPrint("onSynthesizeDataArrived");
    }

    /*
     * @param arg0
     */
    @Override
    public void onSynthesizeFinish(String utteranceId) {
        toPrint("onSynthesizeFinish utteranceId=" + utteranceId);
    }
    /*
     * @param arg0
     */
    @Override
    public void onSpeechStart(String utteranceId) {
        toPrint("onSpeechStart utteranceId=" + utteranceId);
    }

    /*
     * @param arg0
     * 
     * @param arg1
     */
    @Override
    public void onSpeechProgressChanged(String utteranceId, int progress) {
        // toPrint("onSpeechProgressChanged");
    }

    /*
     * @param arg0
     */
    @Override
    public void onSpeechFinish(String utteranceId) {
        toPrint("onSpeechFinish utteranceId=" + utteranceId);
    }

    /*
     * @param arg0
     * 
     * @param arg1
     */
    @Override
    public void onError(String utteranceId, SpeechError error) {
        toPrint("onError error=" + "(" + error.code + ")" + error.description + "--utteranceId=" + utteranceId);
    }
    
    private void toPrint(String str) {
    	Log.w(TAG, "TTS:"+str);
    }
    /*
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case PRINT:
                    print(msg);
                    break;

                default:
                    break;
            }
        }

    };

    private void toPrint(String str) {
        Message msg = Message.obtain();
        msg.obj = str;
        this.mHandler.sendMessage(msg);
    }

    private void print(Message msg) {
        String message = (String) msg.obj;
        if (message != null) {
            Log.w(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            scrollLog(message);
        }
    }

    
    private void scrollLog(String message) {
        Spannable colorMessage = new SpannableString(message + "\n");
        colorMessage.setSpan(new ForegroundColorSpan(0xff0000ff), 0, message.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mShowText.append(colorMessage);
        Layout layout = mShowText.getLayout();
        if (layout != null) {
            int scrollAmount = layout.getLineTop(mShowText.getLineCount()) - mShowText.getHeight();
            if (scrollAmount > 0) {
                mShowText.scrollTo(0, scrollAmount + mShowText.getCompoundPaddingBottom());
            } else {
                mShowText.scrollTo(0, 0);
            }
        }
    }
    */
}
