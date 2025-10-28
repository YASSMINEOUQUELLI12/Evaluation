package ma.projet.service;



import ma.projet.classes.Produit;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class ProduitService extends AbstractService<Produit, Integer> {

    public ProduitService() {
        super(Produit.class);
    }

    /** Liste des produits d'une catégorie donnée */
    public List<Produit> findByCategorie(Integer categorieId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                            "from Produit p where p.categorie.id = :cid",
                            Produit.class
                    ).setParameter("cid", categorieId)
                    .getResultList();
        }
    }

    /** Produits commandés entre deux dates (distinct pour éviter les doublons) */
    public List<Produit> findCommandesBetween(LocalDate dateDebut, LocalDate dateFin) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                            "select distinct l.produit " +
                                    "from LigneCommandeProduit l " +
                                    "where l.commande.date between :d1 and :d2",
                            Produit.class
                    ).setParameter("d1", dateDebut)
                    .setParameter("d2", dateFin)
                    .getResultList();
        }
    }

    /** Produits d'une commande donnée */
    public List<Produit> findByCommande(Integer commandeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                            "select l.produit " +
                                    "from LigneCommandeProduit l " +
                                    "where l.commande.id = :cmdId",
                            Produit.class
                    ).setParameter("cmdId", commandeId)
                    .getResultList();
        }
    }
}

