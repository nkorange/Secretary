package nkorange.secretary.core.nlp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author pengfei.zhu.
 */
public class NLPInfo {

    private List<NLPToken> tokens;

    public NLPInfo() {
        tokens = new ArrayList<NLPToken>();
    }

    public Set<NLPToken> nextToken(String word) {

        Set<NLPToken> res = new HashSet<NLPToken>();
        // TODO ugly code:
        for (int i = 0; i < tokens.size(); i++) {
            if (word.equals(tokens.get(i).getWord())) {
                if (i < tokens.size() - 1) {
                    res.add(tokens.get(i + 1));
                }
            }
        }
        return res;
    }

    public Set<NLPToken> preToken(String word) {

        Set<NLPToken> res = new HashSet<NLPToken>();
        // TODO ugly code:
        for (int i = 0; i < tokens.size(); i++) {
            if (word.equals(tokens.get(i).getWord())) {
                if (i > 0) {
                    res.add(tokens.get(i - 1));
                }
            }
        }
        return res;
    }

    public void appendToken(NLPToken token) {
        tokens.add(token);
    }

    public List<NLPToken> getTokens() {
        return tokens;
    }
}
