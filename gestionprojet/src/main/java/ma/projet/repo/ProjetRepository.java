package ma.projet.repo;

import ma.projet.classes.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjetRepository extends JpaRepository<Projet,Integer> {
    List<Projet> findByChefProjet_Id(Integer empId);           // projets gérés par un employé


    Optional<Projet> findByNom(String gestionDeStock);
}
