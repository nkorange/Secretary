package nkorange.replier.core.module;

import java.net.URLEncoder;

/**
 * @author pengfei.zhu.
 */
public class BrowserModule extends SustainableModule implements CradleModule {


    public BrowserModule() {
    }

    public void openBrowser() {
        try {
            browse("http:about:blank");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openGoogle() {
        try {
            browse("http://www.google.com.hk");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openGoogleAndSearch() {
        try {
            setEnd(false);
            String keywords = inputs.take();
            System.out.println("--------------" + keywords);
            browse("https://www.google.com.hk/?gws_rd=ssl#newwindow=1&safe=strict&q=" + URLEncoder.encode(keywords,
                    "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setEnd(true);
    }

    private void browse(String url) throws Exception {

        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                java.net.URI uri = java.net.URI.create(url);
                java.awt.Desktop dp = java.awt.Desktop.getDesktop();
                if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                    dp.browse(uri);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void report(String msg) {

    }

    public static void main(String[] args) throws Exception {

        BrowserModule module = new BrowserModule();
        module.openBrowser();
    }
}
