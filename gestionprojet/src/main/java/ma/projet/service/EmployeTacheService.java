package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache, EmployeTacheId> {
    @Override public EmployeTacheId create(EmployeTache et){
        Session s= HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction();
        EmployeTacheId id = (EmployeTacheId) s.save(et);
        tx.commit(); s.close(); return id;
    }
    @Override public void update(EmployeTache et){ Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.merge(et); tx.commit(); s.close(); }
    @Override public void delete(EmployeTache et){ Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.remove(s.merge(et)); tx.commit(); s.close(); }
    @Override public EmployeTache findById(EmployeTacheId id){ Session s=HibernateUtil.getSessionFactory().openSession();
        EmployeTache et = s.get(EmployeTache.class,id); s.close(); return et; }
    @Override public List<EmployeTache> findAll(){ Session s=HibernateUtil.getSessionFactory().openSession();
        List<EmployeTache> l=s.createQuery("from EmployeTache", EmployeTache.class).list(); s.close(); return l; }
}
