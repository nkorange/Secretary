package nkorange.replier.core.module;

import nkorange.replier.core.nlp.NLPInfo;
import nkorange.replier.core.nlp.NLPToken;
import nkorange.replier.core.utils.TokenUtil;

import java.util.List;

/**
 * @author pengfei.zhu.
 */
public class ThinkModule extends SustainableModule implements CradleModule {

    /**
     * Core procedure of thinking.<p>
     * Thinking is a complicated procedure. It may be influenced by many factors, like instinct, emotion, environment
     * and so on. Even for a extreme rational brain, the underlying principles are still not fully understood. So
     * trying to build a model to simulate the thinking procedure is quite unrealistic. All I do in this method are
     * just combination of some steps in order to understand the user command:<br>
     * 1. try to match memory if same command has been requested before;<br>
     * 2. try to match relevant memory to give user possible suggestion;<br>
     * 3. check if there are any unknown core tokens, like action, target or else, and tell user about that;<br>
     *
     * @param words
     * @param list
     */
    public void reflect(String words, List<NLPInfo> list) {

        if (matchMemory(words) > 0) {

        }

        matchRelevantMemory(words);
        checkUnknownTokens(list);

    }

    public void report(String s) {

    }

    private long matchMemory(String words) {
        // TODO search memory
        return 0L;
    }

    private void matchRelevantMemory(String words) {
        // I don't know how to do it yet, may use some recommend algorithms.
    }

    private void checkUnknownTokens(List<NLPInfo> list) {

        for (NLPInfo info : list) {
            for (NLPToken token : info.getTokens()) {
                if (TokenUtil.findTokenId(token.getWord()) == -1L) {
                    report("有个词不认识：" + token.getWord());
                }
            }
        }
    }
}
