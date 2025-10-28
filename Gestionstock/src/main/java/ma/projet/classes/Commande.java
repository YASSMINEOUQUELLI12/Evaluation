package ma.projet.classes;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_commande", nullable = false)
    private LocalDate date = LocalDate.now();

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommandeProduit> lignes = new ArrayList<>();

    // === Constructeurs ===
    public Commande() {}

    public Commande(LocalDate date) {
        this.date = date;
    }

    // === Getters & Setters ===
    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<LigneCommandeProduit> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommandeProduit> lignes) {
        this.lignes = lignes;
    }

    // === toString ===
    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}



