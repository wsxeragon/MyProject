package cn.inphase.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import cn.inphase.domain.Category;
import cn.inphase.domain.User;

public class GsonTest {

    @Test
    public void test1() {
        // 使用别名 id ID Id
        String json = "{\"Id\":\"007\",\"age\":\"24\"}";
        User cus = new Gson().fromJson(json, new TypeToken<User>() {
        }.getType());
        System.out.println(cus);

        json = "{\"ID\":\"007\",\"age\":\"24\"}";
        cus = new Gson().fromJson(json, new TypeToken<User>() {
        }.getType());
        System.out.println(cus);
        // 导出null值
        User user1 = new User("001", null, 19, new Date());
        Gson g0 = new Gson();
        System.out.println(g0.toJson(user1));

        Gson g1 = new GsonBuilder().serializeNulls().create();
        System.out.println(g1.toJson(user1));

        // 格式化输出、日期时间及其它
        Gson g2 = new GsonBuilder()
                // 序列化null
                .serializeNulls()
                // 设置日期时间格式，另有2个重载方法
                // 在序列化和反序化时均生效
                .setDateFormat("yyyy-MM-dd")
                // 禁此序列化内部类
                .disableInnerClassSerialization()
                // 生成不可执行的Json（多了 )]}' 这4个字符）
                .generateNonExecutableJson()
                // 禁止转义html标签
                .disableHtmlEscaping()
                // 格式化输出
                .setPrettyPrinting().create();
        System.out.println(g2.toJson(user1));
        user1 = new Gson().fromJson("{\"id\": \"001\",\"account\": null,\"age\": 19,\"birthday\": \"1997-10-09\"}",
                new TypeToken<User>() {
                }.getType());
        System.out.println(user1);
    }

    @Test
    public void test2() throws IOException {
        Category c1 = new Category();
        c1.setId(001);
        c1.setName("栏目1");

        Category c2 = new Category();
        c2.setId(002);
        c2.setName("栏目2");
        List<Category> categories = new ArrayList<>();
        categories.add(c1);
        categories.add(c2);

        Category c0 = new Category();
        c0.setId(000);
        c0.setName("根栏目");
        c0.setChildren(categories);

        c1.setParent(c0);
        c2.setParent(c0);
        // 排除部分不需要序列化的字段
        Gson g1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        System.out.println(g1.toJson(c0));
        System.out.println(GsonTest.class.getClassLoader().getResource("").getPath() + "test.json");
        File file = new File(GsonTest.class.getClassLoader().getResource("").getPath() + "test.json");
        if (!file.exists()) {
            file.createNewFile();
        }
        // 写文件
        OutputStream out = new FileOutputStream(file);
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));// 设置编码
        g1.toJson(c0, Category.class, writer);// 把值写进去
        writer.flush();
        writer.close();

        // 读文件
        InputStream input = new FileInputStream(file);
        JsonReader reader = new JsonReader(new InputStreamReader(input));
        Category val = g1.fromJson(reader, Category.class);
        System.out.println(val);
        reader.close();

    }

    @Test
    public void test3() {
        Gson gson = new GsonBuilder()
                // 为User注册TypeAdapter
                .registerTypeAdapter(User.class, new UserTypeAdapter()).create();
        User user = new User("002", "mona", 16, new Date());
        System.out.println(gson.toJson(user));
        user = gson.fromJson("{\"ID\":\"002\",\"account\":\"mona\",\"age\":16,\"birthday\":\"2017-10-10\"}\r",
                new TypeToken<User>() {
                }.getType());
        System.out.println(user);

        // 自定义数字序列化为字符串
        JsonSerializer<Number> numberJsonSerializer = new JsonSerializer<Number>() {
            @Override
            public JsonElement serialize(Number src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(String.valueOf(src));
            }
        };

        // 自定义特定map中的int型反序列化为String
        JsonDeserializer<Map<String, String>> IntJsonDeserializer = new JsonDeserializer<Map<String, String>>() {
            @Override
            public Map<String, String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    JsonObject object = json.getAsJsonObject();
                    Map<String, String> map = new HashMap<>();
                    map.put("id", object.get("id").getAsString());
                    return map;
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        };

        Gson g1 = new GsonBuilder().registerTypeAdapter(Integer.class, numberJsonSerializer)
                .registerTypeAdapter(Long.class, numberJsonSerializer)
                .registerTypeAdapter(Float.class, numberJsonSerializer)
                .registerTypeAdapter(Double.class, numberJsonSerializer)
                .registerTypeAdapter(Map.class, IntJsonDeserializer).create();
        System.out.println(g1.toJson(1000000000000L));

        Map<String, String> map1 = new Gson().fromJson("{\"id\":100000000}", Map.class);
        System.out.println(map1);

        Map<String, String> map2 = g1.fromJson("{\"id\":100000000}", Map.class);
        System.out.println(map2);
    }

}
