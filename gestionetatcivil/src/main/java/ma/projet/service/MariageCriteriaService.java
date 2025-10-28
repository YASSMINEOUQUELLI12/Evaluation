package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MariageCriteriaService {

    @PersistenceContext
    private EntityManager em;

    /** Nombre d’hommes mariés à exactement 4 femmes entre deux dates (Criteria API) */
    public long countHommesAvecQuatreEpousesEntre(LocalDate d1, LocalDate d2) {
        var cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);

        Root<Mariage> m = cq.from(Mariage.class);
        Path<Homme> homme = m.get("homme");

        var overlap = cb.and(
                cb.lessThanOrEqualTo(m.get("dateDebut"), d2),
                cb.or(cb.isNull(m.get("dateFin")), cb.greaterThanOrEqualTo(m.get("dateFin"), d1))
        );

        cq.select(cb.countDistinct(homme))
                .where(overlap)
                .groupBy(homme)
                .having(cb.equal(cb.countDistinct(m.get("femme")), 4L));

        var res = em.createQuery(cq).getResultList();
        return res.stream().findFirst().orElse(0L);
    }
}

