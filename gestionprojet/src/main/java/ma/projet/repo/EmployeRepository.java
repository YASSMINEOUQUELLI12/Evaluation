package ma.projet.repo;

import ma.projet.classes.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeRepository extends JpaRepository<Employe,Integer> {}

