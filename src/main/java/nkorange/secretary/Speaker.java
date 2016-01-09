package nkorange.secretary;

import akka.actor.UntypedActor;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizerListener;
import nkorange.secretary.translate.tts.IflytekSpeaker;

/**
 * Created by nkorange on 2016/1/9.
 */
public class Speaker extends UntypedActor implements Response {

    private SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
    private SynthesizerListener mSynListener = new IflytekSpeaker();

    public Speaker() {
        super();
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        mTts.setParameter(SpeechConstant.SPEED, "50");
        mTts.setParameter(SpeechConstant.VOLUME, "80");
    }

    public void say(String msg) {
        mTts.startSpeaking(msg, mSynListener);
    }

    @Override
    public void onReceive(Object o) throws Exception {

        String msg = (String) o;
        say(msg);
    }
}
