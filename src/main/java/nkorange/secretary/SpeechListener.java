package nkorange.secretary;

import com.iflytek.cloud.speech.*;
import net.sf.json.*;
import nkorange.secretary.core.utils.Akka;

/**
 * @author pengfei.zhu.
 */
public class SpeechListener implements RecognizerListener {

    private String speech;

    public void onResult(RecognizerResult results, boolean isLast) {
        System.out.println("Result:" + results
                .getResultString
                        ());

        if (results == null) {
            return;
        }

        JSONObject json = JSONObject.fromObject(results.getResultString());
        if (json == null) {
            return;
        }

        int section = json.getInt("sn");

        if (section == 1) {
            speech = "";
        }

        JSONArray words = json.getJSONArray("ws");
        if (words == null || words.size() == 0) {
            return;
        }

        for (int i=0; i<words.size(); i++) {

            JSONObject element = words.getJSONObject(i);
            JSONArray array = element.getJSONArray("cw");
            if (array == null || array.size() == 0) {
                continue;
            }
            for (int j=0; j<array.size(); j++) {
                JSONObject word = array.getJSONObject(j);
                speech += word.getString("w");
            }
        }

        boolean last = json.getBoolean("ls");

        if (last) {
            // Notify brain:
            System.out.println("收到指令：" + speech);
            Akka.tellBrain(speech);
            speech = "";
        }

    }

    //会话发生错误回调接口
    public void onError(SpeechError error) {
        System.out.println(error);
    } //开始录音

    //音量值0~30
    public void onVolumeChanged(int volume) {
        //System.out.println("onVolumeChanged");
    }

    public void onBeginOfSpeech() {
        //System.out.println("onBeginOfSpeech");
    }

    //结束录音
    public void onEndOfSpeech() {
        //System.out.println("onEndOfSpeech");
    }

    //扩展用接口
    public void onEvent(int eventType, int arg1, int arg2, String msg) {
        //System.out.println("onEvent");
    }
}
