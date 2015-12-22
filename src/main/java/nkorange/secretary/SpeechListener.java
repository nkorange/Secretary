package nkorange.secretary;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;

/**
 * @author pengfei.zhu.
 */
public class SpeechListener implements Listener {

    private static final String MY_APP_ID = "secretary_stt";

    public void init() {
        SpeechUtility.createUtility(SpeechConstant.APPID + "= " + MY_APP_ID + " ");
    }

    public String read() {
        return null;
    }
}
