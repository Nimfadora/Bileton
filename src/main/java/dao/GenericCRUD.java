package dao;

import java.util.List;

public interface GenericCRUD <T, K> {
    public void create(T entity);
    public T read(K key);
    public void update(T entity);
    public void delete(K key);

    public List<T> getAll();
}
