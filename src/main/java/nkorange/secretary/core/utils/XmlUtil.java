package nkorange.secretary.core.utils;

import nkorange.secretary.core.memory.Token;
import nkorange.secretary.core.module.Module;
import nkorange.secretary.core.module.Router;
import org.apache.commons.lang3.math.NumberUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author pengfei.zhu.
 */
public class XmlUtil {

    private static DocumentBuilder builder = null;

    private static DocumentBuilder getBuilder() {

        if (builder != null) {
            return builder;
        }
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        if (builder == null) {
            throw new IllegalStateException("Cannot instantiate a document builder");
        }

        return builder;
    }

    public static List<Module> getAllModules(String path) {

        File file = new File(path);
        List<Module> list = new ArrayList<Module>();
        try {
            Document document = getBuilder().parse(file);
            Element rootElement = document.getDocumentElement();
            NodeList modules = rootElement.getElementsByTagName("module");
            Element element;
            Module module;
            for (int i=0; i<modules.getLength(); i++) {

                element = (Element) modules.item(i);
                long id = NumberUtils.toLong(element.getAttribute("id"));
                String source = element.getAttribute("source");
                String entry = element.getAttribute("entry");
                String[] args = element.getAttribute("args").split(",");
                String route = element.getAttribute("route");

                module = new Module();
                module.setId(id);
                module.setSource(source);
                module.setEntry(entry);
                module.setArgs(args);
                module.setRouter(new Router(route));

                list.add(module);
            }

        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public static Map<Long, Token> getAllTokens(String path) {

        File file = new File(path);
        Map<Long, Token> tokens = new TreeMap<Long, Token>();

        try {
            Document document = getBuilder().parse(file);
            Element rootElement = document.getDocumentElement();
            NodeList tokenList = rootElement.getElementsByTagName("token");
            Element element;
            Token token;
            for (int i=0; i<tokenList.getLength(); i++) {
                element = (Element) tokenList.item(i);
                long id = NumberUtils.toLong(element.getAttribute("id"));
                String name = element.getAttribute("name");
                String type = element.getAttribute("type");
                String terms = element.getAttribute("terms");

                token = new Token(id, name, type, terms);
                tokens.put(id, token);
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokens;
    }


}
