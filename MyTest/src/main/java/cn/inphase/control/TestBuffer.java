package cn.inphase.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.junit.Test;

public class TestBuffer {

    @Test
    public void test1() {
        // 如果分配的内存过小，调用Runtime.getRuntime().freeMemory()大小不会变化？
        // 要超过多少内存大小JVM才能感觉到？
        ByteBuffer byteBuffer = ByteBuffer.allocate(102400);
        System.out.println("buffer = " + byteBuffer);
        System.out.println("after alocate:" + Runtime.getRuntime().freeMemory());

        // 这部分直接用的系统内存，所以对JVM的内存没有影响
        ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(102400);
        System.out.println("buffer = " + byteBuffer);
        System.out.println("after allocateDirect:" + Runtime.getRuntime().freeMemory());

        byte[] bytes = new byte[32];
        ByteBuffer byteBuffer3 = ByteBuffer.wrap(bytes);
        System.out.println(byteBuffer3);

        byteBuffer3 = ByteBuffer.wrap(bytes, 10, 10);
        System.out.println(byteBuffer3);
    }

    @Test
    public void test2() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        System.out.println("--------Test reset----------");
        byteBuffer.clear();
        byteBuffer.position(5);
        // 标记位置
        byteBuffer.mark();
        byteBuffer.position(10);
        System.out.println("before reset:" + byteBuffer);
        // 将position重置到mark位置
        byteBuffer.reset();
        System.out.println("after reset:" + byteBuffer);

        System.out.println("--------Test rewind--------");
        byteBuffer.clear();
        byteBuffer.position(10);
        byteBuffer.limit(15);
        System.out.println("before rewind:" + byteBuffer);
        // position重置为0
        byteBuffer.rewind();
        System.out.println("before rewind:" + byteBuffer);

        System.out.println("--------Test flip--------");
        // flip只改变内置属性，不会改变数据
        byteBuffer.clear();
        byteBuffer.position(10);
        byteBuffer.limit(15);
        System.out.println("before flip: " + byteBuffer);
        byteBuffer.flip();
        System.out.println("after flip: " + byteBuffer);
        byteBuffer.clear();
        byteBuffer.put("1234567890abcdefghij".getBytes());
        System.out.println("before flip: " + new String(byteBuffer.array()));
        byteBuffer.flip();
        System.out.println("after flip: " + new String(byteBuffer.array()));

        System.out.println("--------Test get--------");
        System.out.println(byteBuffer);
        System.out.println((char) byteBuffer.get());
        System.out.println((char) byteBuffer.get());
        System.out.println((char) byteBuffer.get());
        byteBuffer.position(12);
        System.out.println((char) byteBuffer.get());
        // 获取绝对index，但不会影响position
        System.out.println((char) byteBuffer.get(2));
        // 每次get之后position都会后移一位
        System.out.println(byteBuffer);

        System.out.println("--------Test compact--------");
        byteBuffer.position(5);
        byteBuffer.limit(15);
        System.out.println("before compact:" + byteBuffer);
        System.out.println("before compact:" + new String(byteBuffer.array()));
        byteBuffer.compact();
        System.out.println("after compact:" + byteBuffer);
        System.out.println("after compact:" + new String(byteBuffer.array()));
    }

    // 使用FileChannel读取文件
    @Test
    public void test3() throws Exception {
        FileInputStream fis = new FileInputStream("C:\\Users\\WSX\\Desktop\\logstash.conf");

        // 将inputstream转到channel
        FileChannel channel = fis.getChannel();

        byte[] bytes = new byte[1024];
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int len;
        while ((len = channel.read(buffer)) != -1) {
            // 注意先调用flip方法反转Buffer,再从Buffer读取数据
            buffer.flip();

            // 第一种方法
            // bytes = buffer.array();
            // System.out.println(new String(bytes));

            // 第二种方法
            buffer.get(bytes, 0, len);
            System.out.println(new String(bytes, 0, len));

            // 第三种方法
            // while (buffer.hasRemaining()) {
            // System.out.print((char) buffer.get());
            // }
            buffer.clear();
        }

        channel.close();
        fis.close();

    }

    // MappedByteBuffer 使用内存文件映射，读取速度会快很多
    @Test
    public void test4() throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("C:\\Users\\WSX\\Desktop\\1.jpg");
            fos = new FileOutputStream("C:\\Users\\WSX\\Desktop\\2.jpg");
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            long size = inChannel.size();
            // 构建一个只读的MappedByteBuffer
            MappedByteBuffer mappedByteBuffer = inChannel.map(MapMode.READ_ONLY, 0, size);
            outChannel.write(mappedByteBuffer);
            inChannel.close();
            outChannel.close();
            // 删除源文件之前需要clean
            clean(mappedByteBuffer);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            inChannel.close();
            outChannel.close();
        }
    }

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
    @Test
    public void fieRead() throws IOException {
        FileInputStream fis = null;
        FileChannel inChannel = null;
        InputStreamReader inp = null;
        int buffer_size = 5 * 1024 * 1024;
        try {
            File file = new File("C:\\Users\\WSX\\Desktop\\123.txt");
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
                System.out.println(str);
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
    }

}
