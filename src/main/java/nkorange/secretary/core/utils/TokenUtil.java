package nkorange.secretary.core.utils;

import nkorange.secretary.core.memory.Token;

import java.util.Map;

/**
 * @author pengfei.zhu.
 */
public class TokenUtil {

    private static Map<Long, Token> tokens = null;

    private static final String TOKEN_PATH = "src/main/resource/memory/tokens.xml";

    static {
        init();
    }

    public static void init() {
        tokens = XmlUtil.getAllTokens(TOKEN_PATH);
    }

    public static long findTokenId(String term) {

        // TODO if map size is getting too large, this method should be optimized
        for (Map.Entry<Long, Token> entry : tokens.entrySet()) {
            if (entry.getValue().getTerms().contains(term)) {
                return entry.getKey();
            }
        }

        return -1L;
    }

    public static Token findToken(long id) {
        return tokens.get(id);
    }


}
