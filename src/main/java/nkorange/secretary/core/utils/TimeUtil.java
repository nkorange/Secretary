package nkorange.secretary.core.utils;

import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.nlp.cn.ner.TimeNormalizer;
import org.fnlp.util.exception.LoadModelException;

import java.util.Date;

/**
 * @author pengfei.zhu.
 */
public class TimeUtil {

    private static final String MODELS_PATH = "src/main/resource/models";

    private static CNFactory factory;

    private static TimeNormalizer normalizer;

    private static void init() {

        try {
            factory = CNFactory.getInstance(MODELS_PATH);
            normalizer = new TimeNormalizer(MODELS_PATH + "/time.m");
        } catch (LoadModelException e) {
            e.printStackTrace();
        }
    }

    static {
        init();
    }

    public static Date interpretTime(String text) {
        return normalizer.parse(text)[0].getTime();
    }
}
