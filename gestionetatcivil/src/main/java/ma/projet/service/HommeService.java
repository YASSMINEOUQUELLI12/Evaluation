package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class HommeService extends AbstractService<Homme> {
    @Override protected Class<Homme> entityClass() { return Homme.class; }

    /** Épouses d’un homme dont le mariage chevauche [d1,d2] */
    public List<Femme> epousesEntreDates(Integer hommeId, LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("""
               
                """, Femme.class)
                    .setParameter("hid", hommeId)
                    .setParameter("debut", d1)
                    .setParameter("fin", d2)
                    .getResultList();
        }
    }

    /** Détails des mariages d’un homme (tous) */
    public List<Mariage> mariages(Integer hommeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("""
          
             """, Mariage.class)
                    .setParameter("hid", hommeId)
                    .getResultList();
        }
    }
}

