package ma.projet.classes;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ligne_commande_produit")
public class LigneCommandeProduit implements Serializable {

    @EmbeddedId
    private LigneCommandeId id = new LigneCommandeId();

    @ManyToOne(optional = false)
    @MapsId("commandeId") // Lie la clé composite
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne(optional = false)
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @Column(nullable = false)
    private int quantite;

    public LigneCommandeProduit() {}

    public LigneCommandeProduit(Commande commande, Produit produit, int quantite) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;

        if (commande != null && produit != null) {
            this.id = new LigneCommandeId(commande.getId(), produit.getId());
        }
    }

    @PostPersist
    @PostLoad
    private void majId() {
        if (commande != null && produit != null) {
            this.id = new LigneCommandeId(commande.getId(), produit.getId());
        }
    }

    // === Getters & Setters ===
    public LigneCommandeId getId() {
        return id;
    }

    public void setId(LigneCommandeId id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "LigneCommandeProduit{" +
                "commande=" + commande.getId() +
                ", produit=" + produit.getReference() +
                ", quantite=" + quantite +
                '}';
    }
}

