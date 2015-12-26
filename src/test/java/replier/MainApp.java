package replier;

import com.iflytek.cloud.speech.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengfei.zhu.
 */
public class MainApp {

    private static RecognizerListener mRecoListener = new RecognizerListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            System.out.println("Result:" + results
                    .getResultString
                            ());
        }

        //会话发生错误回调接口
        public void onError(SpeechError error) {
            System.out.println(error);
        } //开始录音

        //音量值0~30
        public void onVolumeChanged(int volume) {
            System.out.println("onVolumeChanged");
        }

        public void onBeginOfSpeech() {
            System.out.println("onBeginOfSpeech");
        }

        //结束录音
        public void onEndOfSpeech() {
            System.out.println("onEndOfSpeech");
        }

        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, String msg) {
            System.out.println("onEvent");
        }
    };

    public static void main(String[] args) throws InterruptedException, IOException {

        SpeechUtility.createUtility(SpeechConstant.APPID + "=5677c03b");

        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer();
        //2.设置听写参数,详见《iFlytek MSC Reference Manual》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        //3.开始听写
        mIat.startListening(mRecoListener);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("record.wav"));

        List<byte[]> buffers = new ArrayList<byte[]>();

        byte[] voice = new byte[4800];

        int len;
        do {
            len = bis.read(voice);
            buffers.add(voice);
            if (len < 4800) {
                break;
            }
            voice = new byte[4800];
        } while (true);

        for (int i = 0; i < buffers.size(); i++) {
            // 每次写入msc数据4.8K,相当150ms录音数据
            mIat.writeAudio(buffers.get(i), 0, buffers.get(i).length);
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(buffers.size());

        mIat.stopListening();

        synchronized (MainApp.class) {
            while (true) {
                try {
                    MainApp.class.wait();
                } catch (Throwable e) {
                }
            }
        }
        //Thread.sleep(10000);
    }

}
