package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ma.projet.classes.*;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = build();

    private static SessionFactory build() {
        try {
            Configuration cfg = new Configuration().configure(); // if hibernate.cfg.xml else use props
            cfg.addAnnotatedClass(Employe.class);
            cfg.addAnnotatedClass(Projet.class);
            cfg.addAnnotatedClass(Tache.class);
            cfg.addAnnotatedClass(EmployeTache.class);
            cfg.addAnnotatedClass(EmployeTacheId.class);
            return cfg.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("SessionFactory init error", e);
        }
    }
    public static SessionFactory getSessionFactory(){ return sessionFactory; }
}

