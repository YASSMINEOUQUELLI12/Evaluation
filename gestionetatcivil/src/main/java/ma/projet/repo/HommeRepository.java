package ma.projet.repo;

import ma.projet.beans.Homme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HommeRepository extends JpaRepository<Homme, Integer> {}

