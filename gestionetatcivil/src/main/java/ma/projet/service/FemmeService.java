package ma.projet.service;



import ma.projet.beans.Femme;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class FemmeService extends AbstractService<Femme> {
    @Override protected Class<Femme> entityClass() { return Femme.class; }

    /** Native named query : somme des enfants d’une femme entre deux dates */
    public long nombreEnfantsEntre(Integer femmeId, LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Object res = s.createNativeQuery("/*@Femme.nbrEnfantsEntreDeuxDates*/")
                    .setParameter("femmeId", femmeId)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getSingleResult();
            return ((Number) res).longValue();
        }
    }

    /** Femmes mariées au moins deux fois (NamedQuery) */
    public List<Femme> marieesAuMoinsDeuxFois() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Femme.marieeAuMoinsDeuxFois", Femme.class).getResultList();
        }
    }
}

