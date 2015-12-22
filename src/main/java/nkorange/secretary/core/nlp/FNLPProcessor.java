package nkorange.secretary.core.nlp;

import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.nlp.cn.ner.TimeNormalizer;
import org.fnlp.nlp.cn.ner.TimeUnit;
import org.fnlp.util.exception.LoadModelException;


/**
 * @author pengfei.zhu.
 */
public class FNLPProcessor {

    private static final String MODELS_PATH = "src/main/resource/models";

    private CNFactory factory;

    private TimeNormalizer normalizer;

    public FNLPProcessor() {

        try {
            factory = CNFactory.getInstance(MODELS_PATH);
            normalizer = new TimeNormalizer(MODELS_PATH + "/time.m");
        } catch (LoadModelException e) {
            e.printStackTrace();
        }
    }

    public TimeUnit[] recognizeTime(String text) {
        return normalizer.parse(text);
    }

    public static void main(String[] args) {

        FNLPProcessor processor = new FNLPProcessor();
        TimeUnit[] res = processor.recognizeTime("定在明晚十点");
        for (TimeUnit unit : res) {
            System.out.println(unit.getTime());
        }
    }
}
