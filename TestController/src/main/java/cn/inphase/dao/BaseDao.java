package cn.inphase.dao;

public interface BaseDao<T> {

	public int insert(T entity);

	public int delete(T entity);

	public int update(T entity);

	public T select(int id);

}
