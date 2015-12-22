package nkorange.secretary.core.nlp;

/**
 * @author pengfei.zhu.
 */
public class NLPToken {

    private String word;
    private String tag;
    private String ner;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNer() {
        return ner;
    }

    public void setNer(String ner) {
        this.ner = ner;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
