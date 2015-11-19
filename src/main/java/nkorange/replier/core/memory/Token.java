package nkorange.replier.core.memory;

import org.apache.commons.collections.CollectionUtils;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author pengfei.zhu.
 */
public class Token {

    private long id;
    private String name;
    private String type;
    private Set<String> terms;

    public Token() {
    }

    public Token(long id, String name, String type, String terms) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.terms = new TreeSet<String>();
        CollectionUtils.addAll(this.terms, terms.split(","));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getTerms() {
        return terms;
    }

    public void setTerms(Set<String> terms) {
        this.terms = terms;
    }
}
