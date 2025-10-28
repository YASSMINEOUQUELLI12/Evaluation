package ma.projet.classes;



import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@NamedQueries({
        @NamedQuery(name="Tache.prixSup",
                query="SELECT t FROM Tache t WHERE t.prix > :min ORDER BY t.prix DESC")
})
@Entity
@Table(name="tache")
public class Tache {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double prix;

    @ManyToOne(optional=false)
    @JoinColumn(name="projet_id")
    private Projet projet;

    @OneToMany(mappedBy="tache")
    private List<EmployeTache> affectations = new ArrayList<>();

    public Tache() {}
    public Tache(String nom, LocalDate d1, LocalDate d2, double prix, Projet p){
        this.nom=nom; this.dateDebut=d1; this.dateFin=d2; this.prix=prix; this.projet=p;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    public LocalDate getDateFin() {
        return dateFin;
    }
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }


    // getters/setters
}


