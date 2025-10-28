package ma.projet.dao;



import java.util.List;

public interface IDao<T, ID> {
    ID create(T o);
    void update(T o);
    void delete(T o);
    T findById(ID id);
    List<T> findAll();
}

