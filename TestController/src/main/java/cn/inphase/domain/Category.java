package cn.inphase.domain;

import java.util.List;

import com.google.gson.annotations.Expose;

public class Category {
	@Expose(deserialize = true, serialize = true) // 序列化和反序列化都都生效
	public int id;
	@Expose(deserialize = true, serialize = true) // 序列化和反序列化都都生效
	public String name;
	@Expose(deserialize = true, serialize = true) // 序列化和反序列化都都生效
	public List<Category> children;
	// 因业务需要增加，但并不需要序列化
	@Expose(deserialize = true, serialize = false) // 不进行序列化，要进行反序列化
	public Category parent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", children=" + children + ", parent=" + parent + "]";
	}

}
