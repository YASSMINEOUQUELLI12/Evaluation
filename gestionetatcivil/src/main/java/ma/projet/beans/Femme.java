package ma.projet.beans;

import jakarta.persistence.*;


@Entity
@Table(name = "femme")
@NamedQuery(
        name = "Femme.marieeAuMoinsDeuxFois",
        query = """
        select f from Femme f
        where (select count(m) from Mariage m where m.femme = f) >= 2
        """
)
public class Femme extends Personne { }

