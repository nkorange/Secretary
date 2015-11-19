package nkorange.replier.core.module;

import nkorange.replier.core.nlp.NLPInfo;
import nkorange.replier.core.nlp.NLPToken;
import nkorange.replier.core.utils.TokenUtil;

import java.util.*;

/**
 * A condition is used to identify a corresponding module.
 *
 * @author pengfei.zhu.
 */
public class Condition {

    private List<String> exists;

    private Set<String> nonExists;

    private boolean order;

    public Condition() {

        exists = new LinkedList<String>();

        nonExists = new TreeSet<String>(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

    }

    public static Condition fromNLPInfo(NLPInfo info) {

        Condition condition = new Condition();
        for (NLPToken token : info.getTokens()) {
            condition.order = true;
            condition.exists.add(String.valueOf(TokenUtil.findTokenId(token.getWord())));
            //condition.exists.add(token.getWord() + "," + token.getTag());
        }
        return condition;
    }

    public List<String> getExists() {
        return exists;
    }

    public Set<String> getNonExists() {
        return nonExists;
    }

    public boolean isOrder() {
        return order;
    }
}
