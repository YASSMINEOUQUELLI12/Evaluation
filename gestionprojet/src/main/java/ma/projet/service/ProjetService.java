package ma.projet.service;



import ma.projet.dao.IDao;
import ma.projet.classes.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProjetService implements IDao<Projet, Integer> {

    @Override public Integer create(Projet p){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Integer id = (Integer) s.save(p);
        tx.commit(); s.close(); return id;
    }
    @Override public void update(Projet p){ Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.merge(p); tx.commit(); s.close(); }
    @Override public void delete(Projet p){ Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.remove(s.merge(p)); tx.commit(); s.close(); }
    @Override public Projet findById(Integer id){ Session s=HibernateUtil.getSessionFactory().openSession();
        Projet p=s.get(Projet.class,id); s.close(); return p; }
    @Override public List<Projet> findAll(){ Session s=HibernateUtil.getSessionFactory().openSession();
        List<Projet> l = s.createQuery("from Projet", Projet.class).list(); s.close(); return l; }

    /* --- méthodes demandées --- */
    // 1) Liste des tâches planifiées pour un projet:
    public List<Tache> getTachesPlanifiees(Integer projetId){
        Session s=HibernateUtil.getSessionFactory().openSession();
        List<Tache> l = s.createQuery(
                        "select t from Tache t where t.projet.id=:pid order by t.dateDebut", Tache.class)
                .setParameter("pid", projetId).list();
        s.close(); return l;
    }

    // 2) Liste des tâches réalisées (avec dates réelles):
    public List<EmployeTache> getTachesRealiseesAvecDates(Integer projetId){
        Session s=HibernateUtil.getSessionFactory().openSession();
        List<EmployeTache> l = s.createQuery(
                        "select et from EmployeTache et where et.tache.projet.id=:pid " +
                                "order by et.dateDebutReelle", EmployeTache.class)
                .setParameter("pid", projetId).list();
        s.close(); return l;
    }
}
