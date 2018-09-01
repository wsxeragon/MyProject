package cn.inphase.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class MyReader {
    @SuppressWarnings("unchecked")
    public void clean(final Object buffer) throws Exception {
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                try {
                    Method getCleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);
                    getCleanerMethod.setAccessible(true);
                    sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(buffer, new Object[0]);
                    cleaner.clean();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    // MappedByteBuffer 使用内存文件映射，读取速度会快很多
    public int fieRead(String path, String str0) throws IOException {
        int count = 0;
        FileInputStream fis = null;
        FileChannel inChannel = null;
        InputStreamReader inp = null;
        int buffer_size = 5 * 1024 * 1024;
        try {
            File file = new File(path);
            fis = new FileInputStream(file);
            inChannel = fis.getChannel();
            long fileLength = file.length();
            // 构建一个只读的MappedByteBuffer
            MappedByteBuffer mappedByteBuffer = inChannel.map(MapMode.READ_ONLY, 0, fileLength);
            byte[] dst = new byte[buffer_size];// 每次读出指定大小的内容

            for (int offset = 0; offset < fileLength; offset += buffer_size) {
                if (fileLength - offset >= buffer_size) {
                    for (int i = 0; i < buffer_size; i++)
                        dst[i] = mappedByteBuffer.get(offset + i);
                } else {
                    for (int i = 0; i < fileLength - offset; i++)
                        dst[i] = mappedByteBuffer.get(offset + i);
                }

                String str = new String(dst, "utf-8");
                count += getCount(str, str0);
            }
            inChannel.close();
            // 删除源文件之前需要clean
            clean(mappedByteBuffer);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            inChannel.close();
        }
        return count;
    }

    public int getCount(String str, String str0) {
        int count = 0;
        int index = -1;
        while ((index = str.indexOf(str0)) != -1) {
            count++;
            str = str.substring(index + str0.length(), str.length());
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        MyReader myReader = new MyReader();
        System.out.println(myReader.fieRead("C:\\Users\\WSX\\Desktop\\123.txt", "钱"));
    }
}
