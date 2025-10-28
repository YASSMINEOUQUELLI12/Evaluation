package ma.projet.service;



import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractService<T> implements IDao<T> {

    protected void inTransaction(Consumer<Session> work) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            work.accept(session);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    protected abstract Class<T> entityClass();

    @Override public boolean create(T o) {
        inTransaction(s -> s.persist(o)); return true;
    }
    @Override public T getById(Integer id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(entityClass(), id);
        }
    }
    @Override public boolean update(T o) { inTransaction(s -> s.merge(o)); return true; }
    @Override public boolean delete(T o) { inTransaction(s -> s.remove(s.merge(o))); return true; }
    @Override public List<T> getAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from " + entityClass().getSimpleName(), entityClass()).getResultList();
        }
    }
}
