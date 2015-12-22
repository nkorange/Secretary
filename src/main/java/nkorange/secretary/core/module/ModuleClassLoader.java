package nkorange.secretary.core.module;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengfei.zhu.
 */
public class ModuleClassLoader extends URLClassLoader {

    private static final String modulePath = "";

    private List<JarURLConnection> cachedJarFiles = new ArrayList<JarURLConnection>();

    public ModuleClassLoader() {
        super(new URL[]{}, findParentClassLoader());
    }

    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        Class clazz = findParentClassLoader().loadClass(name);
        if (clazz == null) {
            clazz = findClass(name);
        }
        if (resolve) {
            resolveClass(clazz);
        }
        return clazz;
    }

    /**
     * Get module path
     *
     * @return
     */
    private static URL[] getMyURLs() {
        URL url = null;
        try {
            url = new File(modulePath).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new URL[]{url};
    }

    public void addModule(String path) {

        try {
            URL url = new URL(path);
            URLConnection uc = url.openConnection();
            if (uc instanceof JarURLConnection) {
                uc.setUseCaches(true);
                ((JarURLConnection) uc).getManifest();
                cachedJarFiles.add((JarURLConnection) uc);
            }

            addURL(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ClassLoader findParentClassLoader() {
        ClassLoader parent = ModuleManager.class.getClassLoader();
        if (parent == null) {
            parent = ModuleClassLoader.class.getClassLoader();
        }
        if (parent == null) {
            parent = ClassLoader.getSystemClassLoader();
        }
        return parent;
    }
}
