package ma.projet.classes;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employe_tache")
public class EmployeTache {

    @EmbeddedId
    private EmployeTacheId id = new EmployeTacheId();

    @ManyToOne @MapsId("employeId") @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne @MapsId("tacheId") @JoinColumn(name = "tache_id")
    private Tache tache;

    // ⚠️ les noms doivent correspondre exactement à tes getters
    private LocalDate dateDebutReelle;
    private LocalDate dateFinReelle;

    public EmployeTache() {}

    public EmployeTache(Employe employe, Tache tache, LocalDate dateDebutReelle, LocalDate dateFinReelle) {
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
        this.id = new EmployeTacheId(employe.getId(), tache.getId());
    }


    public EmployeTacheId getId() { return id; }
    public void setId(EmployeTacheId id) { this.id = id; }

    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }

    public Tache getTache() { return tache; }
    public void setTache(Tache tache) { this.tache = tache; }

    // ✅ les getters que ton code appelle
    public LocalDate getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(LocalDate d) { this.dateDebutReelle = d; }

    public LocalDate getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(LocalDate d) { this.dateFinReelle = d; }
}





    // getters/setters


