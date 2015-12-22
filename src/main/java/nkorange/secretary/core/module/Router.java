package nkorange.secretary.core.module;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author pengfei.zhu.
 */
public class Router {


    private List<String> exists;

    private Set<String> nonExists;

    private boolean order;

    public Router() {

        exists = new LinkedList<String>();

        nonExists = new TreeSet<String>(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

    }

    public Router(String route) {

        this();
        String[] factors = route.split(";");
        for (String factor : factors) {
            if (factor.contains("exist")) {
                exists = Arrays.asList(factor.substring(6).split(","));
            } else if (factor.contains("absent")) {
                CollectionUtils.addAll(nonExists, factor.substring(7).split(","));
            } else if (factor.contains("order")) {
                order = Integer.valueOf(factor.substring(6)) == 1;
            }
        }
    }

    public boolean match(Condition condition) {

        if (exists.size() != condition.getExists().size() || nonExists.size() != condition
                .getNonExists().size()) {
            return false;
        }

        if (!order) {

            // TODO compare when order of token is irrelevant
        } else {
            int ind = 0;
            for (String token : exists) {

                if (token.contains("|")) {
                    if (!token.contains(condition.getExists().get(ind))) {
                        return false;
                    }
                } else if (!token.equals(condition.getExists().get(ind))) {
                    return false;
                }
                ind++;
            }
        }

        for (String token : nonExists) {
            if (!condition.getNonExists().contains(token)) {
                return false;
            }
        }

        return true;
    }
}
