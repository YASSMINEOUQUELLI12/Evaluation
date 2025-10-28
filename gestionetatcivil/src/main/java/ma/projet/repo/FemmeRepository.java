package ma.projet.repo;

import ma.projet.beans.Femme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FemmeRepository extends JpaRepository<Femme, Integer> {

    @Query(name = "Femme.marieeAuMoinsDeuxFois")
    List<Femme> findMarieesAuMoinsDeuxFois();

    // Somme des enfants d’une femme entre deux dates
    @Query(value = """
        SELECT COALESCE(SUM(m.nbr_enfant), 0)
        FROM mariage m
        WHERE m.femme_id = :femmeId
          AND m.date_debut >= :d1
          AND (m.date_fin IS NULL OR m.date_fin <= :d2)
        """, nativeQuery = true)
    Long sumChildrenBetween(@Param("femmeId") Integer femmeId,
                            @Param("d1") LocalDate d1,
                            @Param("d2") LocalDate d2);
}


