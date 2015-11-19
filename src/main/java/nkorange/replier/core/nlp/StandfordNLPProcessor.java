package nkorange.replier.core.nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author pengfei.zhu.
 */
public class StandfordNLPProcessor implements NLPProcessor {

    private StanfordCoreNLP pipeline;
    private Properties props;

    public StandfordNLPProcessor() {
        init();
    }

    public void init() {

        props = new Properties();
        props.setProperty("annotators", "segment, ssplit, pos, ner, parse");
        props.setProperty("customAnnotatorClass.segment", "edu.stanford.nlp.pipeline.ChineseSegmenterAnnotator");
        props.setProperty("segment.model", "edu/stanford/nlp/models/segmenter/chinese/ctb.gz");
        props.setProperty("segment.sighanCorporaDict", "edu/stanford/nlp/models/segmenter/chinese");
        props.setProperty("segment.serDictionary", "edu/stanford/nlp/models/segmenter/chinese/dict-chris6.ser.gz");
        props.setProperty("segment.sighanPostProcessing", "true");
        props.setProperty("pos.model", "edu/stanford/nlp/models/pos-tagger/chinese-distsim/chinese-distsim.tagger");
        props.setProperty("ner.model", "edu/stanford/nlp/models/ner/chinese.misc.distsim.crf.ser.gz");
        props.setProperty("ner.applyNumericClassifiers", "false");
        props.setProperty("ner.useSUTime", "true");
        props.setProperty("parse.model", "edu/stanford/nlp/models/lexparser/chinesePCFG.ser.gz");
        props.setProperty("ssplit.boundaryTokenRegex", "[.]|[!?]+|[。]|[！？]+");

        pipeline = new StanfordCoreNLP(props);
    }

    public List<NLPInfo> analyze(String text) {

        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        if (sentences == null || sentences.isEmpty()) {
            return null;
        }

        List<NLPInfo> res = new ArrayList<NLPInfo>();
        NLPInfo info;
        for (CoreMap sentence : sentences) {
            info = new NLPInfo();
            NLPToken tokenInfo;
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                tokenInfo = new NLPToken();
                tokenInfo.setWord(token.get(CoreAnnotations.TextAnnotation.class));
                tokenInfo.setTag(token.get(CoreAnnotations.PartOfSpeechAnnotation.class));
                tokenInfo.setNer(token.get(CoreAnnotations.NamedEntityTagAnnotation.class));
                info.appendToken(tokenInfo);
            }
            res.add(info);
        }
        return res;
    }
}
