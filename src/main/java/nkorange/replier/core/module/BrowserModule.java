package nkorange.replier.core.module;

import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;
import nkorange.replier.exceptions.SysInitException;

import java.lang.reflect.Method;

/**
 * @author pengfei.zhu.
 */
public class BrowserModule {

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

    public void openGoogleAndSearch(String[] keywords) {
        try {
            browse("https://www.google.com.hk/?gws_rd=ssl#newwindow=1&safe=strict&q=" + keywords[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void browse(String url) throws Exception {

        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                java.net.URI uri = java.net.URI.create(url);
                java.awt.Desktop dp = java.awt.Desktop.getDesktop() ;
                if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                    dp.browse( uri ) ;
                }
            } catch (Exception e) {
                e.printStackTrace() ;
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
