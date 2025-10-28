package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeService implements IDao<Employe, Integer> {

    @Override
    public Integer create(Employe e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Integer id = (Integer) s.save(e);
        tx.commit(); s.close();
        return id;
    }

    @Override
    public void update(Employe e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.merge(e);
        tx.commit(); s.close();
    }

    @Override
    public void delete(Employe e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.remove(s.merge(e));
        tx.commit(); s.close();
    }

    @Override
    public Employe findById(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Employe e = s.get(Employe.class, id);
        s.close(); return e;
    }

    @Override
    public List<Employe> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Employe> l = s.createQuery("from Employe", Employe.class).list();
        s.close(); return l;
    }

    // --- méthodes demandées ---
    public List<Tache> getTachesRealiseesParEmploye(Integer empId){
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Tache> l = s.createQuery(
                        "select et.tache from EmployeTache et where et.employe.id=:eid", Tache.class)
                .setParameter("eid", empId).list();
        s.close(); return l;
    }

    public List<Projet> getProjetsGeresParEmploye(Integer empId){
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Projet> l = s.createQuery(
                        "from Projet p where p.chefProjet.id=:eid", Projet.class)
                .setParameter("eid", empId).list();
        s.close(); return l;
    }
}

