package ma.projet.util;



import ma.projet.classes.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.InputStream;
import java.util.Properties;

public final class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateUtil.class) {
                if (sessionFactory == null) {
                    try (InputStream in = Thread.currentThread()
                            .getContextClassLoader()
                            .getResourceAsStream("application.properties")) {

                        Properties props = new Properties();
                        if (in != null) props.load(in);
                        else throw new RuntimeException("application.properties introuvable");

                        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                                .applySettings(props)
                                .build();

                        sessionFactory = new MetadataSources(registry)
                                .addAnnotatedClass(Categorie.class)
                                .addAnnotatedClass(Produit.class)
                                .addAnnotatedClass(Commande.class)
                                .addAnnotatedClass(LigneCommandeId.class)
                                .addAnnotatedClass(LigneCommandeProduit.class)
                                .buildMetadata()
                                .buildSessionFactory();
                    } catch (Exception e) {
                        throw new RuntimeException("Échec init Hibernate", e);
                    }
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) sessionFactory.close();
    }
}

