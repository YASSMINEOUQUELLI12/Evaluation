package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class TacheService implements IDao<Tache, Integer> {

    @Override
    public Integer create(Tache t) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Integer id = (Integer) s.save(t);
        tx.commit(); s.close();
        return id;
    }

    @Override
    public void update(Tache t) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.merge(t);
        tx.commit(); s.close();
    }

    @Override
    public void delete(Tache t) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.remove(s.merge(t));
        tx.commit(); s.close();
    }

    @Override
    public Tache findById(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Tache t = s.get(Tache.class, id);
        s.close(); return t;
    }

    @Override
    public List<Tache> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Tache> l = s.createQuery("from Tache", Tache.class).list();
        s.close(); return l;
    }

    // prix > 1000 DH via @NamedQuery("Tache.prixSup")
    public List<Tache> getTachesPrixSup(double min){
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Tache> l = s.createNamedQuery("Tache.prixSup", Tache.class)
                .setParameter("min", min)
                .list();
        s.close(); return l;
    }

    // tâches réalisées entre deux dates (dates réelles)
    public List<EmployeTache> getTachesRealiseesEntre(LocalDate d1, LocalDate d2){
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<EmployeTache> l = s.createQuery(
                        "from EmployeTache et where et.dateDebutReelle >= :d1 and et.dateFinReelle <= :d2",
                        EmployeTache.class)
                .setParameter("d1", d1).setParameter("d2", d2).list();
        s.close(); return l;
    }
}
