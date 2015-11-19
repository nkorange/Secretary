package replier;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.*;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {

    int[] a = new int[10024];
    int count = 0;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream
                ("/Users/admin/Downloads/documents/QuickSort.txt")));

        int i = 0;
        String line = null;
        while ((line = reader.readLine()) != null) {
            a[i++] = Integer.valueOf(line);
        }
        quicksort(0, i - 1);
        System.out.println(count);
        for (int j=0; j<i; j++) {
            System.out.println(a[j]);
        }
    }


    void quicksort(int l, int r) {

        count += (r - l);
        int i, j, x;

        if (l < r) {
            i = l;
            j = r;
            x = a[l];
            while (i < j) {
                while (i < j && a[j] >= x) {
                    j = j - 1;
                }
                if (i < j) {
                    a[i] = a[j];
                    i = i + 1;
                }
                while (i < j && a[i] < x) {
                    i = i + 1;
                }
                if (i < j) {
                    a[j] = a[i];
                    j = j - 1;
                }
            }
            a[i] = x;
            quicksort(l, i - 1);
            quicksort(i + 1, r);
        }
    }
}
