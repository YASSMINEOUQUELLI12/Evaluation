package ma.projet.classes;



import ch.qos.logback.core.util.CachingDateFormatter;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name="projet")
public class Projet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @ManyToOne
    private Employe chefProjet;

    public Projet() {}

    public Projet(String nom, LocalDate dateDebut, LocalDate dateFin, Employe chefProjet) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.chefProjet = chefProjet;
    }

    public Integer getId() { return id; }
    public String getNom() { return nom; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public Employe getChefProjet() { return chefProjet; }
}
