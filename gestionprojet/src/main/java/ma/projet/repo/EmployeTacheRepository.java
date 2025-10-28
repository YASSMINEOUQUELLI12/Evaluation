package ma.projet.repo;



import ma.projet.classes.EmployeTache;
import ma.projet.classes.EmployeTacheId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeTacheRepository extends JpaRepository<EmployeTache, EmployeTacheId> {
    // كل المهام المُنجزة لمشروع مُعيّن، مرتّبة حسب تاريخ البداية الحقيقي
    List<EmployeTache> findByTache_Projet_IdOrderByDateDebutReelleAsc(Integer projetId);
}

