package ma.projet.repo;

import ma.projet.beans.Femme;
import ma.projet.beans.Mariage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MariageRepository extends JpaRepository<Mariage, Integer> {

    // Mariages d’un homme avec la femme chargée (pour l’affichage final)
    @Query("""
      select m
      from Mariage m
      join fetch m.femme
      where m.homme.id = :hommeId
      order by m.dateDebut
    """)
    List<Mariage> findAllByHommeIdWithFemme(Integer hommeId);

    // Épouses d’un homme dont le mariage chevauche [d1, d2]
    @Query("""
        select distinct m.femme
        from Mariage m
        where m.homme.id = :hommeId
          and m.dateDebut <= :fin
          and (m.dateFin is null or m.dateFin >= :debut)
    """)
    List<Femme> findEpousesEntreDates(Integer hommeId, LocalDate debut, LocalDate fin);
}

