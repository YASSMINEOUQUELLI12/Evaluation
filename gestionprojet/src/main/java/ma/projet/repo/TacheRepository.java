package ma.projet.repo;

import ma.projet.classes.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TacheRepository extends JpaRepository<Tache,Integer> {
    List<Tache> findByProjet_IdOrderByDateDebut(Integer projetId);     // tâches planifiées d’un projet
    List<Tache> findByPrixGreaterThanOrderByPrixDesc(double min);      // prix > 1000
}
