package nkorange.secretary.speech.stt;

import com.iflytek.cloud.speech.*;

/**
 * Speech recognizer using iflytek engine
 *
 * @author pengfei.zhu.
 */
public class IflytekRecognizer implements RecognizerListener {

    private SpeechRecognizer mIat;

    public IflytekRecognizer() {

        /*mIat = SpeechRecognizer.createRecognizer();
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        mIat.startListening(this);*/
    }

    public void onVolumeChanged(int i) {
        System.out.println("Iflytek stt:onVolumeChanged");
    }

    public void onBeginOfSpeech() {
        System.out.println("Iflytek stt:onBeginOfSpeech");
    }

    public void onEndOfSpeech() {
        System.out.println("Iflytek stt:onEndOfSpeech");
    }

    public void onResult(RecognizerResult recognizerResult, boolean b) {

        System.out.println("Iflytek stt:" + recognizerResult.getResultString());
    }

    public void onError(SpeechError speechError) {

    }

    public void onEvent(int i, int i1, int i2, String s) {

    }
}
