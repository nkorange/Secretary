package nkorange.replier.core.nlp;

import java.util.List;

/**
 * @author pengfei.zhu.
 */
public interface NLPProcessor {

    public List<NLPInfo> analyze(String text);
}
