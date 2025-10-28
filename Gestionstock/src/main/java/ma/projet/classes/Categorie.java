package ma.projet.classes;


import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "categorie")
public class Categorie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, unique=true, length=50)
    private String code;

    @Column(nullable=false, length=100)
    private String libelle;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produit> produits = new ArrayList<>();

    public Categorie() {}
    public Categorie(String code, String libelle){ this.code=code; this.libelle=libelle; }

    public String getNom() {
        return code;
    }
    // getters/setters...
}






