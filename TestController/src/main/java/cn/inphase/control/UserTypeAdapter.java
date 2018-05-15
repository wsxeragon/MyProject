package cn.inphase.control;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import cn.inphase.domain.User;

public class UserTypeAdapter extends TypeAdapter<User> {
	SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");

	@Override
	public void write(JsonWriter out, User value) throws IOException {
		out.beginObject();
		out.name("id").value(value.getId());
		out.name("account").value(value.getAccount());
		out.name("age").value(value.getAge());
		out.name("birthday").value(format.format(value.getBirthday()));
		out.endObject();

	}

	@Override
	public User read(JsonReader in) throws IOException {
		User user = new User();
		in.beginObject();
		while (in.hasNext()) {
			switch (in.nextName()) {
			case "Id":
			case "ID":
			case "id":
				user.setId(in.nextString());
				break;
			case "age":
				user.setAge(in.nextInt());
				break;
			case "account":
				user.setAccount(in.nextString());
				break;
			case "birthday":
				Date date = null;
				try {
					date = format.parse(in.nextString());
					user.setBirthday(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					user.setBirthday(null);
				}
				break;
			}
		}
		in.endObject();
		return user;
	}

}
