package nkorange.secretary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author pengfei.zhu.
 */
public class ScreenReader implements Listener {

    private BufferedReader screenReader = new BufferedReader(new InputStreamReader(System.in));

    public void init() {

    }

    public String read() {
        try {
            return screenReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }
}
