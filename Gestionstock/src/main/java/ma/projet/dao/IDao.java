package ma.projet.dao;



import java.util.List;

public interface IDao<T, ID> {
    T create(T entity);
    T update(T entity);
    void delete(T entity);
    T findById(ID id);
    List<T> findAll();
}

