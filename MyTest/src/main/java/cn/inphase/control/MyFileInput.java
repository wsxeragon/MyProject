package cn.inphase.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyFileInput {

    static ExecutorService executor = Executors.newFixedThreadPool(10);
    static LinkedBlockingQueue<ABC> queue = new LinkedBlockingQueue<>();

    static List<ABC> list = new LinkedList<>();

    public static void read(List<File> files) {
        BufferedReader br = null;
        try {
            for (int i = 0; i < files.size(); i++) {
                if (files.get(i).isFile()) {
                    InputStreamReader reader = new InputStreamReader(new FileInputStream(files.get(i)));
                    br = new BufferedReader(reader);
                    String line = "";
                    String string = "";
                    while ((line = br.readLine()) != null) {
                        string += line;
                    }
                    String[] strs = string.split("，");
                    ABC abc = new ABC(strs[0], strs[1], Float.parseFloat(strs[2]));
                    queue.add(abc);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sort() {
        Map<String, List<ABC>> map = new HashMap<>();
        int count = 0;
        while (true) {
            try {
                Thread.sleep(500);
                ABC abc = queue.poll(1000, TimeUnit.MICROSECONDS);
                if (map.get(abc.getGroupId()) == null) {
                    List<ABC> list = new LinkedList<>();
                    list.add(abc);
                    map.put(abc.getGroupId(), list);
                } else {
                    List<ABC> list = map.get(abc.getGroupId());
                    list.add(abc);
                    Collections.sort(list, new Comparator<ABC>() {

                        @Override
                        public int compare(ABC o1, ABC o2) {
                            if (o1.getQuota() - o2.getQuota() >= 0)
                                return 1;
                            else
                                return -1;
                        }
                    });

                    map.put(abc.getGroupId(), list);
                }
                count++;
                System.out.println(count);
                if (queue.size() == 0) {
                    for (String k : map.keySet()) {
                        System.out.println(map.get(k).get(0));
                    }
                    executor.shutdown();
                    break;
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        File file = new File("C:\\Users\\WSX\\Desktop\\test");
        File[] files = file.listFiles();
        int a = files.length / 10;
        for (int i = 0; i < 8; i++) {
            List<File> list = Arrays.asList(files).subList(0 + i * a, a * (1 + i));
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    read(list);
                }
            });
        }
        List<File> list = Arrays.asList(files).subList(8 * a, files.length);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                read(list);
            }
        });

        executor.execute(new Runnable() {
            @Override
            public void run() {
                sort();
            }
        });

    }
}

class ABC {
    private String id;
    private String groupId;
    private float quota;

    public ABC() {
    }

    public ABC(String id, String groupId, float quota) {
        super();
        this.id = id;
        this.groupId = groupId;
        this.quota = quota;
    }

    public String getGroupId() {
        return groupId;
    }

    public float getQuota() {
        return quota;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setQuota(float quota) {
        this.quota = quota;
    }

    @Override
    public String toString() {
        return "ABC [id=" + id + ", groupId=" + groupId + ", quota=" + quota + "]";
    }

}
