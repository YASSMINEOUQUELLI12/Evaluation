package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Properties;
import java.util.ResourceBundle;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("application");
            Properties props = new Properties();
            rb.keySet().forEach(k -> props.put(k, rb.getString(k)));

            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(props)
                    .build();

            return new MetadataSources(registry)
                    .addAnnotatedClass(ma.projet.beans.Personne.class)
                    .addAnnotatedClass(ma.projet.beans.Homme.class)
                    .addAnnotatedClass(ma.projet.beans.Femme.class)
                    .addAnnotatedClass(ma.projet.beans.Mariage.class)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Erreur création SessionFactory", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

