package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import jakarta.persistence.criteria.*;
import java.time.LocalDate;

public class MariageService extends AbstractService<Mariage> {
    @Override protected Class<Mariage> entityClass() { return Mariage.class; }

    /** Criteria API : nombre d’hommes mariés à exactement 4 femmes entre deux dates */
    public long countHommesAvecQuatreEpousesEntre(LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);

            Root<Mariage> m = cq.from(Mariage.class);
            Path<Homme> homme = m.get("homme");

            // chevauchement de période
            Predicate overlap = cb.and(
                    cb.lessThanOrEqualTo(m.get("dateDebut"), d2),
                    cb.or(cb.isNull(m.get("dateFin")), cb.greaterThanOrEqualTo(m.get("dateFin"), d1))
            );

            cq.select(cb.countDistinct(homme))
                    .where(overlap)
                    .groupBy(homme)
                    .having(cb.equal(cb.countDistinct(m.get("femme")), 4L));

            return s.createQuery(cq).getResultStream().findFirst().orElse(0L);
        }
    }
}
