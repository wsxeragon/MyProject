package cn.inphase.control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class TestInputStream {
    @Test
    public void test() throws IOException {
        File file = new File("F:/upload/test.txt");
        FileInputStream fis = new FileInputStream("F:/upload/test.txt");
        BufferedInputStream bis = new BufferedInputStream(fis);

        FileOutputStream fos = new FileOutputStream("F:/upload/t.txt");

        byte[] bs = new byte[1024];
        int len = 0;
        while ((len = fis.read(bs)) != -1) {
            fos.write(bs, 0, len);
        }
    }
}
