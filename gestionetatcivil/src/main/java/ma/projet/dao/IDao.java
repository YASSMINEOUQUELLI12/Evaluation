package ma.projet.dao;



import java.util.List;

public interface IDao<T> {
    boolean create(T o);
    T getById(Integer id);
    boolean update(T o);
    boolean delete(T o);
    List<T> getAll();
}

